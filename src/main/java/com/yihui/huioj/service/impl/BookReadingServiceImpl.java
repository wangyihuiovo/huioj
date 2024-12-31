package com.yihui.huioj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yihui.huioj.mapper.BookReadingMapper;
import com.yihui.huioj.model.entity.BookReading;
import com.yihui.huioj.service.BookReadingService;
import org.springframework.stereotype.Service;

/**
* @author Wangyihui
* @description 针对表【book_reading(书籍阅读进度)】的数据库操作Service实现
* @createDate 2024-12-10 22:55:20
*/
@Service
public class BookReadingServiceImpl extends ServiceImpl<BookReadingMapper, BookReading>
    implements BookReadingService {

}




