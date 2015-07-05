package com.alta189.simple.gallery.objects;

import com.alta189.simple.gallery.SimpleGalleryServer;
import com.google.gson.annotations.Expose;

public class Result {
	@Expose
	private final Object result;
	@Expose
	private final boolean error;
	@Expose
	private final String errorMessage;

	private Result(Object result) {
		this(result, false, null);
	}

	private Result(Object result, boolean error, String errorMessage) {
		this.result = result;
		this.error = error;
		this.errorMessage = errorMessage;
	}

	public static Result wrap(Object result) {
		return new Result(result);
	}

	public static Result error(String message) {
		return new Result(null, true, message);
	}

	public Object getResult() {
		return result;
	}

	public boolean isError() {
		return error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String toJson() {
		return SimpleGalleryServer.GSON.toJson(this);
	}
}
