package com.alta189.simple.gallery.utils;

import org.apache.commons.lang3.StringUtils;

public class TitleUtils {
	public static boolean isValidTitle(String title) {
		return StringUtils.isNotEmpty(title) && StringUtils.isNotBlank(title);
	}
}
