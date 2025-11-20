package su.weblock.schema.extrapolator.compoment;

import java.util.HashMap;

import su.weblock.schema.common.data.ValueType;
import su.weblock.schema.extrapolator.Extrapolator;
import su.weblock.schema.extrapolator.ExtrapolatorResult;
import su.weblock.schema.extrapolator.exception.InvalidMultipartDataException;

public class MultipartFormdataExtrapolator implements Extrapolator {    

    private MultipartFormdataParser parser;

    @Override
    public String getDataType() {
        return "multipart/form-data"; 
    }

    @Override
    public SampleParsingResult pushSample(byte[] data) {

        try {
            if (parser == null) {
                parser = new MultipartFormdataParser(data);
            }   else {       
                if (!parser.merge(new MultipartFormdataParser(data))){
                    return SampleParsingResult.INCOMPATIBLE;
                }                
            }   

            return SampleParsingResult.SUCCESS;   
        }   catch (InvalidMultipartDataException ex) {
            return SampleParsingResult.ERROR;
        }
    }

    @Override
    public ExtrapolatorResult result() {
        if (parser == null) {
            return new MultipartFormdataExtrapolatorResult(new HashMap<String, ValueType>());    
        }
        return new MultipartFormdataExtrapolatorResult(parser.getTypes());
    }    
}
