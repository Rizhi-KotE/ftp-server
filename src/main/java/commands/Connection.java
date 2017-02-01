package commands;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Connection {
    private final BufferedReader reader;
    private Socket socket;
    private final BufferedOutputStream bos;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bos = new BufferedOutputStream(socket.getOutputStream());
    }

    public void write(String message) throws IOException {
        bos.write(message.getBytes());
    }

    public void flush() throws IOException {
        bos.flush();
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }
}
