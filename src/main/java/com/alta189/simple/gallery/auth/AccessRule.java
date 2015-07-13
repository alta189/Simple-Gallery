package com.alta189.simple.gallery.auth;

import com.alta189.simple.gallery.objects.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessRule {
	UserRole value() default UserRole.VERIFIED;
	boolean json() default false;
}
