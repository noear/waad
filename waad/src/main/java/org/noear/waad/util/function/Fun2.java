package org.noear.waad.util.function;

/**
 * Created by noear on 14-6-12.
 */
@FunctionalInterface
public interface Fun2<T,P1,P2> {
    T run(P1 p1, P2 p2);
}
