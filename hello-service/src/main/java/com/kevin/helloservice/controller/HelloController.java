package com.kevin.helloservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DiscoveryClient client;

    @GetMapping("/{name}")
    public String sayHello(@PathVariable("name") String name) {
        List<String> services = client.getServices();
        for (String service : services) {
            logger.info("/hello/{}, service_id: {}", name, service);
            List<ServiceInstance> instances = client.getInstances(service);
            for (ServiceInstance instance : instances) {
                logger.info("Service instance info, host: {},  scheme: {}, port: {}, uri: {}, service_id: {}", instance.getHost(), instance.getScheme(), instance.getPort(), instance.getUri(), instance.getServiceId());
            }
        }
        return "Hello, " + name;
    }
}
