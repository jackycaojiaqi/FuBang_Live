package com.xlg.android.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Tools {

	// 字符串转byte
	public static void String2ByteArrayUTF(byte [] out, String in) {
		String2ByteArray(out, in, "utf-8");
	}
	
	public static void String2ByteArrayGBK(byte [] out, String in) {
		String2ByteArray(out, in, "gbk");
	}
	
	public static void String2ByteArray(byte[] out, String in, String charset) {
		try {
			// 判断是否为空
			if(null == in) {
				for(int i = 0; i < out.length; i++) {
					out[i] = 0;
				}
				
				return;
			}
			
			byte[] b = in.getBytes(charset);
			// copy value
			for(int i = 0; i < out.length; i++) {
				if(i < b.length) {
					out[i] = b[i];
				} else {
					out[i] = 0;
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			for(int i = 0; i < out.length; i++) {
				out[i] = 0;
			}
		}
	}
	
	// byte转字符串
	public static String ByteArray2StringUTF(byte [] in) {
		return ByteArray2String(in,"utf-8");
	}
	
	public static String ByteArray2StringGBK(byte [] in) {
		return ByteArray2String(in, "gbk");
	}
	
	public static String ByteArray2String(byte [] in, String charset) {
		try {
			int len = in.length;
			for(int i = 0; i < in.length; i++) {
				if(in[i] == 0) {
					len = i;
					break;
				}
			}
			
			if(len == 0) {
				return "";
			}
			
			if(len == in.length) {
				return new String(in, charset);
			} else {
				byte [] b = new byte[len];
				System.arraycopy(in, 0, b, 0, len);
				return new String(b, charset);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static void PrintObject(Object obj) {
		Class clazz = obj.getClass();

		Method[] methods = clazz.getMethods();
		for(int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String name = method.getName();
			// 判断是否为get开头
			if(false == name.startsWith("get")) {
				continue;
			}
			name.substring(3);
			try {
				System.out.println("Class: " + clazz.getName());
				System.out.println("    =>" + name.substring(3) +
						": " + method.invoke(obj));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
