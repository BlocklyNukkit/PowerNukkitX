package cn.nukkit.command.selector;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.command.exceptions.SelectorSyntaxException;

/**
 * 一些有关目标选择器解析的常用静态函数
 */
@PowerNukkitXOnly
@Since("1.19.50-r4")
public class ParseUtils {
    /**
     * 解析偏移int值
     * @param value 文本
     * @param base 基础值
     * @return 偏移值
     */
    public static int parseOffsetInt(String value, int base) {
        try {
            if (value.startsWith("~")) {
                return value.length() != 1 ? base + Integer.parseInt(value.substring(1)) : base;
            } else {
                return Integer.parseInt(value);
            }
        } catch (NumberFormatException e) {
            throw new SelectorSyntaxException("Error parsing target selector", e);
        }
    }

    /**
     * 解析偏移double值
     * @param value 文本
     * @param base 基础值
     * @return 偏移值
     */
    public static double parseOffsetDouble(String value, double base) {
        try {
            if (value.startsWith("~")) {
                return value.length() != 1 ? base + Double.parseDouble(value.substring(1)) : base;
            } else {
                return Double.parseDouble(value);
            }
        } catch (NumberFormatException e) {
            throw new SelectorSyntaxException("Error parsing target selector", e);
        }
    }

    /**
     * 检查参数是否反转
     * @param value 给定字符串
     * @return 是否反转
     */
    public static boolean checkReversed(String value) {
        return value.startsWith("!");
    }

    /**
     * 要求参数不能取反。若取反，则抛出{@link SelectorSyntaxException}
     * @param value 给定字符串
     */
    public static void cannotReversed(String value) {
        if (checkReversed(value))
            throw new SelectorSyntaxException("Argument cannot be reversed!");
    }

    /**
     * 检查给定值是否在给定的两个数之间
     * @param bound1 边界1
     * @param bound2 边界2
     * @param value 之值
     * @return 给定值是否在给定的两个数之间
     */
    public static boolean checkBetween(double bound1, double bound2, double value) {
        return bound1 < bound2 ?
                (value >= bound1 && value <= bound2) :
                (value >= bound2 && value <= bound1);
    }
}
