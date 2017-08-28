package io.ift.automation.testscaffold.webtest.webUI.exceptions;

/**
 * HtmlElementsExceptions
 */
public class HtmlElementsException extends RuntimeException {
    public HtmlElementsException() {
        super();
    }

    public HtmlElementsException(String message) {
        super(message);
    }

    public HtmlElementsException(String message, Throwable cause) {
        super(message, cause);
    }

    public HtmlElementsException(Throwable cause) {
        super(cause);
    }
}