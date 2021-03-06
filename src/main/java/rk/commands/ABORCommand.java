package rk.commands;

import org.apache.ftpserver.ftplet.FtpException;
import rk.core.FtpSession;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;
import rk.utils.Messages;

import java.io.IOException;

import static rk.utils.Messages.MESSAGE_226;

public class ABORCommand implements Command {
    private final FtpSession session;
    private final String[] args;



    public ABORCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException, FtpException {
        session.stopTask();
        session.getControlConnection().write(MESSAGE_226);
    }
}
