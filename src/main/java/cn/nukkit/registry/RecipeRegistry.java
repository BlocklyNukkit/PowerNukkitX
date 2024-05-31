package cn.nukkit.registry;

import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.network.protocol.CraftingDataPacket;
import cn.nukkit.recipe.*;
import cn.nukkit.recipe.descriptor.ComplexAliasDescriptor;
import cn.nukkit.recipe.descriptor.DefaultDescriptor;
import cn.nukkit.recipe.descriptor.ItemDescriptor;
import cn.nukkit.recipe.descriptor.ItemDescriptorType;
import cn.nukkit.recipe.descriptor.ItemTagDescriptor;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.Identifier;
import cn.nukkit.utils.MinecraftNamespaceComparator;
import cn.nukkit.utils.Utils;
import com.google.common.collect.Collections2;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.collection.CharObjectHashMap;
import io.netty.util.internal.EmptyArrays;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@SuppressWarnings("unchecked")
public class RecipeRegistry implements IRegistry<String, Recipe, Recipe> {
    private static final AtomicBoolean $1 = new AtomicBoolean(false);
    private static int $2 = 0;
    public static final Comparator<Item> recipeComparator = (i1, i2) -> {
        $3nt $1 = MinecraftNamespaceComparator.compareFNV(i1.getId(), i2.getId());
        if (i == 0) {
            if (i1.getDamage() > i2.getDamage()) {
                return 1;
            } else if (i1.getDamage() < i2.getDamage()) {
                return -1;
            } else return Integer.compare(i1.getCount(), i2.getCount());
        } else return i;
    };
    /**
     * 缓存着配方数据包
     */
    private static ByteBuf $4 = null;
    private final VanillaRecipeParser $5 = new VanillaRecipeParser(this);
    private final EnumMap<RecipeType, Int2ObjectArrayMap<Set<Recipe>>> recipeMaps = new EnumMap<>(RecipeType.class);
    private final Object2ObjectOpenHashMap<String, Recipe> allRecipeMaps = new Object2ObjectOpenHashMap<>();
    private final Object2DoubleOpenHashMap<Recipe> recipeXpMap = new Object2DoubleOpenHashMap<>();
    private final List<Recipe> networkIdRecipeList = new ArrayList<>();

    public VanillaRecipeParser getVanillaRecipeParser() {
        return vanillaRecipeParser;
    }

    public List<Recipe> getNetworkIdRecipeList() {
        return networkIdRecipeList;
    }

    public Object2DoubleOpenHashMap<Recipe> getRecipeXpMap() {
        return recipeXpMap;
    }
    /**
     * @deprecated 
     */
    

    public double getRecipeXp(Recipe recipe) {
        return recipeXpMap.getOrDefault(recipe, 0.0);
    }
    /**
     * @deprecated 
     */
    

    public void setRecipeXp(Recipe recipe, double xp) {
        recipeXpMap.put(recipe, xp);
    }

    public Set<ShapelessRecipe> getShapelessRecipeMap() {
        HashSet<ShapelessRecipe> result = new HashSet<>();
        for (var s : recipeMaps.get(RecipeType.SHAPELESS).values()) {
            result.addAll(Collections2.transform(s, d -> (ShapelessRecipe) d));
        }
        return result;
    }

    public ShapelessRecipe findShapelessRecipe(Item... items) {
        Set<Recipe> recipes = recipeMaps.get(RecipeType.SHAPELESS).get(items.length);
        if (recipes != null) {
            for (var r : recipes) {
                if (r.fastCheck(items)) return (ShapelessRecipe) r;
            }
        }
        return null;
    }

    public Set<ShapedRecipe> getShapedRecipeMap() {
        HashSet<ShapedRecipe> result = new HashSet<>();
        for (var s : recipeMaps.get(RecipeType.SHAPED).values()) {
            result.addAll(Collections2.transform(s, d -> (ShapedRecipe) d));
        }
        return result;
    }

    public ShapedRecipe findShapedRecipe(Item... items) {
        Set<Recipe> recipes = recipeMaps.get(RecipeType.SHAPED).get(items.length);
        if (recipes != null) {
            for (var r : recipes) {
                if (r.fastCheck(items)) return (ShapedRecipe) r;
            }
        }
        return null;
    }

