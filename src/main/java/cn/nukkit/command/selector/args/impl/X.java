package cn.nukkit.command.selector.args.impl;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.selector.ParseUtils;
import cn.nukkit.command.selector.SelectorType;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;

import java.util.List;
import java.util.function.Predicate;

@PowerNukkitXOnly
@Since("1.19.50-r4")
public class X extends CoordinateArgument {
    @Override
    public List<Predicate<Entity>> getPredicates(SelectorType selectorType, CommandSender sender, Location basePos, String... arguments) {
        ParseUtils.cannotReversed(arguments[0]);
        basePos.setX(ParseUtils.parseOffsetDouble(arguments[0], basePos.getX()));
        return null;
    }

    @Override
    public String getKeyName() {
        return "x";
    }
}
