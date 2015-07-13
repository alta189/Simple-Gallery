package com.alta189.simple.gallery.objects;

public enum ValidationType {
	STARTS_WITH("Starts With"),
	ENDS_WITH("Ends With"),
	REGEX("Regex");

	private final String value;

	ValidationType(String value) {
		this.value = value;
	}

	public static ValidationType fromValue(String value) {
		for (ValidationType validationType : values()) {
			if (validationType.getValue().equalsIgnoreCase(value)) {
				return validationType;
			}
		}
		return ENDS_WITH; // default value
	}

	public static ValidationType fromName(String value) {
		for (ValidationType validationType : values()) {
			if (validationType.name().equalsIgnoreCase(value)) {
				return validationType;
			}
		}
		return ENDS_WITH; // default value
	}

	public String getValue() {
		return value;
	}
}
