package cn.nukkit.item.food;

import cn.nukkit.Player;
import cn.nukkit.api.DeprecationDetails;
import cn.nukkit.block.Block;
import cn.nukkit.event.player.PlayerEatFoodEvent;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.potion.Effect;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.IntSupplier;

/**
 * @author Snake1999
 * @since 2016/1/13
 */
@SuppressWarnings("StaticInitializerReferencesSubClass")
public abstract class Food {
    private static final Map<NodeStringPluginMeta, Food> registryCustom = new LinkedHashMap<>();
    private static final Map<NodeStringMeta, Food> registryDefault = new LinkedHashMap<>();


    public static final Food apple = registerDefaultFood(new FoodNormal(4, 2.4F).addRelative(Item.APPLE));
    public static final Food apple_golden = registerDefaultFood(new FoodEffective(4, 9.6F)
            .addEffect(Effect.getEffect(Effect.REGENERATION).setAmplifier(1).setDuration(5 * 20))
            .addEffect(Effect.getEffect(Effect.ABSORPTION).setDuration(2 * 60 * 20))
            .addRelative(Item.GOLDEN_APPLE));
    public static final Food apple_golden_enchanted = registerDefaultFood(new FoodEffective(4, 9.6F)
            .addEffect(Effect.getEffect(Effect.REGENERATION).setAmplifier(4).setDuration(30 * 20))
            .addEffect(Effect.getEffect(Effect.ABSORPTION).setDuration(2 * 60 * 20).setAmplifier(3))
            .addEffect(Effect.getEffect(Effect.DAMAGE_RESISTANCE).setDuration(5 * 60 * 20))
            .addEffect(Effect.getEffect(Effect.FIRE_RESISTANCE).setDuration(5 * 60 * 20))
            .addRelative(Item.ENCHANTED_GOLDEN_APPLE));
    public static final Food beef_raw = registerDefaultFood(new FoodNormal(3, 1.8F).addRelative(Item.BEEF));
    public static final Food beetroot = registerDefaultFood(new FoodNormal(1, 1.2F).addRelative(Item.BEETROOT));
    public static final Food beetroot_soup = registerDefaultFood(new FoodInBowl(6, 7.2F).addRelative(Item.BEETROOT_SOUP));
    public static final Food bread = registerDefaultFood(new FoodNormal(5, 6F).addRelative(Item.BREAD));
    public static final Food cake_slice = registerDefaultFood(new FoodNormal(2, 0.4F)
            .addRelative(Block.CAKE, 0).addRelative(Block.CAKE, 1).addRelative(Block.CAKE, 2)
            .addRelative(Block.CAKE, 3).addRelative(Block.CAKE, 4).addRelative(Block.CAKE, 5)
            .addRelative(Block.CAKE, 6));
    public static final Food carrot = registerDefaultFood(new FoodNormal(3, 4.8F).addRelative(Item.CARROT));
    public static final Food carrot_golden = registerDefaultFood(new FoodNormal(6, 14.4F).addRelative(Item.GOLDEN_CARROT));
    public static final Food chicken_raw = registerDefaultFood(new FoodEffective(2, 1.2F)
            .addChanceEffect(0.3F, Effect.getEffect(Effect.HUNGER).setDuration(30 * 20))
            .addRelative(Item.CHICKEN));
    public static final Food chicken_cooked = registerDefaultFood(new FoodNormal(6, 7.2F).addRelative(Item.COOKED_CHICKEN));
    public static final Food chorus_fruit = registerDefaultFood(new FoodChorusFruit());
    public static final Food cookie = registerDefaultFood(new FoodNormal(2, 0.4F).addRelative(Item.COOKIE));
    public static final Food melon_slice = registerDefaultFood(new FoodNormal(2, 1.2F).addRelative(Item.MELON_SLICE));
    public static final Food milk = registerDefaultFood(new FoodMilk().addRelative(Item.BUCKET, 1));
    public static final Food mushroom_stew = registerDefaultFood(new FoodInBowl(6, 7.2F).addRelative(Item.MUSHROOM_STEW));
    public static final Food mutton_cooked = registerDefaultFood(new FoodNormal(6, 9.6F).addRelative(Item.COOKED_MUTTON));
    public static final Food mutton_raw = registerDefaultFood(new FoodNormal(2, 1.2F).addRelative(Item.MUTTON));
    public static final Food porkchop_cooked = registerDefaultFood(new FoodNormal(8, 12.8F).addRelative(Item.COOKED_PORKCHOP));
    public static final Food porkchop_raw = registerDefaultFood(new FoodNormal(3, 1.8F).addRelative(Item.PORKCHOP));
    public static final Food potato_raw = registerDefaultFood(new FoodNormal(1, 0.6F).addRelative(Item.POTATO));
    public static final Food potato_baked = registerDefaultFood(new FoodNormal(5, 7.2F).addRelative(Item.BAKED_POTATO));
    public static final Food potato_poisonous = registerDefaultFood(new FoodEffective(2, 1.2F)
            .addChanceEffect(0.6F, Effect.getEffect(Effect.POISON).setDuration(4 * 20))
            .addRelative(Item.POISONOUS_POTATO));
    public static final Food pumpkin_pie = registerDefaultFood(new FoodNormal(8, 4.8F).addRelative(Item.PUMPKIN_PIE));
    public static final Food rabbit_cooked = registerDefaultFood(new FoodNormal(5, 6F).addRelative(Item.COOKED_RABBIT));
    public static final Food rabbit_raw = registerDefaultFood(new FoodNormal(3, 1.8F).addRelative(Item.RABBIT));
    public static final Food rabbit_stew = registerDefaultFood(new FoodInBowl(10, 12F).addRelative(Item.RABBIT_STEW));
    public static final Food rotten_flesh = registerDefaultFood(new FoodEffective(4, 0.8F)
            .addChanceEffect(0.8F, Effect.getEffect(Effect.HUNGER).setDuration(30 * 20))
            .addRelative(Item.ROTTEN_FLESH));
    public static final Food spider_eye = registerDefaultFood(new FoodEffective(2, 3.2F)
            .addEffect(Effect.getEffect(Effect.POISON).setDuration(4 * 20))
            .addRelative(Item.SPIDER_EYE));
    public static final Food steak = registerDefaultFood(new FoodNormal(8, 12.8F).addRelative(Item.COOKED_BEEF));
    //different kinds of fishes
    public static final Food TROPICAL_FISH = registerDefaultFood(new FoodNormal(1, 0.2F).addRelative(Item.TROPICAL_FISH));
    public static final Food fish_cooked = registerDefaultFood(new FoodNormal(5, 6F).addRelative(Item.COOKED_COD));
    public static final Food fish_raw = registerDefaultFood(new FoodNormal(2, 0.4F).addRelative(Item.COD));
    public static final Food salmon_cooked = registerDefaultFood(new FoodNormal(6, 9.6F).addRelative(Item.COOKED_SALMON));
    public static final Food salmon_raw = registerDefaultFood(new FoodNormal(2, 0.4F).addRelative(Item.SALMON));
    public static final Food pufferfish = registerDefaultFood(new FoodEffective(1, 0.2F)
            .addEffect(Effect.getEffect(Effect.HUNGER).setAmplifier(2).setDuration(15 * 20))
            .addEffect(Effect.getEffect(Effect.NAUSEA).setAmplifier(1).setDuration(15 * 20))
            .addEffect(Effect.getEffect(Effect.POISON).setAmplifier(3).setDuration(60 * 20))
            .addRelative(Item.PUFFERFISH));
    public static final Food dried_kelp = registerDefaultFood(new FoodNormal(1, 0.6F).addRelative(Item.DRIED_KELP).setEatingTick(16));
    public static final Food sweet_berries = registerDefaultFood(new FoodNormal(2, 0.4F).addRelative(Item.SWEET_BERRIES));


