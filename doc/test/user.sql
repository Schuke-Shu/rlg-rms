# DROP DATABASE IF EXISTS rlg_rms;
# CREATE DATABASE IF NOT EXISTS rlg_rms CHARSET utf8mb4;
USE rlg_rms;

# DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    uuid                char(32)        DEFAULT NULL,
    username            varchar(32)     DEFAULT NULL COMMENT '用户名',
    password            varchar(128)    DEFAULT NULL COMMENT '密码',
    avatar_url          varchar(255)    DEFAULT NULL COMMENT '头像url',
    real_name           varchar(255)    DEFAULT NULL COMMENT '用户真实姓名',
    gender              tinyint         UNSIGNED DEFAULT 0 COMMENT '性别，1:男，0:女',
    phone               varchar(32)     DEFAULT NULL COMMENT '手机号码',
    email               varchar(32)     DEFAULT NULL COMMENT '电子邮箱',
    birth               date            DEFAULT NULL COMMENT '生日',
    description         text            DEFAULT NULL COMMENT '个人简介',
    is_enable           tinyint         UNSIGNED DEFAULT 0 COMMENT '是否启用，1:启用，0:禁用',
    sign_up_time        datetime        DEFAULT NULL COMMENT '注册时间',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY (uuid),
    UNIQUE KEY (username),
    UNIQUE KEY (phone),
    UNIQUE KEY (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO user(id, uuid, username, password, real_name, description, sign_up_time, create_time, modified_time) VALUES
    # 测试账号初始密码 P@ssw0rdTest
    (1, 'ef01e9a591a04f7d8c3a89644b727892', '测试', '$2a$10$YRKVkleUHpRt9QcBz6iO9udH9MEy7Z9zzbmQyScE7ibVYTnvv9GIC', '测试', '测试账号', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

# DROP TABLE IF EXISTS user_with_role;
CREATE TABLE IF NOT EXISTS user_with_role(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id             bigint          UNSIGNED NOT NULL COMMENT '用户id',
    role_id             bigint          UNSIGNED NOT NULL COMMENT '用户角色id',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-用户角色关联表';

# DROP TABLE IF EXISTS user_role;
CREATE TABLE IF NOT EXISTS user_role(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    flag                varchar(32)     NOT NULL COMMENT '用户角色唯一标识符',
    description         text            DEFAULT NULL COMMENT '描述',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY (flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户角色表';

# DROP TABLE IF EXISTS user_role_with_permission;
CREATE TABLE IF NOT EXISTS user_role_with_permission(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    role_id             bigint          UNSIGNED NOT NULL COMMENT '用户角色id',
    permission_id       bigint          UNSIGNED NOT NULL COMMENT '用户权限id',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户角色-用户权限关联表';

# DROP TABLE IF EXISTS user_permission;
CREATE TABLE IF NOT EXISTS user_permission(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    flag                varchar(32)     NOT NULL COMMENT '用户权限唯一标识符',
    description         text            DEFAULT NULL COMMENT '描述',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY (flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户权限表';

INSERT INTO user(uuid, username, password, real_name, description, sign_up_time, create_time, modified_time) VALUES
    ('3bbb694735f245bea173fa58820cb2f7', '测试1', '123456', '测试1', '这是一个测试账户1', '2020-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('068a627a021e4823a4142beacf102313', '测试2', '123456', '测试2', '这是一个测试账户2', '2018-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('5d57503a009d405db80324324e228a28', '测试3', '123456', '测试3', '这是一个测试账户3', '2020-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('c8fc64685a9c44718c51da2103015167', '测试4', '123456', '测试4', '这是一个测试账户4', '2020-09-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('2eb36cddee8440e2bf6e5da6e355a504', '测试5', '123456', '测试5', '这是一个测试账户5', '2020-02-21', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('db0cfd30815f403098181d1bfaa7ba25', '测试6', '123456', '测试6', '这是一个测试账户6', '2010-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('feeb0c8300e945278ac87b75085ead80', '测试7', '123456', '测试7', '这是一个测试账户7', '2020-06-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('076e2ed7c9884de0ae760f2b528f2579', '测试8', '123456', '测试8', '这是一个测试账户8', '2003-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ed87c7f70bb442148c60a7df559f39c1', '测试9', '123456', '测试9', '这是一个测试账户9', '2015-07-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('e81a4b227cda4063b0d4a71038896fd2', '测试10', '123456', '测试10', '这是一个测试账户10', '2023-03-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user_with_role(user_id, role_id, create_time, modified_time) VALUES
    (1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user_role(flag, description, create_time, modified_time) VALUES
    ('user1', '这是测试数据1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user2', '这是测试数据2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user3', '这是测试数据3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user4', '这是测试数据4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user5', '这是测试数据5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user6', '这是测试数据6', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user7', '这是测试数据7', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user8', '这是测试数据8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user9', '这是测试数据9', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user10', '这是测试数据10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);