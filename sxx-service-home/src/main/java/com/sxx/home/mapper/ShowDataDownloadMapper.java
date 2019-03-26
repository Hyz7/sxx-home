/**
 * 〈一句话功能简述〉<br>
 * 〈资料下载持久层〉
 *
 * @author hyz
 * @create 2018/12/28 0028
 * @since 1.0.0
 */
package com.sxx.home.mapper;

import com.github.pagehelper.Page;
import com.sxx.framework.domain.data.DataEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ShowDataDownloadMapper {

    /**
     * @param dataClassName    当前分类名称
     * @param dataCategoryName 当前分类行业名称
     * @param name             查询名称
     * @param page             当前页数
     * @param size             当前页记录数
     * @return 结果
     */
    Page<DataEntity> findDataList(@Param("dataClassName") String dataClassName, @Param("dataCategoryName") String dataCategoryName, @Param("name") String name, @Param("page") Integer page, @Param("size") Integer size);

    /**
     * 根据id查询资料下载数据
     * @param dataId 数据id
     * @return 资料下载数据
     */
    @Select("select * from `data` where data_id = #{dataId}")
    DataEntity findByDataId(@Param("dataId") Integer dataId);


    /**
     * 更新资料下载数据
     *
     * @param dataEntity 新数据
     * @return 结果
     */
    void updateData(DataEntity dataEntity);
}
