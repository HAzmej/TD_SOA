package fr.insa.micro.User_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class UserEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserEurekaApplication.class, args);
	}

}
