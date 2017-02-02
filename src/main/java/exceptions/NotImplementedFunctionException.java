package exceptions;

public class NotImplementedFunctionException extends FtpErrorReplyException {
    public NotImplementedFunctionException(String command) {
        super(command);
    }
}
