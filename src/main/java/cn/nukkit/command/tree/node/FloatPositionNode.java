package cn.nukkit.command.tree.node;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;

import java.util.regex.Pattern;

/**
 * 浮点数坐标节点，对应参数类型{@link cn.nukkit.command.data.CommandParamType#POSITION POSITION}
 */
@PowerNukkitXOnly
@Since("1.19.50-r4")
public class FloatPositionNode extends PositionNode {
    // 这个正则可以提取参数中的坐标 也可以用来验证(通过比较字符长度)
    private static final Pattern FLOAT_POS_PATTERN = Pattern.compile("[~^]?-?\\d+(?:\\.\\d+)?|[~^]");

    public FloatPositionNode() {
        super(FLOAT_POS_PATTERN);
    }

}
