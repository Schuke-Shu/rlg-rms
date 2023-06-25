# DROP DATABASE IF EXISTS rlg_rms;
# CREATE DATABASE IF NOT EXISTS rlg_rms CHARSET utf8mb4;
# USE rlg_rms;

# 用户表
DROP TABLE IF EXISTS user;
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
    enable              tinyint         UNSIGNED DEFAULT 0 COMMENT '是否启用，1:启用，0:禁用',
    sign_up_time        datetime        DEFAULT NULL COMMENT '注册时间',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY (uuid),
    UNIQUE KEY (username),
    UNIQUE KEY (phone),
    UNIQUE KEY (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO user(id, uuid, username, password, real_name, gender, description, enable, sign_up_time, create_time, modified_time) VALUES
    # 测试账号初始密码 P@ssw0rdTest
    (1, 'ef01e9a591a04f7d8c3a89644b727892', '测试', '$2a$10$YRKVkleUHpRt9QcBz6iO9udH9MEy7Z9zzbmQyScE7ibVYTnvv9GIC', '测试', 1, '测试账号', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    # root账号初始密码 P@ssw0rdRoot
    (2, '151d3d54031140019237af35d874b4fd', 'rlgroot', '$2a$10$NbOqv/eIJCQl/i2z57ZDeuqh7KIwarT6r8BLk6Wj4HKq7A52N2rXu', 'BOSS', 1, '顶级管理员', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

# 用户-用户角色关联表
DROP TABLE IF EXISTS user_role;
CREATE TABLE IF NOT EXISTS user_role(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id             bigint          UNSIGNED NOT NULL COMMENT '用户id',
    role_id             bigint          UNSIGNED NOT NULL COMMENT '用户角色id',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-用户角色关联表';

INSERT INTO user_role VALUES
    (1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

# 用户角色表
DROP TABLE IF EXISTS role;
CREATE TABLE IF NOT EXISTS role(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    flag                varchar(32)     DEFAULT NULL COMMENT '用户角色唯一标识符',
    description         text            DEFAULT NULL COMMENT '描述',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY (flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户角色表';

INSERT INTO role VALUES
    (1, 'ROLE_USER', '普通用户', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'ROLE_ADMIN_TOP', '顶级管理员', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

# 用户角色-用户权限关联表
DROP TABLE IF EXISTS role_permission;
CREATE TABLE IF NOT EXISTS role_permission(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    role_id             bigint          UNSIGNED NOT NULL COMMENT '用户角色id',
    permission_id       bigint          UNSIGNED NOT NULL COMMENT '用户权限id',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户角色-用户权限关联表';

# 用户权限表
DROP TABLE IF EXISTS permission;
CREATE TABLE IF NOT EXISTS permission(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    flag                varchar(32)     DEFAULT NULL COMMENT '用户权限唯一标识符',
    description         text            DEFAULT NULL COMMENT '描述',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY (flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户权限表';

# 用户登录日志表
DROP TABLE IF EXISTS user_login_log;
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

# 用户文件目录表
DROP TABLE IF EXISTS directory;
CREATE TABLE IF NOT EXISTS directory(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    file_uuid           char(32)        DEFAULT NULL COMMENT '对应文件uuid',
    user_id             bigint          UNSIGNED NOT NULL COMMENT '文件所属用户id',
    filename            varchar(255)    DEFAULT NULL COMMENT '文件名称',
    deep                int             UNSIGNED DEFAULT 0 COMMENT '文件层级',
    parent_id           bigint          UNSIGNED DEFAULT 0 COMMENT '父目录id',
    directory           tinyint         UNSIGNED DEFAULT 0 COMMENT '是否为目录，1:是，0:否',
    file_size           int             UNSIGNED DEFAULT 0 COMMENT '文件大小（冗余）',
    file_size_unit      varchar(3)      DEFAULT NULL COMMENT '文件大小单位（冗余）',
    deleted             tinyint         UNSIGNED DEFAULT 0 COMMENT '是否被删除（进入回收站），1:是，0:否',
    delete_time         datetime        DEFAULT NULL COMMENT '进入回收站时间',
    hidden              tinyint         UNSIGNED DEFAULT 0 COMMENT '是否隐藏，1:是，0:否',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户文件目录表，存储用户的文件目录结构';

# 文件表
DROP TABLE IF EXISTS file;
CREATE TABLE IF NOT EXISTS file(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    uuid                char            NOT NULL,
    user_id             bigint          UNSIGNED COMMENT '文件上传者',
    suffix              varchar(8)      DEFAULT NULL COMMENT '后缀名',
    type                varchar(64)     DEFAULT NULL COMMENT '文件类型',
    size                int             UNSIGNED DEFAULT 0 COMMENT '文件大小',
    size_unit           varchar(3)      DEFAULT NULL COMMENT '文件大小单位',
    upload_time         datetime        DEFAULT NULL COMMENT '文件上传时间',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY (uuid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件表';