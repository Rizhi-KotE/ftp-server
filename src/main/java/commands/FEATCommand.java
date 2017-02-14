package commands;

import core.FtpSession;

import java.io.IOException;

public class FEATCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public FEATCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException {
        session.getControlConnection().writeSequence("211-Features\r\n");
        for (FTPCommands command : FTPCommands.values()) {
            session.getControlConnection().writeSequence(" ");
            session.getControlConnection().writeSequence(command.name());
            session.getControlConnection().writeSequence("\r\n");
        }
        session.getControlConnection().writeSequence(" UTF8\r\n");
        session.getControlConnection().writeSequence("211 End\r\n");
        session.getControlConnection().flush();
    }
}
