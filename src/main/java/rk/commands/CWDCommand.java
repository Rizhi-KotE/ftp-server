package rk.commands;

import org.apache.ftpserver.ftplet.FtpException;
import org.apache.log4j.Logger;
import rk.core.FtpSession;
import rk.exceptions.FTPError501Exception;
import rk.exceptions.FTPError550Exception;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;

import static rk.utils.Messages.MESSAGE_200;


/**
 * Syntax: CWD remote-directory<br>
 * Makes the given directory be the current directory on the remote host.
 */
public class CWDCommand implements Command {
    static final Logger log = Logger.getLogger(CWDCommand.class);
    private final FtpSession session;
    private final String[] args;

    public CWDCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        try {
            if (args.length >= 1) {
                args[0] = String.join(" ", args);
            } else {
                throw new FTPError501Exception("CWD", Arrays.toString(args));
            }
            session.getFileSystem().changeDir(args[0]);
            session.getControlConnection().write(MESSAGE_200);
        } catch (NoSuchFileException e) {
            throw new FTPError550Exception(String.format("File is not exists [%s]", args[0]));
        } catch (FtpException e) {
            log.debug(e, e);
        }
    }
}
