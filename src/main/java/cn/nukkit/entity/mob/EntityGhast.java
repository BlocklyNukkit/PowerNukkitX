package cn.nukkit.entity.mob;

import cn.nukkit.entity.EntityFlyable;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * @author PikyCZ
 */
public class EntityGhast extends EntityMob implements EntityFlyable {

    

    public EntityGhast(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    

    @Override
    protected void initEntity() {
        this.setMaxHealth(10);
        super.initEntity();
    }

    @Override
    public float getWidth() {
        return 4;
    }

    @Override
    public float getHeight() {
        return 4;
    }


    @Override
    public String getOriginalName() {
        return "Ghast";
    }
}
