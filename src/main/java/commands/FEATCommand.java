package commands;

import core.FtpSession;

import java.io.IOException;

public class FEATCommand implements Command {
    private final FtpSession session;
    private final String[] args;
    private final CommandFactory factory;


    public FEATCommand(FtpSession session, String[] args, CommandFactory factory) {

        this.session = session;
        this.args = args;
        this.factory = factory;
    }

    @Override
    public void execute() throws IOException {
        session.getControlConnection().writeSequence("211-Features\r\n");
        for (String command : factory.getCommands()) {
            session.getControlConnection().writeSequence(command);
            session.getControlConnection().writeSequence("\r\n");
        }
        session.getControlConnection().writeSequence("UTF8\r\n");
        session.getControlConnection().writeSequence("211 End\r\n");
        session.getControlConnection().flush();
    }
}
