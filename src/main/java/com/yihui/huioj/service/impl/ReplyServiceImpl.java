package com.yihui.huioj.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihui.huioj.common.ErrorCode;
import com.yihui.huioj.constant.CommonConstant;
import com.yihui.huioj.exception.BusinessException;
import com.yihui.huioj.exception.ThrowUtils;
import com.yihui.huioj.mapper.ReplyMapper;
import com.yihui.huioj.model.dto.comment.CommentQueryRequest;
import com.yihui.huioj.model.dto.reply.ReplyQueryRequest;
import com.yihui.huioj.model.entity.Comment;
import com.yihui.huioj.model.entity.Reply;
import com.yihui.huioj.service.ReplyService;
import com.yihui.huioj.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Wangyihui
 * @description 针对表【reply(回复表)】的数据库操作Service实现
 * @createDate 2024-11-11 14:51:24
 */
@Service
@Slf4j
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply>
        implements ReplyService {

    @Override
    public void validReply(Reply reply, boolean add) {
        if (reply == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String content = reply.getContent();
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
    public QueryWrapper<Reply> getQueryWrapper(ReplyQueryRequest replyQueryRequest) {
        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
        if (replyQueryRequest == null) {
            return queryWrapper;
        }
        Long commentId = replyQueryRequest.getCommentId();
        Long fromUid = replyQueryRequest.getFromUid();
        String sortField = replyQueryRequest.getSortField();
        String sortOrder = replyQueryRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(ObjectUtils.isNotEmpty(commentId), "commentId", commentId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(fromUid), "fromUid", fromUid);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}




