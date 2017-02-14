package exceptions;

public class FTPQuitException extends FtpErrorReplyException {
    public FTPQuitException() {
        super("");
    }

    @Override
    protected String getCode() {
        return "221";
    }
}
