package rk.commands;

import rk.core.FtpSession;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;

import java.io.IOException;

import static java.lang.Integer.parseInt;
import static rk.utils.Messages.MESSAGE_200;

/**
 * The EPRT command allows for the specification of an extended address for the data connection.<br>
 * The extended address must consist of the network protocol as well as the network and transport addresses.<br>
 *
 * The format of EPRT is:<br>
 *
 * EPRT [Delimeter][net-prt][Delimeter][net-addr][Delimeter][tcp-port][Delimeter]<br>
 *
 * net-prt - An address family number defined by IANA.<br>
 * net-addr - A protocol-specific string of the network address.<br>
 * tcp-port - A TCP port number on which the host is listening for data connection.<br>
 * Delimiter - The delimiter character must be one of the ASCII characters in range 33 to 126 inclusive.
 *            The character "|" (ASCII 124) is recommended.
 *
 */
public class EPRTCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    private static final String DELIMITER = "\\|";//FIXME

    public EPRTCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        if (args.length < 1) {
            return;
        }
        String[] args = this.args[0].split(DELIMITER);
        String host = args[2];
        int port = Integer.parseInt(args[3]);
        session.putDataConnection(host, port);
        session.getControlConnection().write(MESSAGE_200);
    }
}
