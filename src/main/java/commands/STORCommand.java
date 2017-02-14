package commands;

import core.FtpSession;
import exceptions.FtpErrorReplyException;
import exceptions.NoSuchMessageException;
import exceptions.FTPError501Exception;

import java.io.*;
import java.util.Arrays;

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
        if(args.length != 1) throw new FTPError501Exception("STOR", Arrays.toString(args));
        File localFile = ftpSession.getFileSystem().getLocalFile(args[0]);
        if(!localFile.exists()) ftpSession.getFileSystem().createFile(args[0]);
        ftpSession.getControlConnection().write("150 \r\n");
        doWork(localFile);
        ftpSession.getControlConnection().write("226 Successfully transferred.\r\n");
    }

    private void doWork(File localFile) throws IOException {
        try(OutputStream fos = new FileOutputStream(localFile)){
            ftpSession.getDataConnection().readTo(fos);
            ftpSession.getDataConnection().close();
        }
    }


}
