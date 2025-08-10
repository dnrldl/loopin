package com.loopin.loopinbackend.domain.auth.security.config;

import com.loopin.loopinbackend.domain.auth.security.entrypoint.CustomAuthenticationEntryPoint;
import com.loopin.loopinbackend.domain.auth.security.handler.CustomAccessDeniedHandler;
import com.loopin.loopinbackend.domain.auth.security.matcher.RequestMatcher;
import com.loopin.loopinbackend.domain.auth.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Auth 관련은 전부 허용
                        .requestMatchers("/api/auth/**").permitAll()

                        // 중복 체크 허용
                        .requestMatchers("/api/users/check/**").permitAll()

                        // Swagger 문서 허용
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // 회원가입 허용
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()

                        // 유저 조회 허용
                        .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()

                        // 게시글 조회 허용
                        .requestMatchers(HttpMethod.GET, "/api/posts", "/api/posts/*").permitAll()

                        // 댓글 조회 허용
                        .requestMatchers(HttpMethod.GET, "/api/posts/*/comments").permitAll()

                        // 그 외 API는 인증 필요
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint) // 401
                        .accessDeniedHandler(accessDeniedHandler) // 403
                );

                // oAuth2

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


