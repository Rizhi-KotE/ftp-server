import org.apache.log4j.Logger;

import java.io.*;
import java.util.NoSuchElementException;

public class CommandInterpreter {
    static final Logger log = Logger.getLogger(CommandInterpreter.class);

    private final DataOutputStream dos;
    private final BufferedReader is;
    private boolean stopped = false;

    public CommandInterpreter(InputStream is, OutputStream os) {
        this.is = new BufferedReader(new InputStreamReader(is));
        dos = new DataOutputStream(os);
    }

    public void run() {
        try {
            writeMessage("220 \n");
            while (!stopped)
                executeCommand();
        } catch (NoSuchElementException e) {
            log.debug("connection is closed");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stopInterpreter();
            log.debug("inputStream is destroyed");
        }
    }

    public void executeCommand() throws IOException {
        String[] tokens = is.readLine().split(" ");
        if (tokens.length < 1) return;
        if (tokens[0].equals("QUIT")) {
            quitCommand(tokens);
        } else if (tokens[0].equals("USER")) {
            userCommand(tokens);
        } else if (tokens[0].equals("PASS")) {
            passCommand(tokens);
        } else if (tokens[0].equals("SYST")) {
            systCommand(tokens);
        } else if (tokens[0].equals("FEAT")) {
            featCommand(tokens);
        } else if (tokens[0].equals("LIST")) {
            listCommand(tokens);
        } else {
            log.debug(String.format("command is not implemented - [%s]", tokens[0]));
            writeMessage("502 \n");
        }
    }

    private void listCommand(String[] tokens) throws IOException {
        if (tokens.length == 1) {

        } else if (tokens.length == 2) {

        }
    }

    private void featCommand(String[] tokens) throws IOException {
        //TODO send all implemented commands list??
        writeMessage("211-Features\n");
        writeMessage("PASV\n");
        writeMessage("LIST\n");
        writeMessage("211 End\n");
    }

    private void systCommand(String[] tokens) throws IOException {
        //TODO ??
        writeMessage("215 UNIX\n");
    }

    private void passCommand(String[] tokens) throws IOException {
        writeMessage("230 \n");
    }

    private void quitCommand(String[] tokens) throws IOException {
        writeMessage("221 \n");
        stopInterpreter();
    }

    private void stopInterpreter() {
        stopped = true;
    }

    private void userCommand(String[] tokens) throws IOException {
        if (tokens.length <= 1) {
            writeMessage("504 \n");
        } else {
            String param = tokens[1];
            writeMessage("331 \n");
        }

    }

    void writeMessage(String message) throws IOException {
        dos.writeBytes(message);
        dos.flush();
    }
}