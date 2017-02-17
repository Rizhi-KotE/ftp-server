package rk.core;

import rk.commands.FTPCommands;
import rk.exceptions.FTPQuitException;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static rk.utils.MessageFactory.getMessage;

/**
 * Read commands from control connection and execute it.
 */
public class CommandInterpreter implements Runnable {
    static final Logger log = Logger.getLogger(CommandInterpreter.class);

    private final Connection connection;
    private final FtpSession session;
    private boolean stopped = false;

    /**
     * @param session - FTP session
     */
    public CommandInterpreter(FtpSession session) {
        this.session = session;
        this.connection = session.getControlConnection();
    }

    /**
     * run interpreter. Handle critic exceptions
     */
    public void run() {
        try {
            writeMessage(getMessage("220"));
            while (!stopped)
                executeCommand();
        } catch (NoSuchElementException e) {//connection closed
            log.debug("", e);
            log.info("connection is closed");
        } catch (Exception e) {
            log.debug(e);
        } finally {
            stopInterpreter();
            log.debug("inputStream is destroyed");
        }
    }


    private void executeCommand() throws Exception {
        try {
            String[] tokens = connection.readLine().split(" ");
            String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);
            if (tokens.length < 1) return;
            FTPCommands.createCommand(tokens[0], session, args).execute();
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

    private void stopInterpreter() {
        stopped = true;
    }

    /**
     * write message to control connection
     * @param message - surprisingly message
     * @throws IOException
     */
    private void writeMessage(String message) throws IOException {
        connection.write(message);
        connection.flush();
    }
}