package rk.exceptions;

/**
 * Created by rizhi-kote on 28.03.17.
 */
public class FTPError450Exception extends FtpErrorReplyException {
    public FTPError450Exception() {
        super("Requested file action not taken.\r\n");
    }

    @Override
    protected String getCode() {
        return "450";
    }
}
