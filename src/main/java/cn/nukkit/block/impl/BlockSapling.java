package cn.nukkit.block.impl;

import cn.nukkit.api.DeprecationDetails;
import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockCrops;
import cn.nukkit.block.BlockFlowable;
import cn.nukkit.block.BlockID;
import cn.nukkit.block.property.ArrayBlockProperty;
import cn.nukkit.block.property.BlockProperties;
import cn.nukkit.block.property.BlockProperty;
import cn.nukkit.block.property.BooleanBlockProperty;
import cn.nukkit.block.property.value.WoodType;
import cn.nukkit.event.level.StructureGrowEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.Level;
import cn.nukkit.level.ListChunkManager;
import cn.nukkit.level.generator.object.BasicGenerator;
import cn.nukkit.level.generator.object.tree.*;
import cn.nukkit.level.particle.BoneMealParticle;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector2;
import cn.nukkit.math.Vector3;
import cn.nukkit.player.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;

/**
 * @author Angelic47 (Nukkit Project)
 */
public class BlockSapling extends BlockFlowable implements BlockFlowerPot.FlowerPotBlock {
    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public static final BlockProperty<WoodType> SAPLING_TYPE =
            new ArrayBlockProperty<>("sapling_type", true, WoodType.class);

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public static final BooleanBlockProperty AGED = new BooleanBlockProperty("age_bit", false);

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public static final BlockProperties PROPERTIES = new BlockProperties(SAPLING_TYPE, AGED);

    @Deprecated
    @DeprecationDetails(
            since = "1.4.0.0-PN",
            replaceWith = "WoodType.OAK",
            by = "PowerNukkit",
            reason = "Use the new BlockProperty system instead")
    public static final int OAK = 0;

    @Deprecated
    @DeprecationDetails(
            since = "1.4.0.0-PN",
            replaceWith = "WoodType.SPRUCE",
            by = "PowerNukkit",
            reason = "Use the new BlockProperty system instead")
    public static final int SPRUCE = 1;

    @Deprecated
    @DeprecationDetails(
            since = "1.4.0.0-PN",
            replaceWith = "WoodType.BIRCH",
            by = "PowerNukkit",
            reason = "Use the new BlockProperty system instead")
    public static final int BIRCH = 2;

    @Deprecated
    @DeprecationDetails(
            since = "1.4.0.0-PN",
            by = "PowerNukkit",
            replaceWith =
                    "ObjectTree.growTree(ChunkManager level, int x, int y, int z, NukkitRandom random, WoodType.BIRCH, true)",
            reason = "Shouldn't even be here")
    public static final int BIRCH_TALL = 8 | BIRCH;

    @Deprecated
    @DeprecationDetails(
            since = "1.4.0.0-PN",
            replaceWith = "WoodType.JUNGLE",
            by = "PowerNukkit",
            reason = "Use the new BlockProperty system instead")
    public static final int JUNGLE = 3;

    @Deprecated
    @DeprecationDetails(
            since = "1.4.0.0-PN",
            replaceWith = "WoodType.ACACIA",
            by = "PowerNukkit",
            reason = "Use the new BlockProperty system instead")
    public static final int ACACIA = 4;

    @Deprecated
    @DeprecationDetails(
            since = "1.4.0.0-PN",
            replaceWith = "WoodType.DARK_OAK",
            by = "PowerNukkit",
            reason = "Use the new BlockProperty system instead")
    public static final int DARK_OAK = 5;

    public BlockSapling() {
        this(0);
    }

