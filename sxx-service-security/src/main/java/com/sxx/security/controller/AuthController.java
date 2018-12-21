package com.sxx.security.controller;

import com.sxx.api.auth.AuthControllerApi;
import com.sxx.framework.domain.ucenter.reponse.LoginResult;
import com.sxx.framework.domain.ucenter.request.LoginRequest;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈授权控制层〉
 *
 * @author hyz
 * @create 2018/12/21 0021
 * @since 1.0.0
 */
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerApi {
    @Autowired
    private AuthService authService;
    /**
     * 登录
     * @param loginRequest 参数
     * @return 结果集
     */
    @Override
    @PostMapping("/login")
    public LoginResult login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Override
    public ResponseResult logout() {
        return null;
    }
}
