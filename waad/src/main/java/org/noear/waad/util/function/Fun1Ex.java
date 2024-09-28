package org.noear.waad.util.function;

/**
 * Created by noear on 14-9-12.
 */
@FunctionalInterface
public interface Fun1Ex<T,P1,Ex extends Throwable> {
    T run(P1 p1) throws Ex;
}
