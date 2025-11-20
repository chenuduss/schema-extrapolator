package su.weblock.schema.extrapolator.compoment;

import su.weblock.schema.common.data.ValueType;
import su.weblock.schema.common.data.ValueTypeHelper;



public class UrlEncodedParser extends SingleLevelDataParser {

 
    @Override
    protected ValueType getMergedType(ValueType t1, ValueType t2) {
        if ((t1 == ValueType.INTEGER) && (t2 == ValueType.BOOLEAN)) {
            return ValueType.INTEGER;
        }   else if ((t2 == ValueType.INTEGER) && (t1 == ValueType.BOOLEAN)) {
            return ValueType.INTEGER;
        }   else if ((t2 == ValueType.FLOAT) && (t1 == ValueType.INTEGER)) {
            return ValueType.FLOAT;
        }   else if ((t1 == ValueType.FLOAT) && (t2 == ValueType.INTEGER)) {
            return ValueType.FLOAT;
        }   else if ((t1 == ValueType.FLOAT) && (t2 == ValueType.BOOLEAN)) {
            return ValueType.FLOAT;
        }   else if ((t2 == ValueType.FLOAT) && (t1 == ValueType.BOOLEAN)) {
            return ValueType.FLOAT;
        }   else if ((t1 == ValueType.ERROR) || (t2 == ValueType.ERROR)) {
            return ValueType.ERROR;
        }   else if (t1 == ValueType.UNKNOWN) {
            if (t2 != ValueType.UNKNOWN) {
                return ValueType.ERROR;
            }  
            return ValueType.UNKNOWN;
        }   else if (t2 == ValueType.UNKNOWN) {
            if (t1 != ValueType.UNKNOWN) {
                return ValueType.ERROR;
            }            
            return ValueType.UNKNOWN;                                              
        }   else {
            if (t1 == t2) {
                return t1;
            }
            return ValueType.STRING;
        }
    }

    private void addKeyValue(String k, String v) {
        var vt = ValueTypeHelper.estimateTypeOfValue(v);
        
        addKeyValueType(k, vt);
    }    

    private void addKeyValue(String kv) {
        String[] parts = kv.split("=", 2);
        if (parts.length == 1) {
            var v = args.get(parts[0]);
            if (v == null) {
                args.put(parts[0], ValueType.UNKNOWN);
            }   else if (v != ValueType.UNKNOWN) {
                args.put(parts[0], ValueType.ERROR);
            }
        }   else {
            addKeyValue(parts[0], parts[1]);
        }
    }

    protected void parse(String s) {
        String[] parts = s.split("&");
        for (var p: parts) {
            if (p.length() > 0) {
                addKeyValue(p);
            }
        }
    }

    public UrlEncodedParser(String s) {
        parse(s);
    }
}
