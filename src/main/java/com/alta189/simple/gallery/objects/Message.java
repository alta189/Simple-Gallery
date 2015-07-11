package com.alta189.simple.gallery.objects;

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import com.google.gson.annotations.Expose;

@Table("messages")
public class Message {
	@Id
	private int id;
	@Field
	@Expose
	private int timeout = 5000;
	@Field
	@Expose
	private String message;
	@Field
	@Expose
	private MessageStyle style = MessageStyle.INFO;
	@Field
	@Expose
	private MessagePosition position = MessagePosition.TOP_RIGHT;
	@Field
	private String sessionId;

	public int getId() {
		return id;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageStyle getStyle() {
		return style;
	}

	public void setStyle(MessageStyle style) {
		this.style = style;
	}

	public MessagePosition getPosition() {
		return position;
	}

	public void setPosition(MessagePosition position) {
		this.position = position;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
