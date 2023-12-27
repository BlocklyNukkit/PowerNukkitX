package cn.nukkit.block;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class BlockTransparent extends Block {
    protected BlockTransparent(BlockState blockState) {
        super(blockState);
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
