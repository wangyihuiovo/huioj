package com.yihui.huioj.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONUtil;
import com.yihui.huioj.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 已登录用户视图（脱敏）
 **/
@Data
public class LoginUserVO implements Serializable {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;
    /**
     * 标签列表（json 数组） 为了更方便的传给前端
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     * @param loginUserVO
     * @return
     */
    public static User voToObj(LoginUserVO loginUserVO) {
        if (loginUserVO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(loginUserVO, user);
        List<String> tagList = loginUserVO.getTags();
        user.setTags(JSONUtil.toJsonStr(tagList));
        return user;
    }

    /**
     * 对象转包装类
     *
     * @param user
     * @return
     */
    public static LoginUserVO objToVo(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        loginUserVO.setTags(JSONUtil.toList(user.getTags(), String.class));
        return loginUserVO;
    }
    
}