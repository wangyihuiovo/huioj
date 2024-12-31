package com.yihui.huioj.model.dto.reply;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yihui.huioj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReplyQueryRequest extends PageRequest implements Serializable {
    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 用户id
     */
    private Long fromUid;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
