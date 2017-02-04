package commands;

import core.*;
import exceptions.FtpErrorReplyException;
import exceptions.NoSuchMessageException;
import jdk.nashorn.internal.objects.annotations.Constructor;

import java.io.*;
import java.net.Socket;
import java.nio.file.NoSuchFileException;

import static utils.MessageFactory.getMessage;

/**
 * Created by rodya on 3.2.17.
 * This class is implementation of {@link Command} interface for command STOR;
 */
public class STORCommand implements Command {
    private final FtpSession ftpSession;
    private final String[] args;

    public STORCommand(FtpSession session, String[] args) {
        this.ftpSession = session;
        this.args = args;
    }

    /**
     * @code = execute
     * This method allows to put file on server.
     */
    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        File file;
        try {
            file = ftpSession.getFileSystem().getLocalFile(args[0]);
            ftpSession.getControlConnection().write(getMessage("150"));
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(ftpSession.getDataConnection().read().getBytes());
            outputStream.close();
            ftpSession.getControlConnection().write("226 Successfully transferred.\r\n");
        }catch(NoSuchFileException e)
        {
            ftpSession.getControlConnection().write(getMessage("150"));
            file = ftpSession.getFileSystem().createFile(args[0]);
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(ftpSession.getDataConnection().read().getBytes());
            outputStream.close();
            ftpSession.getControlConnection().write("226 Successfully transferred.\r\n");
        }

    }


}
