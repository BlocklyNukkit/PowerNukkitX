package cn.nukkit.block.property;

import cn.nukkit.block.property.enums.*;
import cn.nukkit.block.property.type.BlockPropertyType;
import cn.nukkit.block.property.type.BooleanPropertyType;
import cn.nukkit.block.property.type.EnumPropertyType;
import cn.nukkit.block.property.type.IntPropertyType;
import cn.nukkit.math.BlockFace;

import java.util.List;

/**
 * Automatically generated by {@code org.allaymc.codegen.VanillaBlockPropertyTypeGen} <br>
 * Allay Project <p>
 *
 * @author daoge_cmd
 */
public interface CommonBlockProperties {
    BooleanPropertyType ACTIVE = BooleanPropertyType.of("active", false);

    IntPropertyType AGE_16 = IntPropertyType.of("age", 0, 15, 0);

    IntPropertyType AGE_3 = IntPropertyType.of("age", 0, 2, 0);

    IntPropertyType AGE_4 = IntPropertyType.of("age", 0, 3, 0);

    IntPropertyType AGE_6 = IntPropertyType.of("age", 0, 5, 0);

    BooleanPropertyType AGE_BIT = BooleanPropertyType.of("age_bit", false);

    BooleanPropertyType ALLOW_UNDERWATER_BIT = BooleanPropertyType.of("allow_underwater_bit", false);

    BooleanPropertyType ATTACHED_BIT = BooleanPropertyType.of("attached_bit", false);

    EnumPropertyType<Attachment> ATTACHMENT = EnumPropertyType.of("attachment", Attachment.class, Attachment.values()[0]);

    EnumPropertyType<BambooLeafSize> BAMBOO_LEAF_SIZE = EnumPropertyType.of("bamboo_leaf_size", BambooLeafSize.class, BambooLeafSize.values()[0]);

    EnumPropertyType<BambooStalkThickness> BAMBOO_STALK_THICKNESS = EnumPropertyType.of("bamboo_stalk_thickness", BambooStalkThickness.class, BambooStalkThickness.values()[0]);

    BooleanPropertyType BIG_DRIPLEAF_HEAD = BooleanPropertyType.of("big_dripleaf_head", false);

    EnumPropertyType<BigDripleafTilt> BIG_DRIPLEAF_TILT = EnumPropertyType.of("big_dripleaf_tilt", BigDripleafTilt.class, BigDripleafTilt.values()[0]);

    IntPropertyType BITE_COUNTER = IntPropertyType.of("bite_counter", 0, 6, 0);

    IntPropertyType BLOCK_LIGHT_LEVEL = IntPropertyType.of("block_light_level", 0, 15, 0);

    BooleanPropertyType BLOOM = BooleanPropertyType.of("bloom", false);

    IntPropertyType BOOKS_STORED = IntPropertyType.of("books_stored", 0, 63, 0);

    BooleanPropertyType BREWING_STAND_SLOT_A_BIT = BooleanPropertyType.of("brewing_stand_slot_a_bit", false);

    BooleanPropertyType BREWING_STAND_SLOT_B_BIT = BooleanPropertyType.of("brewing_stand_slot_b_bit", false);

    BooleanPropertyType BREWING_STAND_SLOT_C_BIT = BooleanPropertyType.of("brewing_stand_slot_c_bit", false);

    IntPropertyType BRUSHED_PROGRESS = IntPropertyType.of("brushed_progress", 0, 3, 0);

    BooleanPropertyType BUTTON_PRESSED_BIT = BooleanPropertyType.of("button_pressed_bit", false);

    BooleanPropertyType CAN_SUMMON = BooleanPropertyType.of("can_summon", false);

    IntPropertyType CANDLES = IntPropertyType.of("candles", 0, 3, 0);

    EnumPropertyType<CauldronLiquid> CAULDRON_LIQUID = EnumPropertyType.of("cauldron_liquid", CauldronLiquid.class, CauldronLiquid.values()[0]);

    EnumPropertyType<ChemistryTableType> CHEMISTRY_TABLE_TYPE = EnumPropertyType.of("chemistry_table_type", ChemistryTableType.class, ChemistryTableType.values()[0]);

