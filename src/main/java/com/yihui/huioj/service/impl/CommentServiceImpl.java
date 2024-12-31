package com.yihui.huioj.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihui.huioj.common.ErrorCode;
import com.yihui.huioj.constant.CommonConstant;
import com.yihui.huioj.exception.BusinessException;
import com.yihui.huioj.exception.ThrowUtils;
import com.yihui.huioj.mapper.CommentMapper;
import com.yihui.huioj.model.dto.comment.CommentQueryRequest;
import com.yihui.huioj.model.entity.*;
import com.yihui.huioj.model.vo.CommentVO;
import com.yihui.huioj.model.vo.PostVO;
import com.yihui.huioj.model.vo.QuestionVO;
import com.yihui.huioj.service.CommentService;
import com.yihui.huioj.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Wangyihui
 * @description 针对表【comment(评论表)】的数据库操作Service实现
 * @createDate 2024-11-11 14:51:24
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {


    @Override
    public void validComment(Comment comment, boolean add) {
        if (comment == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String content = comment.getContent();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(content), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(content) && content.length() > 500) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "评论内容过长");
        }
    }

    @Override
    public QueryWrapper<Comment> getQueryWrapper(CommentQueryRequest commentQueryRequest) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        if (commentQueryRequest == null) {
            return queryWrapper;
        }
        Long pid = commentQueryRequest.getPid();
        Long fromUid = commentQueryRequest.getFromUid();
        String sortField = commentQueryRequest.getSortField();
        String sortOrder = commentQueryRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(ObjectUtils.isNotEmpty(pid), "pid", pid);
        queryWrapper.eq(ObjectUtils.isNotEmpty(fromUid), "fromUid", fromUid);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public Page<CommentVO> getCommentVOPage(Page<Comment> commentPage, HttpServletRequest request) {
        List<Comment> commentList = commentPage.getRecords();
        Page<CommentVO> commentVOPage = new Page<>(commentPage.getCurrent(), commentPage.getSize(), commentPage.getTotal());
        if (CollUtil.isEmpty(commentList)) {
            return commentVOPage;
        }
        return null;
    }
}




