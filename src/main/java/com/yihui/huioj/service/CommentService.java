package com.yihui.huioj.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yihui.huioj.model.dto.comment.CommentQueryRequest;
import com.yihui.huioj.model.dto.post.PostQueryRequest;
import com.yihui.huioj.model.entity.Comment;
import com.yihui.huioj.model.entity.Post;
import com.yihui.huioj.model.entity.Question;
import com.yihui.huioj.model.vo.CommentVO;
import com.yihui.huioj.model.vo.PostVO;
import com.yihui.huioj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Wangyihui
* @description 针对表【comment(评论表)】的数据库操作Service
* @createDate 2024-11-11 14:51:24
*/
public interface CommentService extends IService<Comment> {
    /**
     * 校验
     *
     * @param comment
     * @param add
     */
    void validComment(Comment comment, boolean add);

    /**
     * 获取查询条件
     *
     * @param commentQueryRequest
     * @return
     */
    QueryWrapper<Comment> getQueryWrapper(CommentQueryRequest commentQueryRequest);

    /**
     * 分页获取封装
     *
     * @param commentPage
     * @param request
     * @return
     */
    Page<CommentVO> getCommentVOPage(Page<Comment> commentPage, HttpServletRequest request);
}
