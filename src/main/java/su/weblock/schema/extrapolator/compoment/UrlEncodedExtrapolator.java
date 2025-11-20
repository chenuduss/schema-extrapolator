package su.weblock.schema.extrapolator.compoment;

import su.weblock.schema.common.data.ValueType;
import su.weblock.schema.extrapolator.Extrapolator;
import su.weblock.schema.extrapolator.ExtrapolatorResult;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class UrlEncodedExtrapolator implements Extrapolator {

    private UrlEncodedParser parser;

    @Override
    public String getDataType() {        
        return "urlencoded";    
    }

    protected SampleParsingResult processSample(String s) {        
        if (parser == null) {
            parser = new UrlEncodedParser(s);
        }   else {            
            if (!parser.merge(new UrlEncodedParser(s))){
                return SampleParsingResult.INCOMPATIBLE;
            }
        }   

        return SampleParsingResult.SUCCESS;    
    }    

    @Override
    public SampleParsingResult pushSample(byte[] data) {        
        return processSample(new String(data, StandardCharsets.UTF_8));
    }

    @Override
    public ExtrapolatorResult result() {        
        if (parser == null) {
            return new UrlEncodedExtrapolatorResult(new HashMap<String, ValueType>());    
        }        
        return new UrlEncodedExtrapolatorResult(parser.getTypes());
    }
    
}
