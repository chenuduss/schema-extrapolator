package su.weblock.schema.openapi;

import java.util.HashMap;
import java.util.Map;

public class OpenApi3CompatibleSchema {

    public OpenApi3CompatibleSchema(String type) {
        this.type = type;
        if (type.equals("object")) {
            properties = new HashMap<>();
        }
    }

    public final String type;
    public String format = null;
    public Map<String, OpenApi3CompatibleSchema> properties = null;
    public OpenApi3CompatibleSchema items = null;
    public boolean nullable = false;
}
