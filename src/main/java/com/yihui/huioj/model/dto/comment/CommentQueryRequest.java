package com.yihui.huioj.model.dto.comment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yihui.huioj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentQueryRequest extends PageRequest implements Serializable {
    /**
     * 帖子id
     */
    private Long pid;

    /**
     * 用户id
     */
    private Long fromUid;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
