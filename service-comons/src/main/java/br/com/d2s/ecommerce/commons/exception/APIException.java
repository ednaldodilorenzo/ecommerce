package br.com.d2s.ecommerce.commons.exception;

public class APIException extends RuntimeException {
    private final APIExceptionType type;

    public APIException(APIExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public APIException(APIExceptionType type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public APIExceptionType getType() {
        return type;
    }
}
