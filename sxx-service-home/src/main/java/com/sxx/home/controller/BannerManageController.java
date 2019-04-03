package com.sxx.home.controller;

import com.sxx.api.banner.BannerManageControllerApi;
import com.sxx.framework.domain.banner.response.BannerResult;
import com.sxx.home.service.BannerManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈banner控制层〉
 *
 * @author hyz
 * @create 2019/4/2 0002
 * @since 1.0.0
 */
@RestController
@RequestMapping("/home")
public class BannerManageController implements BannerManageControllerApi {

    @Autowired
    private BannerManageService bannerManageService;
    /**
     * 查询banner图列表
     *
     * @return banner图列表
     */
    @GetMapping("/banner")
    @Override
    public BannerResult queryBannerList() {
        return bannerManageService.queryBannerList();
    }
}
