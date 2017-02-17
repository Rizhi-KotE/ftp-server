package rk.commands;

import rk.core.Connection;
import rk.core.FtpSession;
import rk.exceptions.NoSuchMessageException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static rk.utils.Messages.MESSAGE_150;
import static rk.utils.Messages.MESSAGE_226;


/**
 * Syntax: LIST [remote-filespec]<br>
 * If remote-filespec refers to a file, sends information about that file.
 * If remote-filespec refers to a directory, sends information about each file in that directory.
 * remote-filespec defaults to the current directory.
 * This command must be preceded by a PORT or PASV command.
 */
public class LISTCommand implements Command {

    private final FtpSession ftpSession;
    private final String[] args;
    static final Logger log = Logger.getLogger(LISTCommand.class);

    public LISTCommand(FtpSession ftpSession, String[] args) {
        this.ftpSession = ftpSession;
        this.args = args;
    }


    @Override
    public void execute() throws IOException, NoSuchMessageException {
        try {
            List<String> files = args.length >= 1 ? ftpSession.getFileSystem().getLsFileList(args[0]) :
                    ftpSession.getFileSystem().getLsFileList();

            ftpSession.getControlConnection().write(MESSAGE_150);
            Connection dataConnection = ftpSession.getDataConnection();
            for (String file : files) {
                dataConnection.writeSequence(file);
            }
            dataConnection.flush();
            ftpSession.getControlConnection().write(MESSAGE_226);
            dataConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
