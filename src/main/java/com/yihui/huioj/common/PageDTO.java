package com.yihui.huioj.common;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class PageDTO<T> implements Serializable {
    private long pageNum;
    private long pageSize;
    private long total;
    private List<T> records;
    private static final long serialVersionUID = 1L;
}
