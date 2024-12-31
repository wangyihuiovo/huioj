package com.yihui.huioj.model.dto.bookfavour;

import lombok.Data;

import java.io.Serializable;

/**
 * 帖子收藏 / 取消收藏请求
 */
@Data
public class BookFavourAddRequest implements Serializable {

    /**
     * 帖子 id
     */
    private Long postId;

    private static final long serialVersionUID = 1L;
}