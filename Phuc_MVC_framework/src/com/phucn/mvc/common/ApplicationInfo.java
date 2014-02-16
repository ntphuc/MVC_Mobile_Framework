package com.phucn.mvc.common;

import com.phucn.mvc.view.BaseActivity;

import android.app.Application;
import android.content.Context;


public class ApplicationInfo extends Application {
	public final String VERSION = "1.1";
	Context appContext;// application context
	Context activityContext;//activity context
	private static ApplicationInfo instance = null;
	private boolean isAppActive;
	public BaseActivity lastShowActivity = null;
	// bien dung de debug
	public static final boolean ISDEBUG = false;

	// Methods
	public ApplicationInfo() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

	}

	public static ApplicationInfo getInstance() {
		if (instance == null) {
			instance = new ApplicationInfo();
			instance.initialize();
		}
		return instance;
	}

	private void initialize() {
		// TODO Auto-generated method stub
		
	}

	public void setAppContext(Context context) {
		this.appContext = context;

	}

	public Context getAppContext() {
		if(appContext == null){
			appContext = new ApplicationInfo();
		}
		return appContext;
	}
	
	public void setActivityContext(Context context) {
		this.activityContext = context;

	}

	public Context getActivityContext() {		
		return activityContext;
	}


	public void setAppActive(boolean isActive) {

		this.isAppActive = isActive;
	}


	
	public boolean isAppActive() {

		return isAppActive;
	}


}
