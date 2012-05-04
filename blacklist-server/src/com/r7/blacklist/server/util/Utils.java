package com.r7.blacklist.server.util;

import java.io.UnsupportedEncodingException;

public class Utils {

	public static String decoderText(String text) {
		try {
			byte[] bytes = text.getBytes("ISO-8859-1");
			text = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return text;
		}

		return text;
	}

}
