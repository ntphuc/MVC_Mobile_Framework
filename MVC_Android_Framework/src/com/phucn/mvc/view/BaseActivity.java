package com.phucn.mvc.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.phucn.mvc.R;
import com.phucn.mvc.common.ApplicationInfo;
import com.phucn.mvc.constants.ErrorConstants;
import com.phucn.mvc.model.ModelEvent;
import com.phucn.mvc.util.LogMessage;
import com.phucn.mvc.util.StringUtil;


public abstract class BaseActivity extends Activity {
	// chuoi action cua cac broadcast message
	public static final String BROADCAST_ACTION = "broadcast.com.project.action";

	protected final int ICON_RIGHT_PARENT = 0;
	protected final int ICON_NEXT_1 = 1;
	protected final int ICON_BUTTON = -1;
	
	// broadcast receiver, nhan broadcast
	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int action = intent.getExtras().getInt("project.action");
			int hasCode = intent.getExtras().getInt("project.hashCode");
			if (hasCode != BaseActivity.this.hashCode()) {
				receiveBroadcast(action, intent.getExtras());
			}
		}
	};

	// dialog hien thi khi request
	private static ProgressDialog progressDlg;
	// progressBar tren header
	private ProgressBar pbHeaderLoading;
	// header kunkun
	private View rlHeader;

	// kiem tra activity da finish hay chua
	public boolean isFinished = false;


	// activity co dang active
	private boolean isActive;

	private LinearLayout rootView;
	protected LinearLayout mainContent;
	private boolean broadcast =false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 ApplicationInfo.getInstance().setAppContext(getApplicationContext());
		 ApplicationInfo.getInstance().setActivityContext(this);
		

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		try {
			IntentFilter filter = new IntentFilter(BaseActivity.BROADCAST_ACTION);
			registerReceiver(receiver, filter);
			this.broadcast = true;
		} catch (Exception e) {
		}
	
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

		super.onSaveInstanceState(outState);
	}

	@Override
	public void finish() {
		isFinished = true;
		super.finish();
	}

	@Override
	public void finishActivity(int requestCode) {
		isFinished = true;
		super.finishActivity(requestCode);
	}

	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (this == ApplicationInfo.getInstance().lastShowActivity) {
			ApplicationInfo.getInstance().lastShowActivity = null;
		}
		try {
			unregisterReceiver(receiver);
		} catch (Exception e) {
		}
	
		System.gc();
		System.runFinalization();
		
		super.onDestroy();

	}

	protected void initHeader() {
		LinearLayout rootView = (LinearLayout) findViewById(R.id.ll_main);
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rlHeader = inflater.inflate(R.layout.layout_header, null);
		rootView.addView(rlHeader, 0);
		
//		//Cap nhat hien thi logo loading
//		pbHeaderLoading = (ProgressBar) rlHeader.findViewById(R.id.pb_kunkun);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(R.layout.layout_main);
		rootView = (LinearLayout) findViewById(R.id.ll_main);
		mainContent = (LinearLayout) findViewById(R.id.ll_content);
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(layoutResID, mainContent);

		initHeader();
	}

	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		super.setContentView(R.layout.layout_main);
		rootView = (LinearLayout) findViewById(R.id.ll_main);
		mainContent = (LinearLayout) findViewById(R.id.ll_content);
		mainContent.addView(view);
		initHeader();
	}

	@Override
	protected void onResume() {
		super.onResume();		
		isActive = true;
		ApplicationInfo.getInstance().setAppActive(true);
		ApplicationInfo.getInstance().lastShowActivity = this;		
	
	}

	@Override
	protected void onPause() {
		super.onPause();
		isActive = false;
		ApplicationInfo.getInstance().setAppActive(false);
		System.gc();
	}
	
	public void setLogoVisible(int visible) {

		rlHeader.setVisibility(visible);
	}
	

	public View getLogoHeader() {
		return rlHeader;
	}

	public void setLogoLoading(boolean visible) {
		if (pbHeaderLoading != null){
			if (visible) {
				pbHeaderLoading.setVisibility(View.VISIBLE);
			} else {
				pbHeaderLoading.setVisibility(View.GONE);
			}
		}
	}


	public AlertDialog showDialog(final String mes) {
		AlertDialog alertDialog = null;
		try {
			alertDialog = new AlertDialog.Builder(this).create();
			// alertDialog.setTitle("ThÃ´ng bÃ¡o");
			alertDialog.setMessage(mes);
			alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					return;
				}
			});
			alertDialog.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alertDialog;
	}
	


	public void onReceiveSuccess(ModelEvent modelEvent) {
		
				
	}


	public void onReceiveError(ModelEvent modelEvent) {		
		switch (modelEvent.getModelCode()) {
		case ErrorConstants.ERROR_COMMON:
			closeProgressDialog();
			showDialog(StringUtil.getString(R.string.ERROR_COMMON));
			break;
		case ErrorConstants.ERROR_NO_CONNECTION:
			showDialog(modelEvent.getModelMessage());
			break;
		case ErrorConstants.ERROR_OBJECT_NOT_EXISTS:
			showDialog(StringUtil.getString(
					R.string.ERROR_OBJECT_NOT_EXISTED));	
			break;
		default:
			closeProgressDialog();
			if (!StringUtil.isNullOrEmpty(modelEvent.getModelMessage())){
				showDialog(modelEvent.getModelMessage());
			}
			break;
		}
	}

	public void showProgressDialog(String content) {
		showProgressDialog(content, true);
	}

	public void showProgressDialog(String content, boolean cancleable) {
		if(progressDlg!=null&&progressDlg.isShowing()){
			closeProgressDialog();
		}
		progressDlg = ProgressDialog.show(this, "", content, true, true);
		progressDlg.setCancelable(cancleable);
		progressDlg.setCanceledOnTouchOutside(false);
	
	}

	public void closeProgressDialog() {
		if (progressDlg != null) {
			try{
				progressDlg.dismiss();
				progressDlg = null;
			}catch (Exception e) {
				LogMessage.i("Exception", e.toString());
			}
		}
	}

	public void showProgressPercentDialog(String content) {
		progressDlg = new ProgressDialog(this);
		progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDlg.setMessage(content);
		progressDlg.setCancelable(false);
		progressDlg.show();
	}

	public void updateProgressPercentDialog(int percent) {
		if (progressDlg != null) {
			progressDlg.setProgress(percent);
		}
	}

	public void closeProgressPercentDialog() {
		if (progressDlg != null) {
			progressDlg.dismiss();
			progressDlg = null;
		}
	}
	
	public void receiveBroadcast(int action, Bundle bundle) {		
		
	}	

	public void sentBroadcast(int action, Bundle bundle) {
		Intent intent = new Intent(BROADCAST_ACTION);
		bundle.putInt("project.action", action);
		bundle.putInt("project.hashCode",intent.getClass().hashCode());
		intent.putExtras(bundle);
		sendBroadcast(intent);
	}
	

	public boolean isActive() {
		return isActive;
	}


}
