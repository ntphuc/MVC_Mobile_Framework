package com.phucn.mvc.util;

import com.phucn.mvc.common.ApplicationInfo;


public class StringUtil {
	
	public static String EMPTY_STRING = "";
	public static String SPACE_STRING = " ";



	public static boolean isNullOrEmpty(String aString) {
		return (aString == null) || ("".equals(aString.trim()));
	}

	
	public static String getString(int id) {
		return ApplicationInfo.getInstance().getAppContext().getResources()
				.getString(id);
	}

	
}
