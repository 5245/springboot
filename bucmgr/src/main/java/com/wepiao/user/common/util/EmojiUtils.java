package com.wepiao.user.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Emoji表情处理
 * @author Jin Song
 *
 */
public class EmojiUtils {

    /**
     * 可能的Emoji编码范围
     */
    private static final String  EMOJI_RANGE_REGEX      = "[\uD83C\uDF00-\uD83D\uDDFF]|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEFF]|[\u2600-\u26FF]|[\u2700-\u27BF]";
    private static final Pattern PATTERN                = Pattern.compile(EMOJI_RANGE_REGEX);
    /**
     * 更全面的Emoji编码范围
     */
    private static final String  EMOJI_RANGE_REGEX_FULL = "[^(\u2E80-\u9FFF\\w\\s`~!@#\\$%\\^&\\*\\(\\)_+-？（）——=\\[\\]{}\\|;。，、《》”：；“！……’:‘\"<,>\\.?/\\\\*)]";
    private static final Pattern PATTERN_FULL           = Pattern.compile(EMOJI_RANGE_REGEX_FULL);

    /**
     * 从input里删除所有的emoji表情
     * 
     * @param input the input string potentially containing emojis (comes as unicode stringfied)
     * @return input string with emojis replaced
     */
    public static String eraseEmojis(String input) {
        if (null == input || 0 == input.length()) {
            return input;
        }
        Matcher matcher = PATTERN.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String replaceEmoji(String input) {
        if (null == input || 0 == input.length()) {
            return input;
        }
        Matcher matcher = PATTERN_FULL.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, " ");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    /**
     * 判断是否存在emoji字符
     * @param input
     * @return
     *
     */
    public static boolean existsEmoji(String input) {
        if (null == input || 0 == input.length()) {
            return false;
        }
        Matcher matcher = PATTERN_FULL.matcher(input);
        while (matcher.find()) {
            return true;
        }
        return false;
    }
}