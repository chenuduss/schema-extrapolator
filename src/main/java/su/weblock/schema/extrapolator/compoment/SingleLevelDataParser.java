package su.weblock.schema.extrapolator.compoment;

import java.util.Map;

import su.weblock.schema.common.data.ValueType;

import java.util.HashMap;

public abstract class SingleLevelDataParser {

    protected Map<String, ValueType> args = new HashMap<String, ValueType>();

    protected abstract ValueType getMergedType(ValueType t1, ValueType t2);

    protected void addKeyValueType(String k, ValueType vt) {      

        var vv = args.get(k);        
        if (vv == null) {
            args.put(k, vt);
        }   else if (vv != vt) {
            args.put(k, getMergedType(vv, vt));
        }        
    }
    
    /*
     * if merge fail, original state not changed
     */
    public boolean merge(SingleLevelDataParser o) {        
        Map<String, ValueType> args_backup = args;
        args = new HashMap<String, ValueType>();
        args.putAll(args_backup); // make copy of backup
        for (var k: o.args.keySet()) {
            addKeyValueType(k, o.args.get(k));
        }

        for (var v: args.values()) {
            if (v == ValueType.ERROR) {
                // merge fail, restore args from backup
                args = args_backup;
                return false;
            }
        }

        return true;
    } 
    
    public Map<String, ValueType> getTypes(){
        return args;
    }
}
