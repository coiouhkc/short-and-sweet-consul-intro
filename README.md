---

# Short and Sweet

## Consul Intro

---

# Service discovery/ service registry

See 

* https://www.nginx.com/blog/service-discovery-in-a-microservices-architecture/

* https://www.baeldung.com/cs/service-discovery-microservices

* https://dzone.com/articles/service-discovery-in-a-microservices-architecture

---

# Consul

See https://www.consul.io/

---

# Notable mentions

See 

* https://github.com/Netflix/eureka

* https://zookeeper.apache.org

* https://github.com/coreos/etcd

---

# Forerunner

See https://www.osgi.org/

TODO: small demo

See https://www.knopflerfish.org/releases/current/docs/javadoc/org/osgi/framework/BundleContext.html#registerService(java.lang.Class,%20S,%20java.util.Dictionary) and https://www.knopflerfish.org/releases/current/docs/javadoc/org/osgi/framework/BundleContext.html#getServiceReferences(java.lang.Class,%20java.lang.String)

---

# Consul demo (self-registration)

See [infra/docker-compose.yml](self/infra/docker-compose.yml) + [self/*](self/*)

---

# Consul demo (3rd-party-registration, k8s)

TODO: small demo

See https://github.com/hashicorp/consul-k8s/blob/main/control-plane/connect-inject/container_init.go

---

# Additional reading

* https://quarkus.io/guides/stork
* https://learn.hashicorp.com/tutorials/consul/kubernetes-minikube?in=consul/kubernetes
* https://github.com/Ecwid/consul-api


---

# Consul REST API

* http://localhost:8500/v1/catalog/services
* http://localhost:8500/v1/health/service/consul?pretty

---

# Snippets

```
kubectl config view --raw > ~/.kube/config
helm install --values helm-consul-values.yaml consul hashicorp/consul --create-namespace --namespace consul --version "0.43.0"
kubectl exec --stdin --tty consul-server-0 --namespace consul -- /bin/sh
kubectl run color-producer-red --image=bratuhia/color-producer:1.0.0 --env="CONSUL_ENABLED=false" --env="APP_COLOR=red" --dry-run=client -o yaml
```

---