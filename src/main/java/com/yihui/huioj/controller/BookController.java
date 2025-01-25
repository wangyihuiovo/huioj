package com.yihui.huioj.controller;

import cn.hutool.json.JSONUtil;
import com.yihui.huioj.annotation.AuthCheck;
import com.yihui.huioj.common.BaseResponse;
import com.yihui.huioj.common.DeleteRequest;
import com.yihui.huioj.common.ErrorCode;
import com.yihui.huioj.common.ResultUtils;
import com.yihui.huioj.constant.UserConstant;
import com.yihui.huioj.exception.BusinessException;
import com.yihui.huioj.exception.ThrowUtils;
import com.yihui.huioj.model.dto.book.BookAddRequest;
import com.yihui.huioj.model.dto.book.BookUpdateRequest;
import com.yihui.huioj.model.entity.Book;
import com.yihui.huioj.model.entity.User;
import com.yihui.huioj.service.BookService;
import com.yihui.huioj.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    private BookService bookService;
    @Resource
    private UserService userService;

    /**
     * 添加书籍（仅管理员）
     * TODO 后面可以添加一个创作者。创作者也可以添加书籍。
     * @param bookAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addBook(@RequestBody BookAddRequest bookAddRequest, HttpServletRequest request) {
        if(bookAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "添加书籍参数为空");
        }
        User loginUser = userService.getLoginUser(request);
        Book book = new Book();
        BeanUtils.copyProperties(bookAddRequest, book);
        List<String> tags = bookAddRequest.getTags();
        if (tags != null) {
            book.setTags(JSONUtil.toJsonStr(tags));
        }
        bookService.validBook(book,true);
        book.setUserId(loginUser.getId());
        boolean result = bookService.save(book);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR,"添加书籍失败");
        long BookId = book.getId();
        return ResultUtils.success(BookId);
    }

    /**
     * 删除书籍
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deletePost(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "书籍删除的请求参数错误");
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Book oldBook = bookService.getById(id);
        ThrowUtils.throwIf(oldBook == null, ErrorCode.NOT_FOUND_ERROR, "书籍不存在");
        // 仅本人或管理员可删除
        if (!oldBook.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = bookService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param bookUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateBook(@RequestBody BookUpdateRequest bookUpdateRequest) {
        if (bookUpdateRequest == null || bookUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "书籍更新请求参数错误");
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookUpdateRequest, book);
        List<String> tags = bookUpdateRequest.getTags();
        if (tags != null) {
            book.setTags(JSONUtil.toJsonStr(tags));
        }
        // 参数校验
        bookService.validBook(book, false);
        long id = bookUpdateRequest.getId();
        // 判断是否存在
        Book oldBook = bookService.getById(id);
        ThrowUtils.throwIf(oldBook == null, ErrorCode.NOT_FOUND_ERROR, "书籍不存在");
        boolean result = bookService.updateById(book);
        return ResultUtils.success(result);
    }
}
