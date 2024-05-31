package cn.nukkit.command.selector.args.impl;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.exceptions.SelectorSyntaxException;
import cn.nukkit.command.selector.ParseUtils;
import cn.nukkit.command.selector.SelectorType;
import cn.nukkit.command.selector.args.CachedSimpleSelectorArgument;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;

import java.util.function.Predicate;


public class LM extends CachedSimpleSelectorArgument {
    @Override
    protected Predicate<Entity> cache(SelectorType selectorType, CommandSender sender, Location basePos, String... arguments) throws SelectorSyntaxException {
        ParseUtils.singleArgument(arguments, getKeyName());
        ParseUtils.cannotReversed(arguments[0]);
        final var $1 = Integer.parseInt(arguments[0]);
        return entity -> entity instanceof Player player && player.getExperienceLevel() >= lm;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getKeyName() {
        return "lm";
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getPriority() {
        return 3;
    }
}
