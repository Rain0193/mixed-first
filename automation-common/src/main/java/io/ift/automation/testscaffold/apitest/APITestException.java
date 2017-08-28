package io.ift.automation.testscaffold.apitest;

/**
 * Created by patrick on 15/3/31.
 *
 * @version $Id$
 */


public class APITestException extends RuntimeException {

    public APITestException() {
        super();
    }

    public APITestException(String message) {
        super(message);
    }

    public APITestException(String message, Throwable cause) {
        super(message, cause);
    }

    public APITestException(Throwable cause) {
        super(cause);
    }

    protected APITestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
