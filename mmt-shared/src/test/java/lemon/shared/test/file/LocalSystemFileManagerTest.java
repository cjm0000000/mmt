package lemon.shared.test.file;

import java.io.File;

import lemon.shared.file.FileManager;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.cjm0000000.shared.test.AbstractTester;

public class LocalSystemFileManagerTest extends AbstractTester {
	private static final String ROOT = LocalSystemFileManagerTest.class.getResource("/").getPath();
	private static final String PATH = "\\usr";
	private static final String FILENAME = "balabala.tmp";
	@Autowired @Qualifier("localSystemFileManager")
	private FileManager fileManager;
	
	@Before
	public void init(){
		File file = new File(ROOT);
		if(!file.exists())
			file.mkdirs();
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
