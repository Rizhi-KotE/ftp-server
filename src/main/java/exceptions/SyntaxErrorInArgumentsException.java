package exceptions;

public class SyntaxErrorInArgumentsException extends FtpErrorReplyException {
    public SyntaxErrorInArgumentsException(String command, String args) {
        super(command + args);
    }
}
