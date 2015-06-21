package com.alta189.simple.gallery;

import org.aeonbits.owner.Config;

@Config.Sources("file:server.properties")
public interface ServerSettings extends Config {
    @DefaultValue("80")
    @Key("server.port")
    int port();

    @DefaultValue("EST5EDT")
    @Key("server.timezone")
    String timezone();

    @Key("dev")
    @DefaultValue("false")
    boolean dev();
}