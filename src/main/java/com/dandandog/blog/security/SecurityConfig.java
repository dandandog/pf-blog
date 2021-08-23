package com.dandandog.blog.security;

import com.dandandog.blog.modules.system.service.AuthUserService;
import com.dandandog.framework.faces.config.properties.PageProperties;
import com.google.common.collect.Multimap;
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
    private AuthUserService authUserService;

    @Autowired
    public SecurityConfig(PageProperties page) {
        this.page = page;
    }

    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("admin").password("{noop}password").roles("ADMIN");
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        FacesFailureHandler facesFailureHandler = new FacesFailureHandler(page.getLoginFailed());
        FacesSuccessHandler facesSuccessHandler = new FacesSuccessHandler(page.getIndex());

        // http.addFilterBefore(captchaFilter(facesFailureHandler), UsernamePasswordAuthenticationFilter.class);
        Multimap<String, String> userRoles = authUserService.loadUserRole();

        for (String key : userRoles.keys()) {
            String[] urls = userRoles.get(key).toArray(new String[0]);
            http.authorizeRequests().antMatchers(urls).hasAuthority(key).
                    and().authorizeRequests().antMatchers(urls).hasRole("ADMIN");
        }

        http.authorizeRequests()
                .antMatchers(page.getForget(), "/test").anonymous()
                .antMatchers("/", "/upload", "/logout", page.getIndex(), "/register").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(page.getLogin()).permitAll()
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


//    private GenericFilterBean captchaFilter(FacesFailureHandler facesFailureHandler) {
//        return new CaptchaAuthenticationFilter((request, response, e) -> {
//            try {
//                facesFailureHandler.onAuthenticationFailure(request, response, new CaptchaErrorException(e.getMessage()));
//            } catch (Exception ignored) {
//                log.error("captcha filter error");
//            }
//        });
//    }

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
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(authUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

//    @Bean
//    public ServletRegistrationBean<CaptchaServlet> getServletRegistrationBean(CaptchaServletFactory servletFactory) {
//        CaptchaServlet captchaServlet = servletFactory.generate(ImageCaptcha.class);
//        ServletRegistrationBean<CaptchaServlet> bean = new ServletRegistrationBean<>(captchaServlet);
//        bean.addUrlMappings("/captcha/image.jpg");
//        return bean;
//    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        ServletListenerRegistrationBean<HttpSessionEventPublisher> registration = new ServletListenerRegistrationBean<>();
        registration.setListener(new HttpSessionEventPublisher());
        return registration;
    }

}
