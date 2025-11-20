package su.weblock.schema.extrapolator.compoment;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import su.weblock.schema.extrapolator.Extrapolator;
import su.weblock.schema.extrapolator.ExtrapolatorResult;

public abstract class JsonCompatibleExtrapolator implements Extrapolator {
 
    protected JsonCompatibleParser parser;
 
    
    @Override
    public ExtrapolatorResult result() {        
        return new JsonCompatibleExtrapolatorResult(parser);
    }       

    protected abstract JsonCompatibleParser makeParser(String s) throws JsonMappingException, JsonProcessingException;
    
    protected SampleParsingResult processSample(String s) {        
       
        try {
            if (parser == null) {
                parser = makeParser(s);
            }   else {       
                if (!parser.merge(makeParser(s))){
                    return SampleParsingResult.INCOMPATIBLE;
                }                
            }

            return SampleParsingResult.SUCCESS;   
        }   catch (JsonMappingException ex) {
            return SampleParsingResult.ERROR;
        }   catch (JsonProcessingException ex) {
            return SampleParsingResult.ERROR;
        }
 
    }       

    @Override
    public SampleParsingResult pushSample(byte[] data) {        
        return processSample(new String(data, StandardCharsets.UTF_8));
    }    
}