    public static final Food suspicious_stew_night_vision = registerDefaultFood(new FoodEffectiveInBow(6, 7.2F)
            .addEffect(Effect.getEffect(Effect.NIGHT_VISION).setAmplifier(1).setDuration(4 * 20)).addRelative(Item.SUSPICIOUS_STEW, 0));


    public static final Food suspicious_stew_jump = registerDefaultFood(new FoodEffectiveInBow(6, 7.2F)
            .addEffect(Effect.getEffect(Effect.JUMP).setAmplifier(1).setDuration(4 * 20)).addRelative(Item.SUSPICIOUS_STEW, 1));


    public static final Food suspicious_stew_weakness = registerDefaultFood(new FoodEffectiveInBow(6, 7.2F)
            .addEffect(Effect.getEffect(Effect.WEAKNESS).setAmplifier(1).setDuration(7 * 20)).addRelative(Item.SUSPICIOUS_STEW, 2));


    public static final Food suspicious_stew_blindness = registerDefaultFood(new FoodEffectiveInBow(6, 7.2F)
            .addEffect(Effect.getEffect(Effect.BLINDNESS).setAmplifier(1).setDuration(6 * 20)).addRelative(Item.SUSPICIOUS_STEW, 3));


    public static final Food suspicious_stew_poison = registerDefaultFood(new FoodEffectiveInBow(6, 7.2F)
            .addEffect(Effect.getEffect(Effect.POISON).setAmplifier(1).setDuration(11 * 20)).addRelative(Item.SUSPICIOUS_STEW, 4));


    public static final Food suspicious_stew_saturation = registerDefaultFood(new FoodEffectiveInBow(6, 7.2F)
            .addEffect(Effect.getEffect(Effect.SATURATION).setAmplifier(1).setDuration(7)).addRelative(Item.SUSPICIOUS_STEW, 6));


