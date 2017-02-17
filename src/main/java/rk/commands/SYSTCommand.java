package rk.commands;

import rk.core.FtpSession;

import java.io.IOException;

/**
 * Syntax: SYST<br>
 * Returns a word identifying the system, the word "Type:",
 * and the default transfer type (as would be set by the TYPE command).
 * For example: UNIX Type: L8
 */
public class SYSTCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public SYSTCommand(FtpSession session, String[] args) {


        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException {
        session.getControlConnection().write("215 UNIX Type: L8\r\n");//TODO
    }
}
