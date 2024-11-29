package fr.insa.micro.Mission_MicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MissionMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MissionMicroServiceApplication.class, args);
	}

}
