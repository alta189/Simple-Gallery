package com.alta189.simple.gallery.utils;

import com.alta189.auto.spark.AutoSparkUtils;
import com.alta189.simple.gallery.objects.MessagePosition;
import com.alta189.simple.gallery.objects.MessageStyle;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class MessageStyleTypeAdapter<T> extends TypeAdapter<T> {
	public void write(JsonWriter out, T value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		 MessageStyle style = (MessageStyle) value;
		out.value(style.name().toLowerCase());
	}

	public T read(JsonReader in) throws IOException {
		try {
			return AutoSparkUtils.safeCast(MessageStyle.valueOf(in.nextString().toUpperCase()));
		} catch (Exception ignored) {
		}
		return null;
	}
}