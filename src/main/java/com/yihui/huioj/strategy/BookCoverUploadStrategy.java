package com.yihui.huioj.strategy;

import cn.hutool.core.io.FileUtil;
import com.yihui.huioj.common.ErrorCode;
import com.yihui.huioj.constant.FileConstant;
import com.yihui.huioj.exception.BusinessException;
import com.yihui.huioj.manager.CosManager;
import com.yihui.huioj.model.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;

@Component
public class BookCoverUploadStrategy implements FileUploadStrategy {

    @Resource
    private CosManager cosManager;

    @Override
    public String upload(MultipartFile multipartFile, User loginUser) {
        // 书籍封面允许 5MB，支持 JPG/PNG/PDF 格式
        long fileSize = multipartFile.getSize();
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long FIVE_M = 5 * 1024 * 1024L;

        if (fileSize > FIVE_M) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "封面大小不能超过5MB");
        }
        if (!Arrays.asList("jpeg", "jpg", "png", "pdf").contains(fileSuffix)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "封面格式不支持");
        }

        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String filename = uuid + "-" + multipartFile.getOriginalFilename();
        String filepath = String.format("/book_cover/%s/%s", loginUser.getId(), filename);

        File file = null;
        try {
            file = File.createTempFile("book", null);
            multipartFile.transferTo(file);
            cosManager.putObject(filepath, file);

            return FileConstant.COS_HOST + filepath;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "封面上传失败");
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }
}
