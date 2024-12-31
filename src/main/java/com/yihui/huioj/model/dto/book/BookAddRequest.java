package com.yihui.huioj.model.dto.book;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建请求
 */
@Data
public class BookAddRequest implements Serializable {

    /**
     * 书名
     */
    private String title;

    /**
     * 作者
     */
    private String author;

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