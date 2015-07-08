package com.alta189.simple.gallery;

import org.aeonbits.owner.Config;

@Config.Sources("file:server.properties")
public interface ServerSettings extends Config {
	@Key("server.port")
    @DefaultValue("80")
    int port();

	@Key("server.timezone")
    @DefaultValue("EST5EDT")
    String timezone();

    @Key("dev")
    @DefaultValue("false")
    boolean dev();

    @Key("email.address")
	@DefaultValue("")
	String emailAddress();

	@Key("email.sendgrid.apikey")
	@DefaultValue("")
	String sendgridApiKey();
}