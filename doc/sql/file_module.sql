-- DROP DATABASE IF EXISTS rlg_rms;
-- CREATE DATABASE IF NOT EXISTS rlg_rms CHARSET utf8mb4;
USE rlg_rms;

-- DROP TABLE IF EXISTS directory;
CREATE TABLE IF NOT EXISTS directory(
    id              bigint(20)      UNSIGNED NOT NULL AUTO_INCREMENT,
    file_uuid       varchar(32)     NOT NULL COMMENT '对应文件uuid',
    user_uuid       varchar(32)     NOT NULL COMMENT '文件所属用户uuid',
    filename        varchar(255)    NOT NULL COMMENT '文件名称',
    deep            int(10)         UNSIGNED DEFAULT 1 COMMENT '文件层级',
    parent_id       bigint(20)      UNSIGNED DEFAULT 0 COMMENT '父目录id',
    is_directory    tinyint(3)      UNSIGNED DEFAULT 0 COMMENT '是否为目录，1:是，0:否',
    is_deleted      tinyint(3)      UNSIGNED DEFAULT 0 COMMENT '是否在回收站中，1:是，0:否',
    delete_time     datetime        DEFAULT NULL COMMENT '进入回收站时间',
    is_hidden       tinyint(3)      UNSIGNED DEFAULT 0 COMMENT '是否隐藏，1:是，0:否',
    gmt_created     datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified    datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户文件目录表，存储用户的文件目录结构';

-- DROP TABLE IF EXISTS file;
CREATE TABLE IF NOT EXISTS file(
    uuid            varchar(32)     NOT NULL,
    user_uuid       varchar(32)     NOT NULL COMMENT '文件上传者',
    suffix          varchar(8)      DEFAULT NULL COMMENT '后缀名',
    type            varchar(64)     DEFAULT NULL COMMENT '文件类型',
    size            bigint(20)      UNSIGNED NOT NULL COMMENT '文件大小',
    upload_time     datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '文件上传时间',
    gmt_created     datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified    datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件表';