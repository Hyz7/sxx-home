package com.sxx.home.service;

import com.alibaba.fastjson.JSON;
import com.sxx.framework.domain.teacher.Teacher;
import com.sxx.framework.domain.teacher.dto.TeacherDTO;
import com.sxx.framework.domain.teacher.response.TeacherDTOResult;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.home.mapper.TeachingMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈授课导师业务层〉
 *
 * @author hyz
 * @create 2019/1/15 0015
 * @since 1.0.0
 */
@Service
public class TeachingService {

    @Autowired
    private TeachingMapper teachingMapper;

    /**
     * 展示授课导师数据
     *
     * @return 结果
     */
    public TeacherDTOResult showTeacherList() {
        List<Teacher> teacherList = teachingMapper.showTeacherList();
        List<TeacherDTO> teacherDTOList = new ArrayList<>(teacherList.size());
        for (Teacher teacher : teacherList) {
            TeacherDTO teacherDTO = new TeacherDTO();
            Map map = JSON.parseObject(teacher.getTeaAchievement(), Map.class);
            BeanUtils.copyProperties(teacher,teacherDTO);
            teacherDTO.setTeaAchievement(map);
            teacherDTOList.add(teacherDTO);
        }
        // 取出擅长领域json
        return new TeacherDTOResult(CommonCode.SUCCESS,teacherDTOList);
    }
}
