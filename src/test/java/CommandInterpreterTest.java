import org.junit.After;
import rk.core.Connection;
import rk.core.FtpSession;
import rk.core.CommandInterpreter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static rk.utils.Messages.*;

public class CommandInterpreterTest {

    private DataOutputStream in;
    private CommandInterpreter commandInterpreter;
    private BufferedReader outStream;

    @Before
    public void initializeTest() throws IOException {

        PipedInputStream is = new PipedInputStream();
        PipedOutputStream os = new PipedOutputStream();

        Socket mock = Mockito.mock(Socket.class);
        when(mock.getInputStream()).thenReturn(is);
        when(mock.getOutputStream()).thenReturn(os);
        Connection connection = new Connection(mock);
        FtpSession ftpSession = new FtpSession(connection);
        commandInterpreter = new CommandInterpreter(ftpSession);

        outStream = new BufferedReader(new InputStreamReader(new PipedInputStream(os)));
        in = new DataOutputStream(new PipedOutputStream(is));
    }

    @After
    public void tearDown() throws Exception {
        in.close();
        outStream.close();
    }

    @Test
    public void quitCommandShouldCloseConnection() throws Exception {
        pushMessage("QUIT\n");
        commandInterpreter.executeCommand();
        assertMessageEqual(MESSAGE_221);
    }

    @Test
    public void userCommandWithoutParameterReply501() throws Exception {
        pushMessage("USER\n");
        commandInterpreter.executeCommand();
        assertMessageEqual("501 ");
    }

    @Test
    public void anonymousShouldAuthenticateWithAnyPassword() throws Exception {
        pushMessage("USER anonymous\n");
        commandInterpreter.executeCommand();
        assertMessageEqual(MESSAGE_331);
        pushMessage("PASS any\n");
        commandInterpreter.executeCommand();
        assertMessageEqual(MESSAGE_230);
    }

    @Test
    public void systCommandShouldReturnUNIX() throws Exception {
        pushMessage("SYST\n");
        commandInterpreter.executeCommand();
        assertMessageEqual("215 UNIX");
    }

    @Test
    public void featCommandShouldReturnUTF8() throws Exception {
        pushMessage("FEAT\n");
        commandInterpreter.executeCommand();
        List<String> strings = readMessage();
        System.out.println(strings);
        assertTrue(strings.contains(" UTF8"));
    }

    private List<String> readMessage() throws IOException {
        List<String> lines = new ArrayList<>();
        while (outStream.ready()) {
            lines.add(outStream.readLine());
        }
        return lines;
    }

    private void assertMessageEqual(String expected) throws Exception {
        if (outStream.ready()) {
            char[] arr = new char[expected.length()];
            outStream.read(arr);
            assertEquals(expected, new String(arr));
        } else
            throw new Exception("no reply");
    }

    private void pushMessage(String s) throws IOException {
        in.writeBytes(s);
        in.flush();
    }

}