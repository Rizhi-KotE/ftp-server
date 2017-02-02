package commands;

import core.Connection;
import core.FtpSession;

import java.io.File;

public class ListCommandTask implements Command {

    private final FtpSession ftpSession;
    private final String[] args;

    public ListCommandTask(FtpSession ftpSession, String[] args) {
        this.ftpSession = ftpSession;
        this.args = args;
    }


    @Override
    public void execute() {
        try {
            ftpSession.getControlConnection().write("125 \n");
            Connection dataConnection = ftpSession.getDataConnection();
                for (File f : new File(".").listFiles()) {
                    dataConnection.writeSequence(f.toString());
                }
                dataConnection.flush();
                ftpSession.getControlConnection().write("250 \n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
