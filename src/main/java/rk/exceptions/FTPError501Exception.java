package rk.exceptions;

public class FTPError501Exception extends FtpErrorReplyException {
    public FTPError501Exception(String command, String args) {
        super(command + args);
    }

    @Override
    protected String getCode() {
        return "501";
    }
}
