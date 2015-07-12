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
import spark.template.freemarker.FreeMarkerEngine;

public class AuthFilter extends SparkFilter {
	private static final AuthFilter instance = new AuthFilter();
	private final AuthenticationManager manager = AuthenticationManager.getInstance();
	private final FreeMarkerEngine engine = new FreeMarkerEngine();

	public static AuthFilter getInstance() {
		return instance;
	}

	private AuthFilter() {
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
		if (response.raw().getStatus() == HttpStatus.SC_NOT_FOUND) {
			response.body(new FreeMarkerEngine().render(new ModelAndView(PageServ.getBaseModel(), "404.ftl")));
		} else if (response.raw().getStatus() == HttpStatus.SC_FORBIDDEN) {
			response.body(engine.render(new ModelAndView(PageServ.getBaseModel(), "403.ftl")));
		}
	}

	public FreeMarkerEngine getEngine() {
		return engine;
	}
}
