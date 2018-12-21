/**
 * 〈一句话功能简述〉<br>
 * 〈授权持久层〉
 *
 * @author hyz
 * @create 2018/12/21 0021
 * @since 1.0.0
 */
package com.sxx.security.dao;

import com.sxx.framework.domain.ucenter.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthReponsitoty extends JpaRepository<User,Integer> {
    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    User findByUsernameAndPassword(String username,String password);

    /**
     * 根据用户名获取对象
     * @param username 用户名
     * @return 用户
     */
    User findByUsername(String username);
}
