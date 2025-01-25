package com.yihui.huioj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yihui.huioj.model.entity.Book;
import com.yihui.huioj.model.vo.BookVO;

import javax.servlet.http.HttpServletRequest;


/**
* @author Wangyihui
* @description 针对表【book(书籍)】的数据库操作Service
* @createDate 2024-12-09 22:58:01
*/
public interface BookService extends IService<Book> {
    /**
     * 校验
     *
     * @param book
     * @param add
     */
    void validBook(Book book, boolean add);

    /**
     * 获取书籍封装
     *
     * @param book
     * @param request
     * @return
     */
    BookVO getBookVO(Book book, HttpServletRequest request);

    /**
     * 分页获取书籍封装
     *
     * @param bookPage
     * @param request
     * @return
     */
    Page<BookVO> getBookVOPage(Page<Book> bookPage, HttpServletRequest request);
}
