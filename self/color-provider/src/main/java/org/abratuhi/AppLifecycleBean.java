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

    @ConfigProperty(name = "consul.enabled", defaultValue = "true")
    boolean consulEnabled;

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
        if (consulEnabled) {
            ConsulClient consulClient = new ConsulClient(consulHost, consulPort);

            NewService newService = new NewService();
            newService.setId("color-provider-" + color);
            newService.setName("color-provider");
            newService.setTags(List.of("color-provider", color));
            newService.setAddress(advertisedHost);
            newService.setPort(advertisedPort);

            consulClient.agentServiceRegister(newService);
        }
    }

    void onStop(@Observes ShutdownEvent ev) {
        if (consulEnabled) {
            ConsulClient consulClient = new ConsulClient(consulHost, consulPort);

            consulClient.agentServiceDeregister("color-provider-" + color);
        }
    }
}
