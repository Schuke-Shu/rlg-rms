-- DROP DATABASE IF EXISTS rlg_rms;
-- CREATE DATABASE IF NOT EXISTS rlg_rms CHARSET utf8mb4;
USE rlg_rms;

DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user(
    id                  bigint(20)      UNSIGNED AUTO_INCREMENT,
    username            varchar(32)     DEFAULT NULL COMMENT '用户名',
    password            varchar(128)    DEFAULT NULL COMMENT '密码',
    avatar_uuid         varchar(255)    DEFAULT NULL COMMENT '头像文件uuid',
    real_name           varchar(255)    DEFAULT NULL COMMENT '用户真实姓名',
    gender              tinyint(3)      UNSIGNED DEFAULT 0 COMMENT '性别，1:男，0:女',
    phone               varchar(32)     DEFAULT NULL COMMENT '手机号码',
    email               varchar(32)     DEFAULT NULL COMMENT '电子邮箱',
    birth               date            DEFAULT NULL COMMENT '生日',
    description         text            DEFAULT NULL COMMENT '个人简介',
    is_enable           tinyint(3)      UNSIGNED DEFAULT 0 COMMENT '是否启用，1:启用，0:禁用',
    last_login_ip       varchar(32)     DEFAULT NULL COMMENT '最后登录IP地址（冗余）',
    last_login_time     datetime        DEFAULT NULL COMMENT '最后登录时间（冗余）',
    gmt_registered      datetime        DEFAULT NULL COMMENT '注册时间',
    gmt_created         datetime        DEFAULT NULL COMMENT '创建时间',
    gmt_modified        datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY (username),
    UNIQUE KEY (phone),
    UNIQUE KEY (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO user(username, password) VALUES ('测试', 'P@ssw0rd');
INSERT INTO user(username, password) VALUES ('root', 'P@ssw0rdRoot');

DROP TABLE IF EXISTS user_login_log;
CREATE TABLE IF NOT EXISTS user_login_log(
    id                  bigint(20)      UNSIGNED AUTO_INCREMENT,
    user_uuid           varchar(32)     DEFAULT NULL,
    username            varchar(32)     DEFAULT NULL COMMENT '用户名（冗余）',
    ip                  varchar(32)     DEFAULT NULL COMMENT '登录IP地址',
    engine              varchar(255)    DEFAULT NULL COMMENT '浏览器内核',
    gmt_login           datetime        DEFAULT NULL COMMENT '登录时间',
    gmt_created         datetime        DEFAULT NULL COMMENT '创建时间',
    gmt_modified        datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录日志表';