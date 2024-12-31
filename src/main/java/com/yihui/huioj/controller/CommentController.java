package com.yihui.huioj.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yihui.huioj.common.*;
import com.yihui.huioj.exception.BusinessException;
import com.yihui.huioj.exception.ThrowUtils;
import com.yihui.huioj.model.dto.comment.CommentAddRequest;
import com.yihui.huioj.model.vo.CommentVO;
import com.yihui.huioj.model.dto.comment.CommentQueryRequest;
import com.yihui.huioj.model.entity.Comment;
import com.yihui.huioj.model.entity.User;
import com.yihui.huioj.service.CommentService;
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

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 新增评论
     *
     * @param commentAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addComment(@RequestBody CommentAddRequest commentAddRequest, HttpServletRequest request) {
        if (commentAddRequest == null || commentAddRequest.getPid() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentAddRequest, comment);
        commentService.validComment(comment, true);
        User loginUser = userService.getLoginUser(request);
        comment.setPid(commentAddRequest.getPid());
        comment.setContent(commentAddRequest.getContent());
        comment.setFromUid(loginUser.getId());
        comment.setFromName(loginUser.getUserName());
        comment.setFromAvatar(loginUser.getUserAvatar());
        comment.setThumbNum(0);
        boolean result = commentService.save(comment);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newCommentId = comment.getId();

        return ResultUtils.success(newCommentId);
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
        Comment oldComment = commentService.getById(id);
        ThrowUtils.throwIf(oldComment == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldComment.getFromUid().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = commentService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 分页获取列表
     *
     * @param commentQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Comment>> listCommentByPage(@RequestBody CommentQueryRequest commentQueryRequest ,HttpServletRequest request) {
        long current = commentQueryRequest.getCurrent();
        long size = commentQueryRequest.getPageSize();
//        String key = COMMENT + commentQueryRequest.getPid();
//        String commentJSON = redisTemplate.opsForValue().get(key);
//        //redis有缓存
//        if(StrUtil.isNotBlank(commentJSON)){
//            PageDTO<Comment> commentPageDTO = JSONUtil.toBean(commentJSON, PageDTO.class);
//            Page<Comment> commentPage = new Page<>(commentPageDTO.getPageNum(), commentPageDTO.getPageSize());
//            commentPage.setTotal(commentPageDTO.getTotal());
//            commentPage.setRecords(commentPageDTO.getRecords());
//            return ResultUtils.success(commentPage);
//        }
        //redis无缓存
        Page<Comment> commentPage = commentService.page(new Page<>(current, size),
                commentService.getQueryWrapper(commentQueryRequest));

        PageDTO<Comment> commentPageDTO = new PageDTO<>();
        commentPageDTO.setPageNum(commentPage.getPages());
        commentPageDTO.setPageSize(commentPage.getSize());
        commentPageDTO.setTotal(commentPage.getTotal());
        commentPageDTO.setRecords(commentPage.getRecords());
//
//        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(commentPageDTO), 30, TimeUnit.MINUTES);
        return ResultUtils.success(commentPage);
    }
}
