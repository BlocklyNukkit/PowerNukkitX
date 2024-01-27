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

package cn.nukkit.inventory;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.BlockSmithingTable;
import cn.nukkit.item.Item;
import cn.nukkit.recipe.SmithingRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * @author joserobjr
 * @since 2020-09-28
 */


public class SmithingInventory extends ContainerInventory implements CraftTypeInventory{
    private static final int EQUIPMENT = 0;
    private static final int INGREDIENT = 1;
    private Item currentResult = Item.AIR;


    public SmithingInventory(BlockSmithingTable blockSmithingTable) {
        super(blockSmithingTable, InventoryType.SMITHING_TABLE, 3);
    }


    @Nullable
    public SmithingRecipe matchRecipe() {
        return Server.getInstance().getRecipeRegistry().findSmithingRecipe(getEquipment(), getIngredient());
    }

    @Override
    public void onSlotChange(int index, Item before, boolean send) {
        if (index == EQUIPMENT || index == INGREDIENT) {
            updateResult();
        }
        super.onSlotChange(index, before, send);
    }


    public void updateResult() {
        Item result;
        SmithingRecipe recipe = matchRecipe();
        if (recipe == null) {
            result = Item.AIR;
        } else {
            result = recipe.getFinalResult(getEquipment());
        }
        setResult(result);
    }

    private void setResult(Item result) {
        this.currentResult = result;
    }


    @NotNull
    public Item getResult() {
        SmithingRecipe recipe = matchRecipe();
        if (recipe == null) {
            return Item.AIR;
        }
        return recipe.getFinalResult(getEquipment());
    }


    public Item getEquipment() {
        return getItem(EQUIPMENT);
    }


    public void setEquipment(Item equipment) {
        setItem(EQUIPMENT, equipment);
    }


    public Item getIngredient() {
        return getItem(INGREDIENT);
    }


    public void setIngredient(Item ingredient) {
        setItem(INGREDIENT, ingredient);
    }

    @Override
    public void onClose(Player who) {
        super.onClose(who);

        who.giveItem(getItem(EQUIPMENT), getItem(INGREDIENT));

        this.clear(EQUIPMENT);
        this.clear(INGREDIENT);
    }

    public @NotNull Item getCurrentResult() {
        return currentResult;
    }
}
