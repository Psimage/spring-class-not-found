package me.yarosbug.scnf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LoadbalancerWorkaroundApp {

	public static void main(String[] args) {
		SpringApplication.run(LoadbalancerWorkaroundApp.class, args);
	}

}
