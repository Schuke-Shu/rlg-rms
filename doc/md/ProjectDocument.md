# 命名规范

# 自定义全局配置项

==`application`配置文件中的自定义全局配置项，供生产环境按需修改==

> 默认值为`-`的配置项在必须手动赋值
>
> （在`application-{xxx}`或者jar包外配置文件中赋值，包内配置需修改`spring.profiles.active`）
>
> 默认值`---`为数组，请查看链接



| 配置名称                      | 说明                             | 默认值                               |
| ----------------------------- | -------------------------------- | ------------------------------------ |
| spring.security.url-allowlist | spring-security配置中的url白名单 | [---](# arr_url-allowlist)           |
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
- `/user/email-login`
- `/user/login`
- `/user/register`
- `/doc.html`
- `/**/*.css`
- `/**/*.js`
- `/favicon.ico`
- `/swagger-resources`
- `/v2/api-docs`