package com.yihui.huioj.model.vo;

import cn.hutool.json.JSONUtil;
import com.yihui.huioj.model.entity.Book;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 帖子视图
 */
@Data
public class BookVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
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
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 观看数
     */
    private Integer VisitNum;


    /**
     * 评分
     */
    private int rating;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 标签列表
     */
    private List<String> tagList;

    /**
     * 创建人信息
     */
    private UserVO user;

    /**
     * 是否已收藏
     */
    private Boolean hasFavour;

    /**
     * 是否已收藏
     */
    private Boolean hasRating;
    /**
     * 包装类转对象
     *
     * @param bookVO
     * @return
     */
    public static Book voToObj(BookVO bookVO) {
        if (bookVO == null) {
            return null;
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookVO, book);
        List<String> tagList = bookVO.getTagList();
        book.setTags(JSONUtil.toJsonStr(tagList));
        return book;
    }

    /**
     * 对象转包装类
     *
     * @param book
     * @return
     */
    public static BookVO objToVo(Book book) {
        if (book == null) {
            return null;
        }
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(book, bookVO);
        bookVO.setTagList(JSONUtil.toList(book.getTags(), String.class));
        return bookVO;
    }
}
