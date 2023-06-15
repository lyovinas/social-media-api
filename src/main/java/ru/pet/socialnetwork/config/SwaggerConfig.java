package ru.pet.socialnetwork.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SwaggerConfig {

    //http://localhost:8080/swagger-ui/index.html
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Social Media API")
                .description("RESTful API для социальной медиа платформы, " +
                        "позволяющей пользователям регистрироваться, входить в систему, " +
                        "создавать посты, переписываться, подписываться на других " +
                        "пользователей и получать свою ленту активности.")
                .version("v0.1")
        );
    }
}
