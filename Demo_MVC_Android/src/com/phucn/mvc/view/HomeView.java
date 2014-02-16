
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
import com.phucn.mvc.dto.ListCategoryDTO;
import com.phucn.mvc.model.ModelEvent;
import com.phucn.mvc.view.cell.CategoryCell;


public class HomeView extends BaseActivity implements OnItemClickListener {

	public List<String> listCategories = new ArrayList<String>();

	private StandardArrayAdapter arrayAdapter;

	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_categories);
		
		listView = (ListView) findViewById(R.id.list_view);
		listView.setOnItemClickListener(this);
		displayData();
	}
	
	public void sendActionEvent() {
		Vector<String> vt = new Vector<String>();
		ActionEvent e = new ActionEvent();
		e.action = ActionEventConstant.GET_LIST_CATEGORY;
		e.viewData = vt;
		e.sender = HomeView.this;
		HomeController.getInstance().sendActionEvent(e);
	}
	
	
	
	@Override
	public void onReceiveSuccess(ModelEvent modelEvent) {
		// TODO Auto-generated method stub
		ActionEvent e = modelEvent.getActionEvent();
		switch (e.action) {
		case ActionEventConstant.GET_LIST_CATEGORY:
			displayData();
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

	public void displayData() {
		// TODO Auto-generated method stub
		listCategories = ListCategoryDTO.getListCategories();
		arrayAdapter = new StandardArrayAdapter(listCategories);
		listView.setAdapter(arrayAdapter);
		
	}
	
	protected void gotoPlaylist(String item) {
		// TODO Auto-generated method stub
		ActionEvent e = new ActionEvent();
		e.sender = this;
		Bundle bundle = new Bundle();
		bundle.putString(IntentConstants.INTENT_CATEGORY, item);
		e.viewData = bundle;
		e.action = ActionEventConstant.GOTO_PLAY_LIST;
		HomeController.getInstance().handleSwitchView(e);
	}

	private class StandardArrayAdapter extends BaseAdapter {

		private List<String> dataSource = new ArrayList<String>();

		public StandardArrayAdapter(final List<String> items) {
			super();
			this.dataSource = items;
		}

		@Override
		public View getView(final int position, View view,
				final ViewGroup parent) {
			CategoryCell cell;
			if (position < dataSource.size()) {
				if (view == null) {
					cell = new CategoryCell(parent.getContext());
					view = cell;
				} else {
					cell = (CategoryCell) view;
				}
				String title = ((String) getItem(position));
				cell.updateData((String) title);

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
		gotoPlaylist((String)listCategories
				.get(arg2));
	}
}
