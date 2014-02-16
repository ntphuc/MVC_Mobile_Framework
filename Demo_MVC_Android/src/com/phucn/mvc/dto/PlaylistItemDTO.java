package com.phucn.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.phucn.mvc.lib.json.JSONArray;
import com.phucn.mvc.lib.json.JSONException;
import com.phucn.mvc.lib.json.JSONObject;

public class PlaylistItemDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String videoId;
	public String title;
	public String description;
	public String thumbUrl;
	public String playlistId;
	public String length;

	
	public static List<PlaylistItemDTO> parseListPlayListItems(JSONArray response) {
		// TODO Auto-generated method stub
		List<PlaylistItemDTO> list = new ArrayList<PlaylistItemDTO>();
		for (int i = 0; i < response.length(); i++) {
			try {
				JSONObject playlist = response.getJSONObject(i);
				PlaylistItemDTO plDTO = new PlaylistItemDTO();
				plDTO.videoId = playlist.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
				plDTO.title=playlist.getJSONObject("snippet").getString("title");
				list.add(plDTO);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list;
	}
}