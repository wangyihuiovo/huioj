package com.yihui.huioj.model.dto.book;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新请求
 */
@Data
public class BookUpdateRequest implements Serializable {

    private Long id;

    /**
     * 书名
     */
    private String title;

    /**
     * 简介
     */
    private String description;

    /**
     * 封面URL
     */
    private String coverUrl;

    /**
     * 标签列表
     */
    private List<String> tags;


    private static final long serialVersionUID = 1L;
}