package com.alta189.simple.gallery.objects;

public enum UserRole {
	GUEST(0),
	UNVERIFIED(10),
	VERIFIED(20),
	MODERATOR(50),
	ADMINISTRATOR(100);

	private final int value;

	UserRole(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
