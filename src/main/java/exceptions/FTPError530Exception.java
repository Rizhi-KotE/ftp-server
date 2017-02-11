package exceptions;

public class FTPError530Exception extends FtpErrorReplyException {
    public FTPError530Exception(String message) {
        super(message);
    }

    @Override
    protected String getCode() {
        return "530";
    }
}
