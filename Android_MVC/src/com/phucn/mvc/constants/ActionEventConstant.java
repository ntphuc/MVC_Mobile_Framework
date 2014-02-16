package com.phucn.mvc.constants;

public class ActionEventConstant {
	public static final int ERROR_CODE_SUCCESS = 200;
	public static final int INVALID = -1;

	//id action Event
	public static final int GET_LIST_CATEGORY = 0;
	public static final int GET_LIST_PLAYLIST = 1;
	public static final int GET_LIST_PLAYLIST_ITEM = 2;
	
	public static String getRequestName(int actionEventId){
		String name="";
		switch (actionEventId) {
		case GET_LIST_CATEGORY:
			name = "getListCategory";
			break;
		case GET_LIST_PLAYLIST:
			name = "search";
			break;
		case GET_LIST_PLAYLIST_ITEM:
			name = "playlistItems";
			break;
		}
		return name;
	}
	
	// switch view event
	public static final int GOTO_PLAY_LIST = 0;	
	public static final int GOTO_PLAY_LIST_ITEM = 1;
	public static final int GOTO_VIDEOS_VIEW = 2;
}
