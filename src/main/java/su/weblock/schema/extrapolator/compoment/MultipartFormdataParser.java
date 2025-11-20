package su.weblock.schema.extrapolator.compoment;

import su.weblock.schema.common.data.ValueType;
import su.weblock.schema.common.data.ValueTypeHelper;
import su.weblock.schema.extrapolator.exception.InvalidMultipartDataException;

import java.util.Arrays;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.fileupload.ParameterParser;

public class MultipartFormdataParser  extends SingleLevelDataParser  {

    private byte[] boundary;

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
        }   else if ((t1 == ValueType.FILE) && (t2 != ValueType.FILE)) {
            return ValueType.ERROR;
        }   else if ((t1 != ValueType.FILE) && (t2 == ValueType.FILE)) {
            return ValueType.ERROR;       
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

    static byte[] extractBoundary(byte[] data) {
        if (data.length < 10) {
            return null;
        }

        if ((data[0]=='-') && (data[1]=='-')) {
            for (int i = 2; i < data.length/2-1; i++) {
                if ((data[i] == '\r') && (data[i+1] == '\n')) {
                    return Arrays.copyOfRange(data, 2, i);
                }
            }
        }

        return null;
    }

    protected void parse(byte[] data) throws InvalidMultipartDataException, IOException {
        boundary = extractBoundary(data);

        if (boundary == null) {
            throw new InvalidMultipartDataException("boundary not found");
        }
        if (boundary.length < 4) {
            throw new InvalidMultipartDataException("boundary too small");
        }        
             
        var multipartStream = new MultipartStream(new ByteArrayInputStream(data), boundary, 4096, null);

        boolean nextPart = multipartStream.skipPreamble();
        while (nextPart) {
            var header = multipartStream.readHeaders();                        
            var parser = new ParameterParser();
            var hparams = parser.parse(header, ';');
            var pname = hparams.get("name");
            if (pname == null) {
                throw new InvalidMultipartDataException("multipart data header not contain \"name\" parameter");
            }
            var fname = hparams.get("filename");
            if (fname != null) {
                addKeyValueType(pname, ValueType.FILE);
                multipartStream.discardBodyData();
            }   else {
                var outs = new ByteArrayOutputStream();
                multipartStream.readBodyData(outs);                
                addKeyValueType(pname, ValueTypeHelper.estimateTypeOfValue(outs.toString(StandardCharsets.UTF_8)));
            }

            nextPart = multipartStream.readBoundary();
        }
    }

    public MultipartFormdataParser(byte[] data) throws InvalidMultipartDataException {
        try {
            parse(data);
        }   catch(IOException ex)   {
            throw new InvalidMultipartDataException(ex);
        }
    }       
    

 
}
