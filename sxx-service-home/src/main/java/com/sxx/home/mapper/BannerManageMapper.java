/**
 * 〈一句话功能简述〉<br>
 * 〈banner持久层〉
 *
 * @author hyz
 * @create 2019/4/2 0002
 * @since 1.0.0
 */
package com.sxx.home.mapper;

import com.sxx.framework.domain.banner.dto.BannerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BannerManageMapper {
    @Select("select banner_id,banner_image from banner where status = '1' order by order_by,update_time")
    List<BannerDTO> queryBannerList();
}
