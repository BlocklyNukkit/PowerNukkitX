package cn.nukkit.command.tree.node;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.command.exceptions.CommandSyntaxException;
import com.google.common.collect.Sets;

import java.util.HashSet;

@PowerNukkitXOnly
@Since("1.19.50-r4")
public class OperatorStringNode extends StringNode {
    private static final HashSet<String> OPERATOR = Sets.newHashSet("+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><");

    @Override
    public void fill(String arg) throws CommandSyntaxException {
        if (OPERATOR.contains(arg)) this.value = arg;
        else throw new CommandSyntaxException();
    }

}
