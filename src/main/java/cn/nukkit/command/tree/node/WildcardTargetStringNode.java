package cn.nukkit.command.tree.node;


import cn.nukkit.Server;
import cn.nukkit.command.exceptions.CommandSyntaxException;
import cn.nukkit.command.tree.ParamNodeType;
import cn.nukkit.command.utils.EntitySelector;
import cn.nukkit.scoreboard.manager.IScoreboardManager;

public class WildcardTargetStringNode extends StringNode {
    private static final IScoreboardManager MANAGER = Server.getInstance().getScoreboardManager();

    @Override
    public void fill(String arg) throws CommandSyntaxException {
        try {
            if (arg.equals("*") || EntitySelector.hasArguments(arg) || Server.getInstance().getPlayer(arg) != null) {//temp not support entity uuid query
                this.value = arg;
            } else throw new CommandSyntaxException();
        } catch (NumberFormatException ignore) {
            throw new CommandSyntaxException();
        }
    }

    @Override
    public ParamNodeType type() {
        return ParamNodeType.WILDCARD_TARGET;
    }
}
