package com.sxx.framework.domain.compus_talk;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈校园宣讲实体类〉
 *
 * @author hyz
 * @create 2019/4/30 0030
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "campus_talk")
public class CompusTalk implements Serializable {
    @Id
    private String id;
    private String title;
    private String content;
    private String image;
    private String videoUrl;
    private Boolean isDelete;
    private String classify;
}
