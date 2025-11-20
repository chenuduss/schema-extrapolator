package su.weblock.schema.extrapolator.compoment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlParser extends JsonCompatibleParser {

    protected void parse(String s) throws JsonMappingException, JsonProcessingException {
        var mapper = new ObjectMapper(new YAMLFactory());
        walk(mapper.readTree(s));        
    }

    public YamlParser(String s) throws JsonMappingException, JsonProcessingException{
        parse(s);
    }

    public YamlParser(){

    }
       
   
    @Override
    protected JsonCompatibleParser createNew() {
        return new YamlParser();
    }    
}
