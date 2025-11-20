package su.weblock.schema.common.data;


import java.util.UUID;

public class ValueTypeHelper {

    static public boolean isGuid(String v) {
        try{
            UUID.fromString(v);
            return true;
        } catch (IllegalArgumentException exception){
            return false;
        }
    }  
    
    static public boolean isPositiveNumber(String v) {
        try{
            return Integer.parseInt(v) >= 0;
        } catch (IllegalArgumentException exception){
            return false;
        }
    }   
    
    static public boolean isIntNumber(String v) {
        try{
            Integer.parseInt(v);

            return true;
        } catch (IllegalArgumentException exception){
            return false;
        }
    }

    static public boolean isFloatNumber(String v) {
        try{
            Double.parseDouble(v);

            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }    
    
    static public boolean isBoolean(String v) {
        return v.equals("1") || v.equals("0");
    }

    static public boolean isBooleanStr(String v) {
        var vl = v.toLowerCase();
        return vl.equals("true") || vl.equals("false") || vl.equals("f") || vl.equals("t") || vl.equals("yes") || vl.equals("no");
    }    

    static public ValueType estimateTypeOfValue(String v) {
        if (ValueTypeHelper.isGuid(v)) {
            return ValueType.GUID;
        }   else if (ValueTypeHelper.isBooleanStr(v)) {
            return ValueType.BOOLEAN_STR;
        }   else if (ValueTypeHelper.isBoolean(v)) {
            return ValueType.BOOLEAN;            
        }   else if (ValueTypeHelper.isIntNumber(v)) {
            return ValueType.INTEGER;
        }   else if (ValueTypeHelper.isFloatNumber(v)) {
            return ValueType.FLOAT;
        }

        return ValueType.STRING;
    }    
}
