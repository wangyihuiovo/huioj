package com.yihui.huioj.model.enums;

public enum TargetTypeEnum {
    /**
     * 文章
     */
    ARTICLE(1, "article"),
    /**
     * 帖子
     */
    POSTS(2, "posts"),
    /**
     * 评论
     */
    COMMENT(3, "comment"),
    ;

    private final int CODE;
    private final String VALUE;

    TargetTypeEnum(int code, String value) {
        CODE = code;
        VALUE = value;
    }

    public int code() {
        return CODE;
    }

    public String val() {
        return VALUE;
    }

    /**
     * 通过字符串获取数值
     *
     * @param value
     * @return code
     */
    public static int getCode(String value) {
        for (TargetTypeEnum p : TargetTypeEnum.values()) {
            if (p.val().equals(value)) {
                return p.code();
            }
        }
        return -1;
    }

    /**
     * 通过字符串获取枚举
     *
     * @param value
     * @return
     */
    public static TargetTypeEnum getTargetType(String value) {
        for (TargetTypeEnum p : TargetTypeEnum.values()) {
            if (p.val().equals(value)) {
                return p;
            }
        }
        return null;
    }

    /**
     * 通过数字获取枚举
     *
     * @param value
     * @return
     */
    public static TargetTypeEnum getTargetType(int value) {
        for (TargetTypeEnum p : TargetTypeEnum.values()) {
            if (p.code() == value) {
                return p;
            }
        }
        return null;
    }
}