package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.RequestMethod;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.auto.spark.Transformer;
import com.alta189.simple.gallery.SimpleGalleryConstants;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.auth.AccessRule;
import com.alta189.simple.gallery.objects.Album;
import com.alta189.simple.gallery.objects.Image;
import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.objects.ResultTransformer;
import com.alta189.simple.gallery.objects.UserRole;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.entity.ContentType;
import spark.Request;
import spark.Response;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@ResourceMapping("/api/images")
@Transformer(ResultTransformer.class)
public class Images {
	private static Images instance;
	private final DiskFileItemFactory factory = new DiskFileItemFactory();

	public Images(File file) {
		factory.setRepository(file);
	}

	public static Images getInstance() {
		return instance;
	}

	public static void setInstance(Images instance) {
		Images.instance = instance;
	}

	@AccessRule(value = UserRole.VERIFIED, json = true)
	@ResourceMapping(value = "/upload", method = RequestMethod.POST)
	public Result upload(Request request, Response response) throws Exception {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		List<FileItem> items = fileUpload.parseRequest(request.raw());
		FileItem item = items.stream()
				.filter(e -> "image".equals(e.getFieldName()))
				.findFirst().get();

		FileItem desc = null;
		try {
			desc = items.stream()
					.filter(e -> "description".equals(e.getFieldName()))
					.findFirst().get();
		} catch (Exception ignored) {
		}

		String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(item.getName());
		item.write(new File(SimpleGalleryConstants.FileLocations.IMAGES_DIRECTORY, fileName));

		String description = null;
		if (desc != null) {
			description = desc.getString();
		}

		Image image = new Image();
		image.setDescription(description);
		image.setImageFile(fileName);

		SimpleGalleryServer.getDatabase().save(image);

		return Result.wrap(image);
	}

	@ResourceMapping(value = "/album", method = RequestMethod.POST)
	public Result setAlbum(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		int imageId = NumberUtils.toInt(request.queryParams("image_id"), -1);
		int albumId = NumberUtils.toInt(request.queryParams("album_id"), -1);

		if (imageId <= 0) {
			return Result.error("invalid image id");
		} else if (albumId <= 0) {
			return Result.error("invalid album id");
		}

		Image image = SimpleGalleryServer.getDatabase().select(Image.class).where().equal("id", imageId).execute().findOne();
		if (image == null) {
			return Result.error("image not found");
		}

		Album album = SimpleGalleryServer.getDatabase().select(Album.class).where().equal("id", albumId).execute().findOne();
		if (album == null) {
			return Result.error("album not found");
		}

		image.setAlbum(albumId);
		SimpleGalleryServer.getDatabase().save(image);

		return SimpleGalleryConstants.Results.SUCCESS;
	}
}
