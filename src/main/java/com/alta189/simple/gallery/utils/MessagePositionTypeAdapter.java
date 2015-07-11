package com.alta189.simple.gallery.utils;

import com.alta189.auto.spark.AutoSparkUtils;
import com.alta189.simple.gallery.objects.MessagePosition;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class MessagePositionTypeAdapter<T> extends TypeAdapter<T> {
	public void write(JsonWriter out, T value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		MessagePosition position = (MessagePosition) value;
		out.value(position.getValue());
	}

	public T read(JsonReader in) throws IOException {
		return AutoSparkUtils.safeCast(MessagePosition.fromValue(in.nextString()));
	}
}