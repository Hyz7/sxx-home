package com.sxx.home.service;

import com.sxx.framework.domain.banner.Banner;
import com.sxx.framework.domain.banner.dto.BannerDTO;
import com.sxx.framework.domain.banner.response.BannerResult;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.home.mapper.BannerManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈banner业务层〉
 *
 * @author hyz
 * @create 2019/4/2 0002
 * @since 1.0.0
 */
@Service
public class BannerManageService {

    @Autowired
    private BannerManageMapper bannerManageMapper;
    /**
     * 查询banner图列表
     *
     * @return banner图列表
     */
    public BannerResult queryBannerList() {
        List<BannerDTO> bannerList = bannerManageMapper.queryBannerList();

        return new BannerResult(CommonCode.SUCCESS,bannerList);
    }
}
