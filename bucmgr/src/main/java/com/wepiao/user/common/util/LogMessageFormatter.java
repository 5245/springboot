package com.wepiao.user.common.util;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class LogMessageFormatter {
    public static String format(String format, Object... argArray) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
        return ft.getMessage();
    }
}
