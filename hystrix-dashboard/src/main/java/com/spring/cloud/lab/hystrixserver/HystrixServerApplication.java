package com.spring.cloud.lab.hystrixserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableHystrixDashboard
@SpringBootApplication
@EnableTurbine
public class HystrixServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixServerApplication.class, args);
	}
}
