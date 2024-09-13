package com.example.thymeleaf.ThymeleafTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ThymeleafTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafTestApplication.class, args);
	}

	@Bean
	RestTemplate getRestTemplate() {

		return new RestTemplate();
	}

	@Bean
	WebClient getWebClient(ClientRegistrationRepository clientRegistrationRepository,
						   OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository) {

		var oauht2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
				clientRegistrationRepository,
				oAuth2AuthorizedClientRepository
		);

		/// System.out.println("nbfmfnmdmf");

		oauht2.setDefaultOAuth2AuthorizedClient(true);

		return WebClient.builder().apply(oauht2.oauth2Configuration()).build();
	}

}
