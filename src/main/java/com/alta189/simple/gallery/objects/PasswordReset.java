package com.alta189.simple.gallery.objects;

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;

import java.util.UUID;

@Table("password_reset")
public class PasswordReset {
	@Id
	private int id;
	@Field
	private int user;
	@Field
	private String key;

	public PasswordReset() {
	}

	public PasswordReset(int user, String key) {
		this.user = user;
		this.key = key;
	}

	public static PasswordReset generateNew(int user) {
		return new PasswordReset(user, UUID.randomUUID().toString());
	}

	public int getId() {
		return id;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
