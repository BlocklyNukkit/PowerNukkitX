package cn.nukkit.command.selector.args.impl;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.selector.ParseUtils;
import cn.nukkit.command.selector.SelectorType;
import cn.nukkit.command.selector.args.ISelectorArgument;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

@PowerNukkitXOnly
@Since("1.19.50-r4")
public class RM implements ISelectorArgument {
    @Nullable
    @Override
    public Predicate<Entity> getPredicate(SelectorType selectorType, CommandSender sender, Location basePos, String... arguments) {
        ParseUtils.cannotReversed(arguments[0]);
        final var rm = Integer.parseInt(arguments[0]);
        return entity -> entity.distanceSquared(basePos) > Math.pow(rm, 2);
    }

    @Override
    public String getKeyName() {
        return "rm";
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
