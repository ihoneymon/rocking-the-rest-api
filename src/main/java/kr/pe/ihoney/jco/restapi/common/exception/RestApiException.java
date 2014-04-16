package kr.pe.ihoney.jco.restapi.common.exception;

/**
 * 통합 RuntimeException
 * 
 * @author ihoneymon
 * 
 */
public class RestApiException extends RuntimeException {
    private static final long serialVersionUID = 3658985351012031364L;

    private Object[] messageParams;

    public RestApiException(Throwable cause, String message) {
        super(message, cause);
    }

    public RestApiException(Throwable cause, String message, Object... messageParams) {
        super(message, cause);
        this.messageParams = messageParams;
    }

    public RestApiException(Throwable cause) {
        super(cause);
    }

    public RestApiException(String message) {
        super(message);
    }

    public Object[] getMessageParams() {
        return this.messageParams;
    }
}
