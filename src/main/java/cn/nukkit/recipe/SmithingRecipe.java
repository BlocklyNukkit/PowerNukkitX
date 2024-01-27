/*
 * https://PowerNukkit.org - The Nukkit you know but Powerful!
 * Copyright (C) 2020  José Roberto de Araújo Júnior
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cn.nukkit.recipe;

import cn.nukkit.item.Item;
import cn.nukkit.recipe.descriptor.ItemDescriptor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

/**
 * @author joserobjr
 * @since 2020-09-28
 */
@ToString
public class SmithingRecipe extends BaseRecipe {

    public SmithingRecipe(@NotNull String recipeId, Item result, ItemDescriptor base, ItemDescriptor addition, ItemDescriptor template) {
        super(recipeId);
        this.ingredients.add(base);
        this.ingredients.add(addition);
        this.ingredients.add(template);

        this.results.add(result);
    }

    @Override
    public boolean match(Input input) {
        return true;
    }

    public Item getResult() {
        return results.get(0);
    }

    public Item getFinalResult(Item equip) {
        Item finalResult = getResult().clone();

        if (equip.hasCompoundTag()) {
            finalResult.setCompoundTag(equip.getCompoundTag());
        }

        int maxDurability = finalResult.getMaxDurability();
        if (maxDurability <= 0 || equip.getMaxDurability() <= 0) {
            return finalResult;
        }

        int damage = equip.getDamage();
        if (damage <= 0) {
            return finalResult;
        }

        finalResult.setDamage(Math.min(maxDurability, damage));
        return finalResult;
    }

    @Override
    public RecipeType getType() {
        return RecipeType.SMITHING_TRANSFORM;
    }

    public ItemDescriptor getBase() {
        return ingredients.get(0);
    }

    public ItemDescriptor getAddition() {
        return ingredients.get(1);
    }

    public ItemDescriptor getTemplate() {
        return ingredients.get(2);
    }
}
