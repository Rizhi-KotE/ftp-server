package rk.commands;


import org.apache.ftpserver.ftplet.FtpException;
import rk.core.FtpSession;
import rk.exceptions.FTPError501Exception;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;
import rk.utils.Messages;

import java.io.IOException;
import java.util.Arrays;

import static rk.utils.Messages.MESSAGE_250;

public class DELEComand implements Command {
    private final FtpSession session;
    private final String[] args;

    public DELEComand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException, FtpException {
        if (args.length >= 1) {
            args[0] = String.join(" ", args);
        } else {
            throw new FTPError501Exception("RMD", Arrays.toString(args));
        }
        session.getFileSystem().removeFile(args[0]);
        session.getControlConnection().write(MESSAGE_250);
    }
}
