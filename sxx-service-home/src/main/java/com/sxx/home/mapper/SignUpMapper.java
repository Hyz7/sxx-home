/**
 * 〈一句话功能简述〉<br>
 * 〈学员注册持久层〉
 *
 * @author hyz
 * @create 2019/3/7 0007
 * @since 1.0.0
 */
package com.sxx.home.mapper;

import com.sxx.framework.domain.student.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignUpMapper {
    /**
     * 添加学员报名信息
     *
     * @param student 学员信息
     */
    void addStudent(Student student);
}