    EnumPropertyType<ChiselType> CHISEL_TYPE = EnumPropertyType.of("chisel_type", ChiselType.class, ChiselType.values()[0]);

    IntPropertyType CLUSTER_COUNT = IntPropertyType.of("cluster_count", 0, 3, 0);

    EnumPropertyType<Color> COLOR = EnumPropertyType.of("color", Color.class, Color.values()[0]);

    BooleanPropertyType COLOR_BIT = BooleanPropertyType.of("color_bit", false);

    IntPropertyType COMPOSTER_FILL_LEVEL = IntPropertyType.of("composter_fill_level", 0, 8, 0);

    BooleanPropertyType CONDITIONAL_BIT = BooleanPropertyType.of("conditional_bit", false);

    EnumPropertyType<CoralColor> CORAL_COLOR = EnumPropertyType.of("coral_color", CoralColor.class, CoralColor.values()[0]);

    IntPropertyType CORAL_DIRECTION = IntPropertyType.of("coral_direction", 0, 3, 0);

    IntPropertyType CORAL_FAN_DIRECTION = IntPropertyType.of("coral_fan_direction", 0, 1, 0);

    BooleanPropertyType CORAL_HANG_TYPE_BIT = BooleanPropertyType.of("coral_hang_type_bit", false);

    BooleanPropertyType COVERED_BIT = BooleanPropertyType.of("covered_bit", false);

    EnumPropertyType<CrackedState> CRACKED_STATE = EnumPropertyType.of("cracked_state", CrackedState.class, CrackedState.values()[0]);

    BooleanPropertyType CRAFTING = BooleanPropertyType.of("crafting", false);

    EnumPropertyType<Damage> DAMAGE = EnumPropertyType.of("damage", Damage.class, Damage.values()[0]);

    BooleanPropertyType DEAD_BIT = BooleanPropertyType.of("dead_bit", false);

    IntPropertyType DEPRECATED = IntPropertyType.of("deprecated", 0, 3, 0);

    // Horizontal-index based
    IntPropertyType DIRECTION = IntPropertyType.of("direction", 0, 3, 0);

    EnumPropertyType<DirtType> DIRT_TYPE = EnumPropertyType.of("dirt_type", DirtType.class, DirtType.values()[0]);

    BooleanPropertyType DISARMED_BIT = BooleanPropertyType.of("disarmed_bit", false);

    BooleanPropertyType DOOR_HINGE_BIT = BooleanPropertyType.of("door_hinge_bit", false);

    EnumPropertyType<DoublePlantType> DOUBLE_PLANT_TYPE = EnumPropertyType.of("double_plant_type", DoublePlantType.class, DoublePlantType.values()[0]);

    BooleanPropertyType DRAG_DOWN = BooleanPropertyType.of("drag_down", false);

    EnumPropertyType<DripstoneThickness> DRIPSTONE_THICKNESS = EnumPropertyType.of("dripstone_thickness", DripstoneThickness.class, DripstoneThickness.values()[0]);

    BooleanPropertyType END_PORTAL_EYE_BIT = BooleanPropertyType.of("end_portal_eye_bit", false);

    BooleanPropertyType EXPLODE_BIT = BooleanPropertyType.of("explode_bit", false);

    BooleanPropertyType EXTINGUISHED = BooleanPropertyType.of("extinguished", false);

    //Base index for BlockFace
    IntPropertyType FACING_DIRECTION = IntPropertyType.of("facing_direction", 0, 5, 0);

    IntPropertyType FILL_LEVEL = IntPropertyType.of("fill_level", 0, 6, 0);

    EnumPropertyType<FlowerType> FLOWER_TYPE = EnumPropertyType.of("flower_type", FlowerType.class, FlowerType.values()[0]);

    IntPropertyType GROUND_SIGN_DIRECTION = IntPropertyType.of("ground_sign_direction", 0, 15, 0);

    IntPropertyType GROWING_PLANT_AGE = IntPropertyType.of("growing_plant_age", 0, 25, 0);

