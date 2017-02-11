package commands;

import core.FtpSession;
import exceptions.FTPError550Exception;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PWDCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    public PWDCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FTPError550Exception {
        session.getControlConnection().writeSequence("257 ");
        byte[] bytes = session.getFileSystem().getPath().getBytes(StandardCharsets.UTF_8);
        String s = new String(bytes);
        session.getControlConnection().writeSequence(s);
        session.getControlConnection().writeSequence("\r\n");
        session.getControlConnection().flush();
    }

}
