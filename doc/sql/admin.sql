# DROP DATABASE IF EXISTS rlg_rms;
# CREATE DATABASE IF NOT EXISTS rlg_rms CHARSET utf8mb4;
USE rlg_rms;

# DROP TABLE IF EXISTS admin;
CREATE TABLE IF NOT EXISTS admin(
    id                  bigint          UNSIGNED NOT NULL  AUTO_INCREMENT,
    creator_id          bigint          UNSIGNED DEFAULT NULL COMMENT '创建者id',
    admin_name          varchar(32)     DEFAULT NULL COMMENT '管理员名',
    password            varchar(128)    DEFAULT NULL COMMENT '密码',
    avatar_url          varchar(255)    DEFAULT NULL COMMENT '头像url',
    real_name           varchar(255)    DEFAULT NULL COMMENT '管理员真实姓名',
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
    UNIQUE KEY (admin_name),
    UNIQUE KEY (phone),
    UNIQUE KEY (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

INSERT INTO admin(id, admin_name, password, real_name, description, sign_up_time, create_time, modified_time) VALUES
    # 测试账号初始密码 P@ssw0rdTest
    (1, '测试', '$2a$10$YRKVkleUHpRt9QcBz6iO9udH9MEy7Z9zzbmQyScE7ibVYTnvv9GIC', '测试', '测试账号', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    # root账号初始密码 P@ssw0rdRoot
    (2, 'rlg_root', '$2a$10$ncZjrWoZe/N0vwdDydo/ue7sKvwea2JxBLXmhQWZMEnhOZUv1wtj2', 'BOSS', '顶级管理员', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

# DROP TABLE IF EXISTS admin_with_role;
CREATE TABLE IF NOT EXISTS admin_with_role(
    id                  bigint          UNSIGNED NOT NULL  AUTO_INCREMENT,
    admin_id            bigint          UNSIGNED NOT NULL COMMENT '管理员id',
    role_id             bigint          UNSIGNED NOT NULL COMMENT '管理员角色id',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员-管理员角色关联表';

INSERT INTO admin_with_role(admin_id, role_id) VALUES
    (2, 1);

# DROP TABLE IF EXISTS admin_role;
CREATE TABLE IF NOT EXISTS admin_role(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    flag                varchar(32)     NOT NULL COMMENT '管理员角色唯一标识符',
    description         text            DEFAULT NULL COMMENT '描述',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY (flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '管理员角色表';

INSERT INTO admin_role(id, flag, description, create_time, modified_time) VALUES
    (1, 'boss', '顶级管理员', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

# DROP TABLE IF EXISTS admin_role_with_permission;
CREATE TABLE IF NOT EXISTS admin_role_with_permission(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    role_id             bigint          UNSIGNED NOT NULL COMMENT '管理员角色id',
    permission_id       bigint          UNSIGNED NOT NULL COMMENT '管理员权限id',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '管理员角色-管理员权限关联表';

# DROP TABLE IF EXISTS admin_permission;
CREATE TABLE IF NOT EXISTS admin_permission(
    id                  bigint          UNSIGNED NOT NULL AUTO_INCREMENT,
    flag                varchar(32)     NOT NULL COMMENT '管理员权限唯一标识符',
    description         text            DEFAULT NULL COMMENT '描述',
    create_time         datetime        DEFAULT NULL COMMENT '创建时间',
    modified_time       datetime        DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY (flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '管理员权限表';