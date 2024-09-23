package br.com.selfmaintenance.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.selfmaintenance.app.components.FiltroSeguranca;
import br.com.selfmaintenance.domain.entities.usuario.UsuarioRole;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    private final FiltroSeguranca filtroSeguranca;
    private final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**"
    };

    public SecurityConfigurations(FiltroSeguranca filtroSeguranca) {
        this.filtroSeguranca = filtroSeguranca;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(SWAGGER_WHITELIST).permitAll();
                    authorize.requestMatchers("/auth/login", "/usuario").permitAll()
                            .requestMatchers("/veiculo", "/veiculo/{id}").hasAuthority(UsuarioRole.CLIENTE.getRole())
                            .requestMatchers("/recurso", "/recurso/{id}").hasAuthority(UsuarioRole.PRESTADOR.getRole())
                            .requestMatchers("/oficina", "/oficina/prestador").hasAnyAuthority(UsuarioRole.OFICINA.getRole());
                    authorize.anyRequest().authenticated();
                })
                .addFilterBefore(this.filtroSeguranca, UsernamePasswordAuthenticationFilter.class)
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
