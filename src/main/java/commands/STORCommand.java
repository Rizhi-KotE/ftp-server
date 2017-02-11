package commands;

import core.FtpSession;
import exceptions.FtpErrorReplyException;
import exceptions.NoSuchMessageException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        try {
            OutputStream outputStream = ftpSession.getFileSystem().getFileOutputStream(args[0]);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            ftpSession.getControlConnection().write(getMessage("150"));
            ftpSession.getDataConnection().readTo(bufferedOutputStream);
            outputStream.close();
            ftpSession.getControlConnection().write("226 Successfully transferred.\r\n");
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        }

    }


}
