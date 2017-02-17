package rk.exceptions;

public class FTPError502Exception extends FtpErrorReplyException {
    public FTPError502Exception(String command) {
        super(command);
    }

    @Override
    protected String getCode() {
        return "502";
    }
}
