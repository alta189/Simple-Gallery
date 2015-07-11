package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.auto.spark.Transformer;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.objects.ResultTransformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import spark.Request;
import spark.Response;


@Controller
@ResourceMapping("/api")
public class Information {
	private static final String OK_STATUS = Result.wrap("ok").toJson();

	@ResourceMapping("/status")
	public String status(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		return OK_STATUS;
	}

	@ResourceMapping("/version")
	public String version(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		return SimpleGalleryServer.VERSION_INFO_JSON;
	}

	@ResourceMapping("/message")
	@Transformer(ResultTransformer.class)
	public Result message(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		request.session(true);
		String message = request.session().attribute("message");
		if (StringUtils.isEmpty(message)) {
			return Result.error("no messages");
		}
		request.session().removeAttribute("message");
		return Result.wrap(message);
	}
}
