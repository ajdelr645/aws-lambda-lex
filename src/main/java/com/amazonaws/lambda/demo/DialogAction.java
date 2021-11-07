package com.amazonaws.lambda.demo;
//changes from github
public class DialogAction {
	
	private String type;
	private String fulfillmentState;
	private Message message;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFulfillmentState() {
		return fulfillmentState;
	}
	public void setFulfillmentState(String fulfillmentState) {
		this.fulfillmentState = fulfillmentState;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	

}
