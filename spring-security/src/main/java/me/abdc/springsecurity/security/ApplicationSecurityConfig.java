package me.abdc.springsecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static me.abdc.springsecurity.security.ApplicationUserPermission.COURSE_WRITE;
import static me.abdc.springsecurity.security.ApplicationUserRole.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers("/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("kim123")
                .password(passwordEncoder.encode("1234"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.grantedAuthorities())
                .build();

        UserDetails user2 = User.builder()
                .username("lee123")
                .password(passwordEncoder.encode("1234"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.grantedAuthorities())
                .build();

        UserDetails user3 = User.builder()
                .username("park123")
                .password(passwordEncoder.encode("1234"))
//                .roles(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.grantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }
}
