package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.simple.gallery.objects.Result;
import spark.Request;
import spark.Response;


@Controller
@ResourceMapping("/api")
public class Information {
	private static final String OK_STATUS = new Result("ok").toJson();

	@ResourceMapping("/status")
	public String status(Request request, Response response) {
		return OK_STATUS;
	}
}
