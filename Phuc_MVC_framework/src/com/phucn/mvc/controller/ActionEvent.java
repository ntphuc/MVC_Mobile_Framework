package com.phucn.mvc.controller;

import com.phucn.mvc.lib.network.HTTPRequest;


public class ActionEvent {
	 public int action;
	 public String method;
	 public Object modelData;
	 public Object viewData;
	 public Object userData;
	 public Object sender;
	 public int tag = 0;
	 
	 public AbstractController controller;
	 public HTTPRequest request;
	 public boolean isBlockRequest;
//	 private HttpUpdateListener updateListener;
	 public boolean isRelogin = false; // bien dung de kiem tra chi relogin 1 lan
	 public void reset(){
		 action = -1;
		 method="";
		 modelData = null;
		 viewData = null;
		 userData = null;
		 sender = null;
	 }
	 
}
