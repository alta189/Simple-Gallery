package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.RequestMethod;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.auto.spark.Transformer;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.objects.Album;
import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.objects.ResultTransformer;
import com.alta189.simple.gallery.utils.TitleUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

@Controller
@ResourceMapping("/api/albums")
@Transformer(ResultTransformer.class)
public class Albums {
	@ResourceMapping(value = "/create", method = RequestMethod.POST)
	public Result create(Request request, Response response) {
		String title = request.queryMap("title").value();
		if (!TitleUtils.isValidTitle(title)) {
			System.out.println("title = '" + title + "'");
			return Result.error("invalid title");
		}

		Album album = new Album();
		album.setTitle(title);
		album.setSubtitle(request.queryParams("subtitle"));
		album.setUploaded(DateTime.now());
		SimpleGalleryServer.getDatabase().save(album);

		return Result.wrap(album);
	}

	@ResourceMapping("/get/:id")
	public Result get(Request request, Response response) {
		int id = NumberUtils.toInt(request.params("id"), -1);
		if (id <= 0) {
			return Result.error("invalid album id");
		}

		Album album = SimpleGalleryServer.getDatabase().select(Album.class).where().equal("id", id).execute().findOne();
		if (album == null) {
			return Result.error("album not found");
		}
		album.refresh();

		return Result.wrap(album);
	}
}