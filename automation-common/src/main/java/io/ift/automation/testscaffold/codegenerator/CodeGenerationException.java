package io.ift.automation.testscaffold.codegenerator;

/**
 * HtmlElementsExceptions
 */
public class CodeGenerationException extends RuntimeException {
    public CodeGenerationException() {
        super();
    }

    public CodeGenerationException(String message) {
        super(message);
    }
    public CodeGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeGenerationException(Throwable cause) {
        super(cause);
    }
}
