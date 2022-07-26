package org.abratuhi;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.List;

@ApplicationScoped
public class AppLifecycleBean {

    @ConfigProperty(name = "consul.host", defaultValue = "localhost")
    String consulHost;

    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consulPort;

    @ConfigProperty(name = "app.color", defaultValue = "black")
    String color;

    @ConfigProperty(name = "app.advertised.host", defaultValue = "localhost")
    String advertisedHost;

    @ConfigProperty(name = "app.advertised.port", defaultValue = "8080")
    Integer advertisedPort;

    void onStart(@Observes StartupEvent ev) {
        ConsulClient consulClient = new ConsulClient(consulHost, consulPort);

        NewService newService = new NewService();
        newService.setId("color-service-" + color);
        newService.setName("color-service");
        newService.setTags(List.of("color-service", color));
        newService.setAddress(advertisedHost);
        newService.setPort(advertisedPort);

        consulClient.agentServiceRegister(newService);
    }

    void onStop(@Observes ShutdownEvent ev) {
        ConsulClient consulClient = new ConsulClient(consulHost, consulPort);

        consulClient.agentServiceDeregister("color-service-" + color);
    }
}