    public BlockSapling(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return SAPLING;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @NotNull @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public WoodType getWoodType() {
        return getPropertyValue(SAPLING_TYPE);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void setWoodType(WoodType woodType) {
        setPropertyValue(SAPLING_TYPE, woodType);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public boolean isAged() {
        return getBooleanValue(AGED);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void setAged(boolean aged) {
        setBooleanValue(AGED, aged);
    }

    @Override
    public String getName() {
        return getWoodType().getEnglishName() + " Sapling";
    }

    @Override
    public boolean place(
            @NotNull Item item,
            @NotNull Block block,
            @NotNull Block target,
            @NotNull BlockFace face,
            double fx,
            double fy,
            double fz,
            Player player) {
        if (BlockFlower.isSupportValid(down())) {
            this.getLevel().setBlock(block, this, true, true);
            return true;
        }

        return false;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(@NotNull Item item, Player player) {
        if (item.isFertilizer()) { // BoneMeal
            if (player != null && !player.isCreative()) {
                item.count--;
            }

            this.getLevel().addParticle(new BoneMealParticle(this));
            if (ThreadLocalRandom.current().nextFloat() >= 0.45) {
                return true;
            }

            this.grow();

            return true;
        }
        return false;
    }

    @PowerNukkitDifference(since = "1.4.0.0-PN", info = "Will break on block update if the supporting block is invalid")
    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (!BlockFlower.isSupportValid(down())) {
                this.getLevel().useBreakOn(this);
                return Level.BLOCK_UPDATE_NORMAL;
            }
        } else if (type == Level.BLOCK_UPDATE_RANDOM) { // Growth
            if (ThreadLocalRandom.current().nextInt(1, 8) == 1
                    && getLevel().getFullLight(add(0, 1, 0)) >= BlockCrops.MINIMUM_LIGHT_LEVEL) {
                if (isAged()) {
                    this.grow();
                } else {
                    setAged(true);
                    this.getLevel().setBlock(this, this, true);
                    return Level.BLOCK_UPDATE_RANDOM;
                }
            } else {
                return Level.BLOCK_UPDATE_RANDOM;
            }
        }
        return Level.BLOCK_UPDATE_NORMAL;
    }

    private void grow() {
        BasicGenerator generator = null;
        boolean bigTree = false;

        Vector3 vector3 = new Vector3(this.x(), this.y() - 1, this.z());

        switch (getWoodType()) {
            case JUNGLE:
                Vector2 vector2;
                if ((vector2 = this.findSaplings(WoodType.JUNGLE)) != null) {
                    vector3 = this.add(vector2.getFloorX(), 0, vector2.getFloorY());
                    generator = new ObjectJungleBigTree(
                            10,
                            20,
                            Block.get(BlockID.LOG, BlockWood.JUNGLE),
                            Block.get(BlockID.LEAVES, BlockLeaves.JUNGLE));
                    bigTree = true;
                }

                if (!bigTree) {
                    generator = new NewJungleTree(4, 7);
                    vector3 = this.add(0, 0, 0);
                }
                break;
            case ACACIA:
                generator = new ObjectSavannaTree();
                vector3 = this.add(0, 0, 0);
                break;
            case DARK_OAK:
                if ((vector2 = this.findSaplings(WoodType.DARK_OAK)) != null) {
                    vector3 = this.add(vector2.getFloorX(), 0, vector2.getFloorY());
                    generator = new ObjectDarkOakTree();
                    bigTree = true;
                }

                if (!bigTree) {
                    return;
                }
                break;
            case SPRUCE:
                if ((vector2 = this.findSaplings(WoodType.SPRUCE)) != null) {
                    vector3 = this.add(vector2.getFloorX(), 0, vector2.getFloorY());
                    generator = new HugeTreesGenerator(0, 0, null, null) {
                        @Override
                        public boolean generate(ChunkManager level, NukkitRandom rand, Vector3 position) {
                            var object = new ObjectBigSpruceTree(0.75f, 4);
                            object.setRandomTreeHeight(rand);
                            if (!this.ensureGrowable(level, rand, position, object.getTreeHeight())) {
                                return false;
                            }
                            object.placeObject(
                                    level, position.getFloorX(), position.getFloorY(), position.getFloorZ(), rand);
                            return true;
                        }
                    };
                    bigTree = true;
                }

                if (bigTree) {
                    break;
                }
            default:
                ListChunkManager chunkManager = new ListChunkManager(this.getLevel());
                ObjectTree.growTree(
                        chunkManager,
                        this.getFloorX(),
                        this.getFloorY(),
                        this.getFloorZ(),
                        new NukkitRandom(),
                        getWoodType(),
                        false);
                StructureGrowEvent event = new StructureGrowEvent(this, chunkManager.getBlocks());
                event.call();
                if (event.isCancelled()) {
                    return;
                }
                if (this.getLevel().getBlock(vector3).getId() == BlockID.DIRT_WITH_ROOTS) {
                    this.getLevel().setBlock(vector3, Block.get(BlockID.DIRT));
                }
                for (Block block : event.getBlockList()) {
                    this.getLevel().setBlock(block, block);
                }
                return;
        }

        if (bigTree) {
            this.getLevel().setBlock(vector3, get(AIR), true, false);
            this.getLevel().setBlock(vector3.add(1, 0, 0), get(AIR), true, false);
            this.getLevel().setBlock(vector3.add(0, 0, 1), get(AIR), true, false);
            this.getLevel().setBlock(vector3.add(1, 0, 1), get(AIR), true, false);
        } else {
            this.getLevel().setBlock(this, get(AIR), true, false);
        }

        ListChunkManager chunkManager = new ListChunkManager(this.getLevel());
        boolean success = generator.generate(chunkManager, new NukkitRandom(), vector3);
        StructureGrowEvent event = new StructureGrowEvent(this, chunkManager.getBlocks());
        event.call();
        if (event.isCancelled() || !success) {
            if (bigTree) {
                this.getLevel().setBlock(vector3, this, true, false);
                this.getLevel().setBlock(vector3.add(1, 0, 0), this, true, false);
                this.getLevel().setBlock(vector3.add(0, 0, 1), this, true, false);
                this.getLevel().setBlock(vector3.add(1, 0, 1), this, true, false);
            } else {
                this.getLevel().setBlock(this, this, true, false);
            }
            return;
        }

        if (this.getLevel().getBlock(vector3).getId() == BlockID.DIRT_WITH_ROOTS) {
            this.getLevel().setBlock(vector3, Block.get(BlockID.DIRT));
        }
        for (Block block : event.getBlockList()) {
            this.getLevel().setBlock(block, block);
        }
    }

    private Vector2 findSaplings(WoodType type) {
        List<List<Vector2>> validVectorsList = new ArrayList<>();
        validVectorsList.add(Arrays.asList(new Vector2(0, 0), new Vector2(1, 0), new Vector2(0, 1), new Vector2(1, 1)));
        validVectorsList.add(
                Arrays.asList(new Vector2(0, 0), new Vector2(-1, 0), new Vector2(0, -1), new Vector2(-1, -1)));
        validVectorsList.add(
                Arrays.asList(new Vector2(0, 0), new Vector2(1, 0), new Vector2(0, -1), new Vector2(1, -1)));
        validVectorsList.add(
                Arrays.asList(new Vector2(0, 0), new Vector2(-1, 0), new Vector2(0, 1), new Vector2(-1, 1)));
        for (List<Vector2> validVectors : validVectorsList) {
            boolean correct = true;
            for (Vector2 vector2 : validVectors) {
                if (!this.isSameType(this.add(vector2.x(), 0, vector2.y()), type)) correct = false;
            }
            if (correct) {
                int lowestX = 0;
                int lowestZ = 0;
                for (Vector2 vector2 : validVectors) {
                    if (vector2.getFloorX() < lowestX) lowestX = vector2.getFloorX();
                    if (vector2.getFloorY() < lowestZ) lowestZ = vector2.getFloorY();
                }
                return new Vector2(lowestX, lowestZ);
            }
        }
        return null;
    }

    @Deprecated
    @DeprecationDetails(
            since = "1.4.0.0-PN",
            by = "PowerNukkit",
            reason = "Checking magic value directly is depreacated",
            replaceWith = "isSameType(Vector3,WoodType)")
    public boolean isSameType(Vector3 pos, int type) {
        Block block = this.getLevel().getBlock(pos);
        return block.getId() == this.getId() && (block.getDamage() & 0x07) == (type & 0x07);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public boolean isSameType(Vector3 pos, WoodType type) {
        Block block = this.getLevel().getBlock(pos);
        return block.getId() == this.getId() && ((BlockSapling) block).getWoodType() == type;
    }

    @Override
    public boolean isFertilizable() {
        return true;
    }
}