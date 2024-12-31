package com.yihui.huioj.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 书籍
 * @TableName book
 */
@TableName(value ="book")
@Data
public class Book implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 书名
     */
    private String title;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 简介
     */
    private String description;

    /**
     * 封面URL
     */
    private String coverUrl;

    /**
     * 标签列表 json
     */
    private String tags;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 观看数
     */
    private Integer VisitNum;

    /**
     * 评分
     */
    private int rating;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}