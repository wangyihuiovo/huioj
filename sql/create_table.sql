-- 创建库
create
    database if not exists my_db;

-- 切换库
use
    my_db;

create table comment
(
    id         bigint auto_increment
        primary key,
    pid        bigint                             not null comment '帖子id',
    fromUid    bigint                             not null comment '用户id',
    fromName   varchar(48)                        not null comment '评论用户名',
    fromAvatar varchar(256)                       null comment '评论用户头像',
    content    text                               null comment '评论内容',
    thumbNum   int      default 0                 not null comment '点赞数',
    status     int      default 0                 not null comment '状态(0-正常，1-封禁)',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '评论表';

create index index_pid
    on comment (pid);

create index index_uid
    on comment (fromUid);


-- auto-generated definition
create table comment_thumb
(
    id         bigint auto_increment comment 'id'
        primary key,
    commentId  bigint                             not null comment '评论 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '评论点赞';

create index idx_commentId
    on comment_thumb (commentId);

create index idx_userId
    on comment_thumb (userId);

-- auto-generated definition
create table post
(
    id         bigint auto_increment comment 'id'
        primary key,
    title      varchar(512)                         null comment '标题',
    content    text                                 null comment '内容',
    tags       varchar(1024)                        null comment '标签列表（json 数组）',
    thumbNum   int        default 0                 not null comment '点赞数',
    favourNum  int        default 0                 not null comment '收藏数',
    userId     bigint                               not null comment '创建用户 id',
    createTime datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint    default 0                 not null comment '是否删除',
    isTop      tinyint(1) default 0                 not null comment '是否置顶'
)
    comment '帖子' collate = utf8mb4_unicode_ci;

create index idx_userId
    on post (userId);


-- auto-generated definition
create table post_favour
(
    id         bigint auto_increment comment 'id'
        primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '帖子收藏';

create index idx_postId
    on post_favour (postId);

create index idx_userId
    on post_favour (userId);

-- auto-generated definition
create table post_thumb
(
    id         bigint auto_increment comment 'id'
        primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '帖子点赞';

create index idx_postId
    on post_thumb (postId);

create index idx_userId
    on post_thumb (userId);

-- auto-generated definition
create table question
(
    id          bigint auto_increment comment 'id'
        primary key,
    title       varchar(512)                       null comment '标题',
    content     text                               null comment '内容',
    tags        varchar(1024)                      null comment '标签列表（json 数组）',
    answer      text                               null comment '题目答案',
    submitNum   int      default 0                 not null comment '题目提交数',
    acceptedNum int      default 0                 not null comment '题目通过数',
    judgeCase   text                               null comment '判题用例（json 数组）',
    judgeConfig text                               null comment '判题配置（json 对象）',
    thumbNum    int      default 0                 not null comment '点赞数',
    favourNum   int      default 0                 not null comment '收藏数',
    userId      bigint                             not null comment '创建用户 id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除'
)
    comment '题目' collate = utf8mb4_unicode_ci;

create index idx_userId
    on question (userId);

-- auto-generated definition
create table question_submit
(
    id         bigint auto_increment comment 'id'
        primary key,
    language   varchar(128)                       not null comment '编程语言',
    code       text                               not null comment '用户代码',
    judgeInfo  text                               null comment '判题信息（json 对象）',
    status     int      default 0                 not null comment '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
    questionId bigint                             not null comment '题目 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
)
    comment '题目提交';

create index idx_questionId
    on question_submit (questionId);

create index idx_userId
    on question_submit (userId);

-- auto-generated definition
create table reply
(
    id         bigint auto_increment
        primary key,
    commentId  bigint                             not null comment '评论id',
    fromUid    bigint                             not null comment '回复用户id',
    fromName   varchar(48)                        not null comment '回复用户名',
    fromAvatar varchar(256)                       null comment '回复用户头像',
    toUid      bigint                             not null comment '被回复用户id',
    toName     varchar(48)                        not null comment '被回复用户名',
    toAvatar   varchar(256)                       null comment '被回复用户头像',
    content    text                               null comment '回复内容',
    thumbNum   int      default 0                 not null comment '点赞数',
    status     int      default 0                 not null comment '状态(0-正常，1-封禁)',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '回复表';

create index index_commentId
    on reply (commentId);

create index index_fromUid
    on reply (fromUid);

-- auto-generated definition
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    tags         varchar(1024)                          null comment '标签列表（json 数组）',
    gender       varchar(10)                            null comment '性别',
    school       varchar(100)                           null comment '学校',
    email        varchar(100)                           null comment '邮箱'
)
    comment '用户' collate = utf8mb4_unicode_ci;

-- auto-generated definition
create table post
(
    id         bigint auto_increment comment 'id'
        primary key,
    title      varchar(512)                         null comment '标题',
    content    text                                 null comment '内容',
    tags       varchar(1024)                        null comment '标签列表（json 数组）',
    thumbNum   int        default 0                 not null comment '点赞数',
    favourNum  int        default 0                 not null comment '收藏数',
    userId     bigint                               not null comment '创建用户 id',
    createTime datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint    default 0                 not null comment '是否删除',
    isTop      tinyint(1) default 0                 not null comment '是否置顶'
)
    comment '帖子' collate = utf8mb4_unicode_ci;

create index idx_userId
    on post (userId);

-- auto-generated definition
create table book
(
    id         bigint auto_increment comment 'id'
        primary key,
    title      varchar(64)                        null comment '书名',
    author     varchar(64)                        null comment '作者',
    description text                           null comment '简介',
    coverUrl     varchar(1024)                      null comment '封面URL',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
)
    comment '书籍' collate = utf8mb4_unicode_ci;

-- auto-generated definition
create table chapter
(
    id         int auto_increment comment 'id'
        primary key,
    bookId     bigint                             not null comment '书籍 id',
    title      varchar(64)                        null comment '章节标题',
    orderNum   int                                not null comment '章节顺序',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint chapter___fk
        foreign key (bookId) references book (id)
            on update cascade on delete cascade
)
    comment '章节表';

create index idx_bookId
    on chapter (bookId);



create table section
(
    id         int auto_increment comment 'id'
        primary key,
    chapterId     int                             not null comment '章节 id',
    title      varchar(64)                        null comment '小节标题',
    content    text                                 null comment '内容',
    orderNum   int                                not null comment '小节顺序',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint section___fk
        foreign key (chapterId) references chapter (id)
            on update cascade on delete cascade
)
    comment '小节表';

create index idx_bookId
    on section (chapterId);


create table book_favour
(
    id         bigint auto_increment comment 'id'
        primary key,
    bookId     bigint                             not null comment '书籍 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint book_favour_book_id_fk
        foreign key (bookId) references book (id)
            on update cascade on delete cascade
)
    comment '书籍收藏';

create index idx_postId
    on book_favour (bookId);

create index idx_userId
    on book_favour (userId);

-- auto-generated definition
create table book_rating
(
    id           bigint auto_increment
        primary key,
    userId       bigint                              not null,
    bookId       bigint                              not null,
    rating       tinyint                             not null,
    createTime   datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint userId
        unique (userId, bookId),
    check (`rating` between 1 and 5)
);

-- auto-generated definition
create table book_reading
(
    id                int auto_increment comment 'id'
        primary key,
    bookId            bigint                             not null comment '书籍 id',
    userId            bigint                             not null comment '创建用户 id',
    lastReadChapterId int                                not null comment '上次阅读章节 id',
    lastReadSectionId int                                not null comment '上次阅读小节 id',
    createTime        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint book_reading_book_id_fk
        foreign key (bookId) references book (id),
    constraint book_reading_chapter_id_fk
        foreign key (lastReadChapterId) references chapter (id)
            on update cascade on delete cascade,
    constraint book_reading_section_id_fk
        foreign key (lastReadSectionId) references section (id)
            on update cascade on delete cascade,
    constraint book_reading_user_id_fk
        foreign key (userId) references user (id)
            on update cascade on delete cascade
)
    comment '书籍阅读进度';

create table content_review
(
    id           bigint auto_increment
        primary key,
    userId       bigint                             not null,
    postId       bigint                             not null,
    adminId      bigint                             not null,
    reviewResult int                                not null,
    reviewReason text                               null,
    createTime   datetime default CURRENT_TIMESTAMP null,
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create index content_review_postId_index
    on content_review (postId);
