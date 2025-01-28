package com.jesuslg.rutilandia2.configuraciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
/**
 * Clase para configurar el WebClient y poderlo usar en mis servicios.
 */
@Configuration
public class configuracionApp {
	
	@Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
	

}
