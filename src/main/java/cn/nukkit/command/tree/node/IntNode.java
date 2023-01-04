package cn.nukkit.command.tree.node;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;

/**
 * 解析对应参数为{@link Integer}值
 * <br>
 * 对应参数类型{@link cn.nukkit.command.data.CommandParamType#INT INT}
 */
@PowerNukkitXOnly
@Since("1.19.50-r4")
public class IntNode extends ParamNode<Integer> {
    @Override
    public void fill(String arg) {
        try {
            this.value = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            this.error();
        }
    }

}
