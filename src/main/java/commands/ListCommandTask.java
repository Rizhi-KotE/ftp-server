package commands;

import core.Connection;
import core.FtpSession;
import exceptions.NoSuchMessageException;

import java.io.File;
import java.io.IOException;

import static utils.MessageFactory.getMessage;

public class ListCommandTask implements Command {

    private final FtpSession ftpSession;
    private final String[] args;

    public ListCommandTask(FtpSession ftpSession, String[] args) {
        this.ftpSession = ftpSession;
        this.args = args;
    }


    @Override
    public void execute() throws IOException, NoSuchMessageException {
        File dir = ftpSession.getWorkingDirectory();
        if (args.length >= 1) dir = ftpSession.getFileSystem().getFile(args[0]);
        ftpSession.getControlConnection().write(getMessage("150"));
        Connection dataConnection = ftpSession.getDataConnection();
        for (File f : dir.listFiles()) {
            dataConnection.writeSequence(f.getName());
            dataConnection.writeSequence("\n");
        }
        dataConnection.flush();
        dataConnection.close();
        ftpSession.getControlConnection().write(getMessage("226"));
    }
}
