package rk.commands;

import rk.core.FtpSession;
import rk.exceptions.NoSuchMessageException;

import java.io.IOException;

import static java.lang.Integer.parseInt;
import static rk.utils.Messages.MESSAGE_200;

/**
 Syntax: PORT a1,a2,a3,a4,p1,p2<br>
 Specifies the host and port to which the server should connect for the next
 file transfer. This is interpreted as IP address a1.a2.a3.a4, port p1*256+p2.
 */
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
        session.getControlConnection().write(MESSAGE_200);
    }
}
