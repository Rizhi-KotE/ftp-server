package exceptions;

public class CommandUnrecognizedException extends FtpErrorReplyException {
    CommandUnrecognizedException(String command){
        super("");
    }
}
