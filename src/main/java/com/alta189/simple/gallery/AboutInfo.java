package com.alta189.simple.gallery;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:about.info")
public interface AboutInfo extends Config {
	@DefaultValue("Unknown Version")
	String version();

	@DefaultValue("0")
	@Key("build.number")
	int build();

	@DefaultValue("UNKNOWN")
	@Key("build.date")
	String buildDate();
}