package com.sxx.home.mapper;

import com.github.pagehelper.Page;
import com.sxx.framework.domain.dynamic.Dynamic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 〈一句话功能简述〉<br>
 * 〈思学行动态展示持久层〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@Mapper
public interface ShowDynamicMapper {
    /**
     * 展示思学行动态信息
     *
     * @param typeId 分类id
     * @return 展示思学行动态信息
     */
    Page<Dynamic> findDynamicList(@Param("typeId") Long typeId);

    /**
     * 根据分类id分页模糊查询动态信息
     *
     * @param name   模糊查询标题名称
     * @param typeId 分类id
     * @return 结果
     */
    Page<Dynamic> findDynamicListByTypeId(@Param("name") String name, @Param("typeId") Long typeId);

    /**
     * 分页模糊查询新闻列表
     *
     * @param typeId 分类id
     * @param name   模糊查询名称
     * @return 结果
     */
    Page<Dynamic> findNewsList1(@Param("typeId") Long typeId, @Param("name") String name);

    /**
     * 根据id查看编辑思学行动态信息
     * @param id 信息id
     * @return 思学行动态信息
     */
    @Select("select * from t_dynamic where id = #{id}")
    Dynamic query(@Param("id") Long id);
}
