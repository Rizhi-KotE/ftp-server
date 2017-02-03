package commands;

import core.Connection;
import core.FtpSession;
import exceptions.NoSuchMessageException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
        try {
            File dir = ftpSession.getWorkingDirectory();
            if (args.length >= 1) dir = new File(args[0]);
            ftpSession.getControlConnection().write(getMessage("125"));
            Process exec = Runtime.getRuntime().exec(String.format("ls -l --time-style=iso %s", dir.getAbsolutePath()));
            exec.waitFor();
            InputStream outputStream = exec.getInputStream();
            Connection dataConnection = ftpSession.getDataConnection();
            dataConnection.write(outputStream);
            dataConnection.close();
            ftpSession.getControlConnection().write(getMessage("250"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
