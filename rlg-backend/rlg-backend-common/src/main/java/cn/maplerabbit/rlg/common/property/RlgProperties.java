package cn.maplerabbit.rlg.common.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目自定义配置项
 */
@Getter
@Setter
@Slf4j
@Component
@ConfigurationProperties(prefix = RlgProperties.PREFIX)
public class RlgProperties
{
    public static final String PREFIX = "rlg";

    public RlgProperties() {log.debug("RlgProperties()...");}

    private CodeProperties code = new CodeProperties();

    private TokenProperties token = new TokenProperties();

    private UserProperties user = new UserProperties();

    private FileProperties file = new FileProperties();

    /**
     * 验证码相关配置
     */
    @Getter
    @Setter
    public static class CodeProperties
    {
        /**
         * 有效时间（单位：分钟）
         */
        private Integer usableTime = 15;
        /**
         * 标题
         */
        private String title = "验证码";
        /**
         * 正文格式（参数：code）
         */
        private String textFormat = "您的验证码为：%s，请勿泄露！";
    }

    /**
     * token相关配置
     */
    @Getter
    @Setter
    public static class TokenProperties
    {
        /**
         * token签名算法
         */
        private String algorithm = "HS256";
        /**
         * token类型
         */
        private String type = "JWT";
        /**
         * 解析和生成token使用的key
         */
        private String secretKey = "RedLeafGarden-JsonWebToken-SecretKey";
        /**
         * token有效时长（单位：分钟）
         */
        private Integer usableMinutes = 10080;
        /**
         * 长度下限
         */
        private Integer minLength = 105;
        /**
         * 存放token的请求头的名称
         */
        private String header = "authorization";
        /**
         * token可刷新临期时间（单位：分钟）
         */
        private Integer refreshAllowTime = 1500;
    }

    /**
     * 用户模块配置
     */
    @Getter
    @Setter
    public static class UserProperties
    {
        private LoginProperties login = new LoginProperties();

        /**
         * 登录相关配置
         */
        @Getter
        @Setter
        public static class LoginProperties
        {
            /**
             * 存放登录方式的请求头名称
             */
            private String loginWayHeader = "login-way";
            /**
             * 账户参数名（用户名、邮箱、手机号等的共同参数名）
             */
            private String paramAccount = "account";
            /**
             * 密钥参数名（密码、验证码等的共同参数名）
             */
            private String paramKey = "key";
        }
    }

    /**
     * 文件模块配置
     */
    @Getter
    @Setter
    public static class FileProperties
    {
        /**
         * 项目文件夹根目录路径
         */
        private String rootDir;
        /**
         * 文件传输缓冲区大小
         */
        private Integer bufferSize;

        private FtpProperties ftp = new FtpProperties();

        /**
         * ftp相关配置
         */
        @Getter
        @Setter
        public static class FtpProperties
        {
            /**
             * ip
             */
            private String host;
            /**
             * 端口
             */
            private Integer port = 21;
            /**
             * 用户名
             */
            private String username;
            /**
             * 密码
             */
            private String password;
            /**
             * 是否被动模式
             */
            private Boolean passiveMode = true;
            /**
             * 连接超时时间
             */
            private Integer connectTimeout = 30000;
            /**
             * 编码格式
             */
            private String encoding = "UTF-8";
            /**
             * 缓冲区大小
             */
            private Integer bufferSize = 8192;

            private FtpPoolProperties pool = new FtpPoolProperties();

            /**
             * Ftp连接池相关配置
             */
            @Getter
            @Setter
            public static class FtpPoolProperties
            {
                /**
                 * 连接池容量
                 */
                private Integer size = 10;
                /**
                 * 加入超时时间（单位：秒）
                 */
                private Integer offerTimeout = 3;
                /**
                 * 心跳检测间隔时间（单位：秒）
                 */
                private Integer aliveCheckIntervalTime = 30;
            }
        }
    }
}