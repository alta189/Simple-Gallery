package com.alta189.simple.gallery.objects;

import com.alta189.auto.spark.AutoSparkUtils;
import com.alta189.auto.spark.SparkResponseTransformer;

public class ResultTransformer extends SparkResponseTransformer {
	private static final ResultTransformer INSTANCE = new ResultTransformer();

	public static ResultTransformer getInstance() {
		return INSTANCE;
	}

	@Override
	public String render(Object model) throws Exception {
		Result result = AutoSparkUtils.safeCast(model);

		if (result == null) {
			return null;
		}

		return result.toJson();
	}
}
