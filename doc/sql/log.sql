# DROP DATABASE IF EXISTS rlg_rms;
# CREATE DATABASE IF NOT EXISTS rlg_rms CHARSET utf8mb4;
USE rlg_rms;

# DROP TABLE IF EXISTS user_login_log;
CREATE TABLE IF NOT EXISTS user_login_log(
    id                  bigint          UNSIGNED NOT NULL  AUTO_INCREMENT,
    user_id             bigint          UNSIGNED DEFAULT NULL,
    username            varchar(32)     DEFAULT NULL COMMENT '用户名（冗余）',
    ip                  varchar(255)    DEFAULT NULL COMMENT '登录IP地址',
    engine              varchar(255)    DEFAULT NULL COMMENT '浏览器内核',
    time                datetime        DEFAULT NULL COMMENT '登录时间',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录日志表';

# DROP TABLE IF EXISTS admin_login_log;
CREATE TABLE IF NOT EXISTS admin_login_log(
    id                  bigint          UNSIGNED NOT NULL  AUTO_INCREMENT,
    admin_id            bigint          UNSIGNED DEFAULT NULL,
    admin_name            varchar(32)     DEFAULT NULL COMMENT '管理员名（冗余）',
    ip                  varchar(255)    DEFAULT NULL COMMENT '登录IP地址',
    engine              varchar(255)    DEFAULT NULL COMMENT '浏览器内核',
    time                datetime        DEFAULT NULL COMMENT '登录时间',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员登录日志表';