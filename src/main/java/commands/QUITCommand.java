package commands;

import core.FtpSession;
import exceptions.FTPQuitException;
import exceptions.FtpErrorReplyException;
import exceptions.NoSuchMessageException;

import java.io.IOException;

public class QUITCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        throw new FTPQuitException();
    }

    public QUITCommand(FtpSession session, String[] args) {
        this.session = session;
        this.args = args;
    }
}
