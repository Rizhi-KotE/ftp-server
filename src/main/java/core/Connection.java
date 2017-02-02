package core;

import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection {
    final static Logger log = Logger.getLogger(Connection.class);

    private final BufferedReader reader;
    private Socket socket;
    private final BufferedOutputStream bos;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bos = new BufferedOutputStream(socket.getOutputStream());
    }

    public void write(String message) throws IOException {
        bos.write(message.getBytes(StandardCharsets.US_ASCII));
        log.debug(message);
        bos.flush();
    }

    public void writeSequence(String message) throws IOException {
        log.debug(message);
        bos.write(message.getBytes(StandardCharsets.US_ASCII));
    }

    public void flush() throws IOException {
        bos.flush();
    }

    public String readLine() throws IOException {
        String message = reader.readLine();
        log.debug(message);
        return message;
    }

    public void close() throws IOException {
        log.debug("CLOSE CONNECTION");
        socket.close();
    }
}
