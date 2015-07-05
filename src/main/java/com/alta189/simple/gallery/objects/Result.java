package com.alta189.simple.gallery.objects;

import com.alta189.simple.gallery.SimpleGalleryServer;
import com.google.gson.annotations.Expose;

public class Result {
	@Expose
	private final Object result;

	public Result(Object result) {
		this.result = result;
	}

	public Object getResult() {
		return result;
	}

	public String toJson() {
		return SimpleGalleryServer.GSON.toJson(this);
	}
}
