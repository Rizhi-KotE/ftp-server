package core;

import commands.CommandFactory;
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
    private final CommandFactory factory;
    private boolean stopped = false;

    public CommandInterpreter(FtpSession session, CommandFactory factory) {
        this.session = session;
        this.connection = session.getControlConnection();
        this.factory = factory;
    }

    public void run() {
        try {
            writeMessage(getMessage("220"));
            while (!stopped)
                executeCommand();
        } catch (NoSuchElementException e) {
            log.debug(e.getMessage());
            log.debug("connection is closed");
        } catch (NoSuchMessageException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
            if (tokens[0].equals("QUIT")) {
                quitCommand(args);
            } else {
                factory.get(tokens[0], args, session).execute();
            }
        } catch (FtpErrorReplyException e) {
            writeMessage(e.getReplyMessage());
            log.info(e.getReplyMessage());
        }
    }

    private void quitCommand(String[] args) throws IOException, NoSuchMessageException {
        writeMessage(getMessage("221"));
        stopInterpreter();
    }

    private void stopInterpreter() {
        stopped = true;
    }

    void writeMessage(String message) throws IOException {
        connection.write(message);
        connection.flush();
    }
}