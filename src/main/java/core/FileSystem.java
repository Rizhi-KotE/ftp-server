package core;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FileSystem {
    static final Logger log = Logger.getLogger(FileSystem.class);

    private final File localRoot;

    private File currentDir;

    public FileSystem(File rootDirectory) {
        localRoot = rootDirectory;
        currentDir = localRoot;
        log.debug(String.format("current directory [%s]", localRoot));
    }

    public void changeDir(String s) throws IOException {
        File newDir = currentDir.toPath().resolve(s).toFile().getCanonicalFile();
        if (!newDir.exists()) {
            throw new NoSuchFileException(newDir.getAbsolutePath());
        }
        currentDir = newDir;
    }

    public boolean createFile(String fileName) throws IOException {
        return currentDir.toPath().resolve(fileName).toFile().createNewFile();
    }

    public List<String> getLsFileList() throws NotDirectoryException, NoSuchFileException {
        return getLsFileList(currentDir.getAbsolutePath());
    }

    public List<String> getLsFileList(String dir) throws NotDirectoryException, NoSuchFileException {
        File file = getLocalFile(dir);
        log.debug(String.format("file list from directory [%s]", file.getAbsolutePath()));
        return getFileList(file).stream().map(this::makeLsString).collect(Collectors.toList());
    }

    private List<File> getFileList(File file) throws NotDirectoryException {
        if (file.isDirectory()) return Arrays.asList(file.listFiles());
        else throw new NotDirectoryException(file.getAbsolutePath());
    }

    private File getLocalFile(String arg) throws NoSuchFileException {
        File file = currentDir.toPath().resolve(arg).toFile();
        if (file.exists())
            return file;
        else
            throw new NoSuchFileException(file.getAbsolutePath());
    }

    //copypaste from
    //https://github.com/mikeweib/Android_FTP/blob/master/src/com/swiftp/CmdLIST.java
    public final static long MS_IN_SIX_MONTHS = 6 * 30 * 24 * 60 * 60 * 1000;

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

    public String getPath() {
        return currentDir.getAbsolutePath();
    }

    public OutputStream getFileOutputStream(String arg) throws IOException {
        File localFile = getLocalFile(arg);
        return new FileOutputStream(localFile);
    }

    public InputStream getFileInputStream(String arg) throws NoSuchFileException, FileNotFoundException {
        return new FileInputStream(getLocalFile(arg));
    }
}
