package rk.commands;

import org.apache.ftpserver.ftplet.FtpException;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;

import java.io.IOException;

public class ABORCommand implements Command{
    @Override
    public void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException, FtpException {

    }
}
