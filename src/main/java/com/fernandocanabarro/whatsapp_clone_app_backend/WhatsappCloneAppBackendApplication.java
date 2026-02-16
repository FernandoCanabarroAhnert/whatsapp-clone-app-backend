package com.fernandocanabarro.whatsapp_clone_app_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WhatsappCloneAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatsappCloneAppBackendApplication.class, args);
	}

}
