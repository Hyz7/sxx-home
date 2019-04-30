package com.sxx.framework.domain.compus_talk.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈CompusTalkListDTO〉
 *
 * @author hyz
 * @create 2019/4/30 0030
 * @since 1.0.0
 */
@Data
public class CompusTalkListDTO implements Serializable {
    private String id;
    private String title;
    private String content;
    private String image;
}
