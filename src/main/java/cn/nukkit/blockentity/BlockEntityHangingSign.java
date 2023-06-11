package cn.nukkit.blockentity;

import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * Author: Cool_Loong <br>
 * Date: 6/11/2023 <br>
 * Allay Project
 */
public class BlockEntityHangingSign extends BlockEntitySign {
    public BlockEntityHangingSign(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public String getName() {
        return this.hasName() ? this.namedTag.getString("CustomName") : "Hanging Sign";
    }

    public boolean hasName() {
        return namedTag.contains("CustomName");
    }


    @Override
    public CompoundTag getSpawnCompound() {
        return super.getSpawnCompound().putString("id", BlockEntity.HANGING_SIGN);
    }
}
