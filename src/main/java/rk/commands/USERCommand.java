package rk.commands;

import rk.core.FtpSession;
import rk.exceptions.FTPError501Exception;
import rk.exceptions.NoSuchMessageException;

import java.io.IOException;
import java.util.Arrays;

import static rk.utils.Messages.MESSAGE_331;


/**
 * Syntax: USER username<br>
 * Send this command to begin the login process.
 * username should be a valid username on the system, or "anonymous" to initiate an anonymous login.
 */
public class USERCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public USERCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FTPError501Exception, NoSuchMessageException {
        if (args.length == 0) throw new FTPError501Exception("USER", Arrays.toString(args));
        session.putUser(args[0]);
        session.getControlConnection().write(MESSAGE_331);
    }

}
