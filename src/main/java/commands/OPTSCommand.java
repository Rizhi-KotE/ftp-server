package commands;

import core.FtpSession;
import exceptions.FtpErrorReplyException;
import exceptions.NoSuchMessageException;

import java.io.IOException;

/**
 * Created by Дмитрий on 10.02.2017.
 */
public class OPTSCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException {
        session.getControlConnection().write("200 \r\n");
    }

    public OPTSCommand(FtpSession session, String[] args) {

        this.session = session;
        this.args = args;
    }
}