    IntPropertyType GROWTH = IntPropertyType.of("growth", 0, 7, 0);

    BooleanPropertyType HANGING = BooleanPropertyType.of("hanging", false);

    BooleanPropertyType HEAD_PIECE_BIT = BooleanPropertyType.of("head_piece_bit", false);

    IntPropertyType HEIGHT = IntPropertyType.of("height", 0, 7, 0);

    IntPropertyType HONEY_LEVEL = IntPropertyType.of("honey_level", 0, 5, 0);

    IntPropertyType HUGE_MUSHROOM_BITS = IntPropertyType.of("huge_mushroom_bits", 0, 15, 0);

    BooleanPropertyType IN_WALL_BIT = BooleanPropertyType.of("in_wall_bit", false);

    BooleanPropertyType INFINIBURN_BIT = BooleanPropertyType.of("infiniburn_bit", false);

    BooleanPropertyType ITEM_FRAME_MAP_BIT = BooleanPropertyType.of("item_frame_map_bit", false);

    BooleanPropertyType ITEM_FRAME_PHOTO_BIT = BooleanPropertyType.of("item_frame_photo_bit", false);

    IntPropertyType KELP_AGE = IntPropertyType.of("kelp_age", 0, 25, 0);

    EnumPropertyType<LeverDirection> LEVER_DIRECTION = EnumPropertyType.of("lever_direction", LeverDirection.class, LeverDirection.values()[0]);

    IntPropertyType LIQUID_DEPTH = IntPropertyType.of("liquid_depth", 0, 15, 0);

    BooleanPropertyType LIT = BooleanPropertyType.of("lit", false);

    EnumPropertyType<BlockFace> MINECRAFT_BLOCK_FACE = EnumPropertyType.of("minecraft:block_face", BlockFace.class, BlockFace.DOWN);

    EnumPropertyType<MinecraftCardinalDirection> MINECRAFT_CARDINAL_DIRECTION = EnumPropertyType.of("minecraft:cardinal_direction", MinecraftCardinalDirection.class, MinecraftCardinalDirection.values()[0]);

    EnumPropertyType<BlockFace> MINECRAFT_FACING_DIRECTION = EnumPropertyType.of("minecraft:facing_direction", BlockFace.class, BlockFace.values()[0]);

    EnumPropertyType<MinecraftVerticalHalf> MINECRAFT_VERTICAL_HALF = EnumPropertyType.of("minecraft:vertical_half", MinecraftVerticalHalf.class, MinecraftVerticalHalf.values()[0]);

    IntPropertyType MOISTURIZED_AMOUNT = IntPropertyType.of("moisturized_amount", 0, 7, 0);

    EnumPropertyType<MonsterEggStoneType> MONSTER_EGG_STONE_TYPE = EnumPropertyType.of("monster_egg_stone_type", MonsterEggStoneType.class, MonsterEggStoneType.values()[0]);

    IntPropertyType MULTI_FACE_DIRECTION_BITS = IntPropertyType.of("multi_face_direction_bits", 0, 63, 0);

    EnumPropertyType<NewLeafType> NEW_LEAF_TYPE = EnumPropertyType.of("new_leaf_type", NewLeafType.class, NewLeafType.values()[0]);

    BooleanPropertyType OCCUPIED_BIT = BooleanPropertyType.of("occupied_bit", false);

    EnumPropertyType<OldLeafType> OLD_LEAF_TYPE = EnumPropertyType.of("old_leaf_type", OldLeafType.class, OldLeafType.values()[0]);

    BooleanPropertyType OPEN_BIT = BooleanPropertyType.of("open_bit", false);

    EnumPropertyType<Orientation> ORIENTATION = EnumPropertyType.of("orientation", Orientation.class, Orientation.values()[0]);

    BooleanPropertyType OUTPUT_LIT_BIT = BooleanPropertyType.of("output_lit_bit", false);

    BooleanPropertyType OUTPUT_SUBTRACT_BIT = BooleanPropertyType.of("output_subtract_bit", false);

