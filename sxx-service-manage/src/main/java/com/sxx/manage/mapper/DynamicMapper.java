package com.sxx.manage.mapper;

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
     * 展示交易信息:公司和服务商列表
     *
     * @param typeId 分类id
     * @return 新闻资讯列表结果
     */
    @Select("select * from t_dynamic td where td.typeId = #{typeId}")
    List<Dynamic> findNewsInfoListByTypeId(@Param("typeId") Long typeId);

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
}
