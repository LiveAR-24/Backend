package com.livear.LiveAR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LiveArApplication {
	static {System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");}
	public static void main(String[] args) {
		SpringApplication.run(LiveArApplication.class, args);
	}

}
