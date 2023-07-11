package cn.maplerabbit.rlg.common.enumpak;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.StringJoiner;

public enum DirectoryEnum
{
    PUBLIC(new String[]{"public"}),
    LOG(new String[]{"log"}),
    SYS_LOG(new String[]{"log", "sys_log"}),
    ERROR_LOG(new String[]{"log", "error_log"});

    private final String[] routerPart;

    private DirectoryEnum(String[] routerPart) {this.routerPart = routerPart;}

    public String router(String root)
    {
        StringJoiner joiner = new StringJoiner(File.separator).add(root);
        for (String r: routerPart) joiner.add(r);
        return joiner.toString();
    }
}