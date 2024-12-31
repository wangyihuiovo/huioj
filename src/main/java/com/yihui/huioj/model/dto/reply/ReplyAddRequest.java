package com.yihui.huioj.model.dto.reply;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReplyAddRequest implements Serializable {
    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 评论内容
     */
    private String content;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
