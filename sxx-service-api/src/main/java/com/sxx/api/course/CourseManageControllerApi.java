/**
 * 〈一句话功能简述〉<br>
 * 〈课程管理接口〉
 *
 * @author hyz
 * @create 2019/3/6 0006
 * @since 1.0.0
 */
package com.sxx.api.course;


import com.sxx.framework.domain.course.ext.TeachplanNode;
import com.sxx.framework.domain.course.response.CourseListDTOResult;
import com.sxx.framework.domain.course.response.CourseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "后台管理思学行课程管理接口", description = "提供操作思学行课程的增删改查")
public interface CourseManageControllerApi {
    /**
     * 根据课程标题分页模糊查询课程信息列表
     *
     * @param courseTitle 课程标题
     * @return 课程信息列表
     */
    @ApiOperation("根据课程标题分页模糊查询课程信息列表")
    CourseListDTOResult queryCourseList(String courseTitle, Integer page, Integer size);

    /**
     * 根据课程id查询课程信息
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    @ApiOperation("根据id查询课程信息")
    CourseResult queryCourseInformationByCourseId(String courseId);

    /**
     * 查询课程计划页面列表
     *
     * @param courseId 课程id
     * @return 结果
     */
    @ApiOperation("课程计划页面列表")
    TeachplanNode findTeachplanList(String courseId);

}
