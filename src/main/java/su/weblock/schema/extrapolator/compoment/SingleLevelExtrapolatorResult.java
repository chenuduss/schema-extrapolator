package su.weblock.schema.extrapolator.compoment;

import su.weblock.schema.common.data.ValueType;
import su.weblock.schema.extrapolator.ExtrapolatorResult;
import su.weblock.schema.openapi.exception.IncompatibleWithOpenApiSchema;
import su.weblock.schema.openapi.Converter;
import su.weblock.schema.openapi.OpenApi3CompatibleSchema;

import java.util.Map;

public class SingleLevelExtrapolatorResult implements ExtrapolatorResult {

    final private Map<String, ValueType> valueTypes;

    public SingleLevelExtrapolatorResult(Map<String, ValueType> valueTypes) {
        this.valueTypes = valueTypes;
    }

    @Override
    public String getShortStringDescription() {        
        String result = "";

        for (var e: valueTypes.entrySet()) {            
            result += "\n"+e.getKey() + ": " +e.getValue().toString();
        }

        return result;
    }

    @Override
    public OpenApi3CompatibleSchema getOpenApiCompatibleSchema() throws IncompatibleWithOpenApiSchema {
        if (valueTypes == null) {
            return null;
        }
        if (valueTypes.isEmpty()) {
            return null;
        }
        return Converter.fromSingleLevel(valueTypes);
    }
        
}
