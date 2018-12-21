package com.sxx.security.service;

import com.sxx.framework.domain.ucenter.User;
import com.sxx.framework.domain.ucenter.reponse.LoginResult;
import com.sxx.framework.domain.ucenter.request.LoginRequest;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.security.dao.AuthReponsitoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈授权业务层〉
 *
 * @author hyz
 * @create 2018/12/21 0021
 * @since 1.0.0
 */
@Service
public class AuthService {
    @Autowired
    private AuthReponsitoty authReponsitoty;
    /**
     * 登录
     * @param loginRequest 参数
     * @return 结果集
     */
    public LoginResult login(LoginRequest loginRequest) {
        User user = authReponsitoty.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null){
            return new LoginResult(CommonCode.FAIL);
        }
        return new LoginResult(CommonCode.SUCCESS);
    }
}
