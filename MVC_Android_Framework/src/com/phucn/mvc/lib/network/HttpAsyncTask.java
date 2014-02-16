
package com.phucn.mvc.lib.network;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

import com.phucn.mvc.lib.network.DataSupplier.Data;
import com.phucn.mvc.util.LogMessage;



interface DataSupplier {
	public class Data {
		byte[] buffer;
		boolean isFinish;
		int length;
	}
	void getNextPart(Data data);
	void releaseData();
	int overallDataSize();
	void reset();
}



@SuppressLint("NewApi")
public class HttpAsyncTask extends AsyncTask<Void, Void, Void> {
	private static int CONNECT_TIMEOUT = 60000; // miliseconds: 1phut
	private static int TIME_OUT = 120000;//miliseconds: 2phut
	public static int TIME_OUT_PAYMENT = 480000;//miliseconds: 8phut
	
	private HTTPRequest request;
	private HTTPResponse response;
	private boolean isSuccess;
	private static final String LogMessage_TAG = "HttpAsyncTask";
	private int readTimeout = TIME_OUT;
	private int connectTimeout = CONNECT_TIMEOUT;
	
	public HttpAsyncTask(HTTPRequest re) {
		this.request = re;
		this.readTimeout = TIME_OUT;
	}
	public HttpAsyncTask(HTTPRequest re, int timeout) {
		this.request = re;
		this.readTimeout = timeout;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		if (request == null || request.isAlive() == false) {
			if (request == null) {
				LogMessage.i(LogMessage_TAG, "Request null");
			}else {
				LogMessage.i(LogMessage_TAG, "Request NOT alive");
			}
			return null;
		}
		
		int countRetry = 0;
		final int NUM_RETRY = 1;
		boolean isRetry = false;
		do {
			isRetry = false;
			countRetry++;
			HttpURLConnection connection = null;
			OutputStream outputStream = null;
			InputStream inputStream = null;
			isSuccess = true;
			//bug sometime response code = -1
			System.setProperty("http.keepAlive", "false");
			try {
				response = new HTTPResponse(request);
				URL _url = new URL(request.getUrl());
				connection = (HttpURLConnection)_url.openConnection();								
				
				connection.setConnectTimeout(TIME_OUT);
				if (request.getContentType() != null) {
					connection.setRequestProperty("Content-type", request.contentType);
				}
				connection.setRequestMethod(request.getMethodType());			
				
				if (HTTPClient.sessionID != null) {
					connection.setRequestProperty("Cookie", HTTPClient.sessionID);
				}
				
				connection.setDoInput(true);
				if (HTTPRequest.POST.equals(request.getMethodType())){
					connection.setDoOutput(true);
					connection.connect();							
					outputStream = connection.getOutputStream();													
					writeData(outputStream, request);					
					outputStream.close();
					outputStream = null;
				} else if (HTTPRequest.GET.equals(request.getMethodType())){
					connection.setDoOutput(false);
					connection.connect();					
				
				}
				
				inputStream = connection.getInputStream();
				
				response.readData(inputStream);
		
				if (response.getDataText() == null && response.getDataBinary() == null && request.isAlive()) {
					isSuccess = false;
					isRetry = true;
//					Collection<List<String>> values = connection.getHeaderFields().values();
					StringBuffer strBuffer = new StringBuffer();
					
					strBuffer.append("ResponseCode: " + connection.getResponseCode());
					strBuffer.append("/ResponseMsg: " + connection.getResponseMessage());

					
					response.setError(HTTPClient.ERR_UNKNOWN, strBuffer.toString());
					LogMessage.i(LogMessage_TAG, "Request: " + request.getAction() + "; " + strBuffer.toString());
				}
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				isSuccess = false;
				response.setError(HTTPClient.ERR_INVALID_URL, e.getMessage() + "/" + e.toString());
				LogMessage.i(LogMessage_TAG, "MalformedURLException - " + e.getMessage());
			} catch (FileNotFoundException e) {
				// TODO: handle exception
				isSuccess = false;
				response.setError(HTTPClient.ERR_UNKNOWN, e.getMessage() + "/" + e.toString());
				LogMessage.i(LogMessage_TAG, "FileNotFoundException - " + e.getMessage() + "/" + e.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				isSuccess = false;
				isRetry = true;
				response.setError(HTTPClient.ERR_UNKNOWN, e.getMessage() + "/" + e.toString());
				LogMessage.i(LogMessage_TAG, "IOException - " + e.getMessage() + "/" + e.toString());
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				isSuccess = false;
				isRetry = true;
				response.setError(HTTPClient.ERR_UNKNOWN, e.getMessage() + "/" + e.toString());
				LogMessage.i(LogMessage_TAG, "Throwable - " + e.getMessage() + "/" + e.toString());
			} finally {
				if (outputStream != null){
					try {
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						LogMessage.i(LogMessage_TAG, "close outputStream - IOException - " + e.getMessage() + "/" + e.toString());
					}
				}
				if (inputStream != null){
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						LogMessage.i(LogMessage_TAG, "close inputStream - IOException - " + e.getMessage() + "/" + e.toString());
					}
				}
				if (connection != null){
					LogMessage.i(LogMessage_TAG, "disconnect");
					connection.disconnect();
				}
			}
		LogMessage.i(LogMessage_TAG, "IS_RETRY:" + (countRetry <= NUM_RETRY && isRetry) + "; Request" + request.getAction());
		}while (countRetry <= NUM_RETRY && isRetry);
		
		HTTPListenner listenner = null;
		boolean isAlive = true;
		if (response != null) {
			listenner = response.getObserver();
			isAlive = response.request.isAlive();
		}
		if (listenner != null && isAlive) {
			if (isSuccess) {
				listenner.onReceiveSuccess(response);
			} else {
				listenner.onReceiveError(response);
			}
		}
		return null;
	}
	
	private void disableConnectionReuseIfNecessary() {
	    // HTTP connection reuse which was buggy pre-froyo
	    if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
	        System.setProperty("http.keepAlive", "false");
	    }
		System.setProperty("networkaddress.cache.ttl","0");
		System.setProperty("networkaddress.cache.negative.ttl" , "0");
	}
		

	void writeData(OutputStream outputStream, DataSupplier dataSupplier) throws IOException{
		Data data = new Data(); 
		while (true){
			dataSupplier.getNextPart(data);
			if (data.buffer != null && data.length > 0) {
				outputStream.write(data.buffer, 0, data.length);
				outputStream.flush();
				dataSupplier.releaseData();
			}
			if (data.isFinish){
				break;
			}
		}
		
	}	
}
