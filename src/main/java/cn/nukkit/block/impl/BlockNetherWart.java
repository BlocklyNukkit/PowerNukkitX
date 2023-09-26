package cn.nukkit.block.impl;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockFlowable;
import cn.nukkit.block.property.BlockProperties;
import cn.nukkit.block.property.IntBlockProperty;
import cn.nukkit.event.block.BlockGrowEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemNetherWart;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.player.Player;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

/**
 * @author Leonidius20
 * @since 22.03.17
 */
public class BlockNetherWart extends BlockFlowable {

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final IntBlockProperty AGE = new IntBlockProperty("age", false, 3);

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final BlockProperties PROPERTIES = new BlockProperties(AGE);

    public BlockNetherWart() {
        this(0);
    }

    public BlockNetherWart(int meta) {
        super(meta);
    }

    @Override
    public boolean place(
            @NotNull Item item,
            @NotNull Block block,
            @NotNull Block target,
            @NotNull BlockFace face,
            double fx,
            double fy,
            double fz,
            Player player) {
        Block down = this.down();
        if (down.getId() == SOUL_SAND) {
            this.getLevel().setBlock(block, this, true, true);
            return true;
        }
        return false;
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (this.down().getId() != SOUL_SAND) {
                this.getLevel().useBreakOn(this);
                return Level.BLOCK_UPDATE_NORMAL;
            }
        } else if (type == Level.BLOCK_UPDATE_RANDOM) {
            if (new Random().nextInt(10) == 1) {
                if (this.getDamage() < 0x03) {
                    BlockNetherWart block = (BlockNetherWart) this.clone();
                    block.setDamage(block.getDamage() + 1);
                    BlockGrowEvent event = new BlockGrowEvent(this, block);
                    event.call();

                    if (!event.isCancelled()) {
                        this.getLevel().setBlock(this, event.getNewState(), true, true);
                    } else {
                        return Level.BLOCK_UPDATE_RANDOM;
                    }
                }
            } else {
                return Level.BLOCK_UPDATE_RANDOM;
            }
        }

        return 0;
    }

    @Override
    public String getName() {
        return "Nether Wart Block";
    }

    @Override
    public int getId() {
        return NETHER_WART_BLOCK;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @NotNull @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (this.getDamage() == 0x03) {
            return new Item[] {new ItemNetherWart(0, 2 + (int) (Math.random() * ((4 - 2) + 1)))};
        } else {
            return new Item[] {new ItemNetherWart()};
        }
    }

    @Override
    public Item toItem() {
        return new ItemNetherWart();
    }
}