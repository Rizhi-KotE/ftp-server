package core;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class FileSystemTest {

    private FileSystem fileSystem;

    @Before
    public void setUp() throws Exception {
        fileSystem = new FileSystem(new File("."));
    }

    @Test
    public void getFile() throws Exception {

    }

    @Test
    public void getPath() throws Exception {

    }

}