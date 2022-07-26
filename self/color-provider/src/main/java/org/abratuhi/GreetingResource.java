package org.abratuhi;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/color")
public class GreetingResource {

    @ConfigProperty(name = "app.color", defaultValue = "black")
    String color;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getColor() {
        return color;
    }
}