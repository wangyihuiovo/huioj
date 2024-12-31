package com.yihui.huioj.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yihui.huioj.common.*;
import com.yihui.huioj.exception.BusinessException;
import com.yihui.huioj.exception.ThrowUtils;
import com.yihui.huioj.model.dto.reply.ReplyAddRequest;
import com.yihui.huioj.model.dto.reply.ReplyQueryRequest;
import com.yihui.huioj.model.entity.Comment;
import com.yihui.huioj.model.entity.Reply;
import com.yihui.huioj.model.entity.User;
import com.yihui.huioj.service.CommentService;
import com.yihui.huioj.service.ReplyService;
import com.yihui.huioj.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

import static com.yihui.huioj.constant.RedisConstant.COMMENT;
import static com.yihui.huioj.constant.RedisConstant.REPLY;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Resource
    private ReplyService replyService;
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 新增回复
     *
     * @param replyAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addReply(@RequestBody ReplyAddRequest replyAddRequest, HttpServletRequest request) {
        if (replyAddRequest == null || replyAddRequest.getCommentId() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Reply reply = new Reply();
        BeanUtils.copyProperties(replyAddRequest, reply);
        replyService.validReply(reply, true);
        User loginUser = userService.getLoginUser(request);
        reply.setFromUid(loginUser.getId());
        reply.setFromName(loginUser.getUserName());
        reply.setFromAvatar(loginUser.getUserAvatar());
        Comment userComment = commentService.getById(replyAddRequest.getCommentId());
        reply.setToUid(userComment.getFromUid());
        reply.setToName(userComment.getFromName());
        reply.setToAvatar(userComment.getFromAvatar());
        boolean result = replyService.save(reply);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newReplyId = reply.getId();
        return ResultUtils.success(newReplyId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deletePost(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Reply oldReply = replyService.getById(id);
        ThrowUtils.throwIf(oldReply == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldReply.getFromUid().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = replyService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 分页获取列表
     *
     * @param replyQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Reply>> listReplyByPage(@RequestBody ReplyQueryRequest replyQueryRequest) {
        long current = replyQueryRequest.getCurrent();
        long size = replyQueryRequest.getPageSize();
        String key = REPLY + replyQueryRequest.getCommentId();
        String replyJSON = redisTemplate.opsForValue().get(key);
        //redis有缓存
        if(StrUtil.isNotBlank(replyJSON)){
            PageDTO<Reply> replyPageDTO = JSONUtil.toBean(replyJSON, PageDTO.class);
            Page<Reply> replyPage = new Page<>(replyPageDTO.getPageNum(), replyPageDTO.getPageSize());
            replyPage.setRecords(replyPageDTO.getRecords());
            return ResultUtils.success(replyPage);
        }
        //redis无缓存
        Page<Reply> replyPage = replyService.page(new Page<>(current, size),
                replyService.getQueryWrapper(replyQueryRequest));
        PageDTO<Reply> replyPageDTO = new PageDTO<>();
        replyPageDTO.setPageNum(replyPage.getPages());
        replyPageDTO.setPageSize(replyPage.getSize());
        replyPageDTO.setTotal(replyPage.getTotal());
        replyPageDTO.setRecords(replyPage.getRecords());
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(replyPageDTO), 30, TimeUnit.MINUTES);
        return ResultUtils.success(replyPage);
    }
}
