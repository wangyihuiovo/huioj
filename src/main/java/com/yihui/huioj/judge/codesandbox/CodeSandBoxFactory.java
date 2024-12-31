package com.yihui.huioj.judge.codesandbox;

import com.yihui.huioj.judge.codesandbox.impl.ExampleCodeSandBoxImpl;
import com.yihui.huioj.judge.codesandbox.impl.RemoteCodeSandBoxImpl;
import com.yihui.huioj.judge.codesandbox.impl.ThirdPartyCodeSandBoxImpl;

/**
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 */
public class CodeSandBoxFactory {

    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandBoxImpl();
            case "remote":
                return new RemoteCodeSandBoxImpl();
            case "thirdParty":
                return new ThirdPartyCodeSandBoxImpl();
            default:
                return new ExampleCodeSandBoxImpl();
        }
    }

}
