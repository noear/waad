package org.noear.waad.utils.fun;

/**
 * Created by noear on 14-9-16.
 */
@FunctionalInterface
public interface Act1Ex <P1,Ex extends Throwable> {
     void run(P1 p1) throws Ex;
}
