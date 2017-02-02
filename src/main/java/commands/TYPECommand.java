package commands;

import commands.Command;
import core.FtpSession;
import exceptions.NoSuchMessageException;

import java.io.IOException;

import static utils.MessageFactory.getMessage;

public class TYPECommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public TYPECommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, NoSuchMessageException {
        session.getControlConnection().write(getMessage("200"));
    }
}
