package core;

import exceptions.RequestedActionNotTakenException;

import java.io.File;
import java.nio.file.NoSuchFileException;

public class FileSystem {
    private final File rootDirectory;

    public FileSystem(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public File getFile(String path) throws NoSuchFileException {
        String absolutePath = rootDirectory.getAbsolutePath();
        File file = new File(absolutePath.substring(0, absolutePath.length() - 2) + path);
        if (file.exists()) return file;
        else throw new NoSuchFileException(file.getAbsolutePath());
    }

    public String getPath(File file) throws RequestedActionNotTakenException {
        String absolutePath = file.getAbsolutePath();
        String root = rootDirectory.getAbsolutePath();
        if (absolutePath.contains(root)) return absolutePath.substring(root.length());
        else throw new RequestedActionNotTakenException(String.format("incorrect path - [%s]", absolutePath));
    }
}
