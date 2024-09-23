package br.com.selfmaintenance.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Selfmaintenance API")
                        .version("1.0.0")
                        .description("API para gerenciamento de manutenção de veículos e recursos relacionados.")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .addTagsItem(new Tag().name("Veículos").description("Operações relacionadas a veículos."))
                .addTagsItem(new Tag().name("Usuários").description("Operações relacionadas a usuários."))
                .addTagsItem(new Tag().name("Recursos").description("Operações relacionadas a recursos."))
                .addTagsItem(new Tag().name("Prestadores").description("Operações relacionadas a prestadores de serviços."))
                .addTagsItem(new Tag().name("Autenticação").description("Operações relacionadas à autenticação de usuários."));

    }

}
