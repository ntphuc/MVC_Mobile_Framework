package com.phucn.mvc.model;

import com.phucn.mvc.constants.Constants;
import com.phucn.mvc.controller.ActionEvent;

public class ModelEvent {
	private ActionEvent actionEvent;
	Object modelData;
	int modelCode;
	int code;
	String modelMessage;
	// luu giu chuoi datatext tra ve tu server, phuc vu trace log
	private String dataText = "";
	// luu giu param cua request khi truyen len server
	private String param = "";
	
	public void setActionEvent(ActionEvent actionEvent) {
		this.actionEvent = actionEvent;
	}
	public ActionEvent getActionEvent() {
		return actionEvent;
	}
	public Object getModelData() {
		return modelData;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	public void setModelData(Object modelData) {
		this.modelData = modelData;
	}
	public int getModelCode() {
		return modelCode;
	}
	public void setModelCode(int modelCode) {
		this.modelCode = modelCode;
	}
	public String getModelMessage() {
		return modelMessage;
	}
	public void setModelMessage(String modelMessage) {
		this.modelMessage = modelMessage;
	}
	public void setDataText(String dataText) {
		this.dataText = dataText;
	}
	public String getDataText() {
		return dataText;
	}
	public void setParams(String para) {
		this.param = para;
	}
	public String getParams() {
		return param;
	}
}
