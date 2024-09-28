package org.noear.waad.utils.fun;

/**
 * Created by noear on 14-9-16.
 */
@FunctionalInterface
public interface Act0Ex<Ex extends Throwable> {
     void run() throws Ex;
}
