package pe.edu.smartspace.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                // 💥 REGISTRAR EL PROVIDER AQUÍ
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(req -> req
                        .requestMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/",
                                "/index.html",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        // ---- USUARIOS ----
                        // Solo ADMIN puede listar / editar / eliminar
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")

                        // Cualquier persona puede registrarse por primera vez
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()

                        // ---- MUEBLES ----
                        // ADMIN puede listar/editar/eliminar
                        .requestMatchers(HttpMethod.GET, "/api/muebles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/muebles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/muebles/**").hasRole("ADMIN")

                        // CLIENTE y ADMIN pueden registrar muebles
                        .requestMatchers(HttpMethod.POST, "/api/muebles").authenticated()


                        // DISEÑOS — NUEVO
                        .requestMatchers(HttpMethod.GET, "/api/disenos/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/disenos/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/disenos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/disenos/**").hasRole("ADMIN")

                        // Resto requiere autenticación
                        .anyRequest().authenticated()
                )


                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}