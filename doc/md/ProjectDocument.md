# 命名规范

## 数据库（未实装）

**==普通表命名规范：`表属性字母 + 模块id + 实体id`==**

**==关联表命名规范：`左表名称_右表名称`==**

> 例：
>
> 某模块id为`01`，其中有某个实体id为`001`，该实体所需的表应命名为`D01001`
>
> `D01001`与`D01002`的关联表命名为`D01001_D01002`

**==id使用两位十六进制==**



==表属性字母==

| 属性名                   | 字母 | 对应英文单词 |
| ------------------------ | ---- | ------------ |
| 数据表（实体类对应的表） | D    | data         |
| 测试表                   | T    | test         |



**元数据表：**

- `meta_module`（模块信息）

  ```mysql
  # 模块信息表
  # DROP TABLE IF EXISTS meta_module;
  CREATE TABLE IF NOT EXISTS meta_module(
      id                  char(2)         NOT NULL COMMENT '模块id，由两位十六进制数组成',
      name                varchar(32)     DEFAULT NULL COMMENT '模块名称',
      note	            varchar(255)    DEFAULT NULL COMMENT '注释',
      PRIMARY KEY (id)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模块信息表，记录所有模块';
  ```

- `meta_entity`（实体信息）

  ```mysql
  # 实体信息表
  # DROP TABLE IF EXISTS meta_entity;
  CREATE TABLE IF NOT EXISTS meta_entity(
      id                  char(3)         NOT NULL COMMENT '实体id，由三位十六进制数组成',
      name                varchar(32)     DEFAULT NULL COMMENT '实体名称',
      module_id			varchar(2)		NOT NULL COMMENT '所属模块id',
      note             	varchar(255)    DEFAULT NULL COMMENT '注释',
      PRIMARY KEY (id)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模块信息表，记录所有实体';
  ```




# 自定义全局配置项

==`application`配置文件中的自定义全局配置项，供生产环境按需修改==

> 默认值为`-`的配置项在必须手动赋值
>
> （在`application-{xxx}`或者jar包外配置文件中赋值，包内配置需修改`spring.profiles.active`）
>
> 默认值`---`为数组，请查看链接



| 配置名称                      | 说明                             | 默认值                               |
| ----------------------------- | -------------------------------- | ------------------------------------ |
| rlg.security.url-allowlist | spring-security配置中的url白名单 | [---](# arr_url-allowlist)           |
| rlg.code.usable-time          | 有效时间（单位：分钟）           | 15                                   |
| rlg.directory.root-dir        | 根目录路径                       | C:/rlg                               |
| rlg.directory.static-dir      | 静态资源路径                     | ${rlg.directory.root-dir}/static     |
| rlg.directory.store-dir       | 用户文件仓库路径                 | ${rlg.directory.root-dir}/store      |
| rlg.directory.log-dir         | 日志文件路径                     | ${rlg.directory.root-dir}/log        |
| rlg.jwt.algorithm             | jwt签名算法                      | HS256                                |
| rlg.jwt.type                  | jwt类型                          | JWT                                  |
| rlg.jwt.secret-key            | 密钥                             | RedLeafGarden-JsonWebToken-SecretKey |
| rlg.jwt.usable-minutes        | JWT的有效时长（单位分钟）        | 10080（一星期）                      |
| rlg.jwt.min-length            | 长度下限                         | 105                                  |
| rlg.jwt.header                | 存放jwt的请求头名称              | Authorization                        |



###### arr_url-allowlist

- `/static/**`
- `/user/register`
- `/doc.html`
- `/**/*.css`
- `/**/*.js`
- `/favicon.ico`
- `/swagger-resources`
- `/v2/api-docs`