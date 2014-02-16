package com.phucn.mvc.model;




import com.phucn.mvc.constants.Constants;
import com.phucn.mvc.controller.ActionEvent;
import com.phucn.mvc.lib.network.HTTPListenner;
import com.phucn.mvc.lib.network.HTTPMessage;
import com.phucn.mvc.lib.network.HTTPRequest;
import com.phucn.mvc.lib.network.HTTPResponse;
import com.phucn.mvc.lib.network.HttpAsyncTask;

public abstract class AbstractModelService implements HTTPListenner{



	public  void onReceiveSuccess(HTTPMessage mes){
		
	}
	
	public  void onReceiveError(HTTPResponse response){
		
	}
	
	public HTTPRequest sendHttpRequest(String url, ActionEvent actionEvent) {
	
		HTTPRequest re = new HTTPRequest();
		re.setUrl(url);		
		re.setAction(actionEvent.action);
		re.setContentType(HTTPMessage.CONTENT_JSON);
		re.setMethodType(Constants.HTTPCONNECTION_GET);
		re.setObserver(this);
		re.setUserData(actionEvent);
		new HttpAsyncTask(re).execute();
		return re;
	}


	
}

