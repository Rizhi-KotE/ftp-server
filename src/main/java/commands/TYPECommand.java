package commands;

import commands.Command;
import core.FtpSession;

import java.io.IOException;

public class TYPECommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public TYPECommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException {
        session.getControlConnection().write("200 \n");
    }
}
