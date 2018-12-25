package com.sxx.manage.mapper;

import com.github.pagehelper.Page;
import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.framework.domain.dynamic.DynamicType;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈思学行动态展示持久层〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@Mapper
public interface DynamicMapper {
    /**
     * 展示思学行动态列表
     *
     * @return 思学行动态列表响应结果
     */
    @Select("select * from t_type")
    List<DynamicType> findDynamicType();
    /**
     * 展示思学行动态信息
     *
     * @param typeId 分类id
     * @return 展示思学行动态信息
     */
    @Select("select t.typeName,t.typeId,d.id,d.title,d.content,d.createTime,d.image from t_type t , t_dynamic d where t.typeId = d.typeId and d.typeId = #{typeId} ORDER BY createTime DESC")
    Page<Dynamic> findDynamicList(@Param("typeId") Long typeId);

    /**
     * 添加记录
     * @param dynamic 实体
     */
    @Insert("insert into t_dynamic values(null,#{typeId},#{title},#{content},#{createTime},#{image})")
    void save(Dynamic dynamic);

    /**
     * 删除记录
     * @param id 记录id
     */
    @Delete("delete from t_dynamic where id = #{id}")
    void delete(@Param("id") Long id);

    /**
     * 根据id查看编辑思学行动态信息
     * @param id 信息id
     * @return 思学行动态信息
     */
    @Select("select * from t_dynamic where id = #{id}")
    Dynamic query(@Param("id") Long id);

    /**
     * 更新记录
     * @param dynamic 思学行动态记录
     */
    void update(Dynamic dynamic);

    /**
     * 根据分类id查询动态信息
     * @param typeId 分类id
     * @return 结果
     */
    @Select("select d.*,t.typeName from t_dynamic d, t_type t where d.typeId = t.typeId and t.typeId = #{typeId} ORDER BY createTime DESC")
    Page<Dynamic> findDynamicListByTypeId(@Param("typeId") String typeId);

    /**
     * 分页模糊查询新闻列表
     * @param typeId 分类id
     * @return 结果
     */
    Page<Dynamic> findNewsList(@Param("typeId") Long typeId,@Param("name") String name);
}
