package com.alta189.simple.gallery.utils;

import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.objects.User;
import spark.Request;
import spark.Response;

public class UserUtils {
	public static User getUser(Request request) {
		request.session(true);
		int userId = -1;
		try {
			userId = request.session().attribute("user-id");
		} catch (Exception ignored) {
		}

		if (userId <= 0) {
			return null;
		}

		return SimpleGalleryServer.getDatabase().select(User.class).where().equal("id", userId).execute().findOne();
	}

	public static void signOut(Request request) {
		request.session(true);
		request.session().removeAttribute("user-id");
	}

	public static void message(Response response, String message) {
		message(response, message, "info");
	}

	public static void message(Response response, String message, String style) {
		response.cookie("message", message);
		response.cookie("message_style", style);
	}
}
