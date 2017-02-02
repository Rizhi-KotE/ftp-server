package commands;

import core.Connection;
import core.FtpSession;
import exceptions.NoSuchMessageException;

import java.io.IOException;

import static java.lang.Integer.parseInt;
import static utils.MessageFactory.getMessage;

public class PORTCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PORTCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, NoSuchMessageException {
        if (args.length < 1) {
            return;
        }
        String[] numbers = args[0].split(",");
        String host = String.join(".", numbers[0], numbers[1], numbers[2], numbers[3]);
        session.putDataConnection(host, parseInt(numbers[4]) * 256 + parseInt(numbers[5]));
        session.getControlConnection().write(getMessage("200"));
    }
}
