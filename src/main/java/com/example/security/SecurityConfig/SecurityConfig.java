package com.example.security.SecurityConfig;

import com.example.security.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;
    // this method deal with username and password in way that i want as a programmer

    @Bean //to use this method !
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //resposible for username!
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }


    //config sec doing dao
    //serivce >> myuser >> check if the user found!
    //User class implements
    //return beck to security config > and add dependency for MyUser...
    //and put it inside dao-------.setUserDetailsService(myUser....) etc!


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //csrf << cross
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                //maybe will be like 60000 lines!
                //permitAll >> means i want this page be public like: Home page, Register Page!
                //if i want add authrization for specific user! use >> hasAuthority()
                // but if i want add authrization for many users! use >> hasAnyAuthority()
//                .requestMatchers("/api/v1/auth/**").permitAll() // ** means all after auth!
                // the autherization must do it on all APIs !!!
//                .requestMatchers(HttpMethod.POST,"/api/v1/auth/register").permitAll()
                //in this request only foucs on post methods!
                .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll()
//                .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll()
                .requestMatchers("/api/v1/auth/get-users", "/api/v1/auth/delete/{username}").hasAuthority("ADMIN")
                //hasRole("admin")
//                .anyRequest().authenticated()
                .anyRequest().permitAll() //<< saving my system with this line!
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();

        return http.build();

    }
}