    BooleanPropertyType PERSISTENT_BIT = BooleanPropertyType.of("persistent_bit", false);

    EnumPropertyType<BlockFace.Axis> PILLAR_AXIS = EnumPropertyType.of("pillar_axis", BlockFace.Axis.class, BlockFace.Axis.values()[0]);

    EnumPropertyType<PortalAxis> PORTAL_AXIS = EnumPropertyType.of("portal_axis", PortalAxis.class, PortalAxis.values()[0]);

    BooleanPropertyType POWERED_BIT = BooleanPropertyType.of("powered_bit", false);

    EnumPropertyType<PrismarineBlockType> PRISMARINE_BLOCK_TYPE = EnumPropertyType.of("prismarine_block_type", PrismarineBlockType.class, PrismarineBlockType.values()[0]);

    IntPropertyType PROPAGULE_STAGE = IntPropertyType.of("propagule_stage", 0, 4, 0);

    BooleanPropertyType RAIL_DATA_BIT = BooleanPropertyType.of("rail_data_bit", false);

    IntPropertyType RAIL_DIRECTION_10 = IntPropertyType.of("rail_direction", 0, 9, 0);

    IntPropertyType RAIL_DIRECTION_6 = IntPropertyType.of("rail_direction", 0, 5, 0);

    IntPropertyType REDSTONE_SIGNAL = IntPropertyType.of("redstone_signal", 0, 15, 0);

    IntPropertyType REPEATER_DELAY = IntPropertyType.of("repeater_delay", 0, 3, 0);

    IntPropertyType RESPAWN_ANCHOR_CHARGE = IntPropertyType.of("respawn_anchor_charge", 0, 4, 0);

    IntPropertyType ROTATION = IntPropertyType.of("rotation", 0, 3, 0);

    EnumPropertyType<SandStoneType> SAND_STONE_TYPE = EnumPropertyType.of("sand_stone_type", SandStoneType.class, SandStoneType.values()[0]);

    EnumPropertyType<SandType> SAND_TYPE = EnumPropertyType.of("sand_type", SandType.class, SandType.values()[0]);

    EnumPropertyType<SaplingType> SAPLING_TYPE = EnumPropertyType.of("sapling_type", SaplingType.class, SaplingType.values()[0]);

    IntPropertyType SCULK_SENSOR_PHASE = IntPropertyType.of("sculk_sensor_phase", 0, 2, 0);

    EnumPropertyType<SeaGrassType> SEA_GRASS_TYPE = EnumPropertyType.of("sea_grass_type", SeaGrassType.class, SeaGrassType.values()[0]);

    EnumPropertyType<SpongeType> SPONGE_TYPE = EnumPropertyType.of("sponge_type", SpongeType.class, SpongeType.values()[0]);

    IntPropertyType STABILITY = IntPropertyType.of("stability", 0, 7, 0);

    BooleanPropertyType STABILITY_CHECK = BooleanPropertyType.of("stability_check", false);

    EnumPropertyType<StoneBrickType> STONE_BRICK_TYPE = EnumPropertyType.of("stone_brick_type", StoneBrickType.class, StoneBrickType.values()[0]);

    EnumPropertyType<StoneSlabType> STONE_SLAB_TYPE = EnumPropertyType.of("stone_slab_type", StoneSlabType.class, StoneSlabType.values()[0]);

    EnumPropertyType<StoneSlabType2> STONE_SLAB_TYPE_2 = EnumPropertyType.of("stone_slab_type_2", StoneSlabType2.class, StoneSlabType2.values()[0]);

    EnumPropertyType<StoneSlabType3> STONE_SLAB_TYPE_3 = EnumPropertyType.of("stone_slab_type_3", StoneSlabType3.class, StoneSlabType3.values()[0]);

    EnumPropertyType<StoneSlabType4> STONE_SLAB_TYPE_4 = EnumPropertyType.of("stone_slab_type_4", StoneSlabType4.class, StoneSlabType4.values()[0]);

    BooleanPropertyType STRIPPED_BIT = BooleanPropertyType.of("stripped_bit", false);

