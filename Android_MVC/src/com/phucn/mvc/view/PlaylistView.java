
package com.phucn.mvc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
import com.phucn.mvc.model.ModelEvent;
import com.phucn.mvc.model.ServerPath;
import com.phucn.mvc.view.cell.PlaylistCell;


public class PlaylistView extends BaseActivity implements OnItemClickListener {

	private StandardArrayAdapter arrayAdapter;



	private ListView listView;

	private String category;



	private List<PlaylistDTO> playList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_categories);
		
		listView = (ListView) findViewById(R.id.list_view);
		listView.setOnItemClickListener(this);
		Bundle bundle = getIntent().getExtras();
		if (bundle!=null){
			category = bundle.getString(IntentConstants.INTENT_CATEGORY);
		
		sendActionEvent();
		
		}
	}
	
	public void sendActionEvent() {
		Vector<String> vt = new Vector<String>();
		vt.add(IntentConstants.INTENT_CATEGORY);
		vt.add(category);
		vt.add(IntentConstants.INTENT_KEY);
		vt.add(ServerPath.KEY);
		vt.add(IntentConstants.INTENT_PART);
		vt.add("snippet");
		vt.add(IntentConstants.INTENT_TYPE);
		vt.add("playlist");
		vt.add(IntentConstants.MAX_RESULTS);
		vt.add("20");
		ActionEvent e = new ActionEvent();
		e.action = ActionEventConstant.GET_LIST_PLAYLIST;
		e.viewData = vt;
		e.sender = PlaylistView.this;
		HomeController.getInstance().sendActionEvent(e);
	}
	
	
	
	@Override
	public void onReceiveSuccess(ModelEvent modelEvent) {
		// TODO Auto-generated method stub
		ActionEvent e = modelEvent.getActionEvent();
		switch (e.action) {
		case ActionEventConstant.GET_LIST_PLAYLIST:
			displayData(modelEvent);
			break;
		default:
			break;
		}
	}
	@SuppressWarnings("unchecked")
	public void displayData(ModelEvent modelEvent) {
		// TODO Auto-generated method stub
		playList = (List<PlaylistDTO>)modelEvent.getModelData();
	
		arrayAdapter = new StandardArrayAdapter(playList);
		
		
		listView.setAdapter(arrayAdapter);
	}
	
	@Override
	public void onReceiveError(ModelEvent modelEvent) {
		// TODO Auto-generated method stub
		super.onReceiveError(modelEvent);
	}
	
	private class StandardArrayAdapter extends BaseAdapter {

		private List<PlaylistDTO> dataSource = new ArrayList<PlaylistDTO>();

		public StandardArrayAdapter(final List<PlaylistDTO> items) {
			super();
			this.dataSource = items;
		}

		@Override
		public View getView(final int position, View view,
				final ViewGroup parent) {
			PlaylistCell cell;
			if (position < dataSource.size()) {
				if (view == null) {
					cell = new PlaylistCell(parent.getContext());
					view = cell;
				} else {
					cell = (PlaylistCell) view;
				}
				PlaylistDTO item = ((PlaylistDTO) getItem(position));
				cell.updateData(item);

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
		gotoPlaylistItem((PlaylistDTO) playList
				.get(arg2));
	}
	
	protected void gotoPlaylistItem(PlaylistDTO item) {
		// TODO Auto-generated method stub
		ActionEvent e = new ActionEvent();
		e.sender = this;
		Bundle bundle = new Bundle();
		bundle.putSerializable(IntentConstants.INTENT_PLAYLIST_DTO, item);
		e.viewData = bundle;
		e.action = ActionEventConstant.GOTO_PLAY_LIST_ITEM;
		HomeController.getInstance().handleSwitchView(e);
	}
}
