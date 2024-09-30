package btw.community.sockthing.sockscrops.recipes;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import btw.crafting.recipe.RecipeManager;
import btw.item.BTWItems;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class SCRecipes extends SCRecipeHelper {
    public static void initRecipes() {
        removeRecipes();
        initKnifeRecipes();
        initPlanterRecipes();
        initHayRecipes();
    }

    private static void removeRecipes() {
        //Remove old Farmland Planter Recipe
        RecipeManager.removeVanillaShapelessRecipe(new ItemStack(BTWBlocks.planterWithSoil), new Object[]{
                new ItemStack(BTWBlocks.planter),
                new ItemStack(BTWBlocks.looseDirt)
        });
    }

    private static void initKnifeRecipes() {
        addKnifeRecipe(SCItems.ironKnife, Item.ingotIron, Item.stick, BTWItems.ironNugget, 6);
        addKnifeRecipe(SCItems.goldKnife, Item.ingotGold, Item.stick, Item.goldNugget, 6);
        addKnifeRecipe(SCItems.diamondKnife, BTWItems.diamondIngot, Item.stick, BTWItems.diamondIngot, 1);
        addKnifeRecipe(SCItems.steelKnife, BTWItems.soulforgedSteelIngot, BTWItems.haft, BTWItems.soulforgedSteelIngot, 1);
    }

    private static void initPlanterRecipes() {

        for (int nutritionLevel = 0; nutritionLevel < 4; nutritionLevel++) {
            RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.planterFarmland, 1, nutritionLevel), new Object[]{
                    new ItemStack(BTWBlocks.planter),
                    new ItemStack(Block.dirt, 1, nutritionLevel)
            });
        }

        for (int nutritionLevel = 0; nutritionLevel < 4; nutritionLevel++) {
            RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.planterFarmland, 1, nutritionLevel), new Object[]{
                    new ItemStack(BTWBlocks.planter),
                    new ItemStack(BTWBlocks.looseDirt, 1, nutritionLevel)
            });
        }

        RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.planterGrass), new Object[]{
                new ItemStack(BTWBlocks.planter),
                new ItemStack(Block.grass)
        });

        RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.planterGrass, 1, NutritionUtils.REDUCED_NUTRITION_LEVEL), new Object[]{
                new ItemStack(BTWBlocks.planter),
                new ItemStack(SCBlocks.grassNutrition, 1, NutritionUtils.REDUCED_NUTRITION_LEVEL)
        });

        RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.planterGrass, 1, NutritionUtils.LOW_NUTRITION_LEVEL), new Object[]{
                new ItemStack(BTWBlocks.planter),
                new ItemStack(SCBlocks.grassNutrition, 1, NutritionUtils.LOW_NUTRITION_LEVEL)
        });

        RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.planterGrass, 1, NutritionUtils.DEPLETED_NUTRITION_LEVEL), new Object[]{
                new ItemStack(BTWBlocks.planter),
                new ItemStack(SCBlocks.grassNutrition, 1, NutritionUtils.DEPLETED_NUTRITION_LEVEL)
        });

    }

    private static void initHayRecipes() {

        RecipeManager.addPistonPackingRecipe(SCBlocks.hayBale,
                new ItemStack(SCItems.cuttings, 8));

        RecipeManager.addPistonPackingRecipe(SCBlocks.strawBale,
                new ItemStack(BTWItems.straw, 8));

        RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.hayBale),
                new ItemStack[]{
                        new ItemStack(SCItems.cuttings),
                        new ItemStack(SCItems.cuttings),
                        new ItemStack(SCItems.cuttings),
                        new ItemStack(SCItems.cuttings),
                        new ItemStack(SCItems.cuttings),
                        new ItemStack(SCItems.cuttings),
                        new ItemStack(SCItems.cuttings),
                        new ItemStack(SCItems.cuttings)
                });

        RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.strawBale),
                new ItemStack[]{
                        new ItemStack(BTWItems.straw),
                        new ItemStack(BTWItems.straw),
                        new ItemStack(BTWItems.straw),
                        new ItemStack(BTWItems.straw),
                        new ItemStack(BTWItems.straw),
                        new ItemStack(BTWItems.straw),
                        new ItemStack(BTWItems.straw),
                        new ItemStack(BTWItems.straw)
                });


    }
}
