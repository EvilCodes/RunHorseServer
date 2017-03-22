package cn.lyj.hero.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
	public static void write(String str,File file){
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file,true);
			fos.write(str.getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
