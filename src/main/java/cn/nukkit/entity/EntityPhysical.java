package cn.nukkit.entity;

import cn.nukkit.Player;
import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.BlockLava;
import cn.nukkit.block.BlockLiquid;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;

import java.util.List;

@PowerNukkitXOnly
@Since("1.6.0.0-PNX")
public abstract class EntityPhysical extends EntityCreature implements EntityAsyncPrepare {
    /**
     * 移动精度阈值，绝对值小于此阈值的移动被视为没有移动
     */
    public static final float PRECISION = 0.00001f;
    /**
     * 实体自由落体运动的时间
     */
    protected int fallingTick = 0;
    /**
     * 提供实时最新碰撞箱位置
     */
    protected final AxisAlignedBB offsetBoundingBox = new SimpleAxisAlignedBB(0, 0, 0, 0, 0, 0);

    private boolean needsCollisionDamage = false;
    public boolean isFloating = false;

    protected boolean needsRecalcMovement = true;
    protected final Vector3 previousCollideMotion = new Vector3();
    protected final Vector3 previousCurrentMotion = new Vector3();

    public EntityPhysical(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public void asyncPrepare(int currentTick) {
        // 计算是否需要重新计算高开销实体运动
        this.needsRecalcMovement = this.level.tickRateOptDelay == 1 || (currentTick & (this.level.tickRateOptDelay - 1)) == 0;
        // 重新计算绝对位置碰撞箱
        this.calculateOffsetBoundingBox();
        // 处理运动
        handleGravity();
        if (needsRecalcMovement) {
            handleCollideMovement(currentTick);
        }
        addTmpMoveMotionXZ(previousCollideMotion);
        handleFrictionMovement();
        handleFloatingMovement();
    }

    @Override
    public boolean onUpdate(int currentTick) {
        // 记录最大高度，用于计算坠落伤害
        if (!this.onGround && this.y > highestPosition) {
            this.highestPosition = this.y;
        }
        // 添加挤压伤害
        if (needsCollisionDamage) {
            this.attack(new EntityDamageEvent(this, EntityDamageEvent.DamageCause.COLLIDE, 3));
        }
        return super.onUpdate(currentTick);
    }

    @Override
    public boolean canBeMovedByCurrents() {
        return true;
    }

    @Override
    public void updateMovement() {
        // 检测自由落体时间
        if (!this.onGround && this.y < this.highestPosition) {
            this.fallingTick++;
        }
        // 计算相对运动
        double diffPosition = (this.x - this.lastX) * (this.x - this.lastX) + (this.y - this.lastY) * (this.y - this.lastY) + (this.z - this.lastZ) * (this.z - this.lastZ);
        double diffRotation = (this.yaw - this.lastYaw) * (this.yaw - this.lastYaw) + (this.pitch - this.lastPitch) * (this.pitch - this.lastPitch);
        double diffMotion = (this.motionX - this.lastMotionX) * (this.motionX - this.lastMotionX) + (this.motionY - this.lastMotionY) * (this.motionY - this.lastMotionY) + (this.motionZ - this.lastMotionZ) * (this.motionZ - this.lastMotionZ);
        if (diffPosition > 0.0001 || diffRotation > 1.0) { //0.2 ** 2, 1.5 ** 2
            this.lastX = this.x;
            this.lastY = this.y;
            this.lastZ = this.z;
            this.lastPitch = this.pitch;
            this.lastYaw = this.yaw;
            this.lastHeadYaw = this.headYaw;
            this.addMovement(this.x, this.y + this.getBaseOffset(), this.z, this.yaw, this.pitch, this.headYaw);
            this.positionChanged = true;
        } else {
            this.positionChanged = false;
        }
        if (diffMotion > 0.0025 || (diffMotion > 0.0001 && this.getMotion().lengthSquared() <= 0.0001)) { //0.05 ** 2
            this.lastMotionX = this.motionX;
            this.lastMotionY = this.motionY;
            this.lastMotionZ = this.motionZ;
            this.addMotion(this.motionX, this.motionY, this.motionZ);
        }
        this.move(this.motionX, this.motionY, this.motionZ);
    }

    public final void addTmpMoveMotion(Vector3 tmpMotion) {
        this.motionX += tmpMotion.x;
        this.motionY += tmpMotion.y;
        this.motionZ += tmpMotion.z;
    }

    public final void addTmpMoveMotionXZ(Vector3 tmpMotion) {
        this.motionX += tmpMotion.x;
        this.motionZ += tmpMotion.z;
    }

    protected void handleGravity() {
        if (this.hasWaterAt(getFootHeight())) {
            resetFallDistance();
        } else {
            this.motionY -= this.getGravity();
        }
    }

    protected void handleFrictionMovement() {
        // 减少移动向量（计算摩擦系数，在冰上滑得更远）
        final double friction = this.getLevel().getBlock(this.temporalVector.setComponents((int) Math.floor(this.x), (int) Math.floor(this.y - 1), (int) Math.floor(this.z) - 1)).getFrictionFactor();
        final double reduce = getMovementSpeed() * (1 - friction * 0.85) * 0.43;
        if (Math.abs(this.motionZ) < PRECISION && Math.abs(this.motionX) < PRECISION) {
            return;
        }
        final double angle = StrictMath.atan2(this.motionZ, this.motionX);
        double tmp = (StrictMath.cos(angle) * reduce);
        if (this.motionX > PRECISION) {
            this.motionX -= tmp;
            if (this.motionX < PRECISION) {
                this.motionX = 0;
            }
        } else if (this.motionX < -PRECISION) {
            this.motionX -= tmp;
            if (this.motionX > -PRECISION) {
                this.motionX = 0;
            }
        } else {
            this.motionX = 0;
        }
        tmp = (StrictMath.sin(angle) * reduce);
        if (this.motionZ > PRECISION) {
            this.motionZ -= tmp;
            if (this.motionZ < PRECISION) {
                this.motionZ = 0;
            }
        } else if (this.motionZ < -PRECISION) {
            this.motionZ -= tmp;
            if (this.motionZ > -PRECISION) {
                this.motionZ = 0;
            }
        } else {
            this.motionZ = 0;
        }
    }

    /**
     * 默认使用nk内置实现，这只是个后备算法
     */
    protected void handleLiquidMovement() {
        final var tmp = new Vector3();
        BlockLiquid blockLiquid = null;
        for (final var each : this.getLevel().getCollisionBlocks(getOffsetBoundingBox(),
                false, true, block -> block instanceof BlockLiquid)) {
            blockLiquid = (BlockLiquid) each;
            final var flowVector = blockLiquid.getFlowVector();
            tmp.x += flowVector.x;
            tmp.y += flowVector.y;
            tmp.z += flowVector.z;
        }
        if (blockLiquid != null) {
            final var len = tmp.length();
            final var speed = getLiquidMovementSpeed(blockLiquid) * 0.3f;
            if (len > 0) {
                this.motionX += tmp.x / len * speed;
                this.motionY += tmp.y / len * speed;
                this.motionZ += tmp.z / len * speed;
            }
        }
    }

    protected void addPreviousLiquidMovement() {
        addTmpMoveMotion(previousCurrentMotion);
    }

    protected void handleFloatingMovement() {
        if (this.isTouchingWater()) {
            if (this.motionY < -this.getGravity() && this.hasWaterAt(getFootHeight())) {
                this.motionY += this.getGravity() * 0.3;
            }
            this.motionY += this.getGravity() * 0.075;
            this.isFloating = true;
        } else {
            this.isFloating = false;
        }
    }

    protected void handleCollideMovement(int currentTick) {
        var selfAABB = getOffsetBoundingBox().getOffsetBoundingBox(this.motionX, this.motionY, this.motionZ);
        var collidingEntities = this.level.fastCollidingEntities(selfAABB, this);
        var size = collidingEntities.size();
        if (size == 0) {
            this.previousCollideMotion.setX(0);
            this.previousCollideMotion.setZ(0);
            return;
        } else {
            if (!onCollide(currentTick, collidingEntities)) {
                return;
            }
        }
        var dxPositives = new DoubleArrayList(size);
        var dxNegatives = new DoubleArrayList(size);
        var dzPositives = new DoubleArrayList(size);
        var dzNegatives = new DoubleArrayList(size);

        var stream = collidingEntities.stream();
        if (size > 4) {
            stream = stream.parallel();
        }
        stream.forEach(each -> {
            AxisAlignedBB targetAABB;
            if (each instanceof EntityPhysical entityPhysical) {
                targetAABB = entityPhysical.getOffsetBoundingBox();
            } else if (each instanceof Player player) {
                targetAABB = player.reCalcOffsetBoundingBox();
            } else {
                return;
            }
            // 计算碰撞箱
            double centerXWidth = (targetAABB.getMaxX() + targetAABB.getMinX() - selfAABB.getMaxX() - selfAABB.getMinX()) * 0.5;
            double centerZWidth = (targetAABB.getMaxZ() + targetAABB.getMinZ() - selfAABB.getMaxZ() - selfAABB.getMinZ()) * 0.5;
            if (centerXWidth > 0) {
                dxPositives.add((targetAABB.getMaxX() - targetAABB.getMinX()) + (selfAABB.getMaxX() - selfAABB.getMinX()) * 0.5 - centerXWidth);
            } else {
                dxNegatives.add((targetAABB.getMaxX() - targetAABB.getMinX()) + (selfAABB.getMaxX() - selfAABB.getMinX()) * 0.5 + centerXWidth);
            }
            if (centerZWidth > 0) {
                dzPositives.add((targetAABB.getMaxZ() - targetAABB.getMinZ()) + (selfAABB.getMaxZ() - selfAABB.getMinZ()) * 0.5 - centerZWidth);
            } else {
                dzNegatives.add((targetAABB.getMaxZ() - targetAABB.getMinZ()) + (selfAABB.getMaxZ() - selfAABB.getMinZ()) * 0.5 + centerZWidth);
            }
        });
        double resultX = (size > 4 ? dxPositives.doubleParallelStream() : dxPositives.doubleStream()).max().orElse(0) - (size > 4 ? dxNegatives.doubleParallelStream() : dxNegatives.doubleStream()).max().orElse(0);
        double resultZ = (size > 4 ? dzPositives.doubleParallelStream() : dzPositives.doubleStream()).max().orElse(0) - (size > 4 ? dzNegatives.doubleParallelStream() : dzNegatives.doubleStream()).max().orElse(0);
        double len = Math.sqrt(resultX * resultX + resultZ * resultZ);
        this.previousCollideMotion.setX(-(resultX / len * getMovementSpeed() * 0.32));
        this.previousCollideMotion.setZ(-(resultZ / len * getMovementSpeed() * 0.32));
    }

    /**
     * @param collidingEntities 碰撞的实体
     * @return false以拦截实体碰撞运动计算
     */
    protected boolean onCollide(int currentTick, List<Entity> collidingEntities) {
        if (currentTick % 10 == 0) {
            if (collidingEntities.stream().filter(Entity::canCollide).count() > 24) {
                this.needsCollisionDamage = true;
            }
        }
        return true;
    }

    protected final float getLiquidMovementSpeed(BlockLiquid liquid) {
        if (liquid instanceof BlockLava) {
            return 0.02f;
        }
        return 0.05f;
    }

    public float getFootHeight() {
        return getCurrentHeight() / 2 - 0.1f;
    }

    protected void calculateOffsetBoundingBox() {
        final double dx = this.getWidth() * 0.5;
        final double dz = this.getWidth() * 0.5;
        this.offsetBoundingBox.setMinX(this.x - dx);
        this.offsetBoundingBox.setMaxX(this.x + dx);
        this.offsetBoundingBox.setMinY(this.y);
        this.offsetBoundingBox.setMaxY(this.y + this.getHeight());
        this.offsetBoundingBox.setMinZ(this.z - dz);
        this.offsetBoundingBox.setMaxZ(this.z + dz);
    }

    public AxisAlignedBB getOffsetBoundingBox() {
        return this.offsetBoundingBox;
    }

    public void resetFallDistance() {
        this.fallingTick = 0;
        super.resetFallDistance();
    }

    @Override
    public float getGravity() {
        return super.getGravity();
    }

    public int getFallingTick() {
        return this.fallingTick;
    }

    public boolean isFloating() {
        return isFloating;
    }

    public void setFloating(boolean floating) {
        isFloating = floating;
    }

    public boolean ifNeedsRecalcMovement() {
        return needsRecalcMovement;
    }
}
