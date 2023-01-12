package com.eliasdejan.digial_menu;

import com.eliasdejan.digial_menu.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.beans.BeanProperty;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/menu-item-types").authenticated()
                .antMatchers("/menu-item-types/edit").authenticated()
                .antMatchers("/menu-items").authenticated()
                .antMatchers("/menu-items/edit").authenticated()
                .antMatchers("/orders").authenticated()
                .antMatchers("/users").authenticated()
                .antMatchers("/users/edit").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/users")
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll();
    }
}
