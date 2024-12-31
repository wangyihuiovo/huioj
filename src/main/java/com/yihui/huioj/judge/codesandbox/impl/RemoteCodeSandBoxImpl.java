package com.yihui.huioj.judge.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yihui.huioj.common.ErrorCode;
import com.yihui.huioj.exception.BusinessException;
import com.yihui.huioj.judge.codesandbox.CodeSandBox;
import com.yihui.huioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yihui.huioj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱，提供实际调用的沙箱
 */
public class RemoteCodeSandBoxImpl implements CodeSandBox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8090/executeCode";
        String requestStr = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .body(requestStr)
                .header("usernameAdmin","password123456")
                .execute()
                .body();
        if(StrUtil.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"远程代码沙箱调用失败," + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
