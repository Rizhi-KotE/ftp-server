package rk.commands;

import rk.core.FtpSession;
import rk.exceptions.NoSuchMessageException;

import java.io.IOException;

import static rk.utils.Messages.MESSAGE_200;

/**
 Syntax: TYPE type-character [second-type-character]<br>
 Sets the type of file to be transferred. type-character can be any of:<br><br>

 A - ASCII text<br>
 E - EBCDIC text<br>
 I - image (binary data)<br>
 L - local format<br>
 For A and E, the second-type-character specifies how the text should be interpreted. It can be:<br>
 N - Non-print (not destined for printing). This is the default if second-type-character is omitted.<br>
 T - Telnet format control ({@code <CR>, <FF>, etc.})<br>
 C - ASA Carriage Control<br>
 For L, the second-type-character specifies the number of bits per byte on the local system, and may not be omitted.<br>
 */
public class TYPECommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public TYPECommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, NoSuchMessageException {
        session.getControlConnection().write(MESSAGE_200);
    }
}
