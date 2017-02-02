package commands;

import exceptions.FtpErrorReplyException;

import java.io.IOException;

public interface Command {
    void execute() throws IOException, FtpErrorReplyException;
}
