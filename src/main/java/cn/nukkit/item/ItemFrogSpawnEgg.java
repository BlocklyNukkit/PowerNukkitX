package cn.nukkit.item;

public class ItemFrogSpawnEgg extends ItemSpawnEgg {
    public ItemFrogSpawnEgg() {
        super(FROG_SPAWN_EGG);
    }

    @Override
    public int getEntityNetworkId() {
        return 132;
    }

    @Override
    public void setAux(Integer aux) {
        throw new UnsupportedOperationException();
    }
}