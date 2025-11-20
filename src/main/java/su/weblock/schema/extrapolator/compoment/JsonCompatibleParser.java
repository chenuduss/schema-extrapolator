package su.weblock.schema.extrapolator.compoment;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import su.weblock.schema.common.data.ValueType;

public abstract class JsonCompatibleParser {
    
    // instanceof ValueType  - primitive
    // instanceof Map        - object
    // instanceof List       - array 
    // null                  - unknown type (null value)  
    protected Object members;  

    public boolean isNull(){
        return members == null;
    }
    public boolean isPrimitive() {
        return members instanceof ValueType;
    }
    public ValueType getPrimitive() {
        return (ValueType)members;
    }

    public boolean isArray() {
        return members instanceof List;
    }

    public List<JsonCompatibleParser> getArray() {
        return (List<JsonCompatibleParser>)members;
    }

    public Map<String, JsonCompatibleParser> getObject() {
        return (Map<String, JsonCompatibleParser>)members;
    } 

    public boolean isObject() {
        return members instanceof Map;
    }      

    protected void initializeArray() {
        var l = new ArrayList<JsonCompatibleParser>();
        l.add(createNew());
        members = l;
    }

    protected void initializeObject() {
        members = new HashMap<String, JsonCompatibleParser>();
    }

     
    
    protected boolean tryMergeWithArray(JsonCompatibleParser p){
        var arr = getArray();
        for (var item: arr){
            if (item.merge(p)) {
                return true;
            }
        }        
        return false;
    }

    protected void mergeOrAddToArray(JsonCompatibleParser p){
        if (!tryMergeWithArray(p)) {
            getArray().add(p);
        }
    }    

    protected void walkArray(JsonNode node) {
        initializeArray();
        for (var n: node){
            mergeOrAddToArray(createNew(n));
        }
    }

    protected void walkObject(JsonNode node) {
        initializeObject();
        var mlist = getObject();
        var iter = node.fields();
        for (; iter.hasNext();) {
            var item = iter.next();
            
            mlist.put(item.getKey(), createNew(item.getValue()));
        }
    }    

    protected void walk(JsonNode node) {
        if (node.isArray()) {
            walkArray(node);
        }   else if (node.isObject()) {
            walkObject(node);
        }   else if (node.isBoolean()) {
            members = ValueType.BOOLEAN;
        }   else if (node.isDouble()) {
            members = ValueType.FLOAT;
        }   else if (node.isFloat()) {
            members = ValueType.FLOAT;
        }   else if (node.isNull()) {
            //
        }   else if (node.isBinary()) {
            members = ValueType.FILE;
        }   else if (node.isInt()) {
            members = ValueType.INTEGER;
        }   else if (node.isLong()) {
            members = ValueType.INTEGER;
        }   else if (node.isNumber()) {
            members = ValueType.INTEGER;
        }   else if (node.isShort()) {
            members = ValueType.INTEGER;
        }   else if (node.isTextual()) {
            members = ValueType.STRING;
        }   else {
            members = ValueType.ERROR;
        }        
    }

    abstract protected JsonCompatibleParser createNew();

    protected JsonCompatibleParser createNew(JsonNode node) {        
        var result = createNew();
        result.walk(node);
        return result;        
    }

    protected boolean mergePrimitive(JsonCompatibleParser o) {
        if (!o.isPrimitive()) {
            return false;
        }
        if (getPrimitive() != o.getPrimitive()) {
            return false;
        }

        return true;
    }

    protected boolean mergeArray(JsonCompatibleParser o) {
        if (!o.isArray()) {
            return false;
        }
        for(var item: o.getArray()) {
            mergeOrAddToArray(item);
        }

        return true;
    }

    protected boolean mergeObject(JsonCompatibleParser o) {
        if (!o.isObject()) {
            return false;
        }

        var backup = clone();
        var current = getObject();
        var other = o.getObject();
        for (var item: other.entrySet()) {
            var p = current.get(item.getKey());
            if (p == null) {
                current.put(item.getKey(), item.getValue());
            }   else if (!p.merge(item.getValue())) {
                copyFrom(backup); // restore from backup
                return false;
            }
        }

        return true;
    }  
    
    protected void copyFrom(JsonCompatibleParser o) {
        if (o.isPrimitive()) {
            members = o.members;
        }   else if (o.isArray()) {
            initializeArray();
            for (var p: o.getArray()) {
                getArray().add(p.clone());
            }
        }   else if (o.isObject()) {
            initializeObject();
            for (var p: o.getObject().entrySet()) {
                getObject().put(p.getKey(), p.getValue().clone());
            }            
        }
    }

    public JsonCompatibleParser clone() {
        var result = createNew();
        result.copyFrom(this);
        return result;
    }

    public boolean merge(JsonCompatibleParser o) {
        if (o.isNull()) {
            return true;
        }   else if (isPrimitive()) {
            return mergePrimitive(o);
        }   else if (isArray()) {
            return mergeArray(o);
        }   else if (isObject()) {
            return mergeObject(o);
        }   else if (isNull()) {
            copyFrom(o);
            return true;
        }

        return false;
    }
}