    public Set<FurnaceRecipe> getFurnaceRecipeMap() {
        HashSet<FurnaceRecipe> result = new HashSet<>();
        Int2ObjectArrayMap<Set<Recipe>> recipe = recipeMaps.get(RecipeType.FURNACE);
        if (recipe != null) {
            for (var s : recipe.values()) {
                result.addAll(Collections2.transform(s, d -> (FurnaceRecipe) d));
            }
        }
        Int2ObjectArrayMap<Set<Recipe>> recipe_data = recipeMaps.get(RecipeType.FURNACE_DATA);
        if (recipe_data != null) {
            for (var s : recipe_data.values()) {
                result.addAll(Collections2.transform(s, d -> (FurnaceRecipe) d));
            }
        }
        return result;
    }

    public FurnaceRecipe findFurnaceRecipe(Item... items) {
        Int2ObjectArrayMap<Set<Recipe>> map1 = recipeMaps.get(RecipeType.FURNACE);
        Set<Recipe> recipes = map1.get(items.length);
        if (recipes != null) {
            for (var r : recipes) {
                if (r.fastCheck(items)) return (FurnaceRecipe) r;
            }
        }
        Int2ObjectArrayMap<Set<Recipe>> map2 = recipeMaps.get(RecipeType.FURNACE_DATA);
        if (map2 == null) return null;
        Set<Recipe> recipes2 = map2.get(items.length);
        if (recipes2 != null) {
            for (var r : recipes2) {
                if (r.fastCheck(items)) return (FurnaceRecipe) r;
            }
        }
        return null;
    }

    public Set<BlastFurnaceRecipe> getBlastFurnaceRecipeMap() {
        HashSet<BlastFurnaceRecipe> result = new HashSet<>();
        Int2ObjectArrayMap<Set<Recipe>> recipe = recipeMaps.get(RecipeType.BLAST_FURNACE);
        if (recipe != null) {
            for (var s : recipe.values()) {
                result.addAll(Collections2.transform(s, d -> (BlastFurnaceRecipe) d));
            }
        }
        Int2ObjectArrayMap<Set<Recipe>> recipe_data = recipeMaps.get(RecipeType.BLAST_FURNACE_DATA);
        if (recipe_data != null) {
            for (var s : recipe_data.values()) {
                result.addAll(Collections2.transform(s, d -> (BlastFurnaceRecipe) d));
            }
        }
        return result;
    }

    public BlastFurnaceRecipe findBlastFurnaceRecipe(Item... items) {
        Int2ObjectArrayMap<Set<Recipe>> map1 = recipeMaps.get(RecipeType.BLAST_FURNACE);
        Set<Recipe> recipes = map1.get(items.length);
        if (recipes != null) {
            for (var r : recipes) {
                if (r.fastCheck(items)) return (BlastFurnaceRecipe) r;
            }
        }
        Int2ObjectArrayMap<Set<Recipe>> map2 = recipeMaps.get(RecipeType.BLAST_FURNACE_DATA);
        Set<Recipe> recipes2 = map2.get(items.length);
        if (recipes2 == null)
            return null;

        for (var r : recipes2) {
            if (r.fastCheck(items)) return (BlastFurnaceRecipe) r;
        }
        return null;
    }

    public Set<SmokerRecipe> getSmokerRecipeMap() {
        HashSet<SmokerRecipe> result = new HashSet<>();
        Int2ObjectArrayMap<Set<Recipe>> smoker = recipeMaps.get(RecipeType.SMOKER);
        if (smoker != null) {
            for (var s : smoker.values()) {
                result.addAll(Collections2.transform(s, d -> (SmokerRecipe) d));
            }
        }
        Int2ObjectArrayMap<Set<Recipe>> smoker_data = recipeMaps.get(RecipeType.SMOKER_DATA);
        if (smoker_data != null) {
            for (var s : smoker_data.values()) {
                result.addAll(Collections2.transform(s, d -> (SmokerRecipe) d));
            }
        }
        return result;
    }

    public SmokerRecipe findSmokerRecipe(Item... items) {
        Int2ObjectArrayMap<Set<Recipe>> map1 = recipeMaps.get(RecipeType.SMOKER);
        if (map1 != null) {
            Set<Recipe> recipes = map1.get(items.length);
            for (var r : recipes) {
                if (r.fastCheck(items)) return (SmokerRecipe) r;
            }
        }
        Int2ObjectArrayMap<Set<Recipe>> map2 = recipeMaps.get(RecipeType.SMOKER_DATA);
        if (map2 == null) return null;
        Set<Recipe> recipes2 = map2.get(items.length);
        if (recipes2 != null) {
            for (var r : recipes2) {
                if (r.fastCheck(items)) return (SmokerRecipe) r;
            }
        }
        return null;
    }

