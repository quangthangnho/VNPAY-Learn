package com.thanhquang.sourcebase.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SwaggerConfiguration {

    @Value("${swagger.host}")
    private String host;

    @Value("${server.servlet.context-path}")
    private String path;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl(host + path.replace("/", ""));
        server.setDescription("Server URL");

        Contact contact = new Contact();
        contact.setEmail("quangvvt.0802@gmail.com");
        contact.setName("Thanh Quang");
        contact.setUrl("fb.com/quang.thangnho");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Tutorial Management API")
                .version("1.0")
                .contact(contact)
                .description("email: quangvvt.0802@gmail.com \n facebook: fb.com/quang.thangnho")
                .termsOfService("quangvvt.0802@gmail.com")
                .license(mitLicense);
        return new OpenAPI().info(info).servers(List.of(server));
    }
}
