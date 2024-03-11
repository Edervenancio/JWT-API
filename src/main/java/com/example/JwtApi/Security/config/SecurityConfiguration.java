package com.example.JwtApi.Security.config;


import ch.qos.logback.classic.encoder.JsonEncoder;
import com.example.JwtApi.Security.Authentication.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /*
 @Configuration: Esta é uma anotação do Spring que indica que a classe anotada é uma classe
  de configuração. As classes de configuração podem conter métodos anotados com @Bean,
  que são usados para instanciar, configurar e inicializar objetos a serem gerenciados pelo contêiner
   Spring.

@EnableWebSecurity: Esta anotação é usada para ativar a segurança da web no projeto Spring Boot.
Essa anotação sinaliza ao Spring que a classe anotada deve ser usada para a configuração
do Spring Security. Isso permite ao desenvolvedor personalizar as regras de segurança,
 como regras de autenticação e autorização para rotas específicas, bem como outras configurações
  de segurança.



     */

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/users/login", //url que usaremos pra fazer o login
            "/users", //url que usaremos para criar um usuario
    };

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/users/test"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de cliente
    public static final String [] ENDPOINTS_CUSTOMER = {
            "/users/test/customer"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de administrador
    public static final String [] ENDPOINTS_ADMIN = {
            "/users/test/administrator"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Desativa a proteção contra CSRF
                .sessionManagement( sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura a política de criação de sessão como stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                        .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR") // Repare que não é necessário colocar "ROLE" antes do nome, como fizemos na definição das roles
                        .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
                        .anyRequest().denyAll()) // Habilita a autorização para as requisições HTTP

                // Adiciona o filtro de autenticação de usuário que criamos, antes do filtro de segurança padrão do Spring Security
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
