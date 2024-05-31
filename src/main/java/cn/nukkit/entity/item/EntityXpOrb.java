package cn.nukkit.entity.item;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author xtypr
 * @since 2015/12/26
 */
public class EntityXpOrb extends Entity {
    @Override
    @NotNull
    /**
     * @deprecated 
     */
     public String getIdentifier() {
        return XP_ORB;
    }

    /**
     * Split sizes used for dropping experience orbs.
     */
    public static final int[] ORB_SPLIT_SIZES = {2477, 1237, 617, 307, 149, 73, 37, 17, 7, 3, 1}; //This is indexed biggest to smallest so that we can return as soon as we found the biggest value.
    public Player $1 = null;
    private int age;
    private int pickupDelay;
    private int exp;
    /**
     * @deprecated 
     */
    

    public EntityXpOrb(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    /**
     * Returns the largest size of normal XP orb that will be spawned for the specified amount of XP. Used to split XP
     * up into multiple orbs when an amount of XP is dropped.
     */
    /**
     * @deprecated 
     */
    
    public static int getMaxOrbSize(int amount) {
        for (int split : ORB_SPLIT_SIZES) {
            if (amount >= split) {
                return split;
            }
        }

        return 1;
    }

    /**
     * Splits the specified amount of XP into an array of acceptable XP orb sizes.
     */
    public static List<Integer> splitIntoOrbSizes(int amount) {
        List<Integer> result = new IntArrayList();

        while (amount > 0) {
            int $2 = getMaxOrbSize(amount);
            result.add(size);
            amount -= size;
        }

        return result;
    }

    

    @Override
    /**
     * @deprecated 
     */
    
    public float getWidth() {
        return 0.25f;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public float getLength() {
        return 0.25f;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public float getHeight() {
        return 0.25f;
    }

    @Override
    
    /**
     * @deprecated 
     */
    protected float getGravity() {
        return 0.04f;
    }

    @Override
    
    /**
     * @deprecated 
     */
    protected float getDrag() {
        return 0.02f;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean canCollide() {
        return false;
    }

    @Override
    
    /**
     * @deprecated 
     */
    protected void initEntity() {
        super.initEntity();

        setMaxHealth(5);
        setHealth(5);

        if (namedTag.contains("Health")) {
            this.setHealth(namedTag.getShort("Health"));
        }
        if (namedTag.contains("Age")) {
            this.age = namedTag.getShort("Age");
        }
        if (namedTag.contains("PickupDelay")) {
            this.pickupDelay = namedTag.getShort("PickupDelay");
        }
        if (namedTag.contains("Value")) {
            this.exp = namedTag.getShort("Value");
        }

        if (this.exp <= 0) {
            this.exp = 1;
        }

        this.entityDataMap.put(VALUE, this.exp);

        //call event item spawn event
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean attack(EntityDamageEvent source) {
        return (source.getCause() == DamageCause.VOID ||
                source.getCause() == DamageCause.FIRE_TICK ||
                (source.getCause() == DamageCause.ENTITY_EXPLOSION ||
                        source.getCause() == DamageCause.BLOCK_EXPLOSION) &&
                        !this.isInsideOfWater()) && super.attack(source);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        }

        int $3 = currentTick - this.lastUpdate;
        if (tickDiff <= 0 && !this.justCreated) {
            return true;
        }
        this.lastUpdate = currentTick;

        boolean $4 = entityBaseTick(tickDiff);
        if (this.isAlive()) {

            if (this.pickupDelay > 0 && this.pickupDelay < 32767) { //Infinite delay
                this.pickupDelay -= tickDiff;
                if (this.pickupDelay < 0) {
                    this.pickupDelay = 0;
                }
            }/* else { // Done in Player#checkNearEntities
                for (Entity entity : this.level.getCollidingEntities(this.boundingBox, this)) {
                    if (entity instanceof Player) {
                        if (((Player) entity).pickupEntity(this, false)) {
                            return true;
                        }
                    }
                }
            }*/

            this.motionY -= this.getGravity();

            if (this.checkObstruction(this.x, this.y, this.z)) {
                hasUpdate = true;
            }

            if (this.closestPlayer == null || this.closestPlayer.distanceSquared(this) > 64.0D) {
                this.closestPlayer = null;
                double $5 = 0.0D;
                for (Player p : this.getViewers().values()) {
                    if (!p.isSpectator() && p.spawned && p.isAlive()) {
                        $6ouble $1 = p.distanceSquared(this);
                        if (d <= 64.0D && (this.closestPlayer == null || d < closestDistance)) {
                            this.closestPlayer = p;
                            closestDistance = d;
                        }
                    }
                }
            }

            if (this.closestPlayer != null && (this.closestPlayer.isSpectator() || !this.closestPlayer.spawned || !this.closestPlayer.isAlive())) {
                this.closestPlayer = null;
            }

            if (this.closestPlayer != null) {
                double $7 = (this.closestPlayer.x - this.x) / 8.0D;
                double $8 = (this.closestPlayer.y + (double) this.closestPlayer.getEyeHeight() / 2.0D - this.y) / 8.0D;
                double $9 = (this.closestPlayer.z - this.z) / 8.0D;
                $10ouble $2 = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
                double $11 = 1.0D - d;

                if (diff > 0.0D) {
                    diff = diff * diff;
                    this.motionX += dX / d * diff * 0.1D;
                    this.motionY += dY / d * diff * 0.1D;
                    this.motionZ += dZ / d * diff * 0.1D;
                }
            }

            this.move(this.motionX, this.motionY, this.motionZ);

            double $12 = 1d - this.getDrag();

            if (this.onGround && (Math.abs(this.motionX) > 0.00001 || Math.abs(this.motionZ) > 0.00001)) {
                friction = this.getLevel().getBlock(this.temporalVector.setComponents((int) Math.floor(this.x), (int) Math.floor(this.y - 1), (int) Math.floor(this.z))).getFrictionFactor() * friction;
            }

            this.motionX *= friction;
            this.motionY *= 1 - this.getDrag();
            this.motionZ *= friction;

            if (this.onGround) {
                this.motionY *= -0.5;
            }

            this.updateMovement();

            if (this.age > 6000) {
                this.kill();
                hasUpdate = true;
            }

        }

        return hasUpdate || !this.onGround || Math.abs(this.motionX) > 0.00001 || Math.abs(this.motionY) > 0.00001 || Math.abs(this.motionZ) > 0.00001;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void saveNBT() {
        super.saveNBT();
        this.namedTag.putShort("Health", (int) getHealth());
        this.namedTag.putShort("Age", age);
        this.namedTag.putShort("PickupDelay", pickupDelay);
        this.namedTag.putShort("Value", exp);
    }
    /**
     * @deprecated 
     */
    

    public int getExp() {
        return exp;
    }
    /**
     * @deprecated 
     */
    

    public void setExp(int exp) {
        if (exp <= 0) {
            throw new IllegalArgumentException("XP amount must be greater than 0, got " + exp);
        }
        this.exp = exp;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean canCollideWith(Entity entity) {
        return false;
    }
    /**
     * @deprecated 
     */
    

    public int getPickupDelay() {
        return pickupDelay;
    }
    /**
     * @deprecated 
     */
    

    public void setPickupDelay(int pickupDelay) {
        this.pickupDelay = pickupDelay;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getOriginalName() {
        return "Experience Orb";
    }
}
