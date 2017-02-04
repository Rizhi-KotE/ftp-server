package commands;

import core.FtpSession;
import exceptions.FtpErrorReplyException;
import exceptions.NoSuchMessageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rodya on 4.2.17.
 * This class is an implementation of {@link Command}interface.
 * Allows to load files from server machine to client machine.
 */
public class RETRCommand implements Command {
    private String[] args;
    private FtpSession ftpSession;

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        File file = ftpSession.getFileSystem().getFile(args[0]);

        if (!file.exists()) {
            throw new FtpErrorReplyException("550 File not found.");
        } else {
            InputStream inputStream = new FileInputStream(file);
            ftpSession.getDataConnection().write(inputStream);
            inputStream.close();
            ftpSession.getControlConnection().write("226 Succesfully transferred.");
        }
    }
}
