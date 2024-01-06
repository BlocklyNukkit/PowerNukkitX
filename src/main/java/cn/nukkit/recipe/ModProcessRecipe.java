package cn.nukkit.recipe;

import cn.nukkit.energy.EnergyType;
import cn.nukkit.recipe.CraftingManager;
import cn.nukkit.recipe.ItemDescriptor;
import cn.nukkit.item.Item;
import cn.nukkit.recipe.Recipe;
import cn.nukkit.recipe.RecipeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface ModProcessRecipe extends Recipe {
    String getCategory();

    @NotNull
    List<ItemDescriptor> getIngredients();

    @NotNull
    List<Item> getExtraResults();

    default String getRecipeId() {
        return CraftingManager.getShapelessItemDescriptorHash(getIngredients()).toString();
    }

    @Nullable
    default EnergyType getEnergyType() {
        return null;
    }

    default double getEnergyCost() {
        return 0;
    }

    default boolean costEnergy() {
        return getEnergyType() != null && getEnergyCost() > 0;
    }

    @Override
    default RecipeType getType() {
        return RecipeType.MOD_PROCESS;
    }

    @NotNull
    default List<Item> getAllResults() {
        var mainResult = getResult();
        var extraResults = getExtraResults().toArray(Item.EMPTY_ARRAY);
        var results = new Item[extraResults.length + (mainResult == null || mainResult.isNull() ? 0 : 1)];
        if (mainResult != null && !mainResult.isNull()) {
            results[0] = mainResult;
        }
        System.arraycopy(extraResults, 0, results, results.length - extraResults.length, extraResults.length);
        return List.of(results);
    }

    @Override
    default void registerToCraftingManager(CraftingManager manager) {
        manager.registerModProcessRecipe(this);
    }

    default boolean matchItems(@NotNull List<Item> inputItems) {
        for (var each : getIngredients()) {
            if (each == null) {
                continue;
            }
            var found = false;
            for (var input : inputItems) {
                if (input != null && !input.isNull() && each.match(input)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}