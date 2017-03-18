package rk.core;

import org.apache.ftpserver.filesystem.nativefs.NativeFileSystemFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.ServerSocket;
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
    public void initializeTest() throws IOException, FtpException {

        PipedInputStream is = new PipedInputStream();
        PipedOutputStream os = new PipedOutputStream();

        Socket mock = Mockito.mock(Socket.class);
        when(mock.getInputStream()).thenReturn(is);
        when(mock.getOutputStream()).thenReturn(os);
        connection = new Connection(mock);
        BaseUser baseUser = new BaseUser();
        baseUser.setHomeDirectory(".");
        FtpSession ftpSession = new FtpSession(connection, new NativeFileSystemFactory().createFileSystemView(baseUser));
        commandInterpreter = new CommandInterpreter(ftpSession);

        outStream = new BufferedReader(new InputStreamReader(new PipedInputStream(os)));
        in = new DataOutputStream(new PipedOutputStream(is));
    }

    @Test
    public void testNameWithWhiteSpaces() throws Exception {
        in.write("CWD /System Volume\n".getBytes(StandardCharsets.UTF_8));
        String s = connection.readLine();
        assertEquals("CWD /System Volume", s);
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

    @Test
    public void checkSocketAddress() throws IOException {
        ServerSocket serverSocket = new ServerSocket(0);
//        serverSocket.accept();
        System.out.println(serverSocket.getLocalSocketAddress());//.getHostAddress());
    }

    @After
    public void tearDown() throws Exception {
        in.close();
        outStream.close();
    }
}