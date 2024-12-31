package com.yihui.huioj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 回复表
 * @TableName reply
 */
@TableName(value ="reply")
@Data
public class Reply implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 回复用户id
     */
    private Long fromUid;

    /**
     * 回复用户名
     */
    private String fromName;

    /**
     * 回复用户头像
     */
    private String fromAvatar;

    /**
     * 被回复用户id
     */
    private Long toUid;

    /**
     * 被回复用户名
     */
    private String toName;

    /**
     * 被回复用户头像
     */
    private String toAvatar;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 状态(0-正常，1-封禁)
     */
    private Integer status;

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