package btw.community.sockthing.sockscrops.recipes;

import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import btw.inventory.util.InventoryUtils;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CookingPotRecipe {
    private final ItemStack[] ingredients;
    private final ItemStack result;
    private final ItemStack requiredLiquid; // e.g. water or milk

    public CookingPotRecipe(ItemStack[] ingredients, ItemStack requiredLiquid, ItemStack result) {
        this.ingredients = ingredients;
        this.requiredLiquid = requiredLiquid;
        this.result = result;
    }

    public ItemStack[] getIngredients() {
        return ingredients;
    }

    public ItemStack getRequiredLiquid() {
        return requiredLiquid;
    }

    public ItemStack getResult() {
        return result;
    }

    public boolean doesInventoryContainIngredients(IInventory inventory) {
        if (ingredients != null && ingredients.length > 0) {
            // Combine duplicates into a map: itemID -> total required
            Map<Integer, Integer> requiredMap = new HashMap<>();
            for (ItemStack ingredient : ingredients) {
                if (ingredient != null) {
                    requiredMap.put(
                            ingredient.itemID,
                            requiredMap.getOrDefault(ingredient.itemID, 0) + ingredient.stackSize
                    );
                }
            }

            // Check inventory against totals
            for (Map.Entry<Integer, Integer> entry : requiredMap.entrySet()) {
                int itemId = entry.getKey();
                int requiredAmount = entry.getValue();

                int totalCount = 0;
                ArrayList<Integer> slots = InventoryUtils.getAllOccupiedStacksOfItem(inventory, itemId);

                for (Integer slot : slots) {
                    ItemStack stack = inventory.getStackInSlot(slot);
                    if (stack != null && stack.itemID == itemId) {
                        totalCount += stack.stackSize;
                    }
                }

                if (totalCount < requiredAmount) {
                    return false; // Not enough of this ingredient
                }
            }
        }
        return true;
    }

    public boolean doesInventoryContainLiquidIngredients(IInventory inventory) {
        if (requiredLiquid != null) {
            ItemStack tempStack = (ItemStack) ((CookingPotTileEntity)inventory).getLiquidStack();
            if (tempStack == null) return false;
            if (tempStack.isItemEqual(requiredLiquid) && tempStack.stackSize == requiredLiquid.stackSize) return true;
        }
        return false;
    }

    public boolean consumeInventoryIngredients(IInventory inventory) {
        boolean bSuccessful = true;

        if (ingredients != null && ingredients.length > 0) {
            for (int listIndex = 0; listIndex < ingredients.length; listIndex++) {
                ItemStack tempStack = (ItemStack) ingredients[listIndex];

                if (tempStack != null) {
                    inventory.setInventorySlotContents(listIndex, null);
//                    if (!InventoryUtils.consumeItemsInInventory(inventory,
//                            tempStack.getItem().itemID, tempStack.getItemDamage(),
//                            tempStack.stackSize, false)) {
//                        bSuccessful = false;
//                    }
                }
            }
        }

        return bSuccessful;
    }

    public boolean consumeInventoryLiquidIngredients(IInventory inventory) {
        boolean bSuccessful = true;

        if (requiredLiquid != null) {
            ItemStack tempStack = (ItemStack) ((CookingPotTileEntity)inventory).getLiquidStack();

            if (tempStack != null) {
                ((CookingPotTileEntity)inventory).setLiquidStack(null);
            }
            else bSuccessful = false;
        }

        return bSuccessful;
    }
}


