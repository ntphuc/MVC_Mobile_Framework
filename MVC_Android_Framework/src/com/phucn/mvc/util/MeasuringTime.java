
package com.phucn.mvc.util;

public class MeasuringTime {
	// thoi diem bat dau tinh thoi gian render
	private static long startTimeRender;
	// thoi diem can tinh thoi gian render
	private static long endTimeRender;
	// thoi diem bat dau tinh thoi gian parser
	private static long startTimeParser;
	// thoi diem can tinh thoi gian parser
	private static long endTimeParser;
	
	public static long getStartTimeRender(){
		startTimeRender = System.currentTimeMillis();
		return startTimeRender;
	}
	

	public static long getTimeRender(){
		return endTimeRender - startTimeRender;
	}
	
	public static void getEndTimeRender(){
		endTimeRender = System.currentTimeMillis();
	}

	public static long getStartTimeParser(){
		startTimeParser = System.currentTimeMillis();
		return startTimeParser;
	}

	public static long getTimeParser(){
		return endTimeParser - startTimeParser;
	}
	
	
	public static void getEndTimeParser(){
		endTimeParser = System.currentTimeMillis();
	}
}
