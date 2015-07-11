package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.auto.spark.Transformer;
import com.alta189.simple.gallery.SimpleGalleryConstants;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.objects.Album;
import com.alta189.simple.gallery.objects.EmailConfirm;
import com.alta189.simple.gallery.objects.Image;
import com.alta189.simple.gallery.objects.PasswordReset;
import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.objects.ResultTransformer;
import com.alta189.simple.gallery.objects.User;
import org.apache.http.entity.ContentType;
import spark.Request;
import spark.Response;

@Controller
@ResourceMapping("/api/dev")
@Transformer(ResultTransformer.class)
public class DevMode {
	@ResourceMapping("/clear/users")
	public Result clearUsers(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		if (!isDevMode()) {
			return Result.error("Not in Dev Mode");
		}
		SimpleGalleryServer.getDatabase().clear(User.class);
		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping("/clear/albums")
	public Result clearAlbums(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		if (!isDevMode()) {
			return Result.error("Not in Dev Mode");
		}
		SimpleGalleryServer.getDatabase().clear(Album.class);
		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping("/clear/email")
	 public Result clearEmail(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		if (!isDevMode()) {
			return Result.error("Not in Dev Mode");
		}
		SimpleGalleryServer.getDatabase().clear(EmailConfirm.class);
		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping("/clear/pass")
	public Result clearPass(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		if (!isDevMode()) {
			return Result.error("Not in Dev Mode");
		}
		SimpleGalleryServer.getDatabase().clear(PasswordReset.class);
		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping("/clear/image")
	 public Result clearImage(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		if (!isDevMode()) {
			return Result.error("Not in Dev Mode");
		}
		SimpleGalleryServer.getDatabase().clear(Image.class);
		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping("/clear")
	public Result clear(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		if (!isDevMode()) {
			return Result.error("Not in Dev Mode");
		}
		SimpleGalleryServer.getDatabase().clear(User.class);
		SimpleGalleryServer.getDatabase().clear(Album.class);
		SimpleGalleryServer.getDatabase().clear(Image.class);
		SimpleGalleryServer.getDatabase().clear(EmailConfirm.class);
		SimpleGalleryServer.getDatabase().clear(PasswordReset.class);
		return SimpleGalleryConstants.Results.SUCCESS;
	}

	public boolean isDevMode() {
		return SimpleGalleryServer.SETTINGS.getBoolean("dev", false);
	}
}
