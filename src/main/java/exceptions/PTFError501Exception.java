package exceptions;

public class PTFError501Exception extends FtpErrorReplyException {
    public PTFError501Exception(String command, String args) {
        super(command + args);
    }

    @Override
    protected String getCode() {
        return "501";
    }
}
