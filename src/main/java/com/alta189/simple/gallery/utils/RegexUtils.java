package com.alta189.simple.gallery.utils;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexUtils {
	public static boolean isValidRegex(final String str) {
		try {
			Pattern.compile(str);
			return true;
		} catch (PatternSyntaxException e) {
			return false;
		}
	}
}
