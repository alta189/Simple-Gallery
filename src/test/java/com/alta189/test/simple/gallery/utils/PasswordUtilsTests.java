package com.alta189.test.simple.gallery.utils;

import com.alta189.simple.gallery.utils.PasswordUtils;
import org.junit.Test;
import org.junit.Assert;

public class PasswordUtilsTests {
	private static final String[] PASS = {"sWn132!ss", "P@ssw0rd"};

	@Test
	public void passingTest() {
		for (String pass : PASS) {
			Assert.assertTrue(PasswordUtils.validPassword(pass));
		}
	}
}
