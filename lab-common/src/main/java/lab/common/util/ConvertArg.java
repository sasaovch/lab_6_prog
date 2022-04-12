package lab.common.util;

import java.net.UnknownHostException;

public interface ConvertArg<T, R> {
    R convert(T t) throws UnknownHostException, NumberFormatException;
}
