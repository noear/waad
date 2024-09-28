package org.noear.waad.util.function;

/**
 * Created by noear on 14-9-12.
 */
@FunctionalInterface
public interface Fun0Ex <T,Ex extends Throwable> {
    T run() throws Ex;
}
