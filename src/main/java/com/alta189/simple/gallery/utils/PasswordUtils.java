package com.alta189.simple.gallery.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class PasswordUtils {
	private static final Pattern PASSWORD_REQUIREMENT = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^&+=])(?=\\S+$).{8,}$");

	public static boolean validPassword(String password) {
		if (StringUtils.isEmpty(password) || StringUtils.isBlank(password)) {
			return false;
		}
		return PASSWORD_REQUIREMENT.matcher(password).matches();
	}
}
