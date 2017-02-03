package core;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileSystem {
    private final File rootDirectory;

    private File currentDir;

    public FileSystem(File rootDirectory, File currentDir) {
        this.rootDirectory = rootDirectory;
        this.currentDir = currentDir;
    }

    public String getLocalPath(String dir1) {
        return rootDirectory.toPath().resolve(Paths.get(dir1)).toString();
    }

    public String getRemotePath() {
        String absolutePath = rootDirectory.toPath().resolve(currentDir.toPath()).toString();
        String path = rootDirectory.toPath().relativize(Paths.get(absolutePath)).toString();
        return "/" + path;
    }

    public String getLocalPath() {
        return currentDir.getAbsolutePath();
    }

    public void changeDir(String s) {
        Path path = Paths.get(s);
        if (path.isAbsolute()) {
            currentDir = Paths.get("/").relativize(path).toFile();
        } else {
            currentDir = currentDir.toPath().resolve(path).toFile();
        }
    }

    public List<File> getFilesList() {
        return Arrays.asList(new File(getLocalPath()));
    }

    public List<File> getFileList(String arg) {
        return Arrays.asList(getFile(arg).listFiles());
    }

    private File getFile(String arg) {
        return new File(getLocalPath(arg));
    }
}
