package com.sxx.controller;

import com.alibaba.fastjson.JSON;
import com.sxx.framework.domain.teacher.Teacher;
import com.sxx.framework.domain.teacher.dto.TeacherDTO;
import com.sxx.framework.domain.teacher.response.TeacherDTOResult;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.mapper.TeachingMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈授课导师控制层〉
 *
 * @author hyz
 * @create 2019/2/28 0028
 * @since 1.0.0
 */
@Controller
@RequestMapping("/teaching")
public class TeachingController {
    @Autowired
    private TeachingMapper teachingMapper;

    /**
     * 展示授课导师数据
     *
     * @return 结果
     */
    @GetMapping("/showTeacherList")
    public String showTeacherList(Model model) {
        List<Teacher> teacherList = teachingMapper.showTeacherList();
        List<TeacherDTO> teacherDTOList = new ArrayList<>(teacherList.size());
        for (Teacher teacher : teacherList) {
            TeacherDTO teacherDTO = new TeacherDTO();
            Map map = JSON.parseObject(teacher.getTeaAchievement(), Map.class);
            BeanUtils.copyProperties(teacher,teacherDTO);
            teacherDTO.setTeaAchievement(map);
            teacherDTOList.add(teacherDTO);
        }
        model.addAttribute("teacherDTOList",teacherDTOList);
        // 取出擅长领域json
        return "teaching";
    }
}
