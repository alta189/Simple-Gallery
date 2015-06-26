package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.ResourceMapping;
import spark.Request;
import spark.Response;


@Controller
public class Information {
	@ResourceMapping("/status")
	public String status(Request request, Response response) {
		return "bob";
	}
}
