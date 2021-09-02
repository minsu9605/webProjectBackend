package com.example.CUSHProject.config;

import com.example.CUSHProject.service.MemberLoginFailService;
import com.example.CUSHProject.service.MemberLoginSuccessService;
import com.example.CUSHProject.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private MemberService memberService;

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new MemberLoginFailService();
    }
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new MemberLoginSuccessService();
    }

    public WebSecurityConfig(MemberService memberService){
        this.memberService = memberService;
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/","/welcome","/account/register","/login","/board/list").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    //.antMatchers("/hello").hasRole("MEMBER")
                    .anyRequest().authenticated()
                    .and()
                .csrf()
                    .ignoringAntMatchers("/usernameOverlap")
                    .ignoringAntMatchers("/nicknameOverlap")
                    .ignoringAntMatchers("/nicknameModify")
                    .ignoringAntMatchers("/usernameModify")
                    .ignoringAntMatchers("/pwCheck")
                    .ignoringAntMatchers("/admin/withdrawal")
                    .and()
                .formLogin()
                    .loginPage("/account/login")
                    //.loginProcessingUrl("/account/login")
                    .successHandler(successHandler())
                    .failureHandler(failureHandler())
                    .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
