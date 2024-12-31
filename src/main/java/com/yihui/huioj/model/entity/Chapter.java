package com.yihui.huioj.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 章节表
 * @TableName chapter
 */
@TableName(value ="chapter")
@Data
public class Chapter implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 书籍 id
     */
    private Long bookId;

    /**
     * 章节标题
     */
    private String title;

    /**
     * 章节顺序
     */
    private Integer orderNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}