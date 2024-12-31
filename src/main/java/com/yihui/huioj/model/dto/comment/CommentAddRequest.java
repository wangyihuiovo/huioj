package com.yihui.huioj.model.dto.comment;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class CommentAddRequest implements Serializable {
    /**
     * 帖子id
     */
    private Long pid;

    /**
     * 评论内容
     */
    private String content;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
