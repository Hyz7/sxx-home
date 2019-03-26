package com.sxx.home.test;

import com.alibaba.fastjson.JSON;
import com.sxx.framework.domain.teacher.Teacher;
import com.sxx.framework.domain.teacher.dto.TeacherDTO;
import com.sxx.home.mapper.TeachingMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试类〉
 *
 * @author hyz
 * @create 2019/1/15 0015
 * @since 1.0.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJson {
    @Autowired
    private TeachingMapper teachingMapper;
    @Test
    public void testJson(){
        List<Teacher> teacherList = teachingMapper.showTeacherList();
        List<TeacherDTO> teacherDTOList = new ArrayList<>(teacherList.size());
        for (Teacher teacher : teacherList) {
            TeacherDTO teacherDTO = new TeacherDTO();
            Map map = JSON.parseObject(teacher.getTeaAchievement(), Map.class);
            BeanUtils.copyProperties(teacher,teacherDTO);
            teacherDTO.setTeaAchievement(map);
            teacherDTOList.add(teacherDTO);
        }
        System.out.println(teacherDTOList);
    }
}
