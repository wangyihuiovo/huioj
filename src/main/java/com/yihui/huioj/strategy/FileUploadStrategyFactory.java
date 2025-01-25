package com.yihui.huioj.strategy;

import com.yihui.huioj.common.ErrorCode;
import com.yihui.huioj.exception.BusinessException;
import com.yihui.huioj.model.enums.FileUploadBizEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileUploadStrategyFactory {

    @Resource
    private UserAvatarUploadStrategy userAvatarUploadStrategy;

    @Resource
    private BookCoverUploadStrategy bookCoverUploadStrategy;

    private final Map<FileUploadBizEnum, FileUploadStrategy> strategyMap = new HashMap<>();

    @PostConstruct
    public void init() {
        strategyMap.put(FileUploadBizEnum.USER_AVATAR, userAvatarUploadStrategy);
        strategyMap.put(FileUploadBizEnum.BOOK_COVER, bookCoverUploadStrategy);
    }

    public FileUploadStrategy getStrategy(FileUploadBizEnum bizEnum) {
        FileUploadStrategy strategy = strategyMap.get(bizEnum);
        if (strategy == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的文件上传类型");
        }
        return strategy;
    }
}
