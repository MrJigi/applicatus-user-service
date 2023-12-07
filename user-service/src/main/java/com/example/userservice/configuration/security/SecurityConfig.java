package com.example.userservice.configuration.security;

import com.example.userservice.configuration.security.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebSecurity
//@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {


//    private final AuthTokenFilter jwtFilter;
//    private final AuthenticationProvider authenticationProvider;
//    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer-> AbstractHttpConfigurer.disable());
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());


//                .ignoringRequestMatchers("/**")
//                .and()
//                .httpBasic()
//                .disable()
//                .formLogin()
//                .disable()
//                http.authorizeHttpRequests( authorize -> authorize
//                .requestMatchers("/**")
//                .permitAll()
//                .requestMatchers("/**")
//                .hasRole("USER")
//                .anyRequest()
//                .authenticated());

//                http.httpBasic(Customizer.withDefaults());
                http.formLogin(formLoginSpec -> formLoginSpec.disable());
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .logout( )
//                .logoutUrl("/entry/logout")
//                .addLogoutHandler(logoutHandler)
//                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())


        return http.build();
    }


//    @Bean
//    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer
//                        .ignoringRequestMatchers("/**")
//                )
////                .authorizeHttpRequests(auth -> {
////                    auth
////                            .requestMatchers(new AntPathRequestMatcher("/api/auth",HttpMethod.POST.toString())).permitAll()
////                            .requestMatchers(new AntPathRequestMatcher("/api/auth/logout",HttpMethod.POST.toString())).permitAll()
////                            .requestMatchers(new AntPathRequestMatcher()).permitAll()
////                            .requestMatchers("**/ws").permitAll()
////                            .anyRequest().authenticated();
////                            .requestMatchers("/**").hasRole("USER")
////                })
//
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/**")
////                        .permitAll()
////                        .requestMatchers("**/ws")
////                        .permitAll()
////                        .requestMatchers("/**")
////                        .hasRole("USER")
////                        .anyRequest().authenticated())
//
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////
////                .authenticationProvider(authenticationProvider)
////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//
////                .logout(logoutHandler -> logoutHandler
////                        .logoutUrl("/api/auth/logout")
////                        .addLogoutHandler(this.logoutHandler)
////                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
////                )
//
//
//                .build();
////                .httpBasic(Customizer.withDefaults())
//
//    }





//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }

}
