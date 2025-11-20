package su.weblock.schema.openapi.exception;

import su.weblock.schema.common.exception.SEBaseException;

public class IncompatibleWithOpenApiSchema extends SEBaseException {
    public IncompatibleWithOpenApiSchema(String msg) {
        super(msg);
    }

    public IncompatibleWithOpenApiSchema() {
        super("incompatible with OpenAPI schema");
    }
}
