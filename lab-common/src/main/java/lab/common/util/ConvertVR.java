package lab.common.util;

public interface ConvertVR<R> {
    R convert(String t, R defaultValue);
}
