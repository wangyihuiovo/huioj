package com.yihui.huioj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 书籍阅读进度
 * @TableName book_reading
 */
@TableName(value ="book_reading")
@Data
public class BookReading implements Serializable {
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
     * 创建用户 id
     */
    private Long userId;

    /**
     * 上次阅读章节 id
     */
    private Integer lastReadChapterId;

    /**
     * 上次阅读小节 id
     */
    private Integer lastReadSectionId;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}