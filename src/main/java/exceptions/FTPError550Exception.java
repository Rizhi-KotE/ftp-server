package exceptions;

public class FTPError550Exception extends FtpErrorReplyException {

    public FTPError550Exception(String message) {
        super(message);
    }

    @Override
    protected String getCode() {
        return "550";
    }
}
