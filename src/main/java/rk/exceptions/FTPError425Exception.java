package rk.exceptions;

public class FTPError425Exception extends FtpErrorReplyException {

    public FTPError425Exception() {
        super("Can't open data connection.");
    }

    @Override
    protected String getCode() {
        return "425";
    }
}
