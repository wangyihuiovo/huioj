package com.yihui.huioj.model.dto.post;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 更新请求
 */
@Data
public class PostUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;


    /**
     * 是否置顶
     */
    private Integer isTop;

    private static final long serialVersionUID = 1L;
}