package rk.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class ConnectionTest {
    private DataOutputStream in;
    private CommandInterpreter commandInterpreter;
    private BufferedReader outStream;
    private Connection connection;

    @Before
    public void initializeTest() throws IOException {

        PipedInputStream is = new PipedInputStream();
        PipedOutputStream os = new PipedOutputStream();

        Socket mock = Mockito.mock(Socket.class);
        when(mock.getInputStream()).thenReturn(is);
        when(mock.getOutputStream()).thenReturn(os);
        connection = new Connection(mock);
        FtpSession ftpSession = new FtpSession(connection);
        commandInterpreter = new CommandInterpreter(ftpSession);

        outStream = new BufferedReader(new InputStreamReader(new PipedInputStream(os)));
        in = new DataOutputStream(new PipedOutputStream(is));
    }

    @Test
    public void checkMessageWithCharsetASKIITrue() throws Exception {
        in.write("list downloads\n".getBytes(StandardCharsets.US_ASCII));
        String s = connection.readLine();
        assertEquals("list downloads", s);
    }

    @Test
    public void checkMessageWithCharsetASKIIFalse() throws Exception {
        in.write("list загрузки\n".getBytes(StandardCharsets.US_ASCII));
        String s = connection.readLine();
        assertNotEquals("list загрузки", s);
    }

    @Test
    public void checkMessageWithCharsetUTF8() throws Exception {
        connection.setCharset(StandardCharsets.UTF_8.toString());
        in.write("list загрузки\n".getBytes(StandardCharsets.UTF_8));
        String s = connection.readLine();
        assertEquals("list загрузки", s);
    }

    @After
    public void tearDown() throws Exception {
        in.close();
        outStream.close();
    }
}