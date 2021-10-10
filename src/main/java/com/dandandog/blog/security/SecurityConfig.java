package com.dandandog.blog.security;

import com.dandandog.blog.security.service.AuthorizedService;
import com.dandandog.framework.faces.config.properties.PageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.annotation.Resource;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PageProperties page;

    @Resource
    private AuthorizedService authorizedService;

    @Autowired
    public SecurityConfig(PageProperties page) {
        this.page = page;
    }

    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        FacesFailureHandler facesFailureHandler = new FacesFailureHandler(page.getLoginFailed());
        FacesSuccessHandler facesSuccessHandler = new FacesSuccessHandler(page.getIndex());

        http.authorizeRequests()
                //游客允许访问
                .antMatchers(page.getAnonymous()).anonymous()
                //所有请求需要登入
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(page.getLogin())
                .permitAll()
                .successHandler(facesSuccessHandler)
                .failureHandler(facesFailureHandler)
                .and()
                .logout().logoutSuccessUrl(page.getLogin())
                .and()
                .sessionManagement().invalidSessionUrl(page.getLogin()).maximumSessions(10)
                .sessionRegistry(sessionRegistry());
        // 关闭CSRF跨域
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/javax.faces.resource/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        AuthenticationProvider provider = new AuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(authorizedService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        ServletListenerRegistrationBean<HttpSessionEventPublisher> registration = new ServletListenerRegistrationBean<>();
        registration.setListener(new HttpSessionEventPublisher());
        return registration;
    }

}
