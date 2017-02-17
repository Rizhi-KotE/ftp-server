package rk.core;

import rk.exceptions.FTPError530Exception;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FtpSessionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private FtpSession ftpSession;

    @Test
    public void userWithNotPasswordEnteredIsNotLogged() throws IOException {
        ftpSession.putUser("USER");
        assertFalse(ftpSession.isLogged());
    }

    @Before
    public void startUp() throws IOException {
        Connection any = Mockito.any(Connection.class);
        ftpSession = new FtpSession(any);

    }

    @Test
    public void onlyAnonymousMayLoggedWithAnyPassword() throws Exception {
        ftpSession.putUser("anonymous");
        ftpSession.putPassword("anyPasswordMayBeHere");
        assertTrue(ftpSession.isLogged());
    }

    @Test
    public void whenUserLogoutSessionIsNotLogged() throws Exception {
        ftpSession.putUser("anonymous");
        ftpSession.putPassword("anyPasswordMayBeHere");
        ftpSession.logout();
        assertFalse(ftpSession.isLogged());
    }

    @Test(expected = FTPError530Exception.class)
    public void throwNotLoggedExceptionWhenIncorrectPass() throws Exception {
        ftpSession.putUser("user");

        ftpSession.putPassword("***");
        thrown.expect(FTPError530Exception.class);
    }
}