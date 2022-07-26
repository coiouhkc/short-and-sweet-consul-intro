package org.abratuhi;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.catalog.CatalogServiceRequest;
import com.ecwid.consul.v1.catalog.CatalogServicesRequest;
import com.ecwid.consul.v1.catalog.model.CatalogService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Path("/colors")
public class GreetingResource {

    @ConfigProperty(name = "consul.host", defaultValue = "localhost")
    String consulHost;

    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consulPort;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public List<String> hello() {
        ConsulClient consulClient = new ConsulClient(consulHost, consulPort);

        return consulClient.getCatalogServices(
                        CatalogServicesRequest.newBuilder().build()
                )
                .getValue().entrySet().stream().filter(stringListEntry -> stringListEntry.getValue().contains("color-service"))
                .flatMap(stringListEntry -> consulClient.getCatalogService(stringListEntry.getKey(), CatalogServiceRequest.newBuilder().build()).getValue().stream())
                .map(this::getColor)
                .collect(Collectors.toList());
    }

    private String getColor(CatalogService service) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://" + service.getServiceAddress() + ":" + service.getServicePort() + "/color"))
                .GET()
                .build();

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}