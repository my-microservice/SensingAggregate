package io.wisoft.eventsourcing.sensing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SensingEventSourcingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensingEventSourcingApplication.class, args);
    }

}
