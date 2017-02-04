package commands;

import core.Connection;
import core.FtpSession;
import exceptions.NoSuchMessageException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static utils.MessageFactory.getMessage;

public class ListCommandTask implements Command {

    private final FtpSession ftpSession;
    private final String[] args;
    static final Logger log = Logger.getLogger(ListCommandTask.class);

    public ListCommandTask(FtpSession ftpSession, String[] args) {
        this.ftpSession = ftpSession;
        this.args = args;
    }


    @Override
    public void execute() throws IOException, NoSuchMessageException {
        try {
            List<File> files = ftpSession.getFileSystem().getFilesList();
            if (args.length >= 1) files = ftpSession.getFileSystem().getFileList(args[0]);

            ftpSession.getControlConnection().write(getMessage("150"));
            Connection dataConnection = ftpSession.getDataConnection();
            for (File f : files) {
                String s = makeLsString(f);
                dataConnection.writeSequence(s);
            }
            dataConnection.flush();
//            Process exec = Runtime.getRuntime().exec(String.format("ls -l --time-style=iso %s", dir.getAbsolutePath()));
//            exec.waitFor();
//            InputStream outputStream = exec.getInputStream();
//            Connection dataConnection = ftpSession.getDataConnection();
//            dataConnection.write(outputStream);
            ftpSession.getControlConnection().write(getMessage("226"));
            dataConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //copypaste from
    //https://github.com/mikeweib/Android_FTP/blob/master/src/com/swiftp/CmdLIST.java
    public final static long MS_IN_SIX_MONTHS = 6 * 30 * 24 * 60 * 60 * 1000;

    protected String makeLsString(File file) throws NoSuchFileException {
        StringBuilder response = new StringBuilder();

        if (!file.exists()) {

            log.debug("makeLsString had nonexistent file");
            throw new NoSuchFileException(file.toString());
        }

        // See Daniel Bernstein's explanation of /bin/ls format at:
        // http://cr.yp.to/ftp/list/binls.html
        // This stuff is almost entirely based on his recommendations.

        String lastNamePart = file.getName();
        // Many clients can't handle files containing these symbols
        if (lastNamePart.contains("*") || lastNamePart.contains("/")) {
            log.debug("Filename omitted due to disallowed character");
            throw new NoSuchFileException(file.toString());
        } else {
            // The following line generates many calls in large directories
            // staticLog.l(Log.DEBUG, "Filename: " + lastNamePart);
        }

        if (file.isDirectory()) {
            response.append("drwxr-xr-x 1 owner group");
        } else {
            // todo: think about special files, symlinks, devices
            response.append("-rw-r--r-- 1 owner group");
        }

        // The next field is a 13-byte right-justified space-padded file size
        long fileSize = file.length();
        String sizeString = String.valueOf(fileSize);
        int padSpaces = 13 - sizeString.length();
        while (padSpaces-- > 0) {
            response.append(' ');
        }
        response.append(sizeString);

        // The format of the timestamp varies depending on whether the mtime
        // is 6 months old
        long mTime = file.lastModified();
        SimpleDateFormat format;
        // Temporarily commented out.. trying to fix Win7 display bug
        if (System.currentTimeMillis() - mTime > MS_IN_SIX_MONTHS) {
            // The mtime is less than 6 months ago
            format = new SimpleDateFormat(" MMM dd HH:mm ", Locale.US);
        } else {
            // The mtime is more than 6 months ago
            format = new SimpleDateFormat(" MMM dd  yyyy ", Locale.US);
        }
        response.append(format.format(new Date(file.lastModified())));
        response.append(lastNamePart);
        response.append("\r\n");
        return response.toString();
    }

}