    EnumPropertyType<StructureBlockType> STRUCTURE_BLOCK_TYPE = EnumPropertyType.of("structure_block_type", StructureBlockType.class, StructureBlockType.values()[0]);

    EnumPropertyType<StructureVoidType> STRUCTURE_VOID_TYPE = EnumPropertyType.of("structure_void_type", StructureVoidType.class, StructureVoidType.values()[0]);

    BooleanPropertyType SUSPENDED_BIT = BooleanPropertyType.of("suspended_bit", false);

    EnumPropertyType<TallGrassType> TALL_GRASS_TYPE = EnumPropertyType.of("tall_grass_type", TallGrassType.class, TallGrassType.values()[0]);

    BooleanPropertyType TOGGLE_BIT = BooleanPropertyType.of("toggle_bit", false);

    EnumPropertyType<TorchFacingDirection> TORCH_FACING_DIRECTION = EnumPropertyType.of("torch_facing_direction", TorchFacingDirection.class, TorchFacingDirection.values()[0]);

    BooleanPropertyType TRIGGERED_BIT = BooleanPropertyType.of("triggered_bit", false);

    EnumPropertyType<TurtleEggCount> TURTLE_EGG_COUNT = EnumPropertyType.of("turtle_egg_count", TurtleEggCount.class, TurtleEggCount.values()[0]);

    IntPropertyType TWISTING_VINES_AGE = IntPropertyType.of("twisting_vines_age", 0, 25, 0);

    BooleanPropertyType UPDATE_BIT = BooleanPropertyType.of("update_bit", false);

    BooleanPropertyType UPPER_BLOCK_BIT = BooleanPropertyType.of("upper_block_bit", false);

    BooleanPropertyType UPSIDE_DOWN_BIT = BooleanPropertyType.of("upside_down_bit", false);

    IntPropertyType VINE_DIRECTION_BITS = IntPropertyType.of("vine_direction_bits", 0, 15, 0);

    EnumPropertyType<WallBlockType> WALL_BLOCK_TYPE = EnumPropertyType.of("wall_block_type", WallBlockType.class, WallBlockType.values()[0]);

    EnumPropertyType<WallConnectionTypeEast> WALL_CONNECTION_TYPE_EAST = EnumPropertyType.of("wall_connection_type_east", WallConnectionTypeEast.class, WallConnectionTypeEast.values()[0]);

    EnumPropertyType<WallConnectionTypeNorth> WALL_CONNECTION_TYPE_NORTH = EnumPropertyType.of("wall_connection_type_north", WallConnectionTypeNorth.class, WallConnectionTypeNorth.values()[0]);

    EnumPropertyType<WallConnectionTypeSouth> WALL_CONNECTION_TYPE_SOUTH = EnumPropertyType.of("wall_connection_type_south", WallConnectionTypeSouth.class, WallConnectionTypeSouth.values()[0]);

    EnumPropertyType<WallConnectionTypeWest> WALL_CONNECTION_TYPE_WEST = EnumPropertyType.of("wall_connection_type_west", WallConnectionTypeWest.class, WallConnectionTypeWest.values()[0]);

    BooleanPropertyType WALL_POST_BIT = BooleanPropertyType.of("wall_post_bit", false);

    IntPropertyType WEEPING_VINES_AGE = IntPropertyType.of("weeping_vines_age", 0, 25, 0);

    IntPropertyType WEIRDO_DIRECTION = IntPropertyType.of("weirdo_direction", 0, 3, 0);

    EnumPropertyType<WoodType> WOOD_TYPE = EnumPropertyType.of("wood_type", WoodType.class, WoodType.values()[0]);

