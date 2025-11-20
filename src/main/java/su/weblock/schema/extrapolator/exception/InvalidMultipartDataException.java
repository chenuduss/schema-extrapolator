package su.weblock.schema.extrapolator.exception;

import su.weblock.schema.common.exception.SEBaseException;

public class InvalidMultipartDataException extends SEBaseException {
    public InvalidMultipartDataException(String msg) {
        super(msg);
    }

    public InvalidMultipartDataException() {
        super("invalid or corrupted multipart/form-data body");
    }

    public InvalidMultipartDataException(Throwable cause) {
        super("invalid or corrupted multipart/form-data body", cause);
    }    
}
