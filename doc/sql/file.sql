# DROP DATABASE IF EXISTS rlg_rms;
# CREATE DATABASE IF NOT EXISTS rlg_rms CHARSET utf8mb4;
USE rlg_rms;

DROP TABLE IF EXISTS directory;
CREATE TABLE IF NOT EXISTS directory(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    file_uuid           char(32)        DEFAULT NULL COMMENT '对应文件uuid',
    user_id             bigint          UNSIGNED NOT NULL COMMENT '文件所属用户id',
    filename            varchar(255)    DEFAULT NULL COMMENT '文件名称',
    deep                int             UNSIGNED DEFAULT 0 COMMENT '文件层级',
    parent_id           bigint          UNSIGNED DEFAULT 0 COMMENT '父目录id',
    directory        tinyint            UNSIGNED DEFAULT 0 COMMENT '是否为目录，1:是，0:否',
    deleted          tinyint            UNSIGNED DEFAULT 0 COMMENT '是否被删除（进入回收站），1:是，0:否',
    delete_time         datetime        DEFAULT NULL COMMENT '进入回收站时间',
    hidden           tinyint            UNSIGNED DEFAULT 0 COMMENT '是否隐藏，1:是，0:否',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户文件目录表，存储用户的文件目录结构';

DROP TABLE IF EXISTS file;
CREATE TABLE IF NOT EXISTS file(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    uuid                char            NOT NULL,
    user_id             bigint          UNSIGNED COMMENT '文件上传者',
    suffix              varchar(8)      DEFAULT NULL COMMENT '后缀名',
    type                varchar(64)     DEFAULT NULL COMMENT '文件类型',
    size                int             UNSIGNED DEFAULT 0 COMMENT '文件大小',
    size_unit           varchar(8)      DEFAULT NULL COMMENT '文件大小单位',
    association_count   int             UNSIGNED DEFAULT 0 COMMENT '关联到此文件的记录数',
    upload_time         datetime        DEFAULT NULL COMMENT '文件上传时间',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件表';