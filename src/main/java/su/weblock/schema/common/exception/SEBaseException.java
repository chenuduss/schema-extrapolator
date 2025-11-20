package su.weblock.schema.common.exception;

public class SEBaseException extends RuntimeException {

    public SEBaseException(String message) {
        super(message);        
    }

    public SEBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

