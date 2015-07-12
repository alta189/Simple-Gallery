package com.alta189.simple.gallery.auth;

import com.alta189.auto.spark.AutoController;
import com.alta189.auto.spark.ResourceRegistration;
import com.alta189.simple.gallery.PageServ;
import com.alta189.simple.gallery.SimpleGalleryConstants;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.objects.UserRole;
import org.apache.http.HttpStatus;
import spark.ModelAndView;
import spark.Spark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthenticationManager {
	private final static AuthenticationManager instance = new AuthenticationManager();
	private final Map<String, UserRole> paths = new HashMap<>();
	private final List<String> jsonPaths = new ArrayList<>();
	private final UserRole DEFAULT_ROLE = UserRole.GUEST;
	private final String ACCESS_DENIED = SimpleGalleryConstants.Results.ACCESS_DENIED.toJson();

	public static AuthenticationManager getInstance() {
		return instance;
	}

	private AuthenticationManager() {
	}

	public void scan() {
		for (AutoController controller : SimpleGalleryServer.AUTO_SPARK.getRegisteredControllers()) {
			controller.getRegistrations().stream().filter(registration -> registration.getClass().equals(ResourceRegistration.class)).forEach(registration -> {
				ResourceRegistration resource = (ResourceRegistration) registration;

				AccessRule accessRule = resource.getMethod().getAnnotation(AccessRule.class);

				UserRole requiredRole = getRequiredRole(accessRule);

				boolean jsonPath = isJsonPath(accessRule);

				resource.getPaths().forEach(path -> {
					paths.put(path, requiredRole);
					if (jsonPath) {
						jsonPaths.add(path);
					}
				});
			});
		}
	}

	private UserRole getRequiredRole(AccessRule accessRule) {
		UserRole requiredRole = DEFAULT_ROLE;
		if (accessRule != null && accessRule.value() != null) {
			requiredRole = accessRule.value();
		}
		return requiredRole;
	}

	private boolean isJsonPath(AccessRule accessRule) {
		return accessRule != null && accessRule.json();
	}

	private UserRole getPathRole(String path) {
		UserRole role = paths.get(path);
		if (role == null) {
			role = DEFAULT_ROLE;
		}
		return role;
	}

	public boolean check(String path, UserRole role) {
		return role.getValue() >= getPathRole(path).getValue();
	}

	public void handle(String path) {
		if (jsonPaths.contains(path)) {
			Spark.halt(HttpStatus.SC_FORBIDDEN, ACCESS_DENIED);
		} else {
			Spark.halt(403, AuthFilter.getInstance().getEngine().render(new ModelAndView(PageServ.getBaseModel(), "403.ftl")));
		}
	}
}
