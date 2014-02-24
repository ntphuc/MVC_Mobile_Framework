
package com.phucn.mvc.view.cell;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phucn.mvc.R;
import com.phucn.mvc.dto.PlaylistItemDTO;


public class PlaylistItemCell extends LinearLayout {
	// textview Noi dung


	private LinearLayout llItemView;
	private PlaylistItemDTO item = new PlaylistItemDTO();

	private ImageView ivAvatar;

	private TextView tvName;

	private TextView tvAddress;


	public static final int VIEW_PLAIN_ROW_CLICK = 1;

	/**
	 * @param context
	 */
	public PlaylistItemCell(Context context) {
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

		
		tvName = (TextView) llItemView.findViewById(R.id.name);

		tvAddress = (TextView) llItemView.findViewById(R.id.address);
		


	}


	/**
	 * @param context
	 * @param attrs
	 */
	public PlaylistItemCell(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}


	public void updateData(PlaylistItemDTO item2) {
		this.item = item2;

		ivAvatar.setTag(null);
		ivAvatar.setImageResource(R.drawable.ico_youtube);
//		if (item2.thumbUrl != null && item2.thumbUrl.length() > 0) {
//			ivAvatar.setTag(KunKunUtils.getLinkThumbYoutube(item2.thumbUrl));
//
//		}
		
		tvName.setText(item2.title);

		tvAddress.setText(item2.description);
	
	}

	public void setImageAvatar() {
//		if (item.thumbUrl != null && item.thumbUrl.length() > 0) {
//			ImageUtil.getImageFromURL(item.thumbUrl,(Activity) getContext(), ivAvatar);
//
//		}
	}

	


}
