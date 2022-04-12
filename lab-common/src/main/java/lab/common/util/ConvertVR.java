package lab.common.util;

import java.net.UnknownHostException;

public interface ConvertVR<T, R> {
    R convert(T t) throws UnknownHostException;
}
