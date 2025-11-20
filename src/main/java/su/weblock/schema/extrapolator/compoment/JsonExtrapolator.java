package su.weblock.schema.extrapolator.compoment;

import su.weblock.schema.extrapolator.Extrapolator;
import su.weblock.schema.extrapolator.ExtrapolatorResult;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonExtrapolator extends JsonCompatibleExtrapolator {



    @Override
    public String getDataType() {
        return "json"; 
    }

    @Override
    protected JsonCompatibleParser makeParser(String s) throws JsonMappingException, JsonProcessingException {
        return new JsonParser(s);
    }
    
}
