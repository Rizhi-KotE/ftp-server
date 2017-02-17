package rk.exceptions;

public class FTPQuitException extends FtpErrorReplyException {
    public FTPQuitException(String message) {
        super(message);
    }

    @Override
    public String getReplyMessage() {
        return getMessage();
    }

    @Override
    protected String getCode() {
        return "221";
    }
}
