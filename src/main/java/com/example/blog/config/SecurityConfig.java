package com.example.blog.config;

import com.example.blog.security.JWTAuthenticationEntryPoint;
import com.example.blog.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {
    private UserDetailsService userDetailsService;

    private JWTAuthenticationEntryPoint authenticationEntryPoint;
    private JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JWTAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    //    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails tomas = User.builder()
//                .username("tomas")
//                .password(passwordEncoder().encode("123"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(tomas, admin);
//    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorize) -> {
//                    authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
//                            .requestMatchers("/api/auth/**").permitAll()
//                            .anyRequest().authenticated();
//                })
//        .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers("/swagger-ui/**").permitAll() // /swagger-ui/index.html
                            .requestMatchers("/v3/api-docs/**").permitAll()
                            .anyRequest().authenticated();})
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
