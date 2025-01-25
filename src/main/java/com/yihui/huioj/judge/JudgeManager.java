package com.yihui.huioj.judge;

import com.yihui.huioj.strategy.DefaultJudgeStrategy;
import com.yihui.huioj.strategy.JavaLanguageJudgeStrategy;
import com.yihui.huioj.strategy.JudgeContext;
import com.yihui.huioj.strategy.JudgeStrategy;
import com.yihui.huioj.judge.codesandbox.model.JudgeInfo;
import com.yihui.huioj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(language)){
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        else if("python".equals(language)){
            System.out.println("python语言暂不支持在线编译，请使用本地IDE进行提交。");
        }
        return judgeStrategy.doJudge(judgeContext);
    }
    /**
     * 运行代码
     * @param judgeContext
     * @return
     */
    JudgeInfo runCode(JudgeContext judgeContext){
        String language = judgeContext.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(language)){
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        else if("python".equals(language)){
            System.out.println("python语言暂不支持在线编译，请使用本地IDE进行提交。");
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
