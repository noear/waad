package org.noear.waad.util;

import org.noear.waad.util.function.Act0Ex;
import org.noear.waad.util.function.Fun0Ex;

public class RunUtils {
    public static <T> T call(Fun0Ex<T, Exception> fun) {
        try {
            return fun.run();
        } catch (Throwable ex) {
            ex = ThrowableUtils.throwableUnwrap(ex);
            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void runTry(Act0Ex<Exception> fun) {
        try {
            fun.run();
        } catch (Throwable ex) {
            ex.printStackTrace();
            //
        }
    }
}
