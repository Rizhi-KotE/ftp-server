package core;

import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.util.Arrays;
import java.util.List;

public class FileSystem {
    static final Logger log = Logger.getLogger(FileSystem.class);

    private final File localRoot;

    private File currentDir;

    public FileSystem(File rootDirectory) {
        localRoot = rootDirectory;
        currentDir = localRoot;
        log.debug(String.format("current directory [%s]", localRoot));
    }

    public void changeDir(String s) throws NoSuchFileException {
        File newDir = currentDir.toPath().resolve(s).toFile();
        if (!newDir.exists()) {
            throw new NoSuchFileException(newDir.getAbsolutePath());
        }
        currentDir = newDir;
    }

    public List<File> getFilesList() throws NotDirectoryException {
        return getFileList(currentDir);
    }

    public List<File> getFileList(String arg) throws NotDirectoryException, NoSuchFileException {
        File file = getLocalFile(arg);
        log.debug(String.format("file list from directory [%s]", file.getAbsolutePath()));
        return getFileList(file);
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

    public String getRemotePath() {
        return currentDir.getAbsolutePath();
    }
}
