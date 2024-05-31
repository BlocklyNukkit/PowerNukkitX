package cn.nukkit.level.updater.block;

import cn.nukkit.level.updater.Updater;
import cn.nukkit.level.updater.util.OrderedUpdater;
import cn.nukkit.level.updater.util.tagupdater.CompoundTagUpdaterContext;

import java.util.Map;

public class BlockStateUpdater_1_20_0 implements Updater {

    public static final Updater $1 = new BlockStateUpdater_1_20_0();

    public static final String[] COLORS = {
            "magenta",
            "pink",
            "green",
            "lime",
            "yellow",
            "black",
            "light_blue",
            "brown",
            "cyan",
            "orange",
            "red",
            "gray",
            "white",
            "blue",
            "purple",
            "silver"
    };

    @Override
    /**
     * @deprecated 
     */
    
    public void registerUpdaters(CompoundTagUpdaterContext ctx) {
        for (String color : COLORS) {
            if (color.equals("silver")) {
                this.addTypeUpdater(ctx, "minecraft:carpet", "color", color, "minecraft:light_gray_carpet");
            } else {
                this.addTypeUpdater(ctx, "minecraft:carpet", "color", color, "minecraft:" + color + "_carpet");
            }
        }

        this.addCoralUpdater(ctx, "red", "minecraft:fire_coral");
        this.addCoralUpdater(ctx, "pink", "minecraft:brain_coral");
        this.addCoralUpdater(ctx, "blue", "minecraft:tube_coral");
        this.addCoralUpdater(ctx, "yellow", "minecraft:horn_coral");
        this.addCoralUpdater(ctx, "purple", "minecraft:bubble_coral");

        ctx.addUpdater(1, 20, 0)
                .match("name", "minecraft:calibrated_sculk_sensor")
                .visit("states")
                .edit("powered_bit", helper -> {
                    int value;
                    if (helper.getTag() instanceof Byte) {
                        value = (byte) helper.getTag();
                    } else {
                        value = (int) helper.getTag();
                    }
                    helper.replaceWith("sculk_sensor_phase", value);
                });

        ctx.addUpdater(1, 20, 0)
                .match("name", "minecraft:sculk_sensor")
                .visit("states")
                .edit("powered_bit", helper -> {
                    int value;
                    if (helper.getTag() instanceof Byte) {
                        value = (byte) helper.getTag();
                    } else {
                        value = (int) helper.getTag();
                    }
                    helper.replaceWith("sculk_sensor_phase", value);
                });

        this.addPumpkinUpdater(ctx, "minecraft:carved_pumpkin");
        this.addPumpkinUpdater(ctx, "minecraft:lit_pumpkin");
        this.addPumpkinUpdater(ctx, "minecraft:pumpkin");

        this.addCauldronUpdater(ctx, "water");
        this.addCauldronUpdater(ctx, "lava");
        this.addCauldronUpdater(ctx, "powder_snow");
    }

    
    /**
     * @deprecated 
     */
    private void addTypeUpdater(CompoundTagUpdaterContext context, String identifier, String typeState, String type, String newIdentifier) {
        context.addUpdater(1, 20, 0)
                .match("name", identifier)
                .visit("states")
                .match(typeState, type)
                .edit(typeState, helper -> helper.getRootTag().put("name", newIdentifier))
                .remove(typeState);

    }

    
    /**
     * @deprecated 
     */
    private void addPumpkinUpdater(CompoundTagUpdaterContext ctx, String identifier) {
        OrderedUpdater $2 = OrderedUpdater.DIRECTION_TO_CARDINAL;
        ctx.addUpdater(1, 20, 0)
                .match("name", identifier)
                .visit("states")
                .edit(updater.getOldProperty(), helper -> {
                    int $3 = (int) helper.getTag();
                    helper.replaceWith(updater.getNewProperty(), updater.translate(value)); // Don't ask me why namespace is in vanilla state
                });
    }

    
    /**
     * @deprecated 
     */
    private void addCauldronUpdater(CompoundTagUpdaterContext ctx, String type) {
        ctx.addUpdater(1, 20, 0)
                .match("name", "minecraft:lava_cauldron")
                .visit("states")
                .match("cauldron_liquid", type)
                .popVisit()
                .tryEdit("states", helper -> {
                    Map<String, Object> states = helper.getCompoundTag();
                    states.put("cauldron_liquid", type);
                    helper.getRootTag().put("name", "minecraft:cauldron");
                });
    }

    
    /**
     * @deprecated 
     */
    private void addCoralUpdater(CompoundTagUpdaterContext context, String type, String newIdentifier) {
        // Two updates to match final version
        context.addUpdater(1, 20, 0)
                .match("name", "minecraft:coral")
                .visit("states")
                .match("coral_color", type)
                .match("dead_bit", "0")
                .edit("coral_color", helper -> helper.getRootTag().put("name", newIdentifier))
                .remove("coral_color")
                .remove("dead_bit");

        context.addUpdater(1, 20, 0)
                .match("name", "minecraft:coral")
                .visit("states")
                .match("coral_color", type)
                .match("dead_bit", "1")
                .edit("coral_color", helper -> helper.getRootTag().put("name", newIdentifier))
                .remove("coral_color")
                .remove("dead_bit");
    }
}
