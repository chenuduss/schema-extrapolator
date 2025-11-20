package su.weblock.schema.extrapolator;

import su.weblock.schema.extrapolator.compoment.YamlExtrapolator;
import su.weblock.schema.common.exception.SEBaseException;
import su.weblock.schema.extrapolator.compoment.JsonExtrapolator;
import su.weblock.schema.extrapolator.compoment.MultipartFormdataExtrapolator;
import su.weblock.schema.extrapolator.compoment.UrlEncodedExtrapolator;

public class ExtrapolatorMaker {
    public enum DataType {
        URLENCODED,
        MULTIPART_FORMDATA,
        JSON,
        YAML,
        XML
    }

    static public DataType getTypeFromHttpContentType(String content_type) throws SEBaseException {
        var s = content_type.toLowerCase();
        if (s.equals("application/x-www-form-urlencoded")) {
            return DataType.URLENCODED;
        }   else if ((s.length() > 18) && s.substring(0, 19).equals("multipart/form-data")) {
            return DataType.MULTIPART_FORMDATA;
        }   else if (s.equals("application/json")) {
            return DataType.JSON;
        }   else if (s.equals("text/x-yaml")) {
            return DataType.YAML;
        }   else if (s.equals("application/x-yaml")) {
            return DataType.YAML;
        }   else if (s.equals("text/yaml")) {
            return DataType.YAML;
        }   else if (s.equals("application/yaml")) {
            return DataType.YAML;
        }   else if (s.equals("text/xml")) {
            return DataType.XML;
        }   else if (s.equals("application/xml")) {
            return DataType.XML;
        }

        throw new SEBaseException("unsupported content type");
    }

    static public Extrapolator make(DataType type) {
        if (type == DataType.URLENCODED){
            return new UrlEncodedExtrapolator();
        }   else if (type == DataType.MULTIPART_FORMDATA){
            return new MultipartFormdataExtrapolator();
        }   else if (type == DataType.JSON){
            return new JsonExtrapolator();
        }   else if (type == DataType.YAML){
            return new YamlExtrapolator();
        }   else if (type == DataType.XML){
            return null;
        }


        return null;
    }

    static public Extrapolator make(String content_type) throws SEBaseException {
        return make(getTypeFromHttpContentType(content_type));
    }
}
