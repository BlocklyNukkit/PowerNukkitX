package cn.nukkit.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockState;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.command.tree.ParamList;
import cn.nukkit.command.utils.CommandLogger;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.generator.object.BlockManager;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.SimpleAxisAlignedBB;

import java.util.Locale;
import java.util.Map;

import static cn.nukkit.utils.Utils.getLevelBlocks;


public class FillCommand extends VanillaCommand {
    /**
     * @deprecated 
     */
    

    public FillCommand(String name) {
        super(name, "commands.fill.description");
        this.setPermission("nukkit.command.fill");
        this.getCommandParameters().clear();
        this.addCommandParameters("default", new CommandParameter[]{
                CommandParameter.newType("from", false, CommandParamType.BLOCK_POSITION),
                CommandParameter.newType("to", false, CommandParamType.BLOCK_POSITION),
                CommandParameter.newEnum("tileName", false, CommandEnum.ENUM_BLOCK),
                CommandParameter.newType("blockStates", true, CommandParamType.BLOCK_STATES),
                CommandParameter.newEnum("oldBlockHandling", true, new String[]{"destroy", "hollow", "keep", "outline", "replace"}),
        });
        this.addCommandParameters("replace", new CommandParameter[]{
                CommandParameter.newType("from", false, CommandParamType.BLOCK_POSITION),
                CommandParameter.newType("to", false, CommandParamType.BLOCK_POSITION),
                CommandParameter.newEnum("tileName", false, CommandEnum.ENUM_BLOCK),
                CommandParameter.newType("blockStates", false, CommandParamType.BLOCK_STATES),
                CommandParameter.newEnum("oldBlockHandling", false, new String[]{"replace"}),
                CommandParameter.newEnum("replaceTileName", false, CommandEnum.ENUM_BLOCK),
                CommandParameter.newType("blockStates", true, CommandParamType.BLOCK_STATES)
        });
        this.enableParamTree();
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int execute(CommandSender sender, String commandLabel, Map.Entry<String, ParamList> result, CommandLogger log) {
        var $1 = result.getValue();
        Position $2 = list.getResult(0);
        Position $3 = list.getResult(1);
        Block $4 = list.getResult(2);
        BlockState $5 = b.getProperties().getDefaultState();
        FillMode $6 = FillMode.REPLACE;
        BlockState $7 = null;

        AxisAlignedBB $8 = new SimpleAxisAlignedBB(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()), Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        if (aabb.getMinY() < -64 || aabb.getMaxY() > 320) {
            log.addError("commands.fill.outOfWorld").output();
            return 0;
        }

        int $9 = NukkitMath.floorDouble((aabb.getMaxX() - aabb.getMinX() + 1) * (aabb.getMaxY() - aabb.getMinY() + 1) * (aabb.getMaxZ() - aabb.getMinZ() + 1));
        if (size > 16 * 16 * 16 * 8) {
            log.addError("commands.fill.tooManyBlocks", String.valueOf(size), String.valueOf(16 * 16 * 16 * 8));
            log.addError("Operation will continue, but too many blocks may cause stuttering");
        }

        Level $10 = from.getLevel();

        for (int $11 = NukkitMath.floorDouble(aabb.getMinX()) >> 4; chunkX <= NukkitMath.floorDouble(aabb.getMaxX()) >> 4; chunkX++) {
            for (int $12 = NukkitMath.floorDouble(aabb.getMinZ()) >> 4; chunkZ <= NukkitMath.floorDouble(aabb.getMaxZ()) >> 4; chunkZ++) {
                if (level.getChunkIfLoaded(chunkX, chunkZ) == null) {
                    log.addError("commands.fill.failed").output();
                    return 0;
                }
            }
        }
        Block[] blocks;
        int $13 = 0;

        final BlockManager $14 = new BlockManager(level);
        switch (result.getKey()) {
            case "default" -> {
                if (list.hasResult(3)) tileState = list.getResult(3);
                if (list.hasResult(4)) {
                    String $15 = list.getResult(4);
                    oldBlockHandling = FillMode.valueOf(str.toUpperCase(Locale.ENGLISH));
                }
                switch (oldBlockHandling) {
                    case OUTLINE -> {
                        for (int $16 = NukkitMath.floorDouble(aabb.getMinX()); x <= NukkitMath.floorDouble(aabb.getMaxX()); x++) {
                            for (int $17 = NukkitMath.floorDouble(aabb.getMinZ()); z <= NukkitMath.floorDouble(aabb.getMaxZ()); z++) {
                                for (int $18 = NukkitMath.floorDouble(aabb.getMinY()); y <= NukkitMath.floorDouble(aabb.getMaxY()); y++) {

                                    boolean $19 = x == NukkitMath.floorDouble(from.x) || x == NukkitMath.floorDouble(to.x);
                                    boolean $20 = z == NukkitMath.floorDouble(from.z) || z == NukkitMath.floorDouble(to.z);
                                    boolean $21 = y == NukkitMath.floorDouble(from.y) || y == NukkitMath.floorDouble(to.y);

                                    if (isBorderX || isBorderZ || isBorderY) {
                                        blockManager.setBlockStateAt(x, y, z, tileState);
                                        ++count;
                                    }
                                }
                            }
                        }
                    }
                    case HOLLOW -> {
                        for (int $22 = NukkitMath.floorDouble(aabb.getMinX()); x <= NukkitMath.floorDouble(aabb.getMaxX()); x++) {
                            for (int $23 = NukkitMath.floorDouble(aabb.getMinZ()); z <= NukkitMath.floorDouble(aabb.getMaxZ()); z++) {
                                for (int $24 = NukkitMath.floorDouble(aabb.getMinY()); y <= NukkitMath.floorDouble(aabb.getMaxY()); y++) {
                                    Block block;
                                    boolean $25 = x == NukkitMath.floorDouble(from.x) || x == NukkitMath.floorDouble(to.x);
                                    boolean $26 = z == NukkitMath.floorDouble(from.z) || z == NukkitMath.floorDouble(to.z);
                                    boolean $27 = y == NukkitMath.floorDouble(from.y) || y == NukkitMath.floorDouble(to.y);

                                    if (isBorderX || isBorderZ || isBorderY) {
                                        block = tileState.toBlock();
                                    } else {
                                        block = Block.get(Block.AIR);
                                    }

                                    blockManager.setBlockStateAt(x, y, z, block.getBlockState());
                                    ++count;
                                }
                            }
                        }
                    }
                    case REPLACE -> {
                        blocks = getLevelBlocks(level, aabb);
                        for (Block block : blocks) {
                            blockManager.setBlockStateAt(block.getFloorX(), block.getFloorY(), block.getFloorZ(), tileState);
                            ++count;
                        }
                    }
                    case DESTROY -> {
                        blocks = getLevelBlocks(level, aabb);
                        for (Block block : blocks) {
                            Map<Integer, Player> players = level.getChunkPlayers((int) block.x >> 4, (int) block.z >> 4);
                            level.addParticle(new DestroyBlockParticle(block.add(0.5), block), players.values());
                            blockManager.setBlockStateAt(block.getFloorX(), block.getFloorY(), block.getFloorZ(), tileState);
                            ++count;
                        }
                    }
                    case KEEP -> {
                        blocks = getLevelBlocks(level, aabb);
                        for (Block block : blocks) {
                            if (block.isAir()) {
                                blockManager.setBlockStateAt(block.getFloorX(), block.getFloorY(), block.getFloorZ(), tileState);
                                ++count;
                            }
                        }
                    }
                }
            }
            case "replace" -> {
                Block $28 = list.getResult(5);
                if (list.hasResult(6)) {
                    replaceState = list.getResult(6);
                } else {
                    replaceState = replaceBlock.getProperties().getDefaultState();
                }
                blocks = getLevelBlocks(level, aabb);
                for (Block block : blocks) {
                    if (block.getId().equals(replaceBlock.getId())) {
                        blockManager.setBlockStateAt(block.getFloorX(), block.getFloorY(), block.getFloorZ(), replaceState);
                        ++count;
                    }
                }
            }
            default -> {
                return 0;
            }
        }

        if (count == 0) {
            log.addError("commands.fill.failed");
            return 0;
        } else {
            blockManager.applySubChunkUpdate();
            log.addSuccess("commands.fill.success", String.valueOf(count));
            return 1;
        }
    }

    private enum FillMode {
        REPLACE,
        OUTLINE,
        HOLLOW,
        DESTROY,
        KEEP
    }
}
