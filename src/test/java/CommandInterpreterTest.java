import commands.FtpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class CommandInterpreterTest {

    private DataOutputStream in;
    private CommandInterpreter commandInterpreter;
    private InputStream outStream;

    @Before
    public void initializeTest() throws IOException {

        PipedInputStream is = new PipedInputStream();
        PipedOutputStream os = new PipedOutputStream();

        FtpSession any = Mockito.any(FtpSession.class);
        commandInterpreter = new CommandInterpreter(any);

        outStream = new PipedInputStream(os);
        in = new DataOutputStream(new PipedOutputStream(is));
    }

    @Test
    public void quitCommandShouldCloseConnection() throws Exception {
        pushMessage("QUIT\n");
        commandInterpreter.executeCommand();
        assertMessageEqual("221 \n");
    }

    @Test
    public void userCommandWithoutParameterReply504() throws Exception {
        pushMessage("USER\n");
        commandInterpreter.executeCommand();
        assertMessageEqual("504 \n");
    }

    @Test
    public void anonymousShouldAuthenticateWithAnyPassword() throws Exception {
        pushMessage("USER anonymous\n");
        commandInterpreter.executeCommand();
        assertMessageEqual("331 \n");
        pushMessage("PASS any\n");
        commandInterpreter.executeCommand();
        assertMessageEqual("230 \n");
    }

    @Test
    public void systCommandShouldReturnUNIX() throws Exception {
        pushMessage("SYST\n");
        commandInterpreter.executeCommand();
        assertMessageEqual("215 UNIX\n");
    }

    @Test
    public void featCommand() throws Exception {
        pushMessage("FEAT\n");
        commandInterpreter.executeCommand();
        assertMessageEqual("211-Features\n");
        assertMessageEqual("PASV\n");
        assertMessageEqual("LIST\n");
        assertMessageEqual("211 End\n");

    }

    private void assertMessageEqual(String expected) throws Exception {
        if (outStream.available() != 0) {
            byte[] arr = new byte[expected.length()];
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