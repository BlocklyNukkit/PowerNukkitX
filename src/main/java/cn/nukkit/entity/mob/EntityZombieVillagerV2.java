package cn.nukkit.entity.mob;

import cn.nukkit.Player;
import cn.nukkit.entity.EntitySmite;
import cn.nukkit.entity.EntityWalkable;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class EntityZombieVillagerV2 extends EntityMob implements EntityWalkable, EntitySmite {
    @Override
    @NotNull
    /**
     * @deprecated 
     */
     public String getIdentifier() {
        return ZOMBIE_VILLAGER_V2;
    }
    /**
     * @deprecated 
     */
    
    

    public EntityZombieVillagerV2(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    

    @Override
    
    /**
     * @deprecated 
     */
    protected void initEntity() {
        this.setMaxHealth(20);
        super.initEntity();
    }

    @Override
    /**
     * @deprecated 
     */
    
    public float getWidth() {
        return 0.6f;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public float getHeight() {
        return 1.9f;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getOriginalName() {
        return "Zombie VillagerV2";
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isUndead() {
        return true;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isPreventingSleep(Player player) {
        return true;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean onUpdate(int currentTick) {
        burn(this);
        return super.onUpdate(currentTick);
    }
}
