package org.noear.waad.util.function;

/**
 * Created by noear on 14-6-12.
 */
@FunctionalInterface
public interface Act2<P1,P2> {
    void run(P1 p1, P2 p2);
}
