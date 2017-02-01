package commands;

import core.FtpSession;

import java.io.IOException;

public class PWDCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PWDCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException {
        session.getControlConnection().writeSequence("257 ");
        session.getControlConnection().writeSequence(session.getWorkingDirectory());
        session.getControlConnection().writeSequence("\n");
        session.getControlConnection().flush();
    }

}
