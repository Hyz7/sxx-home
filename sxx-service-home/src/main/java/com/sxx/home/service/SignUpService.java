package com.sxx.home.service;

import com.sxx.framework.domain.student.Student;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.home.mapper.SignUpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈学员注册业务层〉
 *
 * @author hyz
 * @create 2019/3/7 0007
 * @since 1.0.0
 */
@Service
public class SignUpService {

    @Autowired
    private SignUpMapper signUpMapper;

    /**
     * 添加学员报名信息
     *
     * @param student 学员信息
     * @return 结果
     */
    public ResponseResult studentSignUp(Student student) {
        // 设置为未付款
        student.setIsPay(false);
        signUpMapper.addStudent(student);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
