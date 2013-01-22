package test;

import static org.junit.Assert.*;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import core.Directory;

public class DirectoryTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testDirectory() {
		Directory testDirectoryObject = new Directory("C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn");
		testDirectoryObject.pollDirectory();
		FileSystem fs = FileSystems.getDefault();
		Path testDirPath = fs.getPath("C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn");
		
		assertEquals("working", testDirectoryObject.getContainedFiles(),  Arrays.asList(testDirPath.toFile().listFiles()));

	}
	

}
