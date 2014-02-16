package com.phucn.mvc.controller;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.phucn.mvc.constants.ActionEventConstant;
import com.phucn.mvc.lib.network.NetworkUtil;
import com.phucn.mvc.model.HomeModel;
import com.phucn.mvc.model.ServerPath;
import com.phucn.mvc.view.PlaylistItemView;
import com.phucn.mvc.view.PlaylistView;

public class HomeController extends AbstractController {

	static HomeController instance;

	protected HomeController() {
	}

	public static HomeController getInstance() {
		if (instance == null) {
			instance = new HomeController();
		}
		return instance;
	}

	public void sendActionEvent(ActionEvent e) {
		
		String method = "";
		switch (e.action) {
		case ActionEventConstant.GET_LIST_CATEGORY:
			method = "getListCategory";
			break;
		case ActionEventConstant.GET_LIST_PLAYLIST:
			method = "search";
			break;
		case ActionEventConstant.GET_LIST_PLAYLIST_ITEM:
			method = "playlistItems";
			break;
		}

		try {
			Vector<String> info = (Vector<String>) e.viewData;
			String url = ServerPath.SERVER_PATH
					+ NetworkUtil.createStringURL(method, info);
			HomeModel.getInstance().sendHttpRequest(url, e);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void handleSwitchView(ActionEvent e) {
		// TODO Auto-generated method stub
		Activity base = (Activity) e.sender;
		Intent intent;
		Bundle extras;
		switch (e.action) {
		case ActionEventConstant.GOTO_PLAY_LIST:
			intent = new Intent(base, PlaylistView.class);
			extras = (Bundle) e.viewData;
			intent.putExtras(extras);
			base.startActivity(intent);
			break;
		case ActionEventConstant.GOTO_PLAY_LIST_ITEM:
			intent = new Intent(base, PlaylistItemView.class);
			extras = (Bundle) e.viewData;
			intent.putExtras(extras);
			base.startActivity(intent);
			break;
		default:
			break;
		}
	}

}
