package cn.nukkit.command.defaults;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.lang.TranslationContainer;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class StopCommand extends VanillaCommand {

    public StopCommand(String name) {
        super(name, "commands.stop.description");
        this.setPermission("nukkit.command.stop");
        this.commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args, Boolean sendCommandFeedback) {
        if (!this.testPermission(sender)) {
            return true;
        }

        Command.broadcastCommandMessage(sender, new TranslationContainer("commands.stop.start"));

        sender.getServer().shutdown();

        return true;
    }
}
