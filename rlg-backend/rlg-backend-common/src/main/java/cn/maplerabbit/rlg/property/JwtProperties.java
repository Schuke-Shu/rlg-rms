package cn.maplerabbit.rlg.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@Slf4j
@Component
@ConfigurationProperties(prefix = "rlg.jwt")
public class JwtProperties
{
    /**
     * 解析管理员token使用的key
     */
    private String adminKey;
    /**
     * 解析用户token使用的key
     */
    private String userKey;
    /**
     * token有效时长（单位分钟）
     */
    private String usableMinutes;

    public JwtProperties() {log.debug("JwtProperties()...");}

    @Override
    public String toString()
    {
        return new StringBuilder("JwtProperties{")
                .append("adminKey='").append(adminKey).append('\'')
                .append(", userKey='").append(userKey).append('\'')
                .append(", usableMinutes='").append(usableMinutes).append('\'')
                .append('}')
                .toString();
    }
}