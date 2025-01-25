package com.yihui.huioj.strategy;

import com.yihui.huioj.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadStrategy {
    /**
     * 执行文件上传
     * @param multipartFile 上传的文件
     * @param loginUser 当前用户
     * @return 文件访问地址
     */
    String upload(MultipartFile multipartFile, User loginUser);
}
