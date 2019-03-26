/**
 * 〈一句话功能简述〉<br>
 * 〈授课导师持久层〉
 *
 * @author hyz
 * @create 2019/1/15 0015
 * @since 1.0.0
 */
package com.sxx.home.mapper;

import com.sxx.framework.domain.teacher.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeachingMapper {
    /**
     * 展示授课导师数据
     *
     * @return 结果
     */
    @Select("select * from t_teacher")
    List<Teacher> showTeacherList();
}
