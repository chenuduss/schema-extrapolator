package su.weblock.schema.extrapolator.compoment;

import su.weblock.schema.extrapolator.ExtrapolatorResult;
import su.weblock.schema.openapi.exception.IncompatibleWithOpenApiSchema;
import java.util.ArrayList;
import java.util.List;

import su.weblock.schema.openapi.Converter;
import su.weblock.schema.openapi.OpenApi3CompatibleSchema;



public class JsonCompatibleExtrapolatorResult implements ExtrapolatorResult {

    final private JsonCompatibleParser parser;

    public JsonCompatibleExtrapolatorResult(JsonCompatibleParser parser) {
        this.parser = parser;
    }

    protected void addMembers(String prefix, JsonCompatibleParser p, List<String> out) {
        if (p.isNull()) {
            out.add(prefix+"null");
        }   else if (p.isPrimitive()) {
            out.add(prefix+p.getPrimitive().toString());
        }   else if (p.isArray()) {            
            
            for (var item: p.getArray()) {                                
                if (item.isPrimitive() || item.isNull()) {
                    addMembers(prefix+"- ", item, out);
                }   else {
                    out.add(prefix+"- ");
                    addMembers(prefix+"  ", item, out);
                }
            }
        }   else if (p.isObject()) {
            var obj = p.getObject();
            for (var objitem: obj.entrySet()) {
                if (objitem.getValue().isPrimitive() || objitem.getValue().isNull()) {
                    addMembers(prefix+objitem.getKey()+": ", objitem.getValue(), out);
                }   else {
                    out.add(prefix+objitem.getKey()+": ");
                    addMembers(prefix+"  ", objitem.getValue(), out);
                }
            }
        }   else {
            out.add(prefix+"<ERROR>");
        }
    }

    @Override
    public String getShortStringDescription() {
        if (parser == null) {
            return new String();
        }

        var lines = new ArrayList<String>();
        addMembers("", parser, lines);
        String result = "";
        for(var line: lines) {
            if (result.length() > 0) {
                result += "\n";
            }
            result += line; 
        }
        return result;
    }

    @Override
    public OpenApi3CompatibleSchema getOpenApiCompatibleSchema() throws IncompatibleWithOpenApiSchema {
        if (parser == null) {
            return null;
        }
        return Converter.fromJsonCompatible(parser);
    }    
}

