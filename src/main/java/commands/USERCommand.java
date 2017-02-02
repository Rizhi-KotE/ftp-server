package commands;

import core.FtpSession;
import exceptions.NoSuchMessageException;
import exceptions.SyntaxErrorInArgumentsException;

import java.io.IOException;
import java.util.Arrays;

import static utils.MessageFactory.getMessage;

public class USERCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public USERCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, SyntaxErrorInArgumentsException, NoSuchMessageException {
        if (args.length == 0) throw new SyntaxErrorInArgumentsException("USER", Arrays.toString(args));
        session.putUser(args[0]);
        session.getControlConnection().write(getMessage("331"));
    }

}
