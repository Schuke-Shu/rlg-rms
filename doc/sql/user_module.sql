-- DROP DATABASE IF EXISTS rlg_rms;
-- CREATE DATABASE IF NOT EXISTS rlg_rms CHARSET utf8mb4;
USE rlg_rms;

-- DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user(
    uuid                varchar(32)     NOT NULL,
    username            varchar(32)     NOT NULL COMMENT '用户名',
    password            varchar(128)    NOT NULL COMMENT '密码',
    avatar_uuid         varchar(255)    DEFAULT '' COMMENT '头像文件uuid',
    real_name           varchar(255)    DEFAULT '' COMMENT '用户真实姓名',
    gender              tinyint(3)      UNSIGNED DEFAULT 1 COMMENT '性别，1:男，0:女',
    phone               varchar(32)     DEFAULT '' COMMENT '手机号码',
    email               varchar(32)     DEFAULT '' COMMENT '电子邮箱',
    birth               date            DEFAULT NULL COMMENT '生日',
    description         text            DEFAULT '' COMMENT '个人简介',
    is_enable           tinyint(3)      UNSIGNED DEFAULT 1 COMMENT '是否启用，1:启用，0:禁用',
    last_login_ip       varchar(32)     DEFAULT '' COMMENT '最后登录IP地址（冗余）',
    last_login_time     datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间（冗余）',
    gmt_registered      datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    gmt_created         datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified        datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (uuid),
    UNIQUE KEY (username),
    UNIQUE KEY (phone),
    UNIQUE KEY (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO user(uuid, username, password) VALUES ('1', '测试', 'P@ssw0rd');
INSERT INTO user(uuid, username, password) VALUES ('2', 'root', 'P@ssw0rdRoot');

-- DROP TABLE IF EXISTS user_login_log;
CREATE TABLE IF NOT EXISTS user_login_log(
    id                  bigint(20)      UNSIGNED NOT NULL AUTO_INCREMENT,
    user_uuid           varchar(32)     NOT NULL,
    username            varchar(32)     NOT NULL COMMENT '用户名（冗余）',
    ip                  varchar(32)     DEFAULT NULL COMMENT '登录IP地址',
    engine              varchar(255)    DEFAULT NULL COMMENT '浏览器内核',
    gmt_login           datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    gmt_created         datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified        datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录日志表';