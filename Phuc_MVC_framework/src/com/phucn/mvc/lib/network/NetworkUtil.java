package com.phucn.mvc.lib.network;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import org.json.JSONObject;

import com.phucn.mvc.util.LogMessage;



public class NetworkUtil {
	/** Default encoding */
	public static final String defaultEncoding = "UTF-8";

	
	public static String urlEncode(String sUrl) {
		if (sUrl == null) {
			return null;
		}
		StringBuffer urlOK = new StringBuffer();
		for (int i = 0; i < sUrl.length(); i++) {
			char ch = sUrl.charAt(i);
			switch (ch) {
			// case '<': urlOK.append("%3C"); break;
			// case '>': urlOK.append("%3E"); break;
			// case '/': urlOK.append("%2F"); break;
			case ' ':
				urlOK.append("%20");
				break;
			case '\n':
				urlOK.append("");
				break;
			// case ':': urlOK.append("%3A"); break;
			// case '-': urlOK.append("%2D"); break;
			default:
				urlOK.append(ch);
				break;
			}
		}
		return urlOK.toString();
	}

	public static String getResultFromServer(InputStreamReader iStrm,
			int bufSize) {
		String kq = "";
		StringBuffer response = new StringBuffer();
		char cbuf[] = new char[bufSize];
		int dptr = 0;

		try {
			if (iStrm != null) {
				int size = iStrm.read(cbuf);
				while (size > 0) {
					size--;
					response.append(cbuf[dptr++]);
				}
				kq = new String(response);
			}
		} catch (IOException e) {
		
		}
		return kq;
	}

	public static String getJSONString(String method, Vector dt) {
		String org = null;
		try {
			JSONObject data = new JSONObject();
			data.put("method", method);
			JSONObject params = new JSONObject();
			if (dt != null) {
				for (int i = 0, size = dt.size(); i < size; i += 2) {
					params.put(dt.elementAt(i).toString(), dt.elementAt(i + 1)
							.toString());
				}
			}
			if (dt != null && dt.size() > 0) {
				data.put("params", params);
			}
			org = data.toString();
			LogMessage.i("-----------------", "-----------");
			LogMessage.i("REQUEST:" + method, org);
			LogMessage.i("-----------------", "-----------");
		} catch (Exception ex) {

		}
		return org;
	}

	public static String createStringURL(String method, Vector parameters) {
		String org = null;
		try {
			StringBuffer data = new StringBuffer();
			data.append(method);
			data.append("?");
			if (parameters != null) {
				for (int i = 0, size = parameters.size(); i < size; i += 2) {
					data.append(parameters.elementAt(i).toString());
					data.append("=");
					data.append(parameters.elementAt(i + 1)
							.toString());
					if (i<size-2){
						data.append("&");
					}
				}
			}
			
			org = data.toString();
			LogMessage.i("-----------------", "-----------");
			LogMessage.i("REQUEST:" + method, org);
			LogMessage.i("-----------------", "-----------");
		} catch (Exception ex) {

		}
		return org;
	}
	

	public static JSONObject getJSONObject(Vector params) {
		JSONObject result = new JSONObject();
		try {
			for (int i = 0, size = params.size(); i < size; i += 2) {
				result.put(params.elementAt(i).toString(),
						params.elementAt(i + 1).toString());
			}
		} catch (Exception e) {

		}
		return result;
	}

	public static String getRequestParams(String method, JSONObject params) {
		JSONObject result = new JSONObject();
		try {
			result.put("method", method);
			result.put("params", params);
		} catch (Exception e) {

		}
		return result.toString();

	}



	
}
