package br.com.dalq.fiap_tech_challenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI locaTech() {
        return new OpenAPI()
                .info(new Info().title("Locatech API")
                    .description("Projeto desenvolvido por Daniel Santello")
                    .version("1.0.0")
                    .license(new License()
                            .name("Licença Free")
                            .url("https://www.apache.org/licenses/LICENSE-2.0")
                    )
                );
    }
}
