package com.yihui.huioj.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yihui.huioj.model.dto.comment.CommentQueryRequest;
import com.yihui.huioj.model.dto.reply.ReplyQueryRequest;
import com.yihui.huioj.model.entity.Comment;
import com.yihui.huioj.model.entity.Reply;

/**
 * @author Wangyihui
 * @description 针对表【reply(回复表)】的数据库操作Service
 * @createDate 2024-11-11 14:51:24
 */
public interface ReplyService extends IService<Reply> {
    /**
     * 校验
     *
     * @param reply
     * @param add
     */
    void validReply(Reply reply, boolean add);

    /**
     * 获取查询条件
     *
     * @param replyQueryRequest
     * @return
     */
    QueryWrapper<Reply> getQueryWrapper(ReplyQueryRequest replyQueryRequest);
}
