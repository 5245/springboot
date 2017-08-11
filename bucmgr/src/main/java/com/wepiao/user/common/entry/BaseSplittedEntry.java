package com.wepiao.user.common.entry;

import lombok.Data;

/**
 * @description 封装所有分库分表model的通用属性,所有分库分表的model需要继承该类
 * @author sxk
 * @date 2016年7月7日
 */
@Data
public abstract class BaseSplittedEntry {
    private int dbIndex;
    private int tableIndex;
}
