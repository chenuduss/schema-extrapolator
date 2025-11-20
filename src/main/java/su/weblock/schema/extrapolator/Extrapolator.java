package su.weblock.schema.extrapolator;

public interface Extrapolator {
    public enum  SampleParsingResult {
        SUCCESS,
        ERROR,
        INCOMPATIBLE
    }

    String getDataType();
    SampleParsingResult pushSample(byte[] data);
    ExtrapolatorResult result();
}