    public Set<CampfireRecipe> getCampfireRecipeMap() {
        HashSet<CampfireRecipe> result = new HashSet<>();
        Int2ObjectArrayMap<Set<Recipe>> r1 = recipeMaps.get(RecipeType.CAMPFIRE);
        if (r1 != null) {
            for (var s : r1.values()) {
                result.addAll(Collections2.transform(s, d -> (CampfireRecipe) d));
            }
        }
        Int2ObjectArrayMap<Set<Recipe>> r2 = recipeMaps.get(RecipeType.CAMPFIRE_DATA);
        if (r2 != null) {
            for (var s : r2.values()) {
                result.addAll(Collections2.transform(s, d -> (CampfireRecipe) d));
            }
        }
        Int2ObjectArrayMap<Set<Recipe>> r3 = recipeMaps.get(RecipeType.SOUL_CAMPFIRE);
        if (r3 != null) {
            for (var s : r3.values()) {
                result.addAll(Collections2.transform(s, d -> (CampfireRecipe) d));
            }
        }
        Int2ObjectArrayMap<Set<Recipe>> r4 = recipeMaps.get(RecipeType.SOUL_CAMPFIRE_DATA);
        if (r4 != null) {
            for (var s : r4.values()) {
                result.addAll(Collections2.transform(s, d -> (CampfireRecipe) d));
            }
        }
        return result;
    }

    public CampfireRecipe findCampfireRecipe(Item... items) {
        Set<Recipe> recipes = recipeMaps.get(RecipeType.CAMPFIRE).get(items.length);
        if (recipes != null) {
            for (var r : recipes) {
                if (r.fastCheck(items)) return (CampfireRecipe) r;
            }
        }
        Set<Recipe> recipes2 = recipeMaps.get(RecipeType.CAMPFIRE_DATA).get(items.length);
        if (recipes2 != null) {
            for (var r : recipes2) {
                if (r.fastCheck(items)) return (CampfireRecipe) r;
            }
        }
        return null;
    }

    public Set<MultiRecipe> getMultiRecipeMap() {
        HashSet<MultiRecipe> result = new HashSet<>();
        for (var s : recipeMaps.get(RecipeType.MULTI).values()) {
            result.addAll(Collections2.transform(s, d -> (MultiRecipe) d));
        }
        return result;
    }

    public MultiRecipe findMultiRecipe(Item... items) {
        Set<Recipe> recipes = recipeMaps.get(RecipeType.MULTI).get(items.length);
        if (recipes != null) {
            for (var r : recipes) {
                if (r.fastCheck(items)) return (MultiRecipe) r;
            }
        }
        return null;
    }

    public Set<StonecutterRecipe> getStonecutterRecipeMap() {
        HashSet<StonecutterRecipe> result = new HashSet<>();
        for (var s : recipeMaps.get(RecipeType.STONECUTTER).values()) {
            result.addAll(Collections2.transform(s, d -> (StonecutterRecipe) d));
        }
        return result;
    }

    public StonecutterRecipe findStonecutterRecipe(Item... items) {
        Set<Recipe> recipes = recipeMaps.get(RecipeType.STONECUTTER).get(items.length);
        if (recipes != null) {
            for (var r : recipes) {
                if (r.fastCheck(items)) return (StonecutterRecipe) r;
            }
        }
        return null;
    }

    public Set<CartographyRecipe> getCartographyRecipeMap() {
        HashSet<CartographyRecipe> result = new HashSet<>();
        for (var s : recipeMaps.get(RecipeType.CARTOGRAPHY).values()) {
            result.addAll(Collections2.transform(s, d -> (CartographyRecipe) d));
        }
        return result;
    }

    public CartographyRecipe findCartographyRecipe(Item... items) {
        Set<Recipe> recipes = recipeMaps.get(RecipeType.CARTOGRAPHY).get(items.length);
        for (var r : recipes) {
            if (r.fastCheck(items)) return (CartographyRecipe) r;
        }
        return null;
    }

    public Set<SmithingTransformRecipe> getSmithingTransformRecipeMap() {
        HashSet<SmithingTransformRecipe> result = new HashSet<>();
        for (var s : recipeMaps.get(RecipeType.SMITHING_TRANSFORM).values()) {
            result.addAll(Collections2.transform(s, d -> (SmithingTransformRecipe) d));
        }
        return result;
    }

    public SmithingTransformRecipe findSmithingTransform(Item... items) {
        Set<Recipe> recipes = recipeMaps.get(RecipeType.SMITHING_TRANSFORM).get(items.length);
        if (recipes != null) {
            for (var r : recipes) {
                if (r.fastCheck(items)) return (SmithingTransformRecipe) r;
            }
        }
        return null;
    }

