package rk.commands;

import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;

import java.io.IOException;

/**
 * Should encapsulate arguments of command and logic of it execution
 */
public interface Command {
    void execute() throws IOException, FtpErrorReplyException, NoSuchMessageException;
}
