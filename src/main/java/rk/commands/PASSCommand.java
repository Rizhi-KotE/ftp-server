package rk.commands;

import rk.core.FtpSession;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;
import rk.exceptions.FTPError501Exception;

import java.io.IOException;
import java.util.Arrays;

import static rk.utils.Messages.MESSAGE_230;


/**
 * Syntax: PASS password<br>
 * After sending the USER command, send this command to complete the login process.
 * (Note, however, that an ACCT command may have to be used on some systems.)
 */
public class PASSCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PASSCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        if (args.length < 1) throw new FTPError501Exception("PASS", Arrays.toString(args));
        session.putPassword(args[0]);
        session.getControlConnection().write(MESSAGE_230);
    }
}
