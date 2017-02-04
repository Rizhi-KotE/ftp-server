package core;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Connection {
    final static Logger log = Logger.getLogger(Connection.class);

    private final BufferedInputStream bis;
    private final BufferedOutputStream bos;
    private Socket socket;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        bis = new BufferedInputStream(socket.getInputStream());
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
        Scanner scanner = new Scanner(bis);
        String message = scanner.nextLine();
        log.debug(message);
        return message;
    }

    public void close() throws IOException {
        log.debug("CLOSE CONNECTION");
        socket.close();
    }

    public void write(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[0xFF];
        BufferedInputStream input = new BufferedInputStream(inputStream);
        int readen;
        StringBuilder builder = new StringBuilder();
        while ((readen = input.read(bytes)) != -1) {
            String s = new String(bytes, 0, readen);
            builder.append(s);
        }
        builder.append("\r\n");
        write(builder.toString());
    }

    public String read() throws IOException {
        byte[] bytes = new byte[0xFF];
        int readen;
        StringBuilder stringBuilder = new StringBuilder();
        while ((readen = bis.read()) != -1) {
            String s = new String(bytes, 0, readen);
            stringBuilder.append(s);
        }
        log.debug(String.format("---> %s", stringBuilder.toString()));
        return stringBuilder.toString();
    }
}
