package cn.maplerabbit.rlg.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ErrorDetail
        implements Serializable
{
    /**
     * 抛出异常的类
     */
    private Class<?> from;
    /**
     * 具体异常类（冗余）
     */
    private Class<?> ex;
    /**
     * 异常信息（冗余）
     */
    private String msg;
    /**
     * 抛出时间
     */
    private LocalDateTime thrTime;
    /**
     * 详细信息
     */
    private Throwable error;

    public ErrorDetail(Class<?> from, Throwable error)
    {
        this.from = from;
        this.ex = error.getClass();
        this.msg = error.getMessage();
        this.thrTime = LocalDateTime.now();
        this.error = error;
    }

    @Override
    public String toString()
    {
        return new StringBuilder(this.getClass().getSimpleName())
                .append('{')
                .append("from=").append(from)
                .append(", ex=").append(ex)
                .append(", msg='").append(msg).append('\'')
                .append(", thrTime=").append(thrTime)
                .append(", error=").append(error)
                .append('}')
                .toString();
    }
}