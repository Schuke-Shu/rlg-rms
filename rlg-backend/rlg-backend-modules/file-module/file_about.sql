-- DROP DATABASE IF EXISTS rlg_rms;
-- CREATE DATABASE IF NOT EXISTS rlg_rms CHARSET utf8mb4;
USE rlg_rms;

-- DROP TABLE IF EXISTS file;
CREATE TABLE IF NOT EXISTS file(
    uuid            varchar(32)     NOT NULL,
    user_uuid       varchar(32)     NOT NULL,
    filename        varchar(255)    NOT NULL COMMENT '文件名称',
    deep            int(10)         UNSIGNED DEFAULT 1 COMMENT '文件层级',
    parent_uuid     varchar(32)     DEFAULT NULL COMMENT '父目录uuid',
    is_directory    tinyint(3)      UNSIGNED DEFAULT 0 COMMENT '是否为目录，1:是，0:否',
    suffix          varchar(8)      DEFAULT NULL COMMENT '后缀名',
    size            bigint(20)      UNSIGNED DEFAULT NULL COMMENT '文件大小',
    upload_time     datetime        DEFAULT NULL COMMENT '文件上传时间',
    update_count    int(10)         UNSIGNED DEFAULT 0 COMMENT '修改次数',
    is_deleted      tinyint(3)      UNSIGNED DEFAULT 0 COMMENT '是否在回收站中，1:是，0:否',
    delete_time     datetime        DEFAULT NULL COMMENT '进入回收站时间',
    is_hidden       tinyint(3)      UNSIGNED DEFAULT 0 COMMENT '是否隐藏，1:是，0:否',
    gmt_create      datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified    datetime        DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (uuid),
    UNIQUE KEY (user_uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件表，包含文件与目录';