package exceptions;

public class RequestedActionNotTakenException extends FtpErrorReplyException {
    public RequestedActionNotTakenException(String message) {
        super(message);
    }
}
