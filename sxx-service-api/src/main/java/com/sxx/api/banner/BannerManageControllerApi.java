package com.sxx.api.banner;

import com.sxx.framework.domain.banner.response.BannerResult;
import io.swagger.annotations.Api;

/**
 * 〈一句话功能简述〉<br>
 * 〈banner门户管理api〉
 *
 * @author hyz
 * @create 2019/4/2 0002
 * @since 1.0.0
 */
@Api(value = "管理门户网站banner图接口",description = "提供对banner的操作")
public interface BannerManageControllerApi {
    /**
     * 查询banner图列表
     *
     * @return banner图列表
     */
    BannerResult queryBannerList();
}
