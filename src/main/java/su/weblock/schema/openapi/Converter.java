package su.weblock.schema.openapi;

import java.util.Map;

import su.weblock.schema.common.data.ValueType;
import su.weblock.schema.extrapolator.compoment.JsonCompatibleParser;
import su.weblock.schema.openapi.exception.IncompatibleWithOpenApiSchema;

public class Converter {

    static OpenApi3CompatibleSchema fromPrimitive(ValueType t) throws IncompatibleWithOpenApiSchema{
        if (t == ValueType.BOOLEAN) {
            return new OpenApi3CompatibleSchema("boolean");
        }   else if (t == ValueType.BOOLEAN_STR) {
            return new OpenApi3CompatibleSchema("string");
        }   else if (t == ValueType.FILE) {
            var result = new OpenApi3CompatibleSchema("string");
            result.format = "binary";
            return result;
        }   else if (t == ValueType.STRING) {
            return new OpenApi3CompatibleSchema("string");
        }   else if (t == ValueType.INTEGER) {
            return new OpenApi3CompatibleSchema("integer");
        }   else if (t == ValueType.FLOAT) {
            return new OpenApi3CompatibleSchema("number");
        }   else if (t == ValueType.GUID) {
            var result = new OpenApi3CompatibleSchema("string");
            result.format = "uuid";
            return result;
        }   

        throw new IncompatibleWithOpenApiSchema();
    }

    static public OpenApi3CompatibleSchema fromSingleLevel(Map<String, ValueType> sourceDescription) throws IncompatibleWithOpenApiSchema {
        var result = new OpenApi3CompatibleSchema("object");

        for (var item: sourceDescription.entrySet()) {
            result.properties.put(item.getKey(), fromPrimitive(item.getValue()));
        }

        return result;
    }

    static public OpenApi3CompatibleSchema fromJsonCompatible(JsonCompatibleParser parser) throws IncompatibleWithOpenApiSchema  {
        if (parser.isPrimitive()) {
            return fromPrimitive(parser.getPrimitive());
        }   else if (parser.isNull()){
            var result = new OpenApi3CompatibleSchema("string");
            result.nullable = true;
            return result;
        }   else if (parser.isArray()) {
            var arr = parser.getArray();
            if (arr.size() != 1) {
                throw new IncompatibleWithOpenApiSchema();
            }
            var result = new OpenApi3CompatibleSchema("array");
            result.items = fromJsonCompatible(arr.get(0));
            return result;
        }   else if (parser.isObject()) {
            var obj = parser.getObject();
            var result = new OpenApi3CompatibleSchema("object");
            for (var item: obj.entrySet()) {
                result.properties.put(item.getKey(), fromJsonCompatible(item.getValue()));
            }
            return result;
        }

        throw new IncompatibleWithOpenApiSchema();
    }
}
