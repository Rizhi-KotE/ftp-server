package rk.core;

import org.apache.ftpserver.filesystem.nativefs.NativeFileSystemFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertEquals;

public class FileSystemTest {

    private FileSystem fileSystem;

    @Before
    public void setUp() throws Exception {
        NativeFileSystemFactory nativeFileSystemFactory = new NativeFileSystemFactory();
        BaseUser baseUser = new BaseUser();
        baseUser.setHomeDirectory(".");
        fileSystem = new FileSystem(nativeFileSystemFactory.createFileSystemView(baseUser));
    }

    @Test
    public void getRootWhenChangeToDirAndBack() throws Exception {
        fileSystem.changeDir("src");
        fileSystem.changeDir("../");
        assertEquals("/", fileSystem.getPath());
    }

    @Test
    public void changeDirWithRelativePath() throws Exception {
        fileSystem.changeDir(".git");
        assertEquals("/.git", fileSystem.getPath());
    }

    @Test
    public void changeDirWithAbsolutePath() throws Exception {
        fileSystem.changeDir("/.git");
        assertEquals("/.git", fileSystem.getPath());
    }

    @Test
    public void changeDirWithRelativePathSrc() throws Exception {
        fileSystem.changeDir("src");
        assertEquals("/src", fileSystem.getPath());
    }
}