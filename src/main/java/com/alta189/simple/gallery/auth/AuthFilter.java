package com.alta189.simple.gallery.auth;

import com.alta189.auto.spark.SparkFilter;
import com.alta189.simple.gallery.PageServ;
import com.alta189.simple.gallery.objects.User;
import com.alta189.simple.gallery.objects.UserRole;
import com.alta189.simple.gallery.utils.UserUtils;
import org.apache.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.resource.ClassPathResource;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;

public class AuthFilter extends SparkFilter {
	private static final AuthFilter instance = new AuthFilter();
	private static final String STATIC_DIRECTORY = "public";
	private final AuthenticationManager manager = AuthenticationManager.getInstance();
	private final FreeMarkerEngine engine = new FreeMarkerEngine();

	private AuthFilter() {
	}

	public static AuthFilter getInstance() {
		return instance;
	}

	@Override
	public void before(Request request, Response response) {
		User user = UserUtils.getUser(request);
		UserRole role = UserRole.GUEST;
		if (user != null) {
			role = user.getRole();
		}

		if (!manager.check(request.pathInfo(), role)) {
			manager.handle(request.pathInfo());
		}
	}

	@Override
	public void after(Request request, Response response) {
		if (response.raw().getStatus() == HttpStatus.SC_NOT_FOUND || isNotFound(request.pathInfo())) {
			response.body(new FreeMarkerEngine().render(new ModelAndView(PageServ.getBaseModel(), "404.ftl")));
		} else if (response.raw().getStatus() == HttpStatus.SC_FORBIDDEN) {
			response.body(engine.render(new ModelAndView(PageServ.getBaseModel(), "403.ftl")));
		}
	}

	public boolean isNotFound(String path) {
		if (manager.registeredControllerPath(path)) {
			return false;
		} else if (new ClassPathResource(STATIC_DIRECTORY + path).exists()) {
			return false;
		} else if (new File(STATIC_DIRECTORY + path).exists()) {
			return false;
		}
		return true;
	}

	public FreeMarkerEngine getEngine() {
		return engine;
	}
}
