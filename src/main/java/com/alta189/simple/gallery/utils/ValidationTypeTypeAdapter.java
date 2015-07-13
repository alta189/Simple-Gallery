package com.alta189.simple.gallery.utils;

import com.alta189.auto.spark.AutoSparkUtils;
import com.alta189.simple.gallery.objects.MessagePosition;
import com.alta189.simple.gallery.objects.ValidationType;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ValidationTypeTypeAdapter<T> extends TypeAdapter<T> {
	public void write(JsonWriter out, T value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		ValidationType validationType = (ValidationType) value;
		out.beginObject();
		out.name("name");
		out.value(validationType.name());
		out.name("value");
		out.value(validationType.getValue());
		out.endObject();
	}

	public T read(JsonReader in) throws IOException {
		return AutoSparkUtils.safeCast(ValidationType.fromValue(in.nextString()));
	}
}