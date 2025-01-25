package com.yihui.huioj.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yihui.huioj.common.ErrorCode;
import com.yihui.huioj.exception.BusinessException;
import com.yihui.huioj.exception.ThrowUtils;
import com.yihui.huioj.mapper.BookFavourMapper;
import com.yihui.huioj.mapper.BookMapper;
import com.yihui.huioj.model.entity.*;
import com.yihui.huioj.model.vo.BookVO;
import com.yihui.huioj.model.vo.PostVO;
import com.yihui.huioj.model.vo.UserVO;
import com.yihui.huioj.service.BookService;
import com.yihui.huioj.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Wangyihui
 * @description 针对表【book(书籍)】的数据库操作Service实现
 * @createDate 2024-12-09 22:58:01
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
        implements BookService {

    @Resource
    private UserService userService;
    @Resource
    private BookFavourMapper bookFavourMapper;

    @Override
    public void validBook(Book book, boolean add) {
        if (book == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = book.getTitle();
        String description = book.getDescription();
        String tags = book.getTags();


        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, description, tags), ErrorCode.PARAMS_ERROR, "书籍校验参数错误");
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "书名过长");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 2500) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "描述过长");
        }
        if (StringUtils.isNotBlank(tags) && tags.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标签过长");
        }
    }

    @Override
    public BookVO getBookVO(Book book, HttpServletRequest request) {
        BookVO bookVO = BookVO.objToVo(book);
        long bookId = book.getId();
        // 1. 关联查询用户信息
        Long userId = book.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        bookVO.setUser(userVO);
        // 2. 已登录，获取用户点赞、收藏状态
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            // 获取收藏
            QueryWrapper<BookFavour> bookFavourQueryWrapper = new QueryWrapper<>();
            bookFavourQueryWrapper.in("bookId", bookId);
            bookFavourQueryWrapper.eq("userId", loginUser.getId());
            BookFavour bookFavour = bookFavourMapper.selectOne(bookFavourQueryWrapper);
            bookVO.setHasFavour(bookFavour != null);
        }
        return bookVO;
    }

    @Override
    public Page<BookVO> getBookVOPage(Page<Book> bookPage, HttpServletRequest request) {
        List<Book> bookList = bookPage.getRecords();
        Page<BookVO> bookVOPage = new Page<>(bookPage.getCurrent(), bookPage.getSize(), bookPage.getTotal());
        if (CollUtil.isEmpty(bookList)) {
            return bookVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = bookList.stream().map(Book::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 2. 已登录，获取用户点赞、收藏状态
        Map<Long, Boolean> bookIdHasFavourMap = new HashMap<>();
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            Set<Long> bookIdSet = bookList.stream().map(Book::getId).collect(Collectors.toSet());
            loginUser = userService.getLoginUser(request);
            // 获取收藏
            QueryWrapper<BookFavour> bookFavourQueryWrapper = new QueryWrapper<>();
            bookFavourQueryWrapper.in("bookId", bookIdSet);
            bookFavourQueryWrapper.eq("userId", loginUser.getId());
            List<BookFavour> bookFavourList = bookFavourMapper.selectList(bookFavourQueryWrapper);
            bookFavourList.forEach(bookFavour -> bookIdHasFavourMap.put(bookFavour.getBookId(), true));
        }
        // 填充信息
        List<BookVO> bookVOList = bookList.stream().map(book -> {
            BookVO bookVO = BookVO.objToVo(book);
            Long userId = book.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            bookVO.setUser(userService.getUserVO(user));
            bookVO.setHasFavour(bookIdHasFavourMap.getOrDefault(book.getId(), false));
            return bookVO;
        }).collect(Collectors.toList());
        bookVOPage.setRecords(bookVOList);
        return bookVOPage;
    }
}




