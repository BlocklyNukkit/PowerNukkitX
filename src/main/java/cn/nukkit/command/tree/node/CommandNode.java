package cn.nukkit.command.tree.node;

import cn.nukkit.command.exceptions.CommandSyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class CommandNode extends ParamNode<String> {
    protected final List<String> TMP = new ArrayList<>();

    @Override
    public void fill(String arg) throws CommandSyntaxException {
        if (this.parent.getIndex() != this.parent.parent.getArgs().length) TMP.add(arg);
        else {
            TMP.add(arg);
            var join = new StringJoiner(" ");
            TMP.forEach(join::add);
            this.value = join.toString();
        }
    }

    @Override
    public void reset() {
        super.reset();
        TMP.clear();
    }
}
