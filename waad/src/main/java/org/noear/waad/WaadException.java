package org.noear.waad;

/**
 * @author noear
 * @since 3.3
 */
public class WaadException extends RuntimeException {
    public WaadException(String message) {
        super(message);
    }

    public WaadException(String message, Throwable cause) {
        super(message, cause);
    }

    public WaadException(Throwable cause) {
        super(cause);
    }
}
