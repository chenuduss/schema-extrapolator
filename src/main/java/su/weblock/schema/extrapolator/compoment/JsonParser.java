package su.weblock.schema.extrapolator.compoment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser extends JsonCompatibleParser {
    
    protected void parse(String s) throws JsonMappingException, JsonProcessingException {
        var mapper = new ObjectMapper();
        walk(mapper.readTree(s));        
    }

    public JsonParser(String s) throws JsonMappingException, JsonProcessingException {
        parse(s);
    }

    public JsonParser() {
    }

    @Override
    protected JsonCompatibleParser createNew() {
        return new JsonParser();
    }
}
