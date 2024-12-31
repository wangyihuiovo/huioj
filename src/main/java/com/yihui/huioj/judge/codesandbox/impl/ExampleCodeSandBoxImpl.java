package com.yihui.huioj.judge.codesandbox.impl;

import com.yihui.huioj.judge.codesandbox.CodeSandBox;
import com.yihui.huioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yihui.huioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yihui.huioj.judge.codesandbox.model.JudgeInfo;
import com.yihui.huioj.model.enums.JudgeInfoMessageEnum;
import com.yihui.huioj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱，仅为了跑通业务流程
 */
public class ExampleCodeSandBoxImpl implements CodeSandBox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功！");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
