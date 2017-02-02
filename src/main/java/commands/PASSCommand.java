package commands;

import core.FtpSession;
import exceptions.NoSuchMessageException;
import exceptions.NotLoggedException;
import exceptions.SyntaxErrorInArgumentsException;

import java.io.IOException;
import java.util.Arrays;

import static utils.MessageFactory.getMessage;

public class PASSCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PASSCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, SyntaxErrorInArgumentsException, NotLoggedException, NoSuchMessageException {
        if(args.length < 1) throw new SyntaxErrorInArgumentsException("PASS", Arrays.toString(args));
        session.putPassword(args[0]);
        session.getControlConnection().write(getMessage("230"));
    }
}
