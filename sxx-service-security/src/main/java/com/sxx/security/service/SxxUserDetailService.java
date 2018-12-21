package com.sxx.security.service;

import com.sxx.security.dao.AuthReponsitoty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈登录验证〉
 *
 * @author hyz
 * @create 2018/12/21 0021
 * @since 1.0.0
 */
@Component
@Slf4j
public class SxxUserDetailService implements UserDetailsService {
    @Autowired
    private AuthReponsitoty authReponsitoty;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 技巧01： /login 请求传过来的用户名会传到这里
        log.info("用户名：" + username);

        com.sxx.framework.domain.ucenter.User user = authReponsitoty.findByUsername(username);
        // 02 根据用户名到数据库中查找数据
        // 技巧02：此处是模拟的从数据库中查询到的密码
        String password = passwordEncoder.encode(user.getPassword());

        // 03 返回一个 User 对象（技巧01：这个User对象时实现了UserDetail接口的，这里利用的是Spring框架提供的User对象，也可以使用自定义但是实现了UserDetai接口的User对象）
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
