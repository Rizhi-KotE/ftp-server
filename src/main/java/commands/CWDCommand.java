package commands;

import core.FtpSession;
import exceptions.FtpErrorReplyException;
import exceptions.NoSuchMessageException;
import exceptions.SyntaxErrorInArgumentsException;

import java.io.IOException;
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
        if(args.length == 0) throw new SyntaxErrorInArgumentsException("CWD", Arrays.toString(args));
        session.changeWorkingDirectory(args[0]);
        session.getControlConnection().write(getMessage("200"));
    }
}
