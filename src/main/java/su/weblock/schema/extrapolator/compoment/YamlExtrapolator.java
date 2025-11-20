package su.weblock.schema.extrapolator.compoment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class YamlExtrapolator extends JsonCompatibleExtrapolator {

    @Override
    public String getDataType() {
        return "yaml";
    }

    @Override
    protected JsonCompatibleParser makeParser(String s) throws JsonMappingException, JsonProcessingException {
        return new YamlParser(s);
    }
    
}
