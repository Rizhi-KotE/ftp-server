package rk.commands;

import rk.core.FtpSession;
import rk.exceptions.FTPError530Exception;
import rk.exceptions.FTPError502Exception;
import rk.exceptions.FTPError501Exception;

import java.io.IOException;

/**
 * Syntax: PASV<br>
 * Tells the server to enter "passive mode". In passive mode, the server will
 * wait for the client to establish a connection with it rather than attempting
 * to connect to a client-specified port. The server will respond with the address
 * of the port it is listening on, with a message like:
 * 227 Entering Passive Mode (a1,a2,a3,a4,p1,p2)
 * where a1.a2.a3.a4 is the IP address and p1*256+p2 is the port number.
 */
public class PASVCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PASVCommand(FtpSession session, String[] args) {
        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FTPError501Exception, FTPError530Exception, FTPError502Exception {
        throw new FTPError502Exception("PASV");//TODO
    }
}
