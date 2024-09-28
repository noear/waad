package org.noear.waad.util;

import org.noear.waad.util.function.Act0Ex;
import org.noear.waad.util.function.Fun0Ex;

/**
 * 运行工具
 *
 * @author noear
 * @since 3.0
 * */
public class RunUtils {
    /**
     * 调用并返回
     */
    public static <T> T call(Fun0Ex<T, Exception> fun) {
        try {
            return fun.run();
        } catch (Throwable e) {
            e = ThrowableUtils.throwableUnwrap(e);

            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
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