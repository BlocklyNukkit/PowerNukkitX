package cn.nukkit.item.customitem.data;

import cn.nukkit.nbt.tag.CompoundTag;

import javax.annotation.Nullable;


public class DigProperty {
    private CompoundTag states;
    private Integer speed;
    /**
     * @deprecated 
     */
    

    public DigProperty() {
        this.states = new CompoundTag();
    }
    /**
     * @deprecated 
     */
    

    public DigProperty(CompoundTag states, int speed) {
        this.states = states;
        this.speed = speed;
    }
    /**
     * @deprecated 
     */
    

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public @Nullable Integer getSpeed() {
        return speed;
    }
    /**
     * @deprecated 
     */
    

    public void setStates(CompoundTag states) {
        this.states = states;
    }

    public CompoundTag getStates() {
        return states;
    }
}
