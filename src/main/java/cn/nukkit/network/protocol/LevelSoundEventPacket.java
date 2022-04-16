package cn.nukkit.network.protocol;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.math.Vector3f;
import lombok.ToString;

@ToString
public class LevelSoundEventPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.LEVEL_SOUND_EVENT_PACKET;

    public static final int SOUND_ITEM_USE_ON = 0;
    public static final int SOUND_HIT = 1;
    public static final int SOUND_STEP = 2;
    public static final int SOUND_FLY = 3;
    public static final int SOUND_JUMP = 4;
    public static final int SOUND_BREAK = 5;
    public static final int SOUND_PLACE = 6;
    public static final int SOUND_HEAVY_STEP = 7;
    public static final int SOUND_GALLOP = 8;
    public static final int SOUND_FALL = 9;
    public static final int SOUND_AMBIENT = 10;
    public static final int SOUND_AMBIENT_BABY = 11;
    public static final int SOUND_AMBIENT_IN_WATER = 12;
    public static final int SOUND_BREATHE = 13;
    public static final int SOUND_DEATH = 14;
    public static final int SOUND_DEATH_IN_WATER = 15;
    public static final int SOUND_DEATH_TO_ZOMBIE = 16;
    public static final int SOUND_HURT = 17;
    public static final int SOUND_HURT_IN_WATER = 18;
    public static final int SOUND_MAD = 19;
    public static final int SOUND_BOOST = 20;
    public static final int SOUND_BOW = 21;
    public static final int SOUND_SQUISH_BIG = 22;
    public static final int SOUND_SQUISH_SMALL = 23;
    public static final int SOUND_FALL_BIG = 24;
    public static final int SOUND_FALL_SMALL = 25;
    public static final int SOUND_SPLASH = 26;
    public static final int SOUND_FIZZ = 27;
    public static final int SOUND_FLAP = 28;
    public static final int SOUND_SWIM = 29;
    public static final int SOUND_DRINK = 30;
    public static final int SOUND_EAT = 31;
    public static final int SOUND_TAKEOFF = 32;
    public static final int SOUND_SHAKE = 33;
    public static final int SOUND_PLOP = 34;
    public static final int SOUND_LAND = 35;
    public static final int SOUND_SADDLE = 36;
    public static final int SOUND_ARMOR = 37;
    public static final int SOUND_MOB_ARMOR_STAND_PLACE = 38;
    public static final int SOUND_ADD_CHEST = 39;
    public static final int SOUND_THROW = 40;
    public static final int SOUND_ATTACK = 41;
    public static final int SOUND_ATTACK_NODAMAGE = 42;
    public static final int SOUND_ATTACK_STRONG = 43;
    public static final int SOUND_WARN = 44;
    public static final int SOUND_SHEAR = 45;
    public static final int SOUND_MILK = 46;
    public static final int SOUND_THUNDER = 47;
    public static final int SOUND_EXPLODE = 48;
    public static final int SOUND_FIRE = 49;
    public static final int SOUND_IGNITE = 50;
    public static final int SOUND_FUSE = 51;
    public static final int SOUND_STARE = 52;
    public static final int SOUND_SPAWN = 53;
    public static final int SOUND_SHOOT = 54;
    public static final int SOUND_BREAK_BLOCK = 55;
    public static final int SOUND_LAUNCH = 56;
    public static final int SOUND_BLAST = 57;
    public static final int SOUND_LARGE_BLAST = 58;
    public static final int SOUND_TWINKLE = 59;
    public static final int SOUND_REMEDY = 60;
    public static final int SOUND_UNFECT = 61;
    public static final int SOUND_LEVELUP = 62;
    public static final int SOUND_BOW_HIT = 63;
    public static final int SOUND_BULLET_HIT = 64;
    public static final int SOUND_EXTINGUISH_FIRE = 65;
    public static final int SOUND_ITEM_FIZZ = 66;
    public static final int SOUND_CHEST_OPEN = 67;
    public static final int SOUND_CHEST_CLOSED = 68;
    public static final int SOUND_SHULKERBOX_OPEN = 69;
    public static final int SOUND_SHULKERBOX_CLOSED = 70;
    public static final int SOUND_ENDERCHEST_OPEN = 71;
    public static final int SOUND_ENDERCHEST_CLOSED = 72;
    public static final int SOUND_POWER_ON = 73;
    public static final int SOUND_POWER_OFF = 74;
    public static final int SOUND_ATTACH = 75;
    public static final int SOUND_DETACH = 76;
    public static final int SOUND_DENY = 77;
    public static final int SOUND_TRIPOD = 78;
    public static final int SOUND_POP = 79;
    public static final int SOUND_DROP_SLOT = 80;
    public static final int SOUND_NOTE = 81;
    public static final int SOUND_THORNS = 82;
    public static final int SOUND_PISTON_IN = 83;
    public static final int SOUND_PISTON_OUT = 84;
    public static final int SOUND_PORTAL = 85;
    public static final int SOUND_WATER = 86;
    public static final int SOUND_LAVA_POP = 87;
    public static final int SOUND_LAVA = 88;
    public static final int SOUND_BURP = 89;
    public static final int SOUND_BUCKET_FILL_WATER = 90;
    public static final int SOUND_BUCKET_FILL_LAVA = 91;
    public static final int SOUND_BUCKET_EMPTY_WATER = 92;
    public static final int SOUND_BUCKET_EMPTY_LAVA = 93;
    public static final int SOUND_ARMOR_EQUIP_CHAIN = 94;
    public static final int SOUND_ARMOR_EQUIP_DIAMOND = 95;
    public static final int SOUND_ARMOR_EQUIP_GENERIC = 96;
    public static final int SOUND_ARMOR_EQUIP_GOLD = 97;
    public static final int SOUND_ARMOR_EQUIP_IRON = 98;
    public static final int SOUND_ARMOR_EQUIP_LEATHER = 99;
    public static final int SOUND_ARMOR_EQUIP_ELYTRA = 100;
    public static final int SOUND_RECORD_13 = 101;
    public static final int SOUND_RECORD_CAT = 102;
    public static final int SOUND_RECORD_BLOCKS = 103;
    public static final int SOUND_RECORD_CHIRP = 104;
    public static final int SOUND_RECORD_FAR = 105;
    public static final int SOUND_RECORD_MALL = 106;
    public static final int SOUND_RECORD_MELLOHI = 107;
    public static final int SOUND_RECORD_STAL = 108;
    public static final int SOUND_RECORD_STRAD = 109;
    public static final int SOUND_RECORD_WARD = 110;
    public static final int SOUND_RECORD_11 = 111;
    public static final int SOUND_RECORD_WAIT = 112;
    public static final int SOUND_STOP_RECORD = 113; //Not really a sound
    public static final int SOUND_GUARDIAN_FLOP = 114;
    public static final int SOUND_ELDERGUARDIAN_CURSE = 115;
    public static final int SOUND_MOB_WARNING = 116;
    public static final int SOUND_MOB_WARNING_BABY = 117;
    public static final int SOUND_TELEPORT = 118;
    public static final int SOUND_SHULKER_OPEN = 119;
    public static final int SOUND_SHULKER_CLOSE = 120;
    public static final int SOUND_HAGGLE = 121;
    public static final int SOUND_HAGGLE_YES = 122;
    public static final int SOUND_HAGGLE_NO = 123;
    public static final int SOUND_HAGGLE_IDLE = 124;
    public static final int SOUND_CHORUSGROW = 125;
    public static final int SOUND_CHORUSDEATH = 126;
    public static final int SOUND_GLASS = 127;
    public static final int SOUND_POTION_BREWED = 128;
    public static final int SOUND_CAST_SPELL = 129;
    public static final int SOUND_PREPARE_ATTACK = 130;
    public static final int SOUND_PREPARE_SUMMON = 131;
    public static final int SOUND_PREPARE_WOLOLO = 132;
    public static final int SOUND_FANG = 133;
    public static final int SOUND_CHARGE = 134;
    public static final int SOUND_CAMERA_TAKE_PICTURE = 135;
    public static final int SOUND_LEASHKNOT_PLACE = 136;
    public static final int SOUND_LEASHKNOT_BREAK = 137;
    public static final int SOUND_GROWL = 138;
    public static final int SOUND_WHINE = 139;
    public static final int SOUND_PANT = 140;
    public static final int SOUND_PURR = 141;
    public static final int SOUND_PURREOW = 142;
    public static final int SOUND_DEATH_MIN_VOLUME = 143;
    public static final int SOUND_DEATH_MID_VOLUME = 144;
    public static final int SOUND_IMITATE_BLAZE = 145;
    public static final int SOUND_IMITATE_CAVE_SPIDER = 146;
    public static final int SOUND_IMITATE_CREEPER = 147;
    public static final int SOUND_IMITATE_ELDER_GUARDIAN = 148;
    public static final int SOUND_IMITATE_ENDER_DRAGON = 149;
    public static final int SOUND_IMITATE_ENDERMAN = 150;
    public static final int SOUND_IMITATE_ENDERMITE = 151;
    public static final int SOUND_IMITATE_EVOCATION_ILLAGER = 152;
    public static final int SOUND_IMITATE_GHAST = 153;
    public static final int SOUND_IMITATE_HUSK = 154;
    public static final int SOUND_IMITATE_ILLUSION_ILLAGER = 155;
    public static final int SOUND_IMITATE_MAGMA_CUBE = 156;
    public static final int SOUND_IMITATE_POLAR_BEAR = 157;
    public static final int SOUND_IMITATE_SHULKER = 158;
    public static final int SOUND_IMITATE_SILVERFISH = 159;
    public static final int SOUND_IMITATE_SKELETON = 160;
    public static final int SOUND_IMITATE_SLIME = 161;
    public static final int SOUND_IMITATE_SPIDER = 162;
    public static final int SOUND_IMITATE_STRAY = 163;
    public static final int SOUND_IMITATE_VEX = 164;
    public static final int SOUND_IMITATE_VINDICATION_ILLAGER = 165;
    public static final int SOUND_IMITATE_WITCH = 166;
    public static final int SOUND_IMITATE_WITHER = 167;
    public static final int SOUND_IMITATE_WITHER_SKELETON = 168;
    public static final int SOUND_IMITATE_WOLF = 169;
    public static final int SOUND_IMITATE_ZOMBIE = 170;
    public static final int SOUND_IMITATE_ZOMBIE_PIGMAN = 171;
    public static final int SOUND_IMITATE_ZOMBIE_VILLAGER = 172;
    public static final int SOUND_BLOCK_END_PORTAL_FRAME_FILL = 173;
    public static final int SOUND_BLOCK_END_PORTAL_SPAWN = 174;
    public static final int SOUND_RANDOM_ANVIL_USE = 175;
    public static final int SOUND_BOTTLE_DRAGONBREATH = 176;
    public static final int SOUND_PORTAL_TRAVEL = 177;
    public static final int SOUND_ITEM_TRIDENT_HIT = 178;
    public static final int SOUND_ITEM_TRIDENT_RETURN = 179;
    public static final int SOUND_ITEM_TRIDENT_RIPTIDE_1 = 180;
    public static final int SOUND_ITEM_TRIDENT_RIPTIDE_2 = 181;
    public static final int SOUND_ITEM_TRIDENT_RIPTIDE_3 = 182;
    public static final int SOUND_ITEM_TRIDENT_THROW = 183;
    public static final int SOUND_ITEM_TRIDENT_THUNDER = 184;
    public static final int SOUND_ITEM_TRIDENT_HIT_GROUND = 185;
    public static final int SOUND_DEFAULT = 186;
    public static final int SOUND_BLOCK_FLETCHING_TABLE_USE = 187;
    public static final int SOUND_ELEMCONSTRUCT_OPEN = 188;
    public static final int SOUND_ICEBOMB_HIT = 189;
    public static final int SOUND_BALLOONPOP = 190;
    public static final int SOUND_LT_REACTION_ICEBOMB = 191;
    public static final int SOUND_LT_REACTION_BLEACH = 192;
    public static final int SOUND_LT_REACTION_EPASTE = 193;
    public static final int SOUND_LT_REACTION_EPASTE2 = 194;
    public static final int SOUND_LT_REACTION_GLOW_STICK = 195;
    public static final int SOUND_LT_REACTION_GLOW_STICK_2 = 196;
    public static final int SOUND_LT_REACTION_LUMINOL = 197;
    public static final int SOUND_LT_REACTION_SALT = 198;
    public static final int SOUND_LT_REACTION_FERTILIZER = 199;
    public static final int SOUND_LT_REACTION_FIREBALL = 200;
    public static final int SOUND_LT_REACTION_MGSALT = 201;
    public static final int SOUND_LT_REACTION_MISCFIRE = 202;
    public static final int SOUND_LT_REACTION_FIRE = 203;
    public static final int SOUND_LT_REACTION_MISCEXPLOSION = 204;
    public static final int SOUND_LT_REACTION_MISCMYSTICAL = 205;
    public static final int SOUND_LT_REACTION_MISCMYSTICAL2 = 206;
    public static final int SOUND_LT_REACTION_PRODUCT = 207;
    public static final int SOUND_SPARKLER_USE = 208;
    public static final int SOUND_GLOWSTICK_USE = 209;
    public static final int SOUND_SPARKLER_ACTIVE = 210;
    public static final int SOUND_CONVERT_TO_DROWNED = 211;
    public static final int SOUND_BUCKET_FILL_FISH = 212;
    public static final int SOUND_BUCKET_EMPTY_FISH = 213;
    public static final int SOUND_BUBBLE_UP = 214;
    public static final int SOUND_BUBBLE_DOWN = 215;
    public static final int SOUND_BUBBLE_POP = 216;
    public static final int SOUND_BUBBLE_UPINSIDE = 217;
    public static final int SOUND_BUBBLE_DOWNINSIDE = 218;
    public static final int SOUND_HURT_BABY = 219;
    public static final int SOUND_DEATH_BABY = 220;
    public static final int SOUND_STEP_BABY = 221;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BABY_SPAWN = 222;
    public static final int SOUND_BORN = 223;
    public static final int SOUND_BLOCK_TURTLE_EGG_BREAK = 224;
    public static final int SOUND_BLOCK_TURTLE_EGG_CRACK = 225;
    public static final int SOUND_BLOCK_TURTLE_EGG_HATCH = 226;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_TURTLE_LAY_EGG = 227;
    public static final int SOUND_BLOCK_TURTLE_EGG_ATTACK = 228;
    public static final int SOUND_BEACON_ACTIVATE = 229;
    public static final int SOUND_BEACON_AMBIENT = 230;
    public static final int SOUND_BEACON_DEACTIVATE = 231;
    public static final int SOUND_BEACON_POWER = 232;
    public static final int SOUND_CONDUIT_ACTIVATE = 233;
    public static final int SOUND_CONDUIT_AMBIENT = 234;
    public static final int SOUND_CONDUIT_ATTACK = 235;
    public static final int SOUND_CONDUIT_DEACTIVATE = 236;
    public static final int SOUND_CONDUIT_SHORT = 237;
    public static final int SOUND_SWOOP = 238;
    public static final int SOUND_BLOCK_BAMBOO_SAPLING_PLACE = 239;
    public static final int SOUND_PRESNEEZE = 240;
    public static final int SOUND_SNEEZE = 241;
    public static final int SOUND_AMBIENT_TAME = 242;
    public static final int SOUND_SCARED = 243;
    public static final int SOUND_BLOCK_SCAFFOLDING_CLIMB = 244;
    public static final int SOUND_CROSSBOW_LOADING_START = 245;
    public static final int SOUND_CROSSBOW_LOADING_MIDDLE = 246;
    public static final int SOUND_CROSSBOW_LOADING_END = 247;
    public static final int SOUND_CROSSBOW_SHOOT = 248;
    public static final int SOUND_CROSSBOW_QUICK_CHARGE_START = 249;
    public static final int SOUND_CROSSBOW_QUICK_CHARGE_MIDDLE = 250;
    public static final int SOUND_CROSSBOW_QUICK_CHARGE_END = 251;
    public static final int SOUND_AMBIENT_AGGRESSIVE = 252;
    public static final int SOUND_AMBIENT_WORRIED = 253;
    public static final int SOUND_CANT_BREED = 254;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SHIELD_BLOCK = 255;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_LECTERN_BOOK_PLACE = 256;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_GRINDSTONE_USE = 257;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BELL = 258;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_CAMPFIRE_CRACKLE = 259;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_ROAR = 260;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_STUN = 261;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SWEET_BERRY_BUSH_HURT = 262;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SWEET_BERRY_BUSH_PICK = 263;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_CARTOGRAPHY_TABLE_USE = 264;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_STONECUTTER_USE = 265;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_COMPOSTER_EMPTY = 266;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_COMPOSTER_FILL = 267;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_COMPOSTER_FILL_LAYER = 268;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_COMPOSTER_READY = 269;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BARREL_OPEN = 270;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BARREL_CLOSE = 271;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_RAID_HORN = 272;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_LOOM_USE = 273;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_IN_RAID = 274;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_UI_CARTOGRAPHY_TABLE_USE = 275;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_UI_STONECUTTER_USE = 276;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_UI_LOOM_USE = 277;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SMOKER_USE = 278;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BLAST_FURNACE_USE = 279;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SMITHING_TABLE_USE = 280;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SCREECH = 281;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SLEEP = 282;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_FURNACE_USE = 283;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_MOOSHROOM_CONVERT = 284;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_MILK_SUSPICIOUSLY = 285;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_CELEBRATE = 286;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_JUMP_PREVENT = 287;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_POLLINATE = 288;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BEEHIVE_DRIP = 289;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BEEHIVE_ENTER = 290;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BEEHIVE_EXIT = 291;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BEEHIVE_WORK = 292;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BEEHIVE_SHEAR = 293;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_HONEYBOTTLE_DRINK = 294;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_CAVE = 295;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_RETREAT = 296;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_CONVERT_TO_ZOMBIFIED = 297;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_ADMIRE = 298;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_STEP_LAVA = 299;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_TEMPT = 300;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_PANIC = 301;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_ANGRY = 302;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_WARPED_FOREST = 303;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_SOULSAND_VALLEY = 304;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_NETHER_WASTES = 305;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_BASALT_DELTAS = 306;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_CRIMSON_FOREST = 307;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_RESPAWN_ANCHOR_CHARGE = 308;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_RESPAWN_ANCHOR_DEPLETE = 309;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_RESPAWN_ANCHOR_SET_SPAWN = 310;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_RESPAWN_ANCHOR_AMBIENT = 311;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SOUL_ESCAPE_QUIET = 312;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SOUL_ESCAPE_LOUD = 313;
    @Since("1.4.0.0-PN")
    public static final int SOUND_RECORD_PIGSTEP = 314;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_LINK_COMPASS_TO_LODESTONE = 315;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_USE_SMITHING_TABLE = 316;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_EQUIP_NETHERITE = 317;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_LOOP_WARPED_FOREST = 318;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_LOOP_SOULSAND_VALLEY = 319;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_LOOP_NETHER_WASTES = 320;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_LOOP_BASALT_DELTAS = 321;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_LOOP_CRIMSON_FOREST = 322;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_ADDITION_WARPED_FOREST = 323;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_ADDITION_SOULSAND_VALLEY = 324;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_ADDITION_NETHER_WASTES = 325;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_ADDITION_BASALT_DELTAS = 326;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_ADDITION_CRIMSON_FOREST = 327;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SCULK_SENSOR_POWER_ON = 328;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SCULK_SENSOR_POWER_OFF = 329;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BUCKET_FILL_POWDER_SNOW = 330;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BUCKET_EMPTY_POWDER_SNOW = 331;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_POINTED_DRIPSTONE_CAULDRON_DRIP_LAVA = 332;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_POINTED_DRIPSTONE_CAULDRON_DRIP_WATER = 333;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_POINTED_DRIPSTONE_DRIP_LAVA = 334;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_POINTED_DRIPSTONE_DRIP_WATER = 335;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_CAVE_VINES_PICK_BERRIES = 336;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BIG_DRIPLEAF_TILT_DOWN = 337;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BIG_DRIPLEAF_TILT_UP = 338;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_COPPER_WAX_ON = 339;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_COPPER_WAX_OFF = 340;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SCRAPE = 341;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_PLAYER_HURT_DROWN = 342;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_PLAYER_HURT_ON_FIRE = 343;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_PLAYER_HURT_FREEZE = 344;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_USE_SPYGLASS = 345;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_STOP_USING_SPYGLASS = 346;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMETHYST_BLOCK_CHIME = 347;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_SCREAMER = 348;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_HURT_SCREAMER = 349;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_DEATH_SCREAMER = 350;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_MILK_SCREAMER = 351;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_JUMP_TO_BLOCK = 352;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_PRE_RAM = 353;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_PRE_RAM_SCREAMER = 354;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_RAM_IMPACT = 355;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_RAM_IMPACT_SCREAMER = 356;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SQUID_INK_SQUIRT = 357;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_GLOW_SQUID_INK_SQUIRT = 358;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_CONVERT_TO_STRAY = 359;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_CAKE_ADD_CANDLE = 360;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_EXTINGUISH_CANDLE = 361;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_AMBIENT_CANDLE = 362;


    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BLOCK_CLICK = 363;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_BLOCK_CLICK_FAIL = 364;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SCULK_CATALYST_BLOOM = 365;

    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_SCULK_SHRIEKER_SHRIEK = 366;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_WARDEN_NEARBY_CLOSE = 367;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_WARDEN_NEARBY_CLOSER = 368;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_WARDEN_NEARBY_CLOSEST = 369;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_WARDEN_SLIGHTLY_ANGRY = 370;
    @PowerNukkitOnly
    @Since("FUTURE")
    public static final int SOUND_RECORD_OTHERSIDE = 371;

    @Since("1.6.0.0-PNX")
    public static final int SOUND_TONGUE = 372;
    @Since("1.6.0.0-PNX")
    public static final int SOUND_CRACK_IRON_GOLEM = 373;
    @Since("1.6.0.0-PNX")
    public static final int SOUND_REPAIR_IRON_GOLEM = 374;
    @Since("1.6.0.0-PNX")
    public static final int SOUND_UNDEFINED = 375;

    public int sound;
    public float x;
    public float y;
    public float z;
    public int extraData = -1;
    public String entityIdentifier;
    public boolean isBabyMob;
    public boolean isGlobal;

    @Override
    public void decode() {
        this.sound = (int) this.getUnsignedVarInt();
        Vector3f v = this.getVector3f();
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.extraData = this.getVarInt();
        this.entityIdentifier = this.getString();
        this.isBabyMob = this.getBoolean();
        this.isGlobal = this.getBoolean();
    }

    @Override
    public void encode() {
        this.reset();
        this.putUnsignedVarInt(this.sound);
        this.putVector3f(this.x, this.y, this.z);
        this.putVarInt(this.extraData);
        this.putString(this.entityIdentifier);
        this.putBoolean(this.isBabyMob);
        this.putBoolean(this.isGlobal);
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
