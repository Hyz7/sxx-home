package com.sxx.home.controller;

import com.sxx.api.course.CourseManageControllerApi;
import com.sxx.framework.domain.course.ext.TeachplanNode;
import com.sxx.framework.domain.course.response.CourseListDTOResult;
import com.sxx.framework.domain.course.response.CourseResult;
import com.sxx.home.service.CourseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈课程管理控制层〉
 *
 * @author hyz
 * @create 2019/3/6 0006
 * @since 1.0.0
 */
@RestController
@RequestMapping("/home/course")
public class CourseManageController implements CourseManageControllerApi {
    @Autowired
    private CourseManageService courseManageService;

    /**
     * 根据课程标题分页模糊查询课程信息列表
     *
     * @param courseTitle 课程标题
     * @return 课程信息列表
     */
    @Override
    @GetMapping("/queryCourseList")
    public CourseListDTOResult queryCourseList(String courseTitle,
                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return courseManageService.queryCourseList(courseTitle, page, size);
    }

    /**
     * 根据id查询课程信息
     *
     * @param courseId 课程id
     * @return 课程信息结果
     */
    @Override
    @GetMapping("/queryCourseInformationByCourseId")
    public CourseResult queryCourseInformationByCourseId(@RequestParam("courseId") String courseId) {
        return courseManageService.queryCourseInformationByCourseId(courseId);
    }

    /**
     * 查询课程计划页面列表
     *
     * @param courseId 课程id
     * @return 结果
     */
    @Override
    @GetMapping("/findTeachplanList")
    public TeachplanNode findTeachplanList(@RequestParam("courseId") String courseId) {
        return courseManageService.findTeachplanList(courseId);
    }

}
