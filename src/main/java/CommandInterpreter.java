import commands.Connection;
import commands.FtpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static java.util.Optional.ofNullable;

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
            writeMessage("220 \n");
            while (!stopped)
                executeCommand();
        } catch (NoSuchElementException e) {
            log.debug("connection is closed");
        } catch (NotImplementedFunctionException e) {
            log.info(String.format("command is not implemented - [%s]", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stopInterpreter();
            log.debug("inputStream is destroyed");
        }
    }

    public void executeCommand() throws Exception {
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
    }

    private void quitCommand(String[] args) throws IOException {
        writeMessage("221 \n");
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