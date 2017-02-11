package exceptions;

public abstract class FtpErrorReplyException extends Exception {
    public FtpErrorReplyException(String message) {
        super(message);
    }

    public String getReplyMessage() {
        return String.format(("%s %s\r\n"), getCode(), getMessage());
    }

    abstract protected String getCode();
}
