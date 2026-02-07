package com.iti.backend_challenge.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Password Validator API")
                    .version("1.0")
                    .description("API for password validation service")
                    .contact(
                        Contact()
                            .name("ITI Backend Challenge")
                    )
            )
    }
}

