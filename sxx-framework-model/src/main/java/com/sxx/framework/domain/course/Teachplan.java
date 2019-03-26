package com.sxx.framework.domain.course;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by admin on 2018/2/7.
 */
@Data
@ToString
@Table(name="teachplan")
public class Teachplan implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;
    @Id
    private String id;
    private String pName;
    private String parentId;
    private String grade;
    private String pType;
    private String description;
    private String courseId;
    private String status;
    private Integer orderBy;
    private Double timeLength;
    private String tryLearn;
    private String mediaUrl;
}
