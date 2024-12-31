package com.yihui.huioj.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONUtil;
import com.yihui.huioj.model.entity.Post;
import com.yihui.huioj.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 用户视图（脱敏）
 */
@Data
public class UserVO implements Serializable {

    /**
     * id
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

    private static final long serialVersionUID = 1L;


    /**
     * 包装类转对象
     *
     * @param userVO
     * @return
     */
    public static User voToObj(UserVO userVO) {
        if (userVO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        List<String> tagList = userVO.getTags();
        user.setTags(JSONUtil.toJsonStr(tagList));
        return user;
    }

    /**
     * 对象转包装类
     *
     * @param user
     * @return
     */
    public static UserVO objToVo(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setTags(JSONUtil.toList(user.getTags(), String.class));
        return userVO;
    }
}