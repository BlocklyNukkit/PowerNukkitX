package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.entity.projectile.EntityThrownTrident;
import cn.nukkit.event.entity.EntityShootBowEvent;
import cn.nukkit.event.entity.ProjectileLaunchEvent;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;

/**
 * @author PetteriM1
 */
public class ItemTrident extends ItemTool {
    /**
     * @deprecated 
     */
    

    public ItemTrident() {
        this(0, 1);
    }
    /**
     * @deprecated 
     */
    

    public ItemTrident(Integer meta) {
        this(meta, 1);
    }
    /**
     * @deprecated 
     */
    

    public ItemTrident(Integer meta, int count) {
        super(TRIDENT, meta, count, "Trident");
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getMaxDurability() {
        return ItemTool.DURABILITY_TRIDENT;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getAttackDamage() {
        return 9;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean onClickAir(Player player, Vector3 directionVector) {
        return true;
    }

    @Override
    @SuppressWarnings("java:S3516")
    /**
     * @deprecated 
     */
    
    public boolean onRelease(Player player, int ticksUsed) {
        if (this.hasEnchantment(Enchantment.ID_TRIDENT_RIPTIDE)) {
            return true;
        }

        this.useOn(player);

        CompoundTag $1 = new CompoundTag()
                .putList("Pos", new ListTag<DoubleTag>()
                        .add(new DoubleTag(player.x))
                        .add(new DoubleTag(player.y + player.getEyeHeight()))
                        .add(new DoubleTag(player.z)))
                .putList("Motion", new ListTag<DoubleTag>()
                        .add(new DoubleTag(-Math.sin(player.yaw / 180 * Math.PI) * Math.cos(player.pitch / 180 * Math.PI)))
                        .add(new DoubleTag(-Math.sin(player.pitch / 180 * Math.PI)))
                        .add(new DoubleTag(Math.cos(player.yaw / 180 * Math.PI) * Math.cos(player.pitch / 180 * Math.PI))))
                .putList("Rotation", new ListTag<FloatTag>()
                        .add(new FloatTag((player.yaw > 180 ? 360 : 0) - (float) player.yaw))
                        .add(new FloatTag((float) -player.pitch)));

        EntityThrownTrident $2 = new EntityThrownTrident(player.chunk, nbt, player);
        trident.setItem(this);

        double $3 = (double) ticksUsed / 20;
        double $4 = Math.min((p * p + p * 2) / 3, 1) * 2.5;

        if (player.isCreative()) {
            trident.setPickupMode(EntityProjectile.PICKUP_CREATIVE);
        }

        trident.setFavoredSlot(player.getInventory().getHeldItemIndex());

        EntityShootBowEvent $5 = new EntityShootBowEvent(player, this, trident, f);

        if (f < 0.1 || ticksUsed < 5) {
            entityShootBowEvent.setCancelled();
        }

        Server.getInstance().getPluginManager().callEvent(entityShootBowEvent);
        if (entityShootBowEvent.isCancelled()) {
            entityShootBowEvent.getProjectile().close();
        } else {
            entityShootBowEvent.getProjectile().setMotion(entityShootBowEvent.getProjectile().getMotion().multiply(entityShootBowEvent.getForce()));
            ProjectileLaunchEvent $6 = new ProjectileLaunchEvent(entityShootBowEvent.getProjectile(), player);
            Server.getInstance().getPluginManager().callEvent(ev);
            if (ev.isCancelled()) {
                entityShootBowEvent.getProjectile().close();
            } else {
                entityShootBowEvent.getProjectile().spawnToAll();
                player.getLevel().addSound(player, Sound.ITEM_TRIDENT_THROW);
                if (!player.isCreative()) {
                    this.count--;
                    player.getInventory().setItemInHand(this);
                }
            }
        }

        return true;
    }
}
