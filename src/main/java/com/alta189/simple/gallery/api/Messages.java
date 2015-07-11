package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.RequestMethod;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.auto.spark.Transformer;
import com.alta189.simple.gallery.SimpleGalleryConstants;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.objects.Message;
import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.objects.ResultTransformer;
import com.alta189.simple.gallery.utils.MessageBuilder;
import org.apache.commons.lang3.StringUtils;
import spark.Request;
import spark.Response;

import java.util.List;

@Controller
@ResourceMapping("/api/messages")
@Transformer(ResultTransformer.class)
public class Messages {
	@ResourceMapping(value = "/create", accepts = "application/json", method = RequestMethod.PUT)
	public Result create(Request request, Response response) {
		response.type("application/json");
		if (StringUtils.isEmpty(request.body()) || StringUtils.isBlank(request.body())) {
			return Result.error("invalid request");
		}

		MessageBuilder message = MessageBuilder.fromJson(request.body());

		if (StringUtils.isEmpty(message.getMessage()) || StringUtils.isBlank(message.getMessage())) {
			return Result.error("invalid request");
		}

		message.setSessionId(request).save();

		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping("/list")
	public Result list(Request request, Response response) {
		response.type("application/json");
		String sessionId = request.session(true).id();
		return Result.wrap(SimpleGalleryServer.getDatabase().select(Message.class).where().equal("sessionId", sessionId).execute().find());
	}

	@ResourceMapping("/get")
	public Result get(Request request, Response response) {
		response.type("application/json");
		String sessionId = request.session(true).id();
		final List<Message> messages = SimpleGalleryServer.getDatabase().select(Message.class).where().equal("sessionId", sessionId).execute().find();

		new Thread(() -> {
			messages.forEach(SimpleGalleryServer.getDatabase()::remove);
		}).start();

		return Result.wrap(messages);
	}
}
