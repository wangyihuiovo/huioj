package com.yihui.huioj.model.dto.user;

import com.yihui.huioj.common.PageRequest;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

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
    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}