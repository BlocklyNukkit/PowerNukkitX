package cn.nukkit.recipe;

import cn.nukkit.item.Item;
import cn.nukkit.recipe.CraftingManager;
import cn.nukkit.recipe.Recipe;
import cn.nukkit.recipe.RecipeType;
import lombok.ToString;

import java.util.UUID;


@ToString
public class MultiRecipe implements Recipe {

    private final UUID id;


    public MultiRecipe(UUID id) {
        this.id = id;
    }

    @Override
    public String getRecipeId() {
        return id.toString();
    }

    @Override
    public Item getResult() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerToCraftingManager(CraftingManager manager) {
        manager.registerMultiRecipe(this);
    }

    @Override
    public RecipeType getType() {
        return RecipeType.MULTI;
    }

    public UUID getId() {
        return this.id;
    }
}
