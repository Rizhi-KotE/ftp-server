package commands;

import core.FtpSession;
import exceptions.SyntaxErrorInArgumentsException;

import java.io.IOException;
import java.util.Arrays;

public class USERCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public USERCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, SyntaxErrorInArgumentsException {
        if (args.length < 1) throw new SyntaxErrorInArgumentsException("USER", Arrays.toString(args));
        session.putUser(args[0]);
        session.getControlConnection().write("331 \n");
    }

}
