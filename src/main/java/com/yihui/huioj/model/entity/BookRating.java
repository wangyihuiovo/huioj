package com.yihui.huioj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName book_rating
 */
@TableName(value ="book_rating")
@Data
public class BookRating implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 书籍id
     */
    private Long bookId;

    /**
     * 评分
     */
    private Integer rating;

    /**
     * 创建时间
     */
    private Date created_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}