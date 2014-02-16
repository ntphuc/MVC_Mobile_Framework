package com.phucn.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.phucn.mvc.lib.json.JSONArray;
import com.phucn.mvc.lib.json.JSONException;
import com.phucn.mvc.lib.json.JSONObject;

public class PlaylistDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String playlistId;
	public String title;
	public String description;
	public String thumbUrl;
	public String category;


	public static List<PlaylistDTO> parseListPlayList(JSONArray response) {
		// TODO Auto-generated method stub
		List<PlaylistDTO> list = new ArrayList<PlaylistDTO>();
		for (int i = 0; i < response.length(); i++) {
			try {
				JSONObject playlist = response.getJSONObject(i);
				PlaylistDTO plDTO = new PlaylistDTO();
				plDTO.playlistId = playlist.getJSONObject("id").getString("playlistId");
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
