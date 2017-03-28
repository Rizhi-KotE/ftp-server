package rk.core;

import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class FileSystem {

    public final static long MS_IN_SIX_MONTHS = 6 * 30 * 24 * 60 * 60 * 1000;
    static final Logger log = Logger.getLogger(FileSystem.class);
    private final FileSystemView fileSystem;
    private File currentDir;

    public FileSystem(FileSystemView fileSystem) throws FtpException {
            log.debug(format("New file system view created. Root dir [%s]",
                    fileSystem.getHomeDirectory().getAbsolutePath()));
            this.fileSystem = fileSystem;
    }

    public void changeDir(String s) throws IOException, FtpException {
        fileSystem.changeWorkingDirectory(s);
    }

    public InputStream fileInputSteam(String name) throws FtpException, IOException {
        return fileSystem.getFile(name).createInputStream(0);
    }

    public OutputStream fileOutputStream(String name) throws FtpException, IOException {
        return fileSystem.getFile(name).createOutputStream(0);
    }

    public List<String> getLsFileList() throws NotDirectoryException, NoSuchFileException, FtpException {

        return getLsFileList(fileSystem.getWorkingDirectory().getAbsolutePath());
    }

    public List<String> getLsFileList(String dir) throws NotDirectoryException, NoSuchFileException, FtpException {
        log.info(format("file list from directory [%s]", dir));
        FtpFile file = fileSystem.getFile(dir);
        if (!file.isDirectory()) throw new NotDirectoryException(dir);
        List<? extends FtpFile> ftpFiles = file.listFiles();
        if (ftpFiles == null) throw new NoSuchFileException(dir);
        return ftpFiles.stream()
                .map(o -> (File) o.getPhysicalFile())
                .filter(File::canRead)
                .filter(File::canWrite)
                .map(this::makeLsString).collect(Collectors.toList());
    }

    public File getLocalFile(String arg) {
        return currentDir.toPath().resolve(arg).toFile();
    }

    private String makeLsString(File file) {
        StringBuilder response = new StringBuilder();

        if (!file.exists()) {

            log.debug("makeLsString had nonexistent file");
            throw new IllegalArgumentException(file.toString());
        }

        // See Daniel Bernstein's explanation of /bin/ls format at:
        // http://cr.yp.to/ftp/list/binls.html
        // This stuff is almost entirely based on his recommendations.

        String lastNamePart = file.getName();
        // Many clients can't handle files containing these symbols
        if (lastNamePart.contains("*") || lastNamePart.contains("/")) {
            log.debug("Filename omitted due to disallowed character");
            throw new IllegalArgumentException(file.toString());
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

    public String getPath() throws FtpException {

        return fileSystem.getWorkingDirectory().getAbsolutePath();
    }
}
