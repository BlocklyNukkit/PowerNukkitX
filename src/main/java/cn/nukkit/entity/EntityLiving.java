package cn.nukkit.entity;

import cn.nukkit.Server;
import cn.nukkit.api.*;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockCactus;
import cn.nukkit.block.BlockMagma;
import cn.nukkit.entity.data.ShortEntityData;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.entity.weather.EntityWeather;
import cn.nukkit.event.entity.*;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTurtleShell;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Sound;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.network.protocol.AnimatePacket;
import cn.nukkit.network.protocol.EntityEventPacket;
import cn.nukkit.player.Player;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TickCachedBlockIterator;
import cn.nukkit.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class EntityLiving extends Entity implements EntityDamageable {
    public static final float DEFAULT_SPEED = 0.1f;
    protected int attackTime = 0;
    protected boolean invisible = false;
    protected float movementSpeed = DEFAULT_SPEED;
    protected int turtleTicks = 0;
    private boolean attackTimeByShieldKb;
    private int attackTimeBefore;

    public EntityLiving(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected float getGravity() {
        return 0.08f;
    }

    @Override
    protected float getDrag() {
        return 0.02f;
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        if (this.namedTag.contains("HealF")) {
            this.namedTag.putFloat("Health", this.namedTag.getShort("HealF"));
            this.namedTag.remove("HealF");
        }

        if (!this.namedTag.contains("Health") || !(this.namedTag.get("Health") instanceof FloatTag)) {
            this.namedTag.putFloat("Health", this.getMaxHealth());
        }

        this.health = this.namedTag.getFloat("Health");
    }

    @Override
    public void setHealth(float health) {
        boolean wasAlive = this.isAlive();
        super.setHealth(health);
        if (this.isAlive() && !wasAlive) {
            EntityEventPacket pk = new EntityEventPacket();
            pk.eid = this.getId();
            pk.event = EntityEventPacket.RESPAWN;
            Server.broadcastPacket(this.hasSpawned.values(), pk);
        }
    }

    @Override
    public void saveNBT() {
        super.saveNBT();
        this.namedTag.putFloat("Health", this.getHealth());
    }

    public boolean hasLineOfSight(Entity entity) {
        // todo
        return true;
    }

    public void collidingWith(Entity ent) { // can override (IronGolem|Bats)
        ent.applyEntityCollision(this);
    }

    @PowerNukkitDifference(info = "Using new method to play sounds", since = "1.4.0.0-PN")
    @Override
    public boolean attack(EntityDamageEvent source) {
        if (this.noDamageTicks > 0 && source.getCause() != DamageCause.SUICIDE) { // ignore it if the cause is SUICIDE
            return false;
        } else if (this.attackTime > 0 && !attackTimeByShieldKb) {
            EntityDamageEvent lastCause = this.getLastDamageCause();
            if (lastCause != null && lastCause.getDamage() >= source.getDamage()) {
                return false;
            }
        }

        if (isBlocking() && this.blockedByShield(source)) {
            return false;
        }

        if (super.attack(source)) {
            if (source instanceof EntityDamageByEntityEvent) {
                Entity damager = ((EntityDamageByEntityEvent) source).getDamager();
                if (source instanceof EntityDamageByChildEntityEvent) {
                    damager = ((EntityDamageByChildEntityEvent) source).getChild();
                }

                // Critical hit
                if (damager instanceof Player && !damager.onGround) {
                    AnimatePacket animate = new AnimatePacket();
                    animate.action = AnimatePacket.Action.CRITICAL_HIT;
                    animate.eid = getId();

                    this.getLevel().addChunkPacket(damager.getChunkX(), damager.getChunkZ(), animate);
                    this.getLevel().addSound(this, Sound.GAME_PLAYER_ATTACK_STRONG);

                    source.setDamage(source.getDamage() * 1.5f);
                }

                if (damager.isOnFire() && !(damager instanceof Player)) {
                    this.setOnFire(2 * this.server.getDifficulty());
                }

                double deltaX = this.x() - damager.x();
                double deltaZ = this.z() - damager.z();
                this.knockBack(
                        damager,
                        source.getDamage(),
                        deltaX,
                        deltaZ,
                        ((EntityDamageByEntityEvent) source).getKnockBack());
            }

            EntityEventPacket pk = new EntityEventPacket();
            pk.eid = this.getId();
            pk.event = this.getHealth() <= 0 ? EntityEventPacket.DEATH_ANIMATION : EntityEventPacket.HURT_ANIMATION;

            Server.broadcastPacket(this.hasSpawned.values(), pk);

            this.attackTime = source.getAttackCooldown();
            this.attackTimeByShieldKb = false;
            this.scheduleUpdate();

            return true;
        } else {
            return false;
        }
    }

    public void knockBack(Entity attacker, double damage, double x, double z) {
        this.knockBack(attacker, damage, x, z, 0.4);
    }

    public void knockBack(Entity attacker, double damage, double x, double z, double base) {
        double f = Math.sqrt(x * x + z * z);
        if (f <= 0) {
            return;
        }

        f = 1 / f;

        Vector3 motion = new Vector3(this.motionX, this.motionY, this.motionZ);

        motion.setX(motion.x() / 2d);
        motion.setY(motion.y() / 2d);
        motion.setZ(motion.z() / 2d);
        motion.setX(motion.x() + x * f * base);
        motion.setY(motion.y() + base);
        motion.setZ(motion.z() + z * f * base);

        if (motion.y() > base) {
            motion.setY(base);
        }

        this.setMotion(motion);
    }

    @Override
    public void kill() {
        if (!this.isAlive()) {
            return;
        }
        super.kill();
        EntityDeathEvent ev = new EntityDeathEvent(this, this.getDrops());
        this.server.getPluginManager().callEvent(ev);

        var manager = this.server.getScoreboardManager();
        // 测试环境中此项会null，所以说需要判空下
        if (manager != null) manager.onEntityDead(this);

        if (this.getLevel().getGameRules().getBoolean(GameRule.DO_ENTITY_DROPS)) {
            for (cn.nukkit.item.Item item : ev.getDrops()) {
                this.getLevel().dropItem(this, item);
            }
        }
    }

    @Override
    public boolean entityBaseTick() {
        return this.entityBaseTick(1);
    }

    @Override
    public boolean entityBaseTick(int tickDiff) {
        boolean isBreathing = !this.isInsideOfWater();

        if (this instanceof Player) {
            if (isBreathing && ((Player) this).getInventory().getHelmet() instanceof ItemTurtleShell) {
                turtleTicks = 200;
            } else if (turtleTicks > 0) {
                isBreathing = true;
                turtleTicks--;
            }

            if ((((Player) this).isCreative() || ((Player) this).isSpectator())) {
                isBreathing = true;
            }
        }

        this.setDataFlag(DATA_FLAGS, DATA_FLAG_BREATHING, isBreathing);

        boolean hasUpdate = super.entityBaseTick(tickDiff);

        if (this.isAlive()) {

            if (this.isInsideOfSolid()) {
                hasUpdate = true;
                this.attack(new EntityDamageEvent(this, DamageCause.SUFFOCATION, 1));
            }

            if (this.isOnLadder() || this.hasEffect(Effect.LEVITATION) || this.hasEffect(Effect.SLOW_FALLING)) {
                this.resetFallDistance();
            }

            if (!this.hasEffect(Effect.WATER_BREATHING)
                    && !this.hasEffect(Effect.CONDUIT_POWER)
                    && this.isInsideOfWater()) {
                if (this instanceof EntitySwimmable
                        || (this instanceof Player
                                && (((Player) this).isCreative() || ((Player) this).isSpectator()))) {
                    this.setAirTicks(400);
                } else {
                    if (turtleTicks == 0 || turtleTicks == 200) {
                        hasUpdate = true;
                        int airTicks = this.getAirTicks() - tickDiff;

                        if (airTicks <= -20) {
                            airTicks = 0;
                            this.attack(new EntityDamageEvent(this, DamageCause.DROWNING, 2));
                        }

                        setAirTicks(airTicks);
                    }
                }
            } else {
                if (this instanceof EntitySwimmable) {
                    hasUpdate = true;
                    int airTicks = getAirTicks() - tickDiff;

                    if (airTicks <= -20) {
                        airTicks = 0;
                        this.attack(new EntityDamageEvent(this, DamageCause.SUFFOCATION, 2));
                    }

                    setAirTicks(airTicks);
                } else {
                    int airTicks = getAirTicks();

                    if (airTicks < 400) {
                        setAirTicks(Math.min(400, airTicks + tickDiff * 5));
                    }
                }
            }
        }

        if (this.attackTime > 0) {
            this.attackTime -= tickDiff;
            if (this.attackTime <= 0) {
                attackTimeByShieldKb = false;
            }
            hasUpdate = true;
        }

        // 吐槽：性能不要了是吧放EntityLiving这里
        // 逻辑迁移到EntityVehicle去了
        //        if (this.riding == null) {
        //            for (Entity entity : level.fastNearbyEntities(this.boundingBox.grow(0.20000000298023224, 0.0D,
        // 0.20000000298023224), this)) {
        //                if (entity instanceof EntityRideable) {
        //                    this.collidingWith(entity);
        //                }
        //            }
        //        }

        // Used to check collisions with magma / cactus blocks
        // Math.round处理在某些条件下 出现x.999999的坐标条件,这里选择四舍五入
        var block = this.getLevel().getTickCachedBlock(getFloorX(), (int) (Math.round(this.y()) - 1), getFloorZ());
        if (block instanceof BlockMagma || block instanceof BlockCactus) block.onEntityCollide(this);

        return hasUpdate;
    }

    public Item[] getDrops() {
        return Item.EMPTY_ARRAY;
    }

    public Block[] getLineOfSight(int maxDistance) {
        return this.getLineOfSight(maxDistance, 0);
    }

    public Block[] getLineOfSight(int maxDistance, int maxLength) {
        return this.getLineOfSight(maxDistance, maxLength, new Integer[] {});
    }

    @Deprecated
    public Block[] getLineOfSight(int maxDistance, int maxLength, Map<Integer, Object> transparent) {
        return this.getLineOfSight(maxDistance, maxLength, transparent.keySet().toArray(Utils.EMPTY_INTEGERS));
    }

    public Block[] getLineOfSight(int maxDistance, int maxLength, Integer[] transparent) {
        if (maxDistance > 120) {
            maxDistance = 120;
        }

        if (transparent != null && transparent.length == 0) {
            transparent = null;
        }

        List<Block> blocks = new ArrayList<>();

        var itr = new TickCachedBlockIterator(
                this.getLevel(), this.getPosition(), this.getDirectionVector(), this.getEyeHeight(), maxDistance);

        while (itr.hasNext()) {
            Block block = itr.next();
            blocks.add(block);

            if (maxLength != 0 && blocks.size() > maxLength) {
                blocks.remove(0);
            }

            int id = block.getId();

            if (transparent == null) {
                if (id != 0) {
                    break;
                }
            } else {
                if (Arrays.binarySearch(transparent, id) < 0) {
                    break;
                }
            }
        }

        return blocks.toArray(Block.EMPTY_ARRAY);
    }

    public Block getTargetBlock(int maxDistance) {
        return getTargetBlock(maxDistance, new Integer[] {});
    }

    @Deprecated
    public Block getTargetBlock(int maxDistance, Map<Integer, Object> transparent) {
        return getTargetBlock(maxDistance, transparent.keySet().toArray(Utils.EMPTY_INTEGERS));
    }

    public Block getTargetBlock(int maxDistance, Integer[] transparent) {
        try {
            Block[] blocks = this.getLineOfSight(maxDistance, 1, transparent);
            Block block = blocks[0];
            if (block != null) {
                if (transparent != null && transparent.length != 0) {
                    if (Arrays.binarySearch(transparent, block.getId()) < 0) {
                        return block;
                    }
                } else {
                    return block;
                }
            }
        } catch (Exception ignored) {

        }

        return null;
    }

    public float getMovementSpeed() {
        return this.movementSpeed;
    }

    /**
     * 设置该有生命实体的移动速度
     * <p>
     * Set the movement speed of this Entity.
     *
     * @param speed 速度大小<br>Speed value
     */
    public void setMovementSpeed(float speed) {
        this.movementSpeed = speed;
    }

    public int getAirTicks() {
        return this.getDataPropertyShort(DATA_AIR);
    }

    public void setAirTicks(int ticks) {
        this.setDataProperty(new ShortEntityData(DATA_AIR, ticks));
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    protected boolean blockedByShield(EntityDamageEvent source) {
        Entity damager = null;
        if (source instanceof EntityDamageByChildEntityEvent) {
            damager = ((EntityDamageByChildEntityEvent) source).getChild();
        } else if (source instanceof EntityDamageByEntityEvent) {
            damager = ((EntityDamageByEntityEvent) source).getDamager();
        }
        if (damager == null || damager instanceof EntityWeather || !this.isBlocking()) {
            return false;
        }

        Vector3 entityPos = damager.getPosition();
        Vector3 direction = this.getDirectionVector();
        Vector3 normalizedVector = this.getPosition().subtract(entityPos).normalize();
        boolean blocked = (normalizedVector.x() * direction.x()) + (normalizedVector.z() * direction.z()) < 0.0;
        boolean knockBack = !(damager instanceof EntityProjectile);
        EntityDamageBlockedEvent event = new EntityDamageBlockedEvent(this, source, knockBack, true);
        if (!blocked || !source.canBeReducedByArmor()) {
            event.setCancelled();
        }

        getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }

        if (event.getKnockBackAttacker() && damager instanceof EntityLiving attacker) {
            double deltaX = attacker.x() - this.x();
            double deltaZ = attacker.z() - this.z();
            attacker.knockBack(this, 0, deltaX, deltaZ);
            attacker.attackTime = 10;
            attacker.attackTimeByShieldKb = true;
        }

        onBlock(damager, source, event.getAnimation());
        return true;
    }

    @PowerNukkitOnly
    @PowerNukkitXDifference(since = "1.19.21-r4", info = "add EntityDamageEvent param to help cal the armor damage")
    protected void onBlock(Entity entity, EntityDamageEvent event, boolean animate) {
        if (animate) {
            getLevel().addSound(this, Sound.ITEM_SHIELD_BLOCK);
        }
    }

    @PowerNukkitOnly
    public boolean isBlocking() {
        return this.getDataFlag(DATA_FLAGS_EXTENDED, DATA_FLAG_BLOCKING);
    }

    @PowerNukkitOnly
    public void setBlocking(boolean value) {
        this.setDataFlag(DATA_FLAGS_EXTENDED, DATA_FLAG_BLOCKING, value);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public boolean isPersistent() {
        return namedTag.containsByte("Persistent") && namedTag.getBoolean("Persistent");
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void setPersistent(boolean persistent) {
        namedTag.putBoolean("Persistent", persistent);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void preAttack(Player player) {
        if (attackTimeByShieldKb) {
            attackTimeBefore = attackTime;
            attackTime = 0;
        }
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void postAttack(Player player) {
        if (attackTimeByShieldKb && attackTime == 0) {
            attackTime = attackTimeBefore;
        }
    }

    @PowerNukkitXOnly
    @Since("1.6.0.0-PNX")
    public int getAttackTime() {
        return attackTime;
    }

    @PowerNukkitXOnly
    @Since("1.6.0.0-PNX")
    public boolean isAttackTimeByShieldKb() {
        return attackTimeByShieldKb;
    }

    @PowerNukkitXOnly
    @Since("1.6.0.0-PNX")
    public int getAttackTimeBefore() {
        return attackTimeBefore;
    }
}
