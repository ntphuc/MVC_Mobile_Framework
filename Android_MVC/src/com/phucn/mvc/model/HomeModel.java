package com.phucn.mvc.model;

import java.util.List;
import com.phucn.mvc.constants.ActionEventConstant;
import com.phucn.mvc.constants.ErrorConstants;
import com.phucn.mvc.controller.ActionEvent;
import com.phucn.mvc.controller.HomeController;
import com.phucn.mvc.dto.PlaylistDTO;
import com.phucn.mvc.dto.PlaylistItemDTO;
import com.phucn.mvc.lib.json.JSONArray;
import com.phucn.mvc.lib.json.JSONObject;
import com.phucn.mvc.lib.network.HTTPMessage;
import com.phucn.mvc.lib.network.HTTPResponse;
import com.phucn.mvc.util.StringUtil;


public class HomeModel extends AbstractModelService {
	protected static HomeModel instance;

	protected HomeModel() {
	}

	public static HomeModel getInstance() {
		if (instance == null) {
			instance = new HomeModel();
		}
		return instance;
	}
	
	public void onReceiveError(HTTPResponse response) {
		ActionEvent actionEvent = (ActionEvent) response.getUserData();
		
		ModelEvent model = new ModelEvent();
		model.setDataText(response.getDataText());
		model.setParams(((HTTPResponse) response).getRequest().getDataText());
		model.setActionEvent(actionEvent);
		
		model.setModelCode(ErrorConstants.ERROR_NO_CONNECTION);
		model.setModelMessage(response.getErrMessage());
		HomeController.getInstance().onReceiveError(model);

	}
	
	public void onReceiveSuccess(HTTPMessage mes) {
		ActionEvent actionEvent = (ActionEvent) mes.getUserData();
		ModelEvent model = new ModelEvent();
		model.setDataText(mes.getDataText());
		model.setCode(mes.getCode());
		model.setParams(((HTTPResponse) mes).getRequest().getDataText());
		model.setActionEvent(actionEvent);
		if (StringUtil.isNullOrEmpty((String) mes.getDataText())) {
			model.setModelCode(ErrorConstants.ERROR_COMMON);
			HomeController.getInstance().onReceiveError(model);
			return;
		}
		JSONObject json;
		switch (mes.getAction()) {
		
		case ActionEventConstant.GET_LIST_PLAYLIST:
			
			try {
				json = new JSONObject((String) mes.getDataText());
				JSONObject result = json.getJSONObject("error");
				
				if (result == null) {
					JSONArray response = json.getJSONArray("items");
					List<PlaylistDTO> list = PlaylistDTO.parseListPlayList(response);
					model.setModelData(list);
					HomeController.getInstance().onReceiveSuccess(model);
				} else {
					model.setModelMessage(result.getString("message"));
					HomeController.getInstance().onReceiveError(model);
				}
			} catch (Exception ex) {
				model.setModelCode(ErrorConstants.ERROR_COMMON);
				HomeController.getInstance().onReceiveError(model);
			}
			break;
			
		case ActionEventConstant.GET_LIST_PLAYLIST_ITEM:
			
			try {
				json = new JSONObject((String) mes.getDataText());
				JSONObject result = json.getJSONObject("error");
				
				if (result == null) {
					JSONArray response = json.getJSONArray("items");
					List<PlaylistItemDTO> list = PlaylistItemDTO.parseListPlayListItems(response);
					model.setModelData(list);
					HomeController.getInstance().onReceiveSuccess(model);
				} else {
					model.setModelMessage(result.getString("message"));
					HomeController.getInstance().onReceiveError(model);
				}
			} catch (Exception ex) {
				model.setModelCode(ErrorConstants.ERROR_COMMON);
				HomeController.getInstance().onReceiveError(model);
			}
			break;
		}
	}
		
	

	



}
