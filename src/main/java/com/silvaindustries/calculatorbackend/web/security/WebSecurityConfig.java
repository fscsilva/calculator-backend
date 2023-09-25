package com.silvaindustries.calculatorbackend.web.security;

import com.silvaindustries.calculatorbackend.service.v1.UserService;
import com.silvaindustries.calculatorbackend.web.api.Version;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserService userService;
    private final JwtAuthenticationFilter authFilter;
    private static final String ALL_INCLUDE_PATH = "/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(Version.BASE_PATH + ALL_INCLUDE_PATH, Version.BASE_PATH_V2 + ALL_INCLUDE_PATH)
            .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers(HttpMethod.GET, buildNotSecuredGetResources())
                .permitAll()
                .requestMatchers(HttpMethod.POST, buildNotSecuredGetResources())
                .permitAll()
                .requestMatchers(Version.BASE_PATH  + "/admin/**", Version.BASE_PATH_V2  + "/admin/**").hasAuthority("ADMIN")
                .requestMatchers(Version.BASE_PATH  + "/user/**", Version.BASE_PATH_V2  + "/user/**").hasAnyAuthority("USER", "ADMIN")
                .anyRequest()
                .authenticated())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .exceptionHandling(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form
                .loginPage(Version.BASE_PATH + "/login")
                .defaultSuccessUrl("/calculator")
                .failureUrl("/login.html?error=true")
                .permitAll())
            .logout(logout -> logout
                .logoutUrl(Version.BASE_PATH + "/logout")
                .invalidateHttpSession(true)
                .addLogoutHandler(new SecurityContextLogoutHandler())
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .logoutSuccessUrl(Version.BASE_PATH + "/login").permitAll()
        );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }


    private String[] buildNotSecuredGetResources() {
        return new String[]{"/v3/api-docs", "/swagger-ui/index.html",  "/login", "/logout"};
    }

}
