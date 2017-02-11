package commands;

import core.Connection;
import core.FtpSession;
import exceptions.NoSuchMessageException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static utils.MessageFactory.getMessage;

public class ListCommandTask implements Command {

    private final FtpSession ftpSession;
    private final String[] args;
    static final Logger log = Logger.getLogger(ListCommandTask.class);

    public ListCommandTask(FtpSession ftpSession, String[] args) {
        this.ftpSession = ftpSession;
        this.args = args;
    }


    @Override
    public void execute() throws IOException, NoSuchMessageException {
        try {
            List<String> files = args.length >= 1 ? ftpSession.getFileSystem().getLsFileList(args[0]) :
                    ftpSession.getFileSystem().getLsFileList();

            ftpSession.getControlConnection().write(getMessage("150"));
            Connection dataConnection = ftpSession.getDataConnection();
            for (String file : files) {
                dataConnection.writeSequence(file);
            }
            dataConnection.flush();
//            Process exec = Runtime.getRuntime().exec(String.format("ls -l --time-style=iso %s", dir.getAbsolutePath()));
//            exec.waitFor();
//            InputStream outputStream = exec.getInputStream();
//            Connection dataConnection = ftpSession.getDataConnection();
//            dataConnection.writeFrom(outputStream);
            ftpSession.getControlConnection().write(getMessage("226"));
            dataConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
