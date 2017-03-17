package rk.commands;

import org.apache.ftpserver.ftplet.FtpException;
import rk.core.FtpSession;
import rk.exceptions.FTPError550Exception;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Syntax: PWD<br>
 * Returns the name of the current directory on the remote host.
 */
public class PWDCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PWDCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FTPError550Exception, FtpException {
        session.getControlConnection().writeSequence("257 ");
        byte[] bytes = session.getFileSystem().getPath().getBytes(StandardCharsets.UTF_8);
        String s = new String(bytes);
        session.getControlConnection().writeSequence(s);
        session.getControlConnection().writeSequence("\r\n");
        session.getControlConnection().flush();
    }

}
