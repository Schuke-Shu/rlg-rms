# DROP DATABASE IF EXISTS rlg_rms;
# CREATE DATABASE IF NOT EXISTS rlg_rms CHARSET utf8mb4;
USE rlg_rms;

# DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user(
    id                  bigint          UNSIGNED NOT NULL  AUTO_INCREMENT,
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
    (1, 'ef01e9a591a04f7d8c3a89644b727892', '测试', '$2a$10$YRKVkleUHpRt9QcBz6iO9udH9MEy7Z9zzbmQyScE7ibVYTnvv9GIC', '测试', '测试账号', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    # root账号初始密码 P@ssw0rdRoot
    (2, '0095410cf6704195a7136827e52a2ac9', 'rlg_root', '$2a$10$ncZjrWoZe/N0vwdDydo/ue7sKvwea2JxBLXmhQWZMEnhOZUv1wtj2', 'BOSS', '顶级管理员', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

# DROP TABLE IF EXISTS user_role;
CREATE TABLE IF NOT EXISTS user_role(
    user_id             bigint          UNSIGNED NOT NULL ,
    role_flag           varchar(32)     NOT NULL COMMENT '用户角色唯一标识符',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-用户角色关联表';

INSERT INTO user_role(user_id, role_flag) VALUES
    (1, 'user-normal'),
    (2, 'admin-boss');

# DROP TABLE IF EXISTS role;
CREATE TABLE IF NOT EXISTS role(
    flag                varchar(32)     NOT NULL COMMENT '用户角色唯一标识符',
    description         text            DEFAULT NULL COMMENT '描述',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户角色表';

INSERT INTO role(flag, create_time, modified_time) VALUES
    ('admin-boss'   , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('admin-normal' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user-normal'  , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

# DROP TABLE IF EXISTS role_permission;
CREATE TABLE IF NOT EXISTS role_permission(
    role_flag           varchar(32)     NOT NULL COMMENT '用户角色唯一标识符',
    permission_flag     varchar(32)     NOT NULL COMMENT '用户权限唯一标识符',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户角色-用户权限关联表';

# DROP TABLE IF EXISTS permission;
CREATE TABLE IF NOT EXISTS permission(
    flag                varchar(32)     NOT NULL COMMENT '用户权限唯一标识符',
    description         text            DEFAULT NULL COMMENT '描述',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户权限表';