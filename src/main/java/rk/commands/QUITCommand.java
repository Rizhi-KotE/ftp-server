package rk.commands;

import rk.core.FtpSession;
import rk.exceptions.FTPQuitException;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;

import java.io.IOException;

import static rk.utils.Messages.MESSAGE_221;

/**
 * Syntax: QUIT<br>
 * Terminates the command connection.
 */
public class QUITCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        throw new FTPQuitException(MESSAGE_221);
    }

    public QUITCommand(FtpSession session, String[] args) {
        this.session = session;
        this.args = args;
    }
}
