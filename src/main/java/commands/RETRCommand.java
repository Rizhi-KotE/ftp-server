package commands;

import core.FtpSession;
import exceptions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static utils.MessageFactory.getMessage;

/**
 * Created by rodya on 4.2.17.
 * This class is an implementation of {@link Command}interface.
 * Allows to load files from server machine to client machine.
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
        if (args.length != 1) throw new FTPError501Exception("RETR", Arrays.toString(args));
        File localFile = ftpSession.getFileSystem().getLocalFile(args[0]);
        if (!localFile.exists()) throw new FTPError550Exception(String.format("File is not exists [%s]", args[0]));
        ftpSession.getControlConnection().write(getMessage("150"));
        doWork(localFile);
        ftpSession.getControlConnection().write("226 Succesfully transferred.\r\n");
    }

    private void doWork(File localFile) throws IOException {
        try (InputStream inputStream = new FileInputStream(localFile)) {
            ftpSession.getDataConnection().writeFrom(inputStream);
            ftpSession.getDataConnection().close();
        }
    }
}
