package com.yihui.huioj.judge.codesandbox;

import com.yihui.huioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yihui.huioj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandBox {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
