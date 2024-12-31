package com.yihui.huioj.model.dto.user;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 用户更新个人信息请求
 */
@Data
public class UserUpdateMyRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 学校
     */
    private String school;

    /**
     * 性别
     */
    private String gender;



    private static final long serialVersionUID = 1L;
}