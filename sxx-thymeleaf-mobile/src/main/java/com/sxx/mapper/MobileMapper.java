package com.sxx.mapper;

import com.github.pagehelper.Page;
import com.sxx.framework.domain.dynamic.Dynamic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hyz
 * @create 2019/1/10 0010
 * @since 1.0.0
 */
@Mapper
public interface MobileMapper {

    /**
     * 查询思学行动态列表
     * @param id id
     * @return 列表
     */
    @Select("select t.typeName,t.typeId,d.id,d.title,d.content,d.createTime,d.image,d.url from t_type t , t_dynamic d where t.typeId = d.typeId and d.typeId = #{typeId} ORDER BY createTime DESC")
    Page<Dynamic> findDynamicList(Long id);

    /**
     * 根据id查看编辑思学行动态信息
     * @param id 信息id
     * @return 思学行动态信息
     */
    @Select("select * from t_dynamic where id = #{id}")
    Dynamic query(@Param("id") Long id);
}
