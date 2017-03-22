package cn.lyj.hero.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class StringFormatUtils {
	public static String getUTF_8Str(HttpServletRequest request,String parameter){
		String str = null;
		try {
			str = new String(request.getParameter(parameter).getBytes("ISO-8859-1"),"UTF-8");
			return str;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	 public static <T> ArrayList<T> array2List(T[] array) {
	        List<T> list = Arrays.asList(array);
	        ArrayList newList = new ArrayList(list);
	        return newList;
	    }
}