    List<BlockPropertyType<?>> VALUES = List.of(ACTIVE, AGE_16, AGE_3, AGE_4, AGE_6, AGE_BIT, ALLOW_UNDERWATER_BIT, ATTACHED_BIT, ATTACHMENT, BAMBOO_LEAF_SIZE, BAMBOO_STALK_THICKNESS, BIG_DRIPLEAF_HEAD, BIG_DRIPLEAF_TILT, BITE_COUNTER, BLOCK_LIGHT_LEVEL, BLOOM, BOOKS_STORED, BREWING_STAND_SLOT_A_BIT, BREWING_STAND_SLOT_B_BIT, BREWING_STAND_SLOT_C_BIT, BRUSHED_PROGRESS, BUTTON_PRESSED_BIT, CAN_SUMMON, CANDLES, CAULDRON_LIQUID, CHEMISTRY_TABLE_TYPE, CHISEL_TYPE, CLUSTER_COUNT, COLOR, COLOR_BIT, COMPOSTER_FILL_LEVEL, CONDITIONAL_BIT, CORAL_COLOR, CORAL_DIRECTION, CORAL_FAN_DIRECTION, CORAL_HANG_TYPE_BIT, COVERED_BIT, CRACKED_STATE, CRAFTING, DAMAGE, DEAD_BIT, DEPRECATED, DIRECTION, DIRT_TYPE, DISARMED_BIT, DOOR_HINGE_BIT, DOUBLE_PLANT_TYPE, DRAG_DOWN, DRIPSTONE_THICKNESS, END_PORTAL_EYE_BIT, EXPLODE_BIT, EXTINGUISHED, FACING_DIRECTION, FILL_LEVEL, FLOWER_TYPE, GROUND_SIGN_DIRECTION, GROWING_PLANT_AGE, GROWTH, HANGING, HEAD_PIECE_BIT, HEIGHT, HONEY_LEVEL, HUGE_MUSHROOM_BITS, IN_WALL_BIT, INFINIBURN_BIT, ITEM_FRAME_MAP_BIT, ITEM_FRAME_PHOTO_BIT, KELP_AGE, LEVER_DIRECTION, LIQUID_DEPTH, LIT, MINECRAFT_BLOCK_FACE, MINECRAFT_CARDINAL_DIRECTION, MINECRAFT_FACING_DIRECTION, MINECRAFT_VERTICAL_HALF, MOISTURIZED_AMOUNT, MONSTER_EGG_STONE_TYPE, MULTI_FACE_DIRECTION_BITS, NEW_LEAF_TYPE, OCCUPIED_BIT, OLD_LEAF_TYPE, OPEN_BIT, ORIENTATION, OUTPUT_LIT_BIT, OUTPUT_SUBTRACT_BIT, PERSISTENT_BIT, PILLAR_AXIS, PORTAL_AXIS, POWERED_BIT, PRISMARINE_BLOCK_TYPE, PROPAGULE_STAGE, RAIL_DATA_BIT, RAIL_DIRECTION_10, RAIL_DIRECTION_6, REDSTONE_SIGNAL, REPEATER_DELAY, RESPAWN_ANCHOR_CHARGE, ROTATION, SAND_STONE_TYPE, SAND_TYPE, SAPLING_TYPE, SCULK_SENSOR_PHASE, SEA_GRASS_TYPE, SPONGE_TYPE, STABILITY, STABILITY_CHECK, STONE_BRICK_TYPE, STONE_SLAB_TYPE, STONE_SLAB_TYPE_2, STONE_SLAB_TYPE_3, STONE_SLAB_TYPE_4, STRIPPED_BIT, STRUCTURE_BLOCK_TYPE, STRUCTURE_VOID_TYPE, SUSPENDED_BIT, TALL_GRASS_TYPE, TOGGLE_BIT, TORCH_FACING_DIRECTION, TRIGGERED_BIT, TURTLE_EGG_COUNT, TWISTING_VINES_AGE, UPDATE_BIT, UPPER_BLOCK_BIT, UPSIDE_DOWN_BIT, VINE_DIRECTION_BITS, WALL_BLOCK_TYPE, WALL_CONNECTION_TYPE_EAST, WALL_CONNECTION_TYPE_NORTH, WALL_CONNECTION_TYPE_SOUTH, WALL_CONNECTION_TYPE_WEST, WALL_POST_BIT, WEEPING_VINES_AGE, WEIRDO_DIRECTION, WOOD_TYPE);

    static List<BlockPropertyType<?>> values() {
        return VALUES;
    }
}
