package commands;

import core.FtpSession;
import exceptions.FtpErrorReplyException;
import exceptions.NoSuchMessageException;

import java.io.IOException;

import static java.lang.Integer.parseInt;
import static utils.MessageFactory.getMessage;

/**
 * Created by Дмитрий on 10.02.2017.
 */
public class EPRTCommand implements Command {
    private final FtpSession session;
    private final String[] args;

    //by specification, should to be an askii characters with codes from 33 to 126 inclusive
    private static final String DELIMITER = "\\|";

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
        session.getControlConnection().write(getMessage("200"));
    }
}
