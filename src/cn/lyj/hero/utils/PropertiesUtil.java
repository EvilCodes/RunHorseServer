package cn.lyj.hero.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	
	public static String getValue(String key){
		String value = null;
		Properties p = new Properties();
		String path = PropertiesUtil.class.getResource("/").getPath()+"path.properties";
		File file = new File(path);
		try {
			p.load(new FileInputStream(file));
			value = p.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
}
