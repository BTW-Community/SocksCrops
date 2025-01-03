package btw.community.sockthing.sockscrops.recipes;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.crafting.recipe.RecipeManager;
import btw.crafting.recipe.types.customcrafting.LogChoppingRecipe;
import btw.inventory.util.InventoryUtils;
import btw.item.BTWItems;
import net.minecraft.src.*;

public class SCRecipeHelper {

    public static ItemStack getPlanterContents(int blockID, int metadata){
        if (blockID == SCBlocks.planterGrass.blockID)
        {
            if (metadata == 0) return new ItemStack(Block.grass);
            return new ItemStack(SCBlocks.grassNutrition, 1, metadata * 2);
        }
        else if (blockID >= SCBlocks.planterFarmland.blockID || blockID <= SCBlocks.planterFarmlandDung.blockID ){
            return new ItemStack(BTWBlocks.looseDirt, 1, metadata);
        }
        else return null;
    }

    //----------- Knives -----------//
    public static void addKnifeCraftingRecipes(Item knife, Item ingot, Item stick, Item nugget, int amount) {
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

    public static void addKnifeCuttingRecipes(ItemStack output, ItemStack[] secondaryOutputs, ItemStack input) {
        CraftingManager.getInstance().getRecipeList().add(new KnifeCuttingRecipe(output, secondaryOutputs, input));
    }

    public static void addKnifeCuttingRecipes(ItemStack output, ItemStack[] secondaryOutputs, ItemStack outputLowQuality, ItemStack[] secondaryOutputsLowQuality,
                                            ItemStack input) {
        CraftingManager.getInstance().getRecipeList().add(new KnifeCuttingRecipe(output, secondaryOutputs, outputLowQuality, secondaryOutputsLowQuality, input));
    }
}
