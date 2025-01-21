package com.eroskoller.practicing.cards;

import com.eroskoller.practicing.cards.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@OpenAPIDefinition(
	info = @io.swagger.v3.oas.annotations.info.Info(
		title = "Cards API",
		version = "1.0",
		description = "API for Cards"
	)
)
@EnableConfigurationProperties(value = {CardsContactInfoDto.class})
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
