package com.yihui.huioj.judge;

import com.yihui.huioj.judge.codesandbox.model.JudgeInfo;
import com.yihui.huioj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yihui.huioj.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {


    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);

    /**
     * 运行代码
     * @param questionSubmitAddRequest
     * @return
     */
    JudgeInfo runCode(QuestionSubmitAddRequest questionSubmitAddRequest);
}
