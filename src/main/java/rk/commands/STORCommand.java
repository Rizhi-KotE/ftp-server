package rk.commands;

import org.apache.ftpserver.ftplet.FtpException;
import org.apache.log4j.Logger;
import rk.core.FtpSession;
import rk.exceptions.FTPError501Exception;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;

import static java.lang.String.format;
import static rk.utils.Messages.MESSAGE_150;
import static rk.utils.Messages.MESSAGE_226;

/**
 * Syntax: STOR remote-filename<br>
 * Begins transmission of a file to the remote site.
 * Must be preceded by either a PORT command or a PASV command so the server knows where to accept data from.
 */
public class STORCommand implements Command {
    private static final Logger log = Logger.getLogger(STORCommand.class);

    private final FtpSession ftpSession;
    private final String[] args;

    public STORCommand(FtpSession session, String[] args) {
        this.ftpSession = session;
        this.args = args;
    }

    /**
     * This method allows to put file on server.
     */
    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        try {
            if (args.length >= 1) {
                args[0] = String.join(" ", args);
            } else {
                throw new FTPError501Exception("RETR", Arrays.toString(args));
            }
            ftpSession.getControlConnection().write(MESSAGE_150);
            try (OutputStream inputStream = ftpSession.getFileSystem().fileOutputStream(args[0])) {
                ftpSession.getDataConnection().readTo(inputStream);
                ftpSession.getDataConnection().close();
            }
            ftpSession.getControlConnection().write(MESSAGE_226);
        } catch (NoSuchFileException e) {
            log.info(format("No such file [%s]", args[0]));
            log.trace("", e);
        } catch (FtpException e) {
            log.error("", e);
        }
    }

}
