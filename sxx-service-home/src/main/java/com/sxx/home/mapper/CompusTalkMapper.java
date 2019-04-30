package com.sxx.home.mapper;

import com.sxx.framework.domain.compus_talk.dto.CompusTalkListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈校园宣讲管理持久层〉
 *
 * @author hyz
 * @create 2019/4/30 0030
 * @since 1.0.0
 */
@Mapper
public interface CompusTalkMapper {
    /**
     * 查询校园宣讲列表
     *
     * @return 结果
     */
    @Select("select id,title,content,image from campus_talk")
    List<CompusTalkListDTO> findCompusTalkList();
}