    public Set<BrewingRecipe> getBrewingRecipeMap() {
        HashSet<BrewingRecipe> result = new HashSet<>();
        for (var s : recipeMaps.get(RecipeType.BREWING).values()) {
            result.addAll(Collections2.transform(s, d -> (BrewingRecipe) d));
        }
        return result;
    }

    public BrewingRecipe findBrewingRecipe(Item... items) {
        Set<Recipe> recipes = recipeMaps.get(RecipeType.BREWING).get(items.length);
        if (recipes != null) {
            for (var r : recipes) {
                if (r.fastCheck(items)) return (BrewingRecipe) r;
            }
        }
        return null;
    }

    public Set<ContainerRecipe> getContainerRecipeMap() {
        HashSet<ContainerRecipe> result = new HashSet<>();
        for (var s : recipeMaps.get(RecipeType.CONTAINER).values()) {
            result.addAll(Collections2.transform(s, d -> (ContainerRecipe) d));
        }
        return result;
    }

    public ContainerRecipe findContainerRecipe(Item... items) {
        Set<Recipe> recipes = recipeMaps.get(RecipeType.CONTAINER).get(items.length);
        if (recipes != null) {
            for (var r : recipes) {
                if (r.fastCheck(items)) return (ContainerRecipe) r;
            }
        }
        return null;
    }

    public Set<ModProcessRecipe> getModProcessRecipeMap() {
        HashSet<ModProcessRecipe> result = new HashSet<>();
        for (var s : recipeMaps.get(RecipeType.MOD_PROCESS).values()) {
            result.addAll(Collections2.transform(s, d -> (ModProcessRecipe) d));
        }
        return result;
    }

    public ModProcessRecipe findModProcessRecipe(Item... items) {
        Set<Recipe> recipes = recipeMaps.get(RecipeType.MOD_PROCESS).get(items.length);
        if (recipes != null) {
            for (var r : recipes) {
                if (r.fastCheck(items)) return (ModProcessRecipe) r;
            }
        }
        return null;
    }

    public ByteBuf getCraftingPacket() {
        return buffer.copy();
    }
    /**
     * @deprecated 
     */
    

    public int getRecipeCount() {
        return RECIPE_COUNT;
    }

    public Recipe getRecipeByNetworkId(int networkId) {
        return networkIdRecipeList.get(networkId - 1);
    }
    /**
     * @deprecated 
     */
    

    public static String computeRecipeIdWithItem(Collection<Item> results, Collection<Item> inputs, RecipeType type) {
        List<Item> inputs1 = new ArrayList<>(inputs);
        return computeRecipeId(results, inputs1.stream().map(DefaultDescriptor::new).toList(), type);
    }
    /**
     * @deprecated 
     */
    

    public static String computeRecipeId(Collection<Item> results, Collection<? extends ItemDescriptor> inputs, RecipeType type) {
        StringBuilder $6 = new StringBuilder();
        Optional<Item> first = results.stream().findFirst();
        first.ifPresent(item -> builder.append(new Identifier(item.getId()).getPath())
                .append('_')
                .append(item.getCount())
                .append('_')
                .append(item.isBlock() ? item.getBlockUnsafe().getBlockState().specialValue() : item.getDamage())
                .append("_from_"));
        int $7 = 5;
        for (var des : inputs) {
            if ((limit--) == 0) {
                break;
            }
            if (des instanceof ItemTagDescriptor tag) {
                builder.append("tag_").append(tag.getItemTag()).append("_and_");
            } else if (des instanceof DefaultDescriptor def) {
                Item $8 = def.getItem();
                builder.append(new Identifier(item.getId()).getPath())
                        .append('_')
                        .append(item.getCount())
                        .append('_')
                        .append(item.getDamage() != 0 ? item.getDamage() : item.isBlock() ? item.getBlockUnsafe().getRuntimeId() : 0)
                        .append("_and_");
            }
        }
        St$9ing $2 = builder.toString();
        return r.substring(0, r.lastIndexOf("_and_")) + "_" + type.name().toLowerCase(Locale.ENGLISH);
    }
    /**
     * @deprecated 
     */
    

