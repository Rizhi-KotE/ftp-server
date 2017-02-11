package commands;

import core.FtpSession;
import exceptions.FTPError550Exception;
import exceptions.FtpErrorReplyException;
import exceptions.NoSuchMessageException;
import exceptions.PTFError501Exception;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;

import static utils.MessageFactory.getMessage;

public class CWDCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public CWDCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        try {
            if (args.length == 0) throw new PTFError501Exception("CWD", Arrays.toString(args));
            session.getFileSystem().changeDir(args[0]);
            session.getControlConnection().write(getMessage("200"));
        } catch (NoSuchFileException e) {
            throw new FTPError550Exception(String.format("File is not exists [%s]", args[0]));
        }
    }
}
