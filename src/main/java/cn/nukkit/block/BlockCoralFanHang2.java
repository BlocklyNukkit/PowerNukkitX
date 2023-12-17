package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.blockproperty.ArrayBlockProperty;
import cn.nukkit.blockproperty.BlockProperties;
import cn.nukkit.blockproperty.value.CoralType;
import org.jetbrains.annotations.NotNull;

import static cn.nukkit.blockproperty.CommonBlockProperties.PERMANENTLY_DEAD;


public class BlockCoralFanHang2 extends BlockCoralFanHang {


    public static final ArrayBlockProperty<CoralType> HANG2_TYPE = new ArrayBlockProperty<>("coral_hang_type_bit", true,
            new CoralType[]{CoralType.PURPLE, CoralType.RED}
    ).ordinal(true);


    public static final BlockProperties PROPERTIES = new BlockProperties(HANG2_TYPE, PERMANENTLY_DEAD, HANG_DIRECTION);


    public BlockCoralFanHang2() {
        this(0);
    }


    public BlockCoralFanHang2(int meta) {
        super(meta);
    }
    
    @Override
    public int getId() {
        return CORAL_FAN_HANG2;
    }


    @NotNull
    @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }


    @Override
    public int getType() {
        if ((getDamage() & 0b1) == 0) {
            return BlockCoral.TYPE_BUBBLE;
        } else {
            return BlockCoral.TYPE_FIRE;
        }
    }
}
