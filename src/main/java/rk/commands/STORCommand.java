package rk.commands;

import rk.core.FtpSession;
import rk.exceptions.FTPError501Exception;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import static rk.utils.Messages.MESSAGE_150;
import static rk.utils.Messages.MESSAGE_226;

/**
 * Syntax: STOR remote-filename<br>
 * Begins transmission of a file to the remote site.
 * Must be preceded by either a PORT command or a PASV command so the server knows where to accept data from.
 */
public class STORCommand implements Command {
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
        if (args.length >= 1) {
            args[0] = String.join(" ", args);
        } else {
            throw new FTPError501Exception("STOR", Arrays.toString(args));
        }
        File localFile = ftpSession.getFileSystem().getLocalFile(args[0]);
        if (!localFile.exists()) ftpSession.getFileSystem().createFile(args[0]);
        ftpSession.getControlConnection().write(MESSAGE_150);
        doWork(localFile);
        ftpSession.getControlConnection().write(MESSAGE_226);
    }

    private void doWork(File localFile) throws IOException {
        try (OutputStream fos = new FileOutputStream(localFile)) {
            ftpSession.getDataConnection().readTo(fos);
            ftpSession.getDataConnection().close();
        }
    }


}
