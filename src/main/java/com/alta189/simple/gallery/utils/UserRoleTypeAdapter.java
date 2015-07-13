package com.alta189.simple.gallery.utils;

import com.alta189.auto.spark.AutoSparkUtils;
import com.alta189.simple.gallery.objects.UserRole;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class UserRoleTypeAdapter<T> extends TypeAdapter<T> {
	public void write(JsonWriter out, T value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		UserRole role = (UserRole) value;
		out.beginObject();
		out.name("name");
		out.value(role.name());
		out.name("value");
		out.value(role.getValue());
		out.endObject();
	}

	public T read(JsonReader in) throws IOException {
		return AutoSparkUtils.safeCast(UserRole.fromValue(in.nextInt()));
	}
}