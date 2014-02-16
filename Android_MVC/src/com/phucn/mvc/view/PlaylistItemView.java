package com.phucn.mvc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.phucn.mvc.R;
import com.phucn.mvc.constants.ActionEventConstant;
import com.phucn.mvc.constants.IntentConstants;
import com.phucn.mvc.controller.ActionEvent;
import com.phucn.mvc.controller.HomeController;
import com.phucn.mvc.dto.PlaylistDTO;
import com.phucn.mvc.dto.PlaylistItemDTO;
import com.phucn.mvc.model.ModelEvent;
import com.phucn.mvc.model.ServerPath;
import com.phucn.mvc.view.cell.PlaylistItemCell;

public class PlaylistItemView extends BaseActivity implements OnItemClickListener {

	public List<PlaylistItemDTO> listPlaylist = new ArrayList<PlaylistItemDTO>();

	private StandardArrayAdapter arrayAdapter;



	private ListView listView;

	private PlaylistDTO playlistDTO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_categories);

		listView = (ListView) findViewById(R.id.list_view);
		listView.setOnItemClickListener(this);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			playlistDTO = (PlaylistDTO) bundle
					.getSerializable(IntentConstants.INTENT_PLAYLIST_DTO);

			sendActionEvent();

		}
	}

	public void sendActionEvent() {
		Vector<String> vt = new Vector<String>();
		vt.add(IntentConstants.INTENT_PLAYLIST_ID);
		vt.add(playlistDTO.playlistId);
		vt.add(IntentConstants.INTENT_KEY);
		vt.add(ServerPath.KEY);
		vt.add(IntentConstants.INTENT_PART);
		vt.add("snippet");
		vt.add(IntentConstants.MAX_RESULTS);
		vt.add("20");
		ActionEvent e = new ActionEvent();
		e.action = ActionEventConstant.GET_LIST_PLAYLIST_ITEM;
		e.viewData = vt;
		e.sender = PlaylistItemView.this;
		HomeController.getInstance().sendActionEvent(e);
	}

	@Override
	public void onReceiveSuccess(ModelEvent modelEvent) {
		// TODO Auto-generated method stub
		ActionEvent e = modelEvent.getActionEvent();
		switch (e.action) {
		case ActionEventConstant.GET_LIST_PLAYLIST_ITEM:
			displayData(modelEvent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onReceiveError(ModelEvent modelEvent) {
		// TODO Auto-generated method stub
		super.onReceiveError(modelEvent);
	}

	public void displayData(ModelEvent modelEvent) {
		// TODO Auto-generated method stub
		listPlaylist= (List<PlaylistItemDTO>) modelEvent
				.getModelData();
		arrayAdapter = new StandardArrayAdapter(listPlaylist);
		listView.setAdapter(arrayAdapter);

	}

	private class StandardArrayAdapter extends BaseAdapter {

		private List<PlaylistItemDTO> dataSource = new ArrayList<PlaylistItemDTO>();

		public StandardArrayAdapter(final List<PlaylistItemDTO> items) {
			super();
			this.dataSource = items;
		}

		@Override
		public View getView(final int position, View view,
				final ViewGroup parent) {
			PlaylistItemCell cell;
			if (position < dataSource.size()) {
				if (view == null) {
					cell = new PlaylistItemCell(parent.getContext());
					view = cell;
				} else {
					cell = (PlaylistItemCell) view;
				}
				PlaylistItemDTO item = ((PlaylistItemDTO) getItem(position));
				cell.updateData( item);

			}
			return view;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataSource.size();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int i) {
			// TODO Auto-generated method stub
			return dataSource.get(i);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		PlaylistItemDTO item = (PlaylistItemDTO) (listPlaylist
				.get(arg2));
		Intent videoClient = new Intent(Intent.ACTION_VIEW);
		videoClient.setData(Uri.parse("http://youtube.com/watch?v="
				+ item.videoId));
		startActivityForResult(videoClient, 1234);
	}

}
