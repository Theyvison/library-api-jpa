package io.github.theyvison.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // diabilita CSRF para facilitar testes com Postman
                //.formLogin(Customizer.withDefaults()) // habilita form login padrão do Spring Security
                .formLogin(configurer -> {
                        configurer.loginPage("/login").permitAll();
                })// página de login personalizada
                .httpBasic(Customizer.withDefaults()) // habilita autenticação HTTP Basic
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated()) // todas as requisições exigem autenticação
                .build();
    }
}
