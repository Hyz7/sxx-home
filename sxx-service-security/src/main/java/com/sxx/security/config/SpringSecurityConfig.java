package com.sxx.security.config;

import com.sxx.security.handler.SxxAuthenticationFailureHandler;
import com.sxx.security.handler.SxxAuthenticationSuccessHandle;
import com.sxx.security.service.SxxUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 〈一句话功能简述〉<br>
 * 〈自定义SpringSecurity类〉
 *
 * @author hyz
 * @create 2018/12/21 0021
 * @since 1.0.0
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 依赖注入自定义的登录成功处理器
     */
    @Autowired
    private SxxAuthenticationSuccessHandle sxxAuthenticationSuccessHandler;

    /**
     * 依赖注入自定义的登录失败处理器
     */
    @Autowired
    private SxxAuthenticationFailureHandler sxxAuthenticationFailureHandler;
    @Autowired
    private SxxUserDetailService sxxUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 添加自定义的认证逻辑
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // 创建DaoAuthenticationProvider实例
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 将自定义的认证逻辑添加到DaoAuthenticationProvider
        authProvider.setUserDetailsService(sxxUserDetailService);
        // 设置自定义的密码加密
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin()
                .successHandler(sxxAuthenticationSuccessHandler)
                .failureHandler(sxxAuthenticationFailureHandler);
        http.csrf().disable(); // 关闭csrf验证
    }

    /**
     * 创建PasswordEncoder对应的Bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
