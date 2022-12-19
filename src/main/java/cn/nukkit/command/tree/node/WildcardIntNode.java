package cn.nukkit.command.tree.node;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.command.exceptions.CommandSyntaxException;

@PowerNukkitXOnly
@Since("1.19.50-r4")
public class WildcardIntNode extends ParamNode<Integer> {
    private final int defaultV;

    public WildcardIntNode() {
        this(Integer.MIN_VALUE);
    }

    public WildcardIntNode(int defaultV) {
        this.defaultV = defaultV;
    }

    @Override
    public void fill(String arg) throws CommandSyntaxException {
        try {
            if (arg.length() == 1 && arg.charAt(0) == '*') {
                this.value = defaultV;
            } else this.value = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new CommandSyntaxException();
        }
    }

}
