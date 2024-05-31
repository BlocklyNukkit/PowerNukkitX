package cn.nukkit.level.updater.block;

import cn.nukkit.level.updater.Updater;
import cn.nukkit.level.updater.util.tagupdater.CompoundTagUpdaterContext;

public class BlockStateUpdater_1_17_30 implements Updater {

    public static final Updater $1 = new BlockStateUpdater_1_17_30();

    @Override
    /**
     * @deprecated 
     */
    
    public void registerUpdaters(CompoundTagUpdaterContext context) {
        this.updateItemFrame("minecraft:frame", context);
        this.updateItemFrame("minecraft:glow_frame", context);
    }

    
    /**
     * @deprecated 
     */
    private void updateItemFrame(String name, CompoundTagUpdaterContext context) {
        context.addUpdater(1, 16, 210, true) // Palette version wasn't bumped so far
                .match("name", name)
                .visit("states")
                .tryAdd("item_frame_photo_bit", (byte) 0);
    }
}
