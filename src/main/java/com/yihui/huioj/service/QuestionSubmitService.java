package com.yihui.huioj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yihui.huioj.judge.codesandbox.model.JudgeInfo;
import com.yihui.huioj.model.dto.question.QuestionQueryRequest;
import com.yihui.huioj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yihui.huioj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yihui.huioj.model.entity.Question;
import com.yihui.huioj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yihui.huioj.model.entity.User;
import com.yihui.huioj.model.vo.QuestionSubmitVO;
import com.yihui.huioj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Wangyihui
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-09-10 20:30:23
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
