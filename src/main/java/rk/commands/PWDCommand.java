package rk.commands;

import org.apache.ftpserver.ftplet.FtpException;
import org.apache.log4j.Logger;
import rk.core.FtpSession;
import rk.exceptions.FTPError550Exception;

import java.io.IOException;

import static java.lang.String.format;

/**
 * Syntax: PWD<br>
 * Returns the name of the current directory on the remote host.
 */
public class PWDCommand implements Command {
    public static final Logger log = Logger.getLogger(PWDCommand.class);

    private final FtpSession session;
    private final String[] args;

    public PWDCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FTPError550Exception, FtpException {
        String path = session.getFileSystem().getPath();
        log.info(format("Current working directory [%s]", path));
        session.getControlConnection().write(format("257 \"%s\"\r\n", path));
    }

}
