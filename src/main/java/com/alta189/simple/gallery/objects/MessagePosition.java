package com.alta189.simple.gallery.objects;

public enum MessagePosition {
	TOP_CENTER("top-center"),
	TOP_LEFT("top-left"),
	TOP_RIGHT("top-right"),
	BOTTOM_CENTER("bottom-center"),
	BOTTOM_LEFT("bottom-left"),
	BOTTOM_RIGHT("bottom-right");

	private final String value;

	MessagePosition(String value) {
		this.value = value;
	}

	public static MessagePosition fromValue(String value) {
		for (MessagePosition position : values()) {
			if (position.getValue().equalsIgnoreCase(value)) {
				return position;
			}
		}
		return TOP_RIGHT; // default value
	}

	public String getValue() {
		return value;
	}
}
