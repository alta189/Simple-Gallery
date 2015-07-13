package com.alta189.simple.gallery.objects;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public enum UserRole {
	GUEST(0),
	UNVERIFIED(10),
	VERIFIED(20),
	MODERATOR(50),
	ADMINISTRATOR(100);

	public static final ImmutableList<ImmutableMap<String, Object>> SELECT_OPTIONS;
	private final int value;

	static {
		ImmutableList.Builder<ImmutableMap<String, Object>> builder = ImmutableList.builder();
		for (UserRole role : values()) {
			ImmutableMap.Builder<String, Object> mapBuilder = ImmutableMap.builder();
			mapBuilder.put("value", role.getValue());
			mapBuilder.put("display", role.name());
			builder.add(mapBuilder.build());
		}
		SELECT_OPTIONS = builder.build();
	}

	UserRole(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static UserRole fromValue(int value) {
		for (UserRole role : values()) {
			if (role.getValue() == value) {
				return role;
			}
		}
		return GUEST; // default value
	}

	public static UserRole fromName(String value) {
		return fromName(value, GUEST);
	}

	public static UserRole fromName(String value, UserRole defaultRole) {
		for (UserRole role : values()) {
			if (role.name().equalsIgnoreCase(value)) {
				return role;
			}
		}
		return defaultRole; // default value
	}
}
