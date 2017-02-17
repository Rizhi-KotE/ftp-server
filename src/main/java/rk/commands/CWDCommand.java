package rk.commands;

import rk.core.FtpSession;
import rk.exceptions.FTPError550Exception;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;
import rk.exceptions.FTPError501Exception;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;

import static rk.utils.Messages.MESSAGE_200;


/**
 Syntax: CWD remote-directory<br>
 Makes the given directory be the current directory on the remote host.
 */
public class CWDCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public CWDCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        try {
            if (args.length == 0) throw new FTPError501Exception("CWD", Arrays.toString(args));
            session.getFileSystem().changeDir(args[0]);
            session.getControlConnection().write(MESSAGE_200);
        } catch (NoSuchFileException e) {
            throw new FTPError550Exception(String.format("File is not exists [%s]", args[0]));
        }
    }
}
