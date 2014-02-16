package com.phucn.mvc.controller;


import com.phucn.mvc.lib.network.HTTPRequest;
import com.phucn.mvc.model.ModelEvent;
import com.phucn.mvc.view.BaseActivity;


public abstract class AbstractController {
	abstract public void sendActionEvent(ActionEvent e);
    public void handleSwitchView(ActionEvent e){
    
    };
    public void onReceiveError(final ModelEvent modelEvent) {
    	
    	final ActionEvent e = modelEvent.getActionEvent();
		BaseActivity ac = (BaseActivity) e.sender;
		HTTPRequest request = e.request;
		if (ac.isFinished) {
			return;
		}
		ac.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				BaseActivity base = (BaseActivity) e.sender;
				if (base != null) {
					base.onReceiveSuccess(modelEvent);
				}
			}
		});
    };
    public void onReceiveSuccess(final ModelEvent modelEvent) {
		final ActionEvent e = modelEvent.getActionEvent();
		BaseActivity ac = (BaseActivity) e.sender;
		HTTPRequest request = e.request;
		if (ac.isFinished) {
			return;
		}
		ac.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				BaseActivity base = (BaseActivity) e.sender;
				if (base != null) {
					base.onReceiveSuccess(modelEvent);
				}
			}
		});
	
	}
}
