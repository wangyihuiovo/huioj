package com.yihui.huioj.strategy;

import com.qcloud.cos.model.PutObjectResult;
import com.yihui.huioj.common.ErrorCode;
import com.yihui.huioj.constant.FileConstant;
import com.yihui.huioj.exception.BusinessException;
import com.yihui.huioj.manager.CosManager;
import com.yihui.huioj.model.entity.User;
import com.yihui.huioj.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import cn.hutool.core.io.FileUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;

@Component
public class UserAvatarUploadStrategy implements FileUploadStrategy {

    @Resource
    private CosManager cosManager;
    @Resource
    private UserService userService;

    @Override
    public String upload(MultipartFile multipartFile, User loginUser) {
        // 校验文件大小与格式
        long fileSize = multipartFile.getSize();
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 2 * 1024 * 1024L;

        if (fileSize > ONE_M) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "头像大小不能超过2MB");
        }
        if (!Arrays.asList("jpeg", "jpg", "png", "webp").contains(fileSuffix)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "头像格式不支持");
        }

        // 生成文件路径
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String filename = uuid + "-" + multipartFile.getOriginalFilename();
        String filepath = String.format("/user_avatar/%s/%s", loginUser.getId(), filename);

        File file = null;
        try {
            file = File.createTempFile("user_avatar", null);
            multipartFile.transferTo(file);
            PutObjectResult putObjectResult = cosManager.putObject(filepath, file);
            // 上传成功后，将头像 URL 更新到用户信息中
            String fileUrl = FileConstant.COS_HOST + filepath;
            loginUser.setUserAvatar(fileUrl);
            boolean result = userService.updateById(loginUser);
            if (!result) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户上传头像失败");
            }
            System.out.println("用户上传头像成功！");
            // 返回文件 URL
            return FileConstant.COS_HOST + filepath;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "头像上传失败");
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }
}
