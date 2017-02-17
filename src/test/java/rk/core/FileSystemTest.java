package rk.core;

import static junit.framework.TestCase.assertEquals;

public class FileSystemTest {

//    private FileSystem fileSystem;
//
//    @Before
//    public void setUp() throws Exception {
//        fileSystem = new FileSystem(new File("/dir1"));
//    }
//
//    @Test
//    public void getFile() throws Exception {
//        fileSystem = new FileSystem(new File("/").getAbsoluteFile());
//        fileSystem.changeDir("/dir2");
//        assertEquals("/home/rizhi-kote/Student/labs/AIPOS/ftp-server/dir2", fileSystem.getLocalPath());
//        assertEquals("/dir2", fileSystem.getRemotePath());
//    }
//
//    @Test
//    public void getPath() throws Exception {
//        assertEquals("/dir1/dir2", fileSystem.getLocalPath("dir2"));
//    }
//
//    @Test
//    public void getRootWhenCurentDirEqualsRootDir() throws Exception {
//        assertEquals("/", fileSystem.getRemotePath());
//    }
//
//    @Test
//    public void getRootWhenChangeToDirAndBack() throws Exception {
//        fileSystem.changeDir("src");
//        fileSystem.changeDir("../");
//        assertEquals("/", fileSystem.getRemotePath());
//    }
//
//    @Test
//    public void changeDirWithRelativePath() throws Exception {
//        fileSystem.changeDir(".git");
//        assertEquals("/.git", fileSystem.getRemotePath());
//    }
//
//    @Test
//    public void changeDirWithAbsolutePath() throws Exception {
//        fileSystem.changeDir("/.git");
//        assertEquals("/.git", fileSystem.getRemotePath());
//    }
//
//    @Test
//    public void changeDirWithRelativePathSrc() throws Exception {
//        fileSystem.changeDir("src");
//        assertEquals("/src", fileSystem.getRemotePath());
//    }
//
//    @Test
//    public void relativeBack() throws Exception {
//        FileSystem fileSystem = new FileSystem(new File("/usr").getAbsoluteFile());
//        fileSystem.changeDir("../");
//        assertEquals("/use", fileSystem.getLocalPath());
//    }
}