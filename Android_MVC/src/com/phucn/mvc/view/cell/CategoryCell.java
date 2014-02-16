
package com.phucn.mvc.view.cell;


import com.phucn.mvc.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings.TextSize;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryCell extends LinearLayout {
	

	private LinearLayout llItemView;

	private ImageView ivAvatar;

	private TextView tvName;

	private String item;

	public static final int VIEW_PLAIN_ROW_CLICK = 1;

	/**
	 * @param context
	 */
	public CategoryCell(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);

	}


	private void initView(Context context) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		llItemView = (LinearLayout) inflater.inflate(R.layout.item_list_category,
				this);

		ivAvatar = (ImageView) llItemView.findViewById(R.id.ivAvatar);
		//ivAvatar.setVisibility(View.GONE);
		
		
		tvName = (TextView) llItemView.findViewById(R.id.name);
		


	}

	

	

	/**
	 * @param context
	 * @param attrs
	 */
	public CategoryCell(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}


	
	public void updateData(String item) {
		this.item = item;

//		if (item.thumbUrl != null && item.thumbUrl.length() > 0) {
//			ivAvatar.setTag(item.thumbUrl);
//
//		}
		
		tvName.setText(	item);
				

		//tvAddress.setText(item.description);
	
	}

	



	

	

}
