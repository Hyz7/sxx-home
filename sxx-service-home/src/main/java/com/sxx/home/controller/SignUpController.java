package com.sxx.home.controller;

import com.sxx.api.home.SignUpControllerApi;
import com.sxx.framework.domain.student.Student;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.home.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈学员注册控制层〉
 *
 * @author hyz
 * @create 2019/3/7 0007
 * @since 1.0.0
 */
@RestController
@RequestMapping("/home")
public class SignUpController implements SignUpControllerApi {

    @Autowired
    private SignUpService signUpService;

    /**
     * 添加学员报名信息
     *
     * @param student 学员信息
     * @return 结果
     */
    @Override
    @PostMapping("/studentSignUp")
    public ResponseResult studentSignUp(@RequestBody Student student) {
        return signUpService.studentSignUp(student);
    }
}
