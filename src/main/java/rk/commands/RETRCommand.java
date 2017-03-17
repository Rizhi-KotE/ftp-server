package rk.commands;

import rk.core.FtpSession;
import rk.exceptions.FTPError501Exception;
import rk.exceptions.FTPError550Exception;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static rk.utils.Messages.MESSAGE_150;
import static rk.utils.Messages.MESSAGE_226;

/**
 * Syntax: RETR remote-filename<br>
 * Begins transmission of a file from the remote host.
 * Must be preceded by either a PORT command or a PASV command to indicate where the server should send data.
 */
public class RETRCommand implements Command {
    private String[] args;
    private FtpSession ftpSession;

    public RETRCommand(FtpSession session, String[] args) {
        ftpSession = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        if (args.length >= 1) {
            args[0] = String.join(" ", args);
        } else {
            throw new FTPError501Exception("RETR", Arrays.toString(args));
        }
        File localFile = ftpSession.getFileSystem().getLocalFile(args[0]);
        if (!localFile.exists()) throw new FTPError550Exception(String.format("File is not exists [%s]", args[0]));
        ftpSession.getControlConnection().write(MESSAGE_150);
        doWork(localFile);
        ftpSession.getControlConnection().write(MESSAGE_226);
    }

    private void doWork(File localFile) throws IOException {
        try (InputStream inputStream = new FileInputStream(localFile)) {
            ftpSession.getDataConnection().writeFrom(inputStream);
            ftpSession.getDataConnection().close();
        }
    }
}
