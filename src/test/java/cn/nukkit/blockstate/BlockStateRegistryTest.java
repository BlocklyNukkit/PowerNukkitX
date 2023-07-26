package cn.nukkit.blockstate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cn.nukkit.block.BlockID;
import cn.nukkit.block.BlockWall;
import cn.nukkit.math.BlockFace;
import java.util.NoSuchElementException;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.powernukkit.tests.junit.jupiter.PowerNukkitExtension;

@ExtendWith(PowerNukkitExtension.class)
class BlockStateRegistryTest {
    @Test
    void getKnownBlockStateIdByRuntimeIdAndViceVersa() {
        BlockWall wall = new BlockWall();
        wall.setWallType(BlockWall.WallType.DIORITE);
        wall.setWallPost(true);
        wall.setConnection(BlockFace.NORTH, BlockWall.WallConnectionType.TALL);
        val runtimeId = wall.getRuntimeId();
        val stateId = wall.getStateId();
        val minimalistStateId = wall.getMinimalistStateId();
        val legacyStateId = wall.getLegacyStateId();
        val unknownStateId = wall.getPersistenceName() + ";unknown=" + wall.getDataStorage();

        assertEquals(stateId, BlockStateRegistry.getKnownBlockStateIdByRuntimeId(runtimeId));
        assertEquals(runtimeId, BlockStateRegistry.getKnownRuntimeIdByBlockStateId(stateId));
        assertEquals(runtimeId, BlockStateRegistry.getKnownRuntimeIdByBlockStateId(minimalistStateId));
        assertEquals(runtimeId, BlockStateRegistry.getKnownRuntimeIdByBlockStateId(legacyStateId));
        assertEquals(runtimeId, BlockStateRegistry.getKnownRuntimeIdByBlockStateId(unknownStateId));
        assertEquals(BlockID.COBBLE_WALL, BlockStateRegistry.getBlockIdByRuntimeId(runtimeId));
    }

    @Test
    void getBlockIdByRuntimeId() {
        assertThrows(NoSuchElementException.class, () -> BlockStateRegistry.getBlockIdByRuntimeId(999999999));
        int runtimeId = BlockStateRegistry.getKnownRuntimeIdByBlockStateId(
                "minecraft:stone_brick_stairs;upside_down_bit=0;weirdo_direction=1");
        assertEquals(109, BlockStateRegistry.getBlockIdByRuntimeId(runtimeId));
    }
}
