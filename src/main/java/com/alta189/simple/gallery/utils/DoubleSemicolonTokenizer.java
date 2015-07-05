package com.alta189.simple.gallery.utils;

import org.aeonbits.owner.Tokenizer;

public class DoubleSemicolonTokenizer implements Tokenizer {
	@Override
	public String[] tokens(String s) {
		return s.split(";;");
	}
}
