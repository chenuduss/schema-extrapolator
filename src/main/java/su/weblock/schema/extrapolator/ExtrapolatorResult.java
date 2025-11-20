package su.weblock.schema.extrapolator;

import su.weblock.schema.openapi.OpenApi3CompatibleSchema;
import su.weblock.schema.openapi.exception.IncompatibleWithOpenApiSchema;

public interface ExtrapolatorResult {
    String getShortStringDescription();
    OpenApi3CompatibleSchema getOpenApiCompatibleSchema() throws IncompatibleWithOpenApiSchema;
}
