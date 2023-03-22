package com.example.dom3.config;

import com.example.dom3.filters.JwtFilter;
import com.example.dom3.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final JwtFilter jwtFilter;

    @Autowired
    public SpringSecurityConfig(UserService userService, JwtFilter jwtFilter){
        this.userService = userService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userService);
    }

    @Override
    protected  void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/api/users/add/**").hasAuthority("can_create_users")
                .antMatchers("/api/users/get/**").hasAuthority("can_read_users")
                .antMatchers("/api/users/update/**").hasAuthority("can_read_users")
                .antMatchers("/api/users/delete/**").hasAuthority("can_delete_users")
                .antMatchers("/api/machine/get/**").hasAuthority("can_search_machines")
                .antMatchers("/api/machine/errors/**").hasAuthority("can_search_machines")
                .antMatchers("/api/machine/start/**").hasAuthority("can_start_machines")
                .antMatchers("/api/machine/stop/**").hasAuthority("can_stop_machines")
                .antMatchers("/api/machine/restart/**").hasAuthority("can_restart_machines")
                .antMatchers("/api/machine/create/**").hasAuthority("can_create_machines")
                .antMatchers("/api/machine/destroy/**").hasAuthority("can_destroy_machines")
                .antMatchers("/api/machine/schedule/**").hasAuthority("can_schedule_machines")
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return  super.authenticationManager();
    }
}
