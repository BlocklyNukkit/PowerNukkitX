package cn.nukkit.entity;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.entity.item.*;
import cn.nukkit.entity.mob.*;
import cn.nukkit.entity.passive.*;
import cn.nukkit.entity.projectile.*;
import cn.nukkit.entity.weather.EntityLightning;
import com.google.common.collect.ImmutableMap;

/**
 * 从mc标准实体标识符获取Nukkit的实体网络ID{@link Entity#getNetworkId()}
 * Link {@link cn.nukkit.network.protocol.AddEntityPacket#LEGACY_IDS LEGACY_IDS}
 */
@PowerNukkitXOnly
@Since("1.19.60-r1")
public final class EntityIds {
    public static ImmutableMap<String, Integer> IDENTIFIER_2_IDS = ImmutableMap.<String, Integer>builder()
            .put("minecraft:chest_boat", EntityChestBoat.NETWORK_ID)//218
            .put("minecraft:trader_llama", 157)
            .put("minecraft:allay", EntityAllay.NETWORK_ID)//134
            .put("minecraft:tadpole", EntityTadpole.NETWORK_ID)//133
            .put("minecraft:frog", EntityFrog.NETWORK_ID)
            .put("minecraft:warden", EntityWarden.NETWORK_ID)
            .put("minecraft:axolotl", EntityAxolotl.NETWORK_ID)
            .put("minecraft:glow_squid", EntityGlowSquid.NETWORK_ID)//129
            .put("minecraft:goat", EntityGoat.NETWORK_ID)//128
            .put("minecraft:piglin_brute", EntityPiglinBrute.NETWORK_ID)
            .put("minecraft:zoglin", EntityZoglin.NETWORK_ID)
            .put("minecraft:strider", EntityStrider.NETWORK_ID)
            .put("minecraft:hoglin", EntityHoglin.NETWORK_ID)
            .put("minecraft:piglin", EntityPiglin.NETWORK_ID)
            .put("minecraft:bee", EntityBee.NETWORK_ID)
            .put("minecraft:fox", EntityFox.NETWORK_ID)//121
            .put("minecraft:wandering_trader", EntityWanderingTrader.NETWORK_ID)//118
            .put("minecraft:zombie_villager_v2", EntityZombieVillager.NETWORK_ID)
            .put("minecraft:villager_v2", EntityVillager.NETWORK_ID)
            .put("minecraft:pillager", EntityPillager.NETWORK_ID)
            .put("minecraft:panda", EntityPanda.NETWORK_ID)
            .put("minecraft:cod", EntityCod.NETWORK_ID)
            .put("minecraft:tropicalfish", EntityTropicalFish.NETWORK_ID)//111
            .put("minecraft:drowned", EntityDrowned.NETWORK_ID)
            .put("minecraft:salmon", EntitySalmon.NETWORK_ID)
            .put("minecraft:pufferfish", EntityPufferfish.NETWORK_ID)
            .put("minecraft:balloon", 107)
            .put("minecraft:ice_bomb", 106)
            .put("minecraft:vex", EntityVex.NETWORK_ID)
            .put("minecraft:evocation_illager", EntityEvoker.NETWORK_ID)
            .put("minecraft:evocation_fang", 103)
            .put("minecraft:llama_spit", 102)
            .put("minecraft:lingering_potion", EntityPotionLingering.NETWORK_ID)
            .put("minecraft:command_block_minecart", 100)
            .put("minecraft:chest_minecart", EntityMinecartChest.NETWORK_ID)//98
            .put("minecraft:tnt_minecart", EntityMinecartTNT.NETWORK_ID)
            .put("minecraft:hopper_minecart", EntityMinecartHopper.NETWORK_ID)
            .put("minecraft:area_effect_cloud", EntityAreaEffectCloud.NETWORK_ID)
            .put("minecraft:small_fireball", 94)
            .put("minecraft:lightning_bolt", EntityLightning.NETWORK_ID)
            .put("minecraft:wither_skull_dangerous", 91)
            .put("minecraft:boat", EntityBoat.NETWORK_ID)
            .put("minecraft:wither_skull", 89)
            .put("minecraft:leash_knot", 88)
            .put("minecraft:ender_pearl", EntityEnderPearl.NETWORK_ID)
            .put("minecraft:splash_potion", EntityPotion.NETWORK_ID)
            .put("minecraft:fireball", 85)
            .put("minecraft:minecart", EntityMinecartEmpty.NETWORK_ID)//84
            .put("minecraft:painting", EntityPainting.NETWORK_ID)
            .put("minecraft:egg", EntityEgg.NETWORK_ID)
            .put("minecraft:snowball", EntitySnowball.NETWORK_ID)
            .put("minecraft:arrow", EntityArrow.NETWORK_ID)
            .put("minecraft:dragon_fireball", 79)
            .put("minecraft:fishing_hook", EntityFishingHook.NETWORK_ID)
            .put("minecraft:shulker_bullet", 76)
            .put("minecraft:cat", EntityCat.NETWORK_ID)//75
            .put("minecraft:turtle", EntityTurtle.NETWORK_ID)//74
            .put("minecraft:thrown_trident", EntityThrownTrident.NETWORK_ID)
            .put("minecraft:fireworks_rocket", EntityFirework.NETWORK_ID)
            .put("minecraft:ender_crystal", EntityEndCrystal.NETWORK_ID)
            .put("minecraft:eye_of_ender_signal", 70)
            .put("minecraft:xp_orb", EntityXPOrb.NETWORK_ID)//69
            .put("minecraft:xp_bottle", EntityExpBottle.NETWORK_ID)
            .put("minecraft:falling_block", EntityFallingBlock.NETWORK_ID)
            .put("minecraft:tnt", EntityPrimedTNT.NETWORK_ID)
            .put("minecraft:item", EntityItem.NETWORK_ID)
            .put("minecraft:player", 63)
            .put("minecraft:tripod_camera", 62)
            .put("minecraft:armor_stand", EntityArmorStand.NETWORK_ID)//61
            .put("minecraft:ravager", EntityRavager.NETWORK_ID)
            .put("minecraft:phantom", EntityPhantom.NETWORK_ID)
            .put("minecraft:vindicator", EntityVindicator.NETWORK_ID)//57
            .put("minecraft:agent", 56)
            .put("minecraft:endermite", EntityEndermite.NETWORK_ID)
            .put("minecraft:shulker", EntityShulker.NETWORK_ID)
            .put("minecraft:ender_dragon", EntityEnderDragon.NETWORK_ID)
            .put("minecraft:wither", EntityWither.NETWORK_ID)
            .put("minecraft:elder_guardian", EntityElderGuardian.NETWORK_ID)
            .put("minecraft:guardian", EntityGuardian.NETWORK_ID)
            .put("minecraft:npc", EntityNPCEntity.NETWORK_ID)//51
            .put("minecraft:wither_skeleton", EntityWitherSkeleton.NETWORK_ID)//48
            .put("minecraft:husk", EntityHusk.NETWORK_ID)
            .put("minecraft:stray", EntityStray.NETWORK_ID)
            .put("minecraft:witch", EntityWitch.NETWORK_ID)
            .put("minecraft:zombie_villager", EntityZombieVillagerV1.NETWORK_ID)
            .put("minecraft:blaze", EntityBlaze.NETWORK_ID)
            .put("minecraft:magma_cube", EntityMagmaCube.NETWORK_ID)
            .put("minecraft:ghast", EntityGhast.NETWORK_ID)
            .put("minecraft:cave_spider", EntityCaveSpider.NETWORK_ID)
            .put("minecraft:silverfish", EntitySilverfish.NETWORK_ID)
            .put("minecraft:enderman", EntityEnderman.NETWORK_ID)
            .put("minecraft:slime", EntitySlime.NETWORK_ID)
            .put("minecraft:zombie_pigman", EntityZombiePigman.NETWORK_ID)
            .put("minecraft:spider", EntitySpider.NETWORK_ID)
            .put("minecraft:skeleton", EntitySkeleton.NETWORK_ID)
            .put("minecraft:creeper", EntityCreeper.NETWORK_ID)
            .put("minecraft:zombie", EntityZombie.NETWORK_ID)
            .put("minecraft:dolphin", EntityDolphin.NETWORK_ID)//31
            .put("minecraft:parrot", EntityParrot.NETWORK_ID)
            .put("minecraft:llama", EntityLlama.NETWORK_ID)
            .put("minecraft:polar_bear", EntityPolarBear.NETWORK_ID)
            .put("minecraft:zombie_horse", EntityZombieHorse.NETWORK_ID)
            .put("minecraft:skeleton_horse", EntitySkeletonHorse.NETWORK_ID)
            .put("minecraft:mule", EntityMule.NETWORK_ID)
            .put("minecraft:donkey", EntityDonkey.NETWORK_ID)//24
            .put("minecraft:horse", EntityHorse.NETWORK_ID)
            .put("minecraft:ocelot", EntityOcelot.NETWORK_ID)
            .put("minecraft:snow_golem", 21)
            .put("minecraft:iron_golem", 20)
            .put("minecraft:bat", EntityBat.NETWORK_ID)
            .put("minecraft:rabbit", EntityRabbit.NETWORK_ID)
            .put("minecraft:squid", EntitySquid.NETWORK_ID)//17
            .put("minecraft:mooshroom", EntityMooshroom.NETWORK_ID)
            .put("minecraft:villager", EntityVillagerV1.NETWORK_ID)
            .put("minecraft:wolf", EntityWolf.NETWORK_ID)
            .put("minecraft:sheep", EntitySheep.NETWORK_ID)
            .put("minecraft:pig", EntityPig.NETWORK_ID)
            .put("minecraft:cow", EntityCow.NETWORK_ID)
            .put("minecraft:chicken", EntityChicken.NETWORK_ID)//10
            .build();
}
