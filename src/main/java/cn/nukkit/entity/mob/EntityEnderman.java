package cn.nukkit.entity.mob;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityWalkable;
import cn.nukkit.entity.data.EntityFlag;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;

/**
 * @author PikyCZ
 */
public class EntityEnderman extends EntityMob implements EntityWalkable {

    @Override
    @NotNull
    /**
     * @deprecated 
     */
    
    public String getIdentifier() {
        return ENDERMAN;
    }
    /**
     * @deprecated 
     */
    

    public EntityEnderman(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }


    @Override
    
    /**
     * @deprecated 
     */
    protected void initEntity() {
        this.setMaxHealth(40);
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
        return 2.9f;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getOriginalName() {
        return "Enderman";
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isPreventingSleep(Player player) {
        return this.getDataFlag(EntityFlag.ANGRY);
    }
}
