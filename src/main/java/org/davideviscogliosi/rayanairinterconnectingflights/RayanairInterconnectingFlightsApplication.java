package org.davideviscogliosi.rayanairinterconnectingflights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RayanairInterconnectingFlightsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RayanairInterconnectingFlightsApplication.class, args);
    }

}
