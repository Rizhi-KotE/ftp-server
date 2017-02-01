package commands;

import core.FtpSession;

import java.io.IOException;

public class SYSTCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public SYSTCommand(FtpSession session, String[] args) {


        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException {
        session.getControlConnection().write("215 UNIX\n");
    }
}
