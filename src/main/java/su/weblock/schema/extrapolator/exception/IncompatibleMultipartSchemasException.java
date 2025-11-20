package su.weblock.schema.extrapolator.exception;

import su.weblock.schema.common.exception.SEBaseException;

class IncompatibleMultipartSchemasException extends SEBaseException {
    public IncompatibleMultipartSchemasException(String msg) {
        super(msg);
    }

    public IncompatibleMultipartSchemasException() {
        super("incompatible multipart/form-data bodies");
    }
}
