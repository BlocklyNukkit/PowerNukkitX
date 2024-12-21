package cn.nukkit.entity.passive;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockBeehive;
import cn.nukkit.block.BlockFlower;
import cn.nukkit.block.BlockWitherRose;
import cn.nukkit.blockentity.BlockEntityBeehive;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityFlyable;
import cn.nukkit.entity.ai.behavior.Behavior;
import cn.nukkit.entity.ai.behaviorgroup.BehaviorGroup;
import cn.nukkit.entity.ai.behaviorgroup.IBehaviorGroup;
import cn.nukkit.entity.ai.controller.LiftController;
import cn.nukkit.entity.ai.controller.LookController;
import cn.nukkit.entity.ai.controller.SpaceMoveController;
import cn.nukkit.entity.ai.evaluator.MemoryCheckNotEmptyEvaluator;
import cn.nukkit.entity.ai.executor.BeeAttackExecutor;
import cn.nukkit.entity.ai.executor.MoveToTargetExecutor;
import cn.nukkit.entity.ai.executor.SpaceRandomRoamExecutor;
import cn.nukkit.entity.ai.executor.WolfAttackExecutor;
import cn.nukkit.entity.ai.memory.CoreMemoryTypes;
import cn.nukkit.entity.ai.route.finder.impl.SimpleSpaceAStarRouteFinder;
import cn.nukkit.entity.ai.route.posevaluator.FlyingPosEvaluator;
import cn.nukkit.entity.ai.sensor.NearestBlockSensor;
import cn.nukkit.entity.data.EntityFlag;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.Sound;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;

/**
 * @author joserobjr
 */

public class EntityBee extends EntityAnimal implements EntityFlyable {

    @Override
    @NotNull public String getIdentifier() {
        return BEE;
    }

    private boolean hasNectar = false;

    private boolean stayAtFlower = false;

    public EntityBee(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return new BehaviorGroup(
                this.tickSpread,
                Set.of(),
                Set.of(
                        new Behavior(new BeeAttackExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.7f, 33, true, 20),
                                new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.ATTACK_TARGET)
                                , 6, 1),
                        new Behavior(new MoveToTargetExecutor(CoreMemoryTypes.NEAREST_BLOCK, 0.22f, true), new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.NEAREST_BLOCK), 4, 1),
                        new Behavior(new SpaceRandomRoamExecutor(0.15f, 12, 100, 20, false, -1, true, 10), (entity -> true), 1, 1)
                ),
                Set.of(
                        new NearestBlockSensor(20, 5, 20)
                ),
                Set.of(new SpaceMoveController(), new LookController(true, true), new LiftController()),
                new SimpleSpaceAStarRouteFinder(new FlyingPosEvaluator(), this),
                this
        );
    }

    @Override
    public float getWidth() {
        if (this.isBaby()) {
            return 0.275f;
        }
        return 0.55f;
    }

    @Override
    public float getHeight() {
        if (this.isBaby()) {
            return 0.25f;
        }
        return 0.5f;
    }

    public boolean hasNectar() {
        return this.hasNectar;
    }

    public void setNectar(boolean hasNectar) {
        this.hasNectar = hasNectar;
    }

    public boolean isAngry() {
        return getMemoryStorage().get(CoreMemoryTypes.IS_ANGRY);
    }

    public void setAngry(boolean angry) {
        getMemoryStorage().put(CoreMemoryTypes.IS_ANGRY, angry);
        setDataFlag(EntityFlag.ANGRY, angry);
    }

    public void setAngry(Entity entity) {
        setAngry(true);
        getMemoryStorage().put(CoreMemoryTypes.ATTACK_TARGET, entity);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if(source.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            if(ticksLived < 10) {
                source.setCancelled();
                return false;
            }
        }
        if(source instanceof EntityDamageByEntityEvent event) {
            for(Entity entity : getLevel().getCollidingEntities(this.getBoundingBox().grow(4, 4, 4))) {
                if(entity instanceof EntityBee bee) {
                    bee.setAngry(event.getDamager());
                }
            }
        }
        return super.attack(source);
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if(currentTick % 20 == 0) {
            getMemoryStorage().put(CoreMemoryTypes.LOOKING_BLOCK, shouldSearchBeehive() ? BlockBeehive.class : BlockFlower.class);
            Class<? extends Block> blockClass = this.getMemoryStorage().get(CoreMemoryTypes.LOOKING_BLOCK);
            if (blockClass.isAssignableFrom(BlockFlower.class)) {
                Arrays.stream(level.getCollisionBlocks(getBoundingBox().grow(1.5, 1.5, 1.5), false, true))
                        .filter(block -> block instanceof BlockFlower)
                        .findAny().ifPresent(flower -> {
                            if(flower instanceof BlockWitherRose) {
                                this.kill();
                            } else if(stayAtFlower) {
                                this.setNectar(true);
                                this.getLevel().addSound(this, Sound.MOB_BEE_POLLINATE);
                            }
                            stayAtFlower = !stayAtFlower;
                        });
            } else if(blockClass.isAssignableFrom(BlockBeehive.class)) {
                Arrays.stream(level.getCollisionBlocks(getBoundingBox().grow(1.5, 1.5, 1.5), false, true))
                        .filter(block -> {
                            if(block instanceof BlockBeehive hive) {
                                BlockEntityBeehive hiveEntity = hive.getOrCreateBlockEntity();
                                return !hiveEntity.isHoneyFull() && hiveEntity.getOccupantsCount() < 3;
                            }
                            return false;
                        })
                        .findAny().ifPresent(block -> {
                            BlockEntityBeehive hiveEntity = ((BlockBeehive) block).getOrCreateBlockEntity();
                            hiveEntity.addOccupant(this);
                        });
            }
        }
        return super.onUpdate(currentTick);
    }

    @Override
    protected void initEntity() {
        this.setMaxHealth(10);
        super.initEntity();
    }

    public void nectarDelivered(BlockEntityBeehive blockEntityBeehive) {
        this.setNectar(false);
    }

    public void leftBeehive(BlockEntityBeehive blockEntityBeehive) {

    }

    public boolean shouldSearchBeehive() {
        return hasNectar() || getLevel().isRaining() || !getLevel().isDay();
    }

    @Override
    public void saveNBT() {
        super.saveNBT();
        super.namedTag.putBoolean("hasNectar", hasNectar);
    }

    @Override
    public String getOriginalName() {
        return "Bee";
    }
}
