package com.yihui.huioj.strategy;

import com.yihui.huioj.model.dto.question.JudgeCase;
import com.yihui.huioj.judge.codesandbox.model.JudgeInfo;
import com.yihui.huioj.model.entity.Question;
import com.yihui.huioj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase> judgeCaseList;
    private Question question;
    private QuestionSubmit questionSubmit;
    private String message;
    private String language;
}