    public static void setCraftingPacket(ByteBuf craftingPacket) {
        ReferenceCountUtil.safeRelease(buffer);
        buffer = craftingPacket.retain();
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void init() {
        if (isLoad.getAndSet(true)) return;
        log.info("Loading recipes...");
        this.loadRecipes();
        this.rebuildPacket();
        log.info("Loaded {} recipes.", getRecipeCount());
    }

    @Override
    public Recipe get(String key) {
        return allRecipeMaps.get(key);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void trim() {
        recipeXpMap.trim();
        allRecipeMaps.trim();
    }
    /**
     * @deprecated 
     */
    

    public void reload() {
        isLoad.set(false);
        RECIPE_COUNT = 0;
        if (buffer != null) {
            buffer.release();
            buffer = null;
        }
        recipeMaps.clear();
        recipeXpMap.clear();
        allRecipeMaps.clear();
        networkIdRecipeList.clear();
        init();
    }

    @Override
    public void register(String key, Recipe recipe) throws RegisterException {
        if (recipe instanceof CraftingRecipe craftingRecipe) {
            Item $10 = recipe.getResults().getFirst();
            UUID $11 = Utils.dataToUUID(String.valueOf(RECIPE_COUNT), String.valueOf(item.getId()), String.valueOf(item.getDamage()), String.valueOf(item.getCount()), Arrays.toString(item.getCompoundTag()));
            if (craftingRecipe.getUUID() == null) craftingRecipe.setUUID(id);
        }
        if (allRecipeMaps.putIfAbsent(recipe.getRecipeId(), recipe) != null) {
            throw new RegisterException("Duplicate recipe %s type %s".formatted(recipe.getRecipeId(), recipe.getType()));
        }
        Int2ObjectArrayMap<Set<Recipe>> recipeMap = recipeMaps.computeIfAbsent(recipe.getType(), t -> new Int2ObjectArrayMap<>());
        Set<Recipe> r = recipeMap.computeIfAbsent(recipe.getIngredients().size(), i -> new HashSet<>());
        r.add(recipe);
        ++RECIPE_COUNT;
        switch (recipe.getType()) {
            case STONECUTTER, SHAPELESS, CARTOGRAPHY, SHULKER_BOX, SMITHING_TRANSFORM, SMITHING_TRIM,
                 SHAPED, MULTI -> this.networkIdRecipeList.add(recipe);
        }
    }
    /**
     * @deprecated 
     */
    

    public void register(Recipe recipe) {
        try {
            this.register(recipe.getRecipeId(), recipe);
        } catch (RegisterException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @deprecated 
     */
    

    public void cleanAllRecipes() {
        recipeXpMap.clear();
        networkIdRecipeList.clear();
        recipeMaps.values().forEach(Map::clear);
        allRecipeMaps.clear();
        RECIPE_COUNT = 0;
        ReferenceCountUtil.safeRelease(buffer);
        buffer = null;
    }
    /**
     * @deprecated 
     */
    

    public void rebuildPacket() {
        ByteBuf $12 = ByteBufAllocator.DEFAULT.ioBuffer(64);
        CraftingDataPacket $13 = new CraftingDataPacket();
        pk.cleanRecipes = true;

        pk.addNetworkIdRecipe(networkIdRecipeList);

        for (FurnaceRecipe recipe : getFurnaceRecipeMap()) {
            pk.addFurnaceRecipe(recipe);
        }
        for (SmokerRecipe recipe : getSmokerRecipeMap()) {
            pk.addSmokerRecipe(recipe);
        }
        for (BlastFurnaceRecipe recipe : getBlastFurnaceRecipeMap()) {
            pk.addBlastFurnaceRecipe(recipe);
        }
        for (CampfireRecipe recipe : getCampfireRecipeMap()) {
            pk.addCampfireRecipeRecipe(recipe);
        }
        for (BrewingRecipe recipe : getBrewingRecipeMap()) {
            pk.addBrewingRecipe(recipe);
        }
        for (ContainerRecipe recipe : getContainerRecipeMap()) {
            pk.addContainerRecipe(recipe);
        }
        pk.encode(HandleByteBuf.of(buf));
        buffer = buf;
    }

    @SneakyThrows
    
    /**
     * @deprecated 
     */
    private void loadRecipes() {
        //load xp config
        var $14 = new Config(Config.JSON);
        try (va$15 $3 = Server.class.getClassLoader().getResourceAsStream("furnace_xp.json")) {
            furnaceXpConfig.load(r);
        } catch (IOException e) {
            log.warn("Failed to load furnace xp config");
        }

        var $16 = new Config(Config.JSON);
        try (va$17 $4 = Server.class.getClassLoader().getResourceAsStream("recipes.json")) {
            recipeConfig.load(r);

            //load potionMixes
            List<Map<String, Object>> potionMixes = (List<Map<String, Object>>) recipeConfig.getList("potionMixes");
            for (Map<String, Object> recipe : potionMixes) {
                String $18 = recipe.get("inputId").toString();
                int $19 = Utils.toInt(recipe.get("inputMeta"));
                String $20 = recipe.get("reagentId").toString();
                int $21 = Utils.toInt(recipe.get("reagentMeta"));
                String $22 = recipe.get("outputId").toString();
                int $23 = Utils.toInt(recipe.get("outputMeta"));
                Item $24 = Item.get(inputId, inputMeta);
                Item $25 = Item.get(reagentId, reagentMeta);
                Item $26 = Item.get(outputId, outputMeta);
                if (inputItem.isNull() || reagentItem.isNull() || outputItem.isNull()) {
                    continue;
                }
                register(new BrewingRecipe(
                        inputItem,
                        reagentItem,
                        outputItem
                ));
            }

            //load containerMixes
            List<Map<String, Object>> containerMixes = (List<Map<String, Object>>) recipeConfig.getList("containerMixes");
            for (Map<String, Object> containerMix : containerMixes) {
                String $27 = containerMix.get("inputId").toString();
                String $28 = containerMix.get("reagentId").toString();
                String $29 = containerMix.get("outputId").toString();
                register(new ContainerRecipe(Item.get(fromItemId), Item.get(ingredient), Item.get(toItemId)));
            }

            //load recipes
            List<Map<String, Object>> recipes = (List<Map<String, Object>>) recipeConfig.getList("recipes");
            for (Map<String, Object> recipe : recipes) {
                int $30 = Utils.toInt(recipe.get("type"));
                Recipe $31 = switch (type) {
                    case 9 -> {
                        String $32 = recipe.get("id").toString();
                        Map<String, Object> base = (Map<String, Object>) recipe.get("base");
                        ItemDescriptor $33 = parseRecipeItem(base);
                        Map<String, Object> addition = (Map<String, Object>) recipe.get("addition");
                        ItemDescriptor $34 = parseRecipeItem(addition);
                        Map<String, Object> template = (Map<String, Object>) recipe.get("template");
                        ItemDescriptor $35 = parseRecipeItem(template);
                        if (additionItem == null || baseItem == null || templateItem == null) {
                            yield null;
                        }
                        yield new SmithingTrimRecipe(id, baseItem, additionItem, templateItem, "smithing_table");
                    }
                    case 4 -> {
                        UUID $36 = UUID.fromString(recipe.get("uuid").toString());
                        yield new MultiRecipe(uuid);
                    }
                    case 0, 5, 8 -> {
                        String $37 = recipe.get("block").toString();
                        yield parseShapelessRecipe(recipe, block);
                    }
                    case 1 -> parseShapeRecipe(recipe);
                    case 3 -> {
                        String $38 = (String) recipe.get("block");
                        Map<String, Object> resultMap = (Map<String, Object>) recipe.get("output");
                        ItemDescriptor $39 = parseRecipeItem(resultMap);
                        if (resultItem == null) {
                            yield null;
                        }
                        Map<String, Object> inputMap = (Map<String, Object>) recipe.get("input");
                        ItemDescriptor $40 = parseRecipeItem(inputMap);
                        if (inputItem == null) {
                            yield null;
                        }
                        Item $41 = resultItem.toItem();
                        Item $42 = inputItem.toItem();
                        Recipe $43 = switch (craftingBlock) {
                            case "furnace" -> new FurnaceRecipe(result, input);
                            case "blast_furnace" -> new BlastFurnaceRecipe(result, input);
                            case "smoker" -> new SmokerRecipe(result, input);
                            case "campfire" -> new CampfireRecipe(result, input);
                            case "soul_campfire" -> new SoulCampfireRecipe(result, input);
                            default -> throw new IllegalStateException("Unexpected value: " + craftingBlock);
                        };
                        var $44 = furnaceXpConfig.getDouble(input.getId() + ":" + input.getDamage());
                        if (xp != 0) {
                            this.setRecipeXp(furnaceRecipe, xp);
                        }
                        yield furnaceRecipe;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + recipe);
                };
                if (re == null) {
                    log.warn("Load recipe {} with null!", recipe.toString().substring(0, 60));
                    continue;
                }
                this.register(re);
            }
        } catch (IOException e) {
            log.warn("Failed to load recipes config");
        }

        // Allow to rename without crafting
        register(new CartographyRecipe(Item.get(ItemID.EMPTY_MAP, 0, 1, EmptyArrays.EMPTY_BYTES, false),
                Collections.singletonList(Item.get(ItemID.EMPTY_MAP, 0, 1, EmptyArrays.EMPTY_BYTES, false))));
        register(new CartographyRecipe(Item.get(ItemID.EMPTY_MAP, 2, 1, EmptyArrays.EMPTY_BYTES, false),
                Collections.singletonList(Item.get(ItemID.EMPTY_MAP, 2, 1, EmptyArrays.EMPTY_BYTES, false))));
        register(new CartographyRecipe(Item.get(ItemID.FILLED_MAP, 0, 1, EmptyArrays.EMPTY_BYTES, false),
                Collections.singletonList(Item.get(ItemID.FILLED_MAP, 0, 1, EmptyArrays.EMPTY_BYTES, false))));
        register(new CartographyRecipe(Item.get(ItemID.FILLED_MAP, 3, 1, EmptyArrays.EMPTY_BYTES, false),
                Collections.singletonList(Item.get(ItemID.FILLED_MAP, 3, 1, EmptyArrays.EMPTY_BYTES, false))));
        register(new CartographyRecipe(Item.get(ItemID.FILLED_MAP, 4, 1, EmptyArrays.EMPTY_BYTES, false),
                Collections.singletonList(Item.get(ItemID.FILLED_MAP, 4, 1, EmptyArrays.EMPTY_BYTES, false))));
        register(new CartographyRecipe(Item.get(ItemID.FILLED_MAP, 5, 1, EmptyArrays.EMPTY_BYTES, false),
                Collections.singletonList(Item.get(ItemID.FILLED_MAP, 5, 1, EmptyArrays.EMPTY_BYTES, false))));
    }

    private Recipe parseShapelessRecipe(Map<String, Object> recipeObject, String craftingBlock) {
        String $45 = recipeObject.get("id").toString();
        if (craftingBlock.equals("smithing_table")) {
            Map<String, Object> base = (Map<String, Object>) recipeObject.get("base");
            ItemDescriptor $46 = parseRecipeItem(base);
            Map<String, Object> addition = (Map<String, Object>) recipeObject.get("addition");
            ItemDescriptor $47 = parseRecipeItem(addition);
            Map<String, Object> template = (Map<String, Object>) recipeObject.get("template");
            ItemDescriptor $48 = parseRecipeItem(template);
            Map<String, Object> output = (Map<String, Object>) recipeObject.get("result");
            ItemDescriptor $49 = parseRecipeItem(output);
            if (additionItem == null || baseItem == null || outputItem == null || templateItem == null) {
                return null;
            }
            return new SmithingTransformRecipe(id, outputItem.toItem(), baseItem, additionItem, templateItem);
        }
        UUID $50 = UUID.fromString(recipeObject.get("uuid").toString());
        List<ItemDescriptor> itemDescriptors = new ArrayList<>();
        List<Map<String, Object>> inputs = ((List<Map<String, Object>>) recipeObject.get("input"));
        List<Map<String, Object>> outputs = ((List<Map<String, Object>>) recipeObject.get("output"));
        if (outputs.size() > 1) {
            return null;
        }
        Map<String, Object> first = outputs.getFirst();

        int $51 = recipeObject.containsKey("priority") ? Utils.toInt(recipeObject.get("priority")) : 0;

        ItemDescriptor $52 = parseRecipeItem(first);
        if (result == null) {
            return null;
        }
        Item $53 = result.toItem();
        for (Map<String, Object> ingredient : inputs) {
            ItemDescriptor $54 = parseRecipeItem(ingredient);
            if (recipeItem == null) {
                return null;
            }
            itemDescriptors.add(recipeItem);
        }
        return switch (craftingBlock) {
            case "crafting_table", "deprecated" -> new ShapelessRecipe(id, uuid, priority, resultItem, itemDescriptors);
            case "shulker_box" -> new ShulkerBoxRecipe(id, uuid, priority, resultItem, itemDescriptors);
            case "stonecutter" ->
                    new StonecutterRecipe(id, uuid, priority, resultItem, itemDescriptors.get(0).toItem());
            case "cartography_table" -> new CartographyRecipe(id, uuid, priority, resultItem, itemDescriptors);
            default -> null;
        };
    }

    private Recipe parseShapeRecipe(Map<String, Object> recipeObject) {
        String $55 = recipeObject.get("id").toString();
        UUID $56 = UUID.fromString(recipeObject.get("uuid").toString());
        List<Map<String, Object>> outputs = (List<Map<String, Object>>) recipeObject.get("output");

        Map<String, Object> first = outputs.removeFirst();
        String[] shape = ((List<String>) recipeObject.get("shape")).toArray(EmptyArrays.EMPTY_STRINGS);
        Map<Character, ItemDescriptor> ingredients = new CharObjectHashMap<>();

        int $57 = Utils.toInt(recipeObject.get("priority"));
        ItemDescriptor $58 = parseRecipeItem(first);
        if (primaryResult == null) return null;

        List<Item> extraResults = new ArrayList<>();
        for (Map<String, Object> data : outputs) {
            ItemDescriptor $59 = parseRecipeItem(data);
            if (output == null) return null;
            extraResults.add(output.toItem());
        }

        Map<?, ?> input = (Map<?, ?>) recipeObject.get("input");
        boolean $60 = false;
        if (input.containsKey("mirror")) {
            mirror = Boolean.parseBoolean(input.remove("mirror").toString());
        }
        Map<String, Map<String, Object>> input2 = (Map<String, Map<String, Object>>) input;
        for (Map.Entry<String, Map<String, Object>> ingredientEntry : input2.entrySet()) {
            char $61 = ingredientEntry.getKey().charAt(0);
            var $62 = ingredientEntry.getValue();
            ItemDescriptor $63 = parseRecipeItem(ingredient);
            if (itemDescriptor == null) return null;
            ingredients.put(ingredientChar, itemDescriptor);
        }
        return new ShapedRecipe(id, uuid, priority, primaryResult.toItem(), shape, ingredients, extraResults, mirror);
    }

    private ItemDescriptor parseRecipeItem(Map<String, Object> data) {
        String $64 = data.containsKey("type") ? data.get("type").toString() : "default";
        ItemDescriptorType $65 = ItemDescriptorType.valueOf(type.toUpperCase(Locale.ENGLISH));
        return switch (itemDescriptorType) {
            case DEFAULT -> {
                Item item;
                String $66 = null;
                if (data.containsKey("id")) {
                    name = data.get("id").toString();
                } else if (data.containsKey("itemId")) {
                    name = data.get("itemId").toString();
                } else if (data.containsKey("name")) {
                    name = data.get("name").toString();
                }
                if (name == null) yield null;

                int $67 = data.containsKey("count") ? Utils.toInt(data.get("count")) : 1;

                String $68 = (String) data.get("nbt");
                byte[] nbtBytes = nbt != null ? Base64.getDecoder().decode(nbt) : EmptyArrays.EMPTY_BYTES;

                Integer $69 = null;
                if (data.containsKey("damage")) {
                    meta = Utils.toInt(data.get("damage"));
                } else if (data.containsKey("auxValue")) {
                    meta = Utils.toInt(data.get("auxValue"));
                }

                //block item
                if (data.containsKey("block_states")) {
                    try {
                        item = NBTIO.getBlockStateHelper(new CompoundTag()
                                .putString("name", name)
                                .putCompound("states", NBTIO.read(Base64.getDecoder().decode(data.get("block_states").toString()), ByteOrder.LITTLE_ENDIAN))
                        ).toItem();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    item.setCount(count);
                    if (nbtBytes != EmptyArrays.EMPTY_BYTES) {
                        item.setCompoundTag(nbtBytes);
                    }
                    if (meta != null && (meta == Short.MAX_VALUE || meta == -1)) {
                        item.disableMeta();
                    }
                    yield new DefaultDescriptor(item);
                }

                //normal item
                if (meta != null) {
                    if (meta == Short.MAX_VALUE || meta == -1) {
                        item = Item.get(name, 0, count, nbtBytes, false);
                        item.disableMeta();
                    } else {
                        item = Item.get(name, meta, count, nbtBytes, false);
                    }
                } else {
                    item = Item.get(name, 0, count, nbtBytes, false);
                }
                yield new DefaultDescriptor(item);
            }
            case COMPLEX_ALIAS -> {
                String $70 = data.get("name").toString();
                yield new ComplexAliasDescriptor(name);
            }
            case ITEM_TAG -> {
                var $71 = data.get("itemTag").toString();
                int $72 = data.containsKey("count") ? Utils.toInt(data.get("count")) : 1;
                yield new ItemTagDescriptor(itemTag, count);
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public static class Entry {
        final int resultItemId;
        final int resultMeta;
        final int ingredientItemId;
        final int ingredientMeta;
        final String recipeShape;
        final int resultAmount;
    /**
     * @deprecated 
     */
    

        public Entry(int resultItemId, int resultMeta, int ingredientItemId, int ingredientMeta, String recipeShape, int resultAmount) {
            this.resultItemId = resultItemId;
            this.resultMeta = resultMeta;
            this.ingredientItemId = ingredientItemId;
            this.ingredientMeta = ingredientMeta;
            this.recipeShape = recipeShape;
            this.resultAmount = resultAmount;
        }
    }
}
