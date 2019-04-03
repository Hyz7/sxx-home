package com.sxx.framework.domain.banner.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈bannerDTO〉
 *
 * @author hyz
 * @create 2019/4/3 0003
 * @since 1.0.0
 */
@Data
public class BannerDTO implements Serializable {
    private String bannerId;
    private String bannerImage;
}
