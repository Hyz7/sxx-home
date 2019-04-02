package com.sxx.home.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sxx.framework.domain.course.Course;
import com.sxx.framework.domain.course.dto.CourseListDTO;
import com.sxx.framework.domain.course.ext.TeachplanNode;
import com.sxx.framework.domain.course.response.CourseListDTOResult;
import com.sxx.framework.domain.course.response.CourseResult;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.home.mapper.CourseManageMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈课程管理业务层〉
 *
 * @author hyz
 * @create 2019/3/6 0006
 * @since 1.0.0
 */
@Service
public class CourseManageService {
    @Autowired
    private CourseManageMapper courseManageMapper;

    /**
     * 根据课程标题分页模糊查询课程信息列表
     *
     * @param courseTitle 课程标题
     * @return 课程信息列表
     */
    public CourseListDTOResult queryCourseList(String courseTitle, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Page<CourseListDTO> courseListDTOS = courseManageMapper.queryList(courseTitle);
        List<CourseListDTO> courseListDTOSResult = courseListDTOS.getResult();
        return new CourseListDTOResult(CommonCode.SUCCESS, courseListDTOSResult);
    }

    /**
     * 根据id查询课程信息
     *
     * @param courseId 课程id
     * @return 课程信息结果
     */
    @Transactional(rollbackOn = Exception.class)
    public CourseResult queryCourseInformationByCourseId(String courseId) {
        if (StringUtils.isEmpty(courseId)){
            new CourseResult(CommonCode.INVALID_PARAM, null);
        }
        // 增加课程播放次数
        courseManageMapper.addWatchCount(courseId);
        Course course = courseManageMapper.queryCourseInformationByCourseId(courseId);
        return new CourseResult(CommonCode.SUCCESS, course);
    }

    /**
     * 查询课程计划页面列表
     *
     * @param courseId 课程id
     * @return 结果
     */
    public TeachplanNode findTeachplanList(String courseId) {
        if (StringUtils.isEmpty(courseId)){
            return null;
        }
        return courseManageMapper.findTeachplanList(courseId);
    }


}
