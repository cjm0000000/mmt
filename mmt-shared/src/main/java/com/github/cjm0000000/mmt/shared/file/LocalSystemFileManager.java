package com.github.cjm0000000.mmt.shared.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.file.FileManager;

/**
 * Local system file manager
 * 
 * @author lemon
 * @version 1.1
 *
 */
@Service("localSystemFileManager")
public class LocalSystemFileManager implements FileManager {

	@Override
	public void writeFile(String path, String fileName, byte[] bytes) {
		File fp = new File(path);
		if(!fp.exists())
			fp.mkdirs();
		try (OutputStream os = new FileOutputStream(new File(path + File.separator + fileName))) {
			os.write(bytes);
		} catch (IOException e) {
			throw new MmtException("写入文件失败。", e.getCause());
		}
	}

	@Override
	public byte[] readFile(String path, String fileName) {
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path + File.separator + fileName))) {
			try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
				byte[] temp = new byte[1024];
				int size = 0;
				while ((size = in.read(temp)) != -1)
					out.write(temp, 0, size);
				return out.toByteArray();
			}
		} catch (IOException e) {
			throw new MmtException("读取文件失败。", e.getCause());
		}
	}

}
