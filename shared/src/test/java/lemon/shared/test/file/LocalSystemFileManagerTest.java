package lemon.shared.test.file;

import java.io.File;

import lemon.shared.file.FileManager;
import lemon.shared.file.LocalSystemFileManager;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LocalSystemFileManagerTest {
	private static final String ROOT = "C:\\TEMPS";
	private static final String PATH = "\\usr";
	private static final String FILENAME = "balabala.tmp";
	
	private FileManager fileManager;
	
	@Before
	public void init(){
		File file = new File(ROOT);
		if(!file.exists())
			file.mkdirs();
		fileManager = new LocalSystemFileManager();
	}
	
	@After
	public void destory(){
		File file = new File(ROOT + PATH +File.separator + FILENAME);
		if(file.exists())
			file.delete();
		file = new File(ROOT + PATH);
		if(file.exists())
			file.delete();
		file = new File(ROOT);
		if(file.exists())
			file.delete();
	}
	
	@Test
	public void test(){
		String str = "asflbo中文";
		//write
		write(str);
		//read
		byte[] bytes = read();
		assertEquals(str, new String(bytes));
	}
	
	private void write(String str){
		byte[] bytes = str.getBytes();
		fileManager.writeFile(ROOT+PATH, FILENAME, bytes);
	}
	
	private byte[] read(){
		return fileManager.readFile(ROOT+PATH, FILENAME);
	}

}
