package core;

import commands.FTPCommands;
import exceptions.FTPError502Exception;
import exceptions.FTPQuitException;
import exceptions.FtpErrorReplyException;
import exceptions.NoSuchMessageException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static java.util.Optional.ofNullable;
import static utils.MessageFactory.getMessage;

public class CommandInterpreter {
    static final Logger log = Logger.getLogger(CommandInterpreter.class);

    private final Connection connection;
    private final FtpSession session;
    private boolean stopped = false;

    public CommandInterpreter(FtpSession session) {
        this.session = session;
        this.connection = session.getControlConnection();
    }

    public void run() {
        try {
            writeMessage(getMessage("220"));
            while (!stopped)
                executeCommand();
        } catch (NoSuchElementException e) {
            log.debug("", e);
            log.info("connection is closed");
        } catch (NoSuchMessageException e) {
            log.debug("No such message", e);
        } catch (Exception e) {
            log.debug(e);
        } finally {
            stopInterpreter();
            log.debug("inputStream is destroyed");
        }
    }

    public void executeCommand() throws Exception {
        try {
            String[] tokens = ofNullable(connection.readLine())
                    .orElseThrow(() -> new IOException("empty string"))
                    .split(" ");
            String[] args = Arrays.stream(tokens).skip(1).toArray(String[]::new);
            if (tokens.length < 1) return;
            executeCommand(tokens[0], args);
        } catch (FTPQuitException e) {
            log.debug("", e);
            writeMessage(e.getReplyMessage());
            log.info(e.getReplyMessage());
            stopInterpreter();
        } catch (FtpErrorReplyException e) {
            log.debug("", e);
            writeMessage(e.getReplyMessage());
            log.info(e.getReplyMessage());
        }
    }

    private void executeCommand(String command, String[] args) throws FtpErrorReplyException, IOException, NoSuchMessageException {
        try {
            FTPCommands.valueOf(command).createCommand(session, args).execute();
        } catch (IllegalArgumentException e) {
            throw new FTPError502Exception(command);
        }
    }

    private void stopInterpreter() {
        stopped = true;
    }

    void writeMessage(String message) throws IOException {
        connection.write(message);
        connection.flush();
    }
}