package commands;

import exceptions.FtpErrorReplyException;
import exceptions.NoSuchMessageException;

import java.io.IOException;

public interface Command {
    void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException;
}
