package btw.community.sockthing.sockscrops.recipes;

import btw.crafting.recipe.RecipeManager;
import btw.inventory.util.InventoryUtils;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class SCRecipeHelper {
    //----------- Knives -----------//
    public static void addKnifeRecipe(Item knife, Item ingot, Item stick, Item nugget, int amount) {
        RecipeManager.addRecipe(new ItemStack(knife),
                new Object[]{
                        "I ",
                        " S",
                        'I', new ItemStack(ingot, 1),
                        'S', new ItemStack(stick, 1)
                });

        RecipeManager.addRecipe(new ItemStack(knife),
                new Object[]{
                        " I",
                        "S ",
                        'I', new ItemStack(ingot, 1),
                        'S', new ItemStack(stick, 1)
                });

        //smelting
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(nugget, amount),
                new ItemStack[]{
                        new ItemStack(knife, 1, InventoryUtils.IGNORE_METADATA)
                });
    }
}
