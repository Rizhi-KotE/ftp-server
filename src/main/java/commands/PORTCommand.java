package commands;

import core.FtpSession;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class PORTCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PORTCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException {
        if (args.length < 1) {
            return;
        }
        String[] numbers = args[0].split(",");
        String host = String.join(".", numbers[0], numbers[1], numbers[2], numbers[3]);
        session.setDataHost(host);
        session.setDataPort(parseInt(numbers[4]) * 256 + parseInt(numbers[5]));
        session.getControlConnection().write("200 \n");
    }
}
