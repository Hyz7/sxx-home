package com.sxx.home.controller;

import com.sxx.api.home.TeachingControllerApi;
import com.sxx.framework.domain.teacher.response.TeacherDTOResult;
import com.sxx.home.service.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈授课导师界面控制层〉
 *
 * @author hyz
 * @create 2019/1/15 0015
 * @since 1.0.0
 */
@RestController
@RequestMapping("/home/teaching")
public class TeachingController implements TeachingControllerApi {

    @Autowired
    private TeachingService teachingService;

    /**
     * 展示授课导师数据
     *
     * @return 结果
     */
    @Override
    @GetMapping("/showTeacherList")
    public TeacherDTOResult showTeacherList() {
        return teachingService.showTeacherList();
    }
}
