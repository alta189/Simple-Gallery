package com.alta189.simple.gallery.utils;

import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.objects.Message;
import com.alta189.simple.gallery.objects.MessagePosition;
import com.alta189.simple.gallery.objects.MessageStyle;
import spark.Request;

public class MessageBuilder {
	private final Message message;

	public MessageBuilder() {
		this(new Message());
	}

	public MessageBuilder(Message message) {
		this.message = message;
	}

	public int getTimeout() {
		return message.getTimeout();
	}

	public MessageBuilder setTimeout(int timeout) {
		message.setTimeout(timeout);
		return this;
	}

	public String getMessage() {
		return message.getMessage();
	}

	public MessageBuilder setMessage(String message) {
		this.message.setMessage(message);
		return this;
	}

	public MessageStyle getStyle() {
		return message.getStyle();
	}

	public MessageBuilder setStyle(MessageStyle style) {
		message.setStyle(style);
		return this;
	}

	public MessagePosition getPosition() {
		return message.getPosition();
	}

	public MessageBuilder setPosition(MessagePosition position) {
		message.setPosition(position);
		return this;
	}

	public String getSessionId() {
		return message.getSessionId();
	}

	public MessageBuilder setSessionId(String sessionId) {
		message.setSessionId(sessionId);
		return this;
	}

	public MessageBuilder setSessionId(Request request) {
		setSessionId(request.session(true).id());
		return this;
	}

	public Message build() {
		return message;
	}

	public void save() {
		SimpleGalleryServer.getDatabase().save(message);
	}

	public void delete() {
		SimpleGalleryServer.getDatabase().remove(message);
	}

	public static MessageBuilder fromJson(String json) {
		return new MessageBuilder(SimpleGalleryServer.GSON.fromJson(json, Message.class));
	}

	public static MessageBuilder get(Request request) {
		return new MessageBuilder().setSessionId(request);
	}
}
