package rk.commands;

import org.apache.ftpserver.ftplet.FtpException;
import org.apache.log4j.Logger;
import rk.core.FtpSession;
import rk.exceptions.FTPError450Exception;
import rk.exceptions.FTPError501Exception;
import rk.exceptions.FtpErrorReplyException;
import rk.exceptions.NoSuchMessageException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;
import static rk.exceptions.ExceptionHandleUtils.handle;
import static rk.utils.Messages.MESSAGE_150;
import static rk.utils.Messages.MESSAGE_226;
import static rk.utils.Messages.MESSAGE_450;

/**
 * Syntax: RETR remote-filename<br>
 * Begins transmission of a file from the remote host.
 * Must be preceded by either a PORT command or a PASV command to indicate where the server should send data.
 */
public class RETRCommand implements Command {
    public static final Logger log = Logger.getLogger(RETRCommand.class);

    private String[] args;
    private FtpSession ftpSession;

    public RETRCommand(FtpSession session, String[] args) {
        ftpSession = session;
        this.args = args;
    }

    @Override
    public void execute() throws IOException, NoSuchMessageException, FTPError501Exception, FTPError450Exception {
        if (args.length >= 1) {
            args[0] = String.join(" ", args);
        } else {
            throw new FTPError501Exception("RETR", Arrays.toString(args));
        }
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(this::runWork);
        ftpSession.putTask(voidCompletableFuture);
    }

    private void runWork() {
        try {
            ftpSession.getControlConnection().write(MESSAGE_150);
            try (InputStream inputStream = ftpSession.getFileSystem().fileInputSteam(args[0])) {
                ftpSession.getDataConnection().writeFrom(inputStream);
                ftpSession.getDataConnection().close();
            }
            handleWorkEnd();
        } catch (NoSuchFileException e) {
            log.info(format("No such file [%s]", args[0]));
            log.trace("", e);
        } catch (Exception e) {
            handle(log, ftpSession, e);
        }
    }

    private void handleWorkEnd() throws IOException, FtpException, FTPError450Exception {
        if (Thread.interrupted()) {
            ftpSession.getFileSystem().removeFile(args[0]);
            ftpSession.getControlConnection().write(MESSAGE_450);
        } else {
            ftpSession.getControlConnection().write(MESSAGE_226);
        }
        ftpSession.stopTask();
    }
}
