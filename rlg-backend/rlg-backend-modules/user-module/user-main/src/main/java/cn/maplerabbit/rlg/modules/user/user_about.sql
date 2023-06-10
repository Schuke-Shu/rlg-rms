-- DROP DATABASE IF EXISTS rlg_rms;
-- CREATE DATABASE IF NOT EXISTS rlg_rms CHARSET utf8mb4;
USE rlg_rms;

-- DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user(
    uuid            varchar(32)     NOT NULL COMMENT 'uuid',
    nickname        varchar(32)    NOT NULL COMMENT '昵称',
    password        varchar(128)    NOT NULL COMMENT '密码',
    gmt_create      datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified    datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (uuid),
    UNIQUE KEY (nickname)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- DROP TABLE IF EXISTS user_info;
CREATE TABLE IF NOT EXISTS user_info(
    user_uuid       varchar(32)     NOT NULL COMMENT '用户uuid',
    avatar          varchar(255)    DEFAULT NULL COMMENT '头像URL',
    real_name        varchar(255)    DEFAULT NULL COMMENT '用户真实姓名',
    gender          tinyint(3)      UNSIGNED DEFAULT 1 COMMENT '性别，1:男，0:女',
    phone           varchar(32)     DEFAULT NULL COMMENT '手机号码',
    email           varchar(32)     DEFAULT NULL COMMENT '电子邮箱',
    birth           date            DEFAULT NULL COMMENT '生日',
    description     text            DEFAULT NULL COMMENT '个人简介',
    is_enable       tinyint(3)      UNSIGNED DEFAULT 1 COMMENT '是否启用，1:启用，0:禁用',
    last_login_ip   varchar(32)     DEFAULT NULL COMMENT '最后登录IP地址',
    last_login_time datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
    register        datetime        DEFAULT CURRENT_DATE COMMENT '注册时间',
    gmt_create      datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified    datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (user_uuid),
    UNIQUE KEY (phone),
    UNIQUE KEY (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';