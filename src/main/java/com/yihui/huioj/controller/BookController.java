package com.yihui.huioj.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.yihui.huioj.model.dto.post.PostQueryRequest;
import com.yihui.huioj.model.entity.Book;
import com.yihui.huioj.model.entity.Post;
import com.yihui.huioj.model.entity.User;
import com.yihui.huioj.model.vo.BookVO;
import com.yihui.huioj.model.vo.PostVO;
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
    public BaseResponse<Long> addBook(@RequestBody BookAddRequest bookAddRequest, HttpServletRequest request) {
        if(bookAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "添加书籍参数为空");
        }
        User loginUser = userService.getLoginUser(request);
        if(userService.isAdmin(loginUser)){//TODO 后面可以添加一个创作者。
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookAddRequest, book);
        List<String> tags = bookAddRequest.getTags();
        if (tags != null) {
            book.setTags(JSONUtil.toJsonStr(tags));
        }
        bookService.validPost(book,true);
        book.setUserId(loginUser.getId());
        boolean result = bookService.save(book);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long BookId = book.getId();
        return ResultUtils.success(BookId);
    }

    /**
     * 删除书籍（管理员）
     * TODO 后面可以添加一个创作者。创作者也可以删除书籍。
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteBook(@RequestBody DeleteRequest deleteRequest,HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"删除书籍参数为空");
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        Book oldBook = bookService.getById(id);
        ThrowUtils.throwIf(oldBook == null, ErrorCode.NOT_FOUND_ERROR,"该书籍不存在");
        // 仅本人或管理员可删除
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = bookService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（管理员）
     * TODO 后面可以添加一个创作者。创作者也可以更新书籍。
     * @param bookUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateBook(@RequestBody BookUpdateRequest bookUpdateRequest) {
        if (bookUpdateRequest == null || bookUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookUpdateRequest, book);
        List<String> tags = bookUpdateRequest.getTags();
        if (tags != null) {
            book.setTags(JSONUtil.toJsonStr(tags));
        }
        // 参数校验
        bookService.validPost(book, false);
        long id = bookUpdateRequest.getId();
        // 判断是否存在
        Book oldBook = bookService.getById(id);
        ThrowUtils.throwIf(oldBook == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = bookService.updateById(book);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<BookVO> getBookVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Book book = bookService.getById(id);
        if (book == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(postService.getPostVO(book, request));
    }



    /**
     * 分页获取列表（仅管理员）
     *
     * @param postQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Post>> listPostByPage(@RequestBody PostQueryRequest postQueryRequest) {
        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();
        Page<Post> postPage = postService.page(new Page<>(current, size),
                postService.getQueryWrapper(postQueryRequest));
        return ResultUtils.success(postPage);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param postQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<PostVO>> listPostVOByPage(@RequestBody PostQueryRequest postQueryRequest,
                                                       HttpServletRequest request) {
        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Post> postPage = postService.page(new Page<>(current, size),
                postService.getQueryWrapper(postQueryRequest));
        return ResultUtils.success(postService.getPostVOPage(postPage, request));
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param postQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<PostVO>> listMyPostVOByPage(@RequestBody PostQueryRequest postQueryRequest,
                                                         HttpServletRequest request) {
        if (postQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        postQueryRequest.setUserId(loginUser.getId());
        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Post> postPage = postService.page(new Page<>(current, size),
                postService.getQueryWrapper(postQueryRequest));
        return ResultUtils.success(postService.getPostVOPage(postPage, request));
    }


}
