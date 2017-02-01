package commands;

import core.FtpSession;

import java.io.IOException;

public class PASSCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PASSCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException {
        session.getControlConnection().write("230 \n");
    }
}