    public static final Food suspicious_stew_fire_resistance = registerDefaultFood(new FoodEffectiveInBow(6, 7.2F)
            .addEffect(Effect.getEffect(Effect.FIRE_RESISTANCE).setAmplifier(1).setDuration(2 * 20)).addRelative(Item.SUSPICIOUS_STEW, 7));


    public static final Food suspicious_stew_regeneration = registerDefaultFood(new FoodEffectiveInBow(6, 7.2F)
            .addEffect(Effect.getEffect(Effect.REGENERATION).setAmplifier(1).setDuration(6 * 20)).addRelative(Item.SUSPICIOUS_STEW, 8));


    public static final Food suspicious_stew_wither = registerDefaultFood(new FoodEffectiveInBow(6, 7.2F)
            .addEffect(Effect.getEffect(Effect.WITHER).setAmplifier(1).setDuration(6 * 20)).addRelative(Item.SUSPICIOUS_STEW, 9));

    @Deprecated
    @DeprecationDetails(since = "1.4.0.0-PN", reason = "Was added in Cloudburst Nukkit with another name", replaceWith = "honey_bottle")

    public static final Food honey = registerDefaultFood(new FoodHoney(6, 1.2F).addRelative(Item.HONEY_BOTTLE));


    public static final Food honey_bottle = honey;


    public static final Food glow_berries = registerDefaultFood(new FoodNormal(2, 0.4F).addRelative(Item.GLOW_BERRIES));

    //Opened API for plugins
    public static Food registerFood(Food food, Plugin plugin) {
        Objects.requireNonNull(food);
        Objects.requireNonNull(plugin);
        food.relativeIDs.forEach(n -> {
            if (n instanceof NodeStringPluginMeta nodeStringIDMeta) {
                registryCustom.put(nodeStringIDMeta, food);
            }
        });
        return food;
    }

    private static Food registerDefaultFood(Food food) {
        food.relativeIDs.forEach(n -> registryDefault.put(n, food));
        return food;
    }

    public static Food getByRelative(Item item) {
        Objects.requireNonNull(item);
        return getByRelative(item.getId(), item.getDamage());
    }

    public static Food getByRelative(Block block) {
        Objects.requireNonNull(block);
        return getByRelative(block.getId(), block.getBlockState().specialValue());
    }

    public static Food getByRelative(String stringID, int meta) {
        final Food[] result = {null};
        registryCustom.forEach((n, f) -> {
            if (n.id.equals(stringID) && n.meta == meta && n.plugin.isEnabled())
                result[0] = f;
        });
        if (result[0] == null) {
            registryDefault.forEach((n, f) -> {
                if (n.id.equals(stringID) && n.meta == meta) result[0] = f;
            });
        }
        return result[0];
    }

    protected int restoreFood = 0;
    protected float restoreSaturation = 0;
    protected final List<NodeStringMeta> relativeIDs = new ArrayList<>();

    public final boolean eatenBy(Player player) {
        PlayerEatFoodEvent event = new PlayerEatFoodEvent(player, this);
        player.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;
        return event.getFood().onEatenBy(player);
    }

    protected boolean onEatenBy(Player player) {
        player.getFoodData().addFood(this);
        return true;
    }

    public Food addRelative(String relativeID) {
        return addRelative(relativeID, 0);
    }

    public Food addRelative(String relativeID, int meta) {
        NodeStringMeta node = new NodeStringMeta(relativeID, meta);
        return addRelative(node);
    }

    public Food addRelative(String stringID, int meta, Plugin plugin) {
        var node = new NodeStringPluginMeta(stringID, meta, plugin);
        return addRelative(node);
    }

    private Food addRelative(NodeStringMeta node) {
        if (!relativeIDs.contains(node)) relativeIDs.add(node);
        return this;
    }

    public int getRestoreFood() {
        return restoreFood;
    }

    public Food setRestoreFood(int restoreFood) {
        this.restoreFood = restoreFood;
        return this;
    }

    public float getRestoreSaturation() {
        return restoreSaturation;
    }

    public Food setRestoreSaturation(float restoreSaturation) {
        this.restoreSaturation = restoreSaturation;
        return this;
    }

    protected int eatingTick = 31;


    protected IntSupplier eatingTickSupplier;


    public int getEatingTick() {
        return eatingTick;
    }

    public Food setEatingTick(int eatingTick) {
        this.eatingTick = eatingTick;
        return this;
    }

    @Nullable


    public IntSupplier getEatingTickSupplier() {
        return eatingTickSupplier;
    }

    public Food setEatingTickSupplier(IntSupplier eatingTickSupplier) {
        this.eatingTickSupplier = eatingTickSupplier;
        return this;
    }

    static class NodeStringMeta {
        final String id;
        final int meta;

        NodeStringMeta(String id, int meta) {
            this.id = id;
            this.meta = meta;
        }
    }

    static class NodeStringPluginMeta extends NodeStringMeta {
        final Plugin plugin;

        NodeStringPluginMeta(String id, int meta, Plugin plugin) {
            super(id, meta);
            this.plugin = plugin;
        }
    }
}
