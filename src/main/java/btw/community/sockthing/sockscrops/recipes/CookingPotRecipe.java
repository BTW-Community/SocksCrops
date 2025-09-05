package btw.community.sockthing.sockscrops.recipes;

import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import btw.inventory.util.InventoryUtils;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

import java.util.*;

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
        if (ingredients == null || ingredients.length == 0) return true;

        // Copy required items into a mutable list
        List<ItemStack> requiredList = new ArrayList<>();
        for (ItemStack item : ingredients) {
            if (item != null) requiredList.add(item.copy());
        }

        // Check each item slot
        for (int slot = 0; slot < 6; slot++) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (stack == null) continue;

            // Try to match this stack against a required item
            Iterator<ItemStack> iterator = requiredList.iterator();
            while (iterator.hasNext()) {
                ItemStack required = iterator.next();
                if (stack.itemID == required.itemID) {
                    iterator.remove(); // matched
                    break;
                }
            }
        }

        // If any required items remain unmatched, return false
        return requiredList.isEmpty();
    }

    public boolean doesInventoryContainLiquidIngredients(IInventory inventory) {
        // If no liquid is required, always true
        if (requiredLiquid == null) return true;

        ItemStack liquidStack = ((CookingPotTileEntity) inventory).getLiquidStack();
        if (liquidStack == null) return false;

        // Check that item matches and we have enough
        return liquidStack.isItemEqual(requiredLiquid)
                && liquidStack.stackSize == requiredLiquid.stackSize;
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


