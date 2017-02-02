package exceptions;

public class NotLoggedException extends FtpErrorReplyException {
    public NotLoggedException(String message) {
        super(message);
    }
}
