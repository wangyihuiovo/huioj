package com.yihui.huioj.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yihui.huioj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentVO extends PageRequest implements Serializable {
    private Long id;

    /**
     * 帖子id
     */
    private Long pid;

    /**
     * 用户id
     */
    private Long fromUid;

    /**
     * 评论用户名
     */
    private String fromName;

    /**
     * 评论用户头像
     */
    private String fromAvatar;

    /**
     * 评论内容
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
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
