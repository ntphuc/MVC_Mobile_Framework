
package com.phucn.mvc.util;

import android.util.Log;

public class LogMessage{

	public static boolean ISDEBUG;
	public static void d(String tag, String msg){
		if(ISDEBUG){
			Log.d(tag, msg);
		}
	}
	
	public static void d(String tag, String msg, Throwable tr){
		if(ISDEBUG){
			Log.d(tag, msg, tr);
		}
	}
	
	public static void e(String tag, String msg){
		if(ISDEBUG){
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag, String msg, Throwable tr) {
		if(ISDEBUG){
			Log.e(tag, msg, tr);
		}
	}
	
	public static void i(String tag, String msg){
		if(ISDEBUG){
			Log.i(tag, msg);
		}
	}
	
	public static void i(String tag, String msg, Throwable tr){
		if(ISDEBUG){
			Log.i(tag, msg, tr);
		}
	}	
	
	public static void v(String tag, String msg){
		if(ISDEBUG){
			Log.v(tag, msg);
		}
	}
	
	public static void v(String tag, String msg, Throwable tr){
		if(ISDEBUG){
			Log.v(tag, msg, tr);
		}
	}
	
	public static void w(String tag, String msg){
		if(ISDEBUG){
			Log.w(tag, msg);
		}
	}
	
	public static void w(String tag, String msg, Throwable tr){
		if(ISDEBUG){
			Log.w(tag, msg, tr);
		}
	}
}
