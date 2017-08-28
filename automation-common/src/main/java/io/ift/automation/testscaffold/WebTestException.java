package io.ift.automation.testscaffold;

/**
 * HtmlElementsExceptions
 */
public class WebTestException extends RuntimeException {
    public WebTestException() {
        super();
    }

    public WebTestException(String message) {
        super(message);
    }
    public WebTestException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebTestException(Throwable cause) {
        super(cause);
    }
}