package btw.community.sockthing.sockscrops.recipes;

import btw.block.BTWBlocks;
import btw.block.blocks.PlanterBlock;
import btw.block.blocks.TallGrassBlock;
import btw.community.sockthing.sockscrops.SocksCropsAddon;
import btw.community.sockthing.sockscrops.block.SCBlockIDs;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.PlanterBaseBlock;
import btw.community.sockthing.sockscrops.block.blocks.PlanterGrassBlock;
import btw.community.sockthing.sockscrops.block.tileentities.LargeFlowerPotTileEntity;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import btw.crafting.recipe.RecipeManager;
import btw.inventory.util.InventoryUtils;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class SCRecipes extends SCRecipeHelper {
    public static void initRecipes() {
        removeRecipes();
        initKnifeRecipes();
        initPlanterRecipes();
        initHayRecipes();
        initHollowLogRecipes();
        initMossRecipes();
        initSunflowerRecipes();
        initFlowerpotRecipes();

        initFoodRecipes();
    }

    private static void initFoodRecipes() {
        initBerryRecipes();
    }

    private static void initBerryRecipes() {
        RecipeManager.addShapelessRecipe(new ItemStack(SCItems.berryBowl),
                new ItemStack[]{
                        new ItemStack(Item.bowlEmpty),
                        new ItemStack(Item.sugar),
                        new ItemStack(SCItems.sweetberry),
                        new ItemStack(SCItems.blueberry),
                });
    }

    private static void initFlowerpotRecipes() {

        RecipeManager.removeVanillaRecipe(new ItemStack(Item.flowerPot, 1), new Object[] {"# #", " # ", '#', Item.brick});
        RecipeManager.addRecipe(new ItemStack(SCItems.flowerPot, 1), new Object[] {"# #", " # ", '#', Item.brick});

        RecipeManager.addRecipe(new ItemStack(SCItems.largeFlowerpot, 1), new Object[] {
                "B B",
                "BBB",
                'B', Item.brick
        });
    }

    private static void initMossRecipes() {

        RecipeManager.addRecipe(new ItemStack(SCBlocks.mossCarpet, 1), new Object[] {
                "MMM",
                'M', SCItems.mossBall
        });

        RecipeManager.addShapelessRecipe(new ItemStack(SCItems.mossBall, 3),
                new ItemStack[]{
                        new ItemStack(SCBlocks.mossCarpet)
                });

        RecipeManager.addPistonPackingRecipe(SCBlocks.mossBlock,
                new ItemStack(SCItems.mossBall, 8));

        RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.mossBlock),
                new ItemStack[]{
                        new ItemStack(SCItems.mossBall),
                        new ItemStack(SCItems.mossBall),
                        new ItemStack(SCItems.mossBall),
                        new ItemStack(SCItems.mossBall),
                        new ItemStack(SCItems.mossBall),
                        new ItemStack(SCItems.mossBall),
                        new ItemStack(SCItems.mossBall),
                        new ItemStack(SCItems.mossBall)
                });

        RecipeManager.addShapelessRecipe(new ItemStack(SCItems.mossBall, 8),
                new ItemStack[]{
                        new ItemStack(SCBlocks.mossBlock)
                });
    }

    private static void removeRecipes() {
        //Remove old Farmland Planter Recipe
        RecipeManager.removeVanillaShapelessRecipe(new ItemStack(BTWBlocks.planterWithSoil), new Object[]{
                new ItemStack(BTWBlocks.planter),
                new ItemStack(BTWBlocks.looseDirt)
        });
    }

    private static void initSunflowerRecipes() {
        RecipeManager.addShapelessRecipe( new ItemStack( SCItems.sunflowerSeeds, 2 ), new Object[] {
                new ItemStack( SCItems.sunflower )
        } );

        RecipeManager.addHopperFilteringRecipe(new ItemStack(SCItems.sunflowerSeeds, 2),
                new ItemStack(SCItems.sunflower),
                new ItemStack(BTWBlocks.wickerPane));
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
                new ItemStack(SCBlocks.grassNutrition, 1, NutritionUtils.REDUCED_NUTRITION_LEVEL + 1)
        });

        RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.planterGrass, 1, NutritionUtils.LOW_NUTRITION_LEVEL), new Object[]{
                new ItemStack(BTWBlocks.planter),
                new ItemStack(SCBlocks.grassNutrition, 1, NutritionUtils.LOW_NUTRITION_LEVEL + 1)
        });

        RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.planterGrass, 1, NutritionUtils.DEPLETED_NUTRITION_LEVEL), new Object[]{
                new ItemStack(BTWBlocks.planter),
                new ItemStack(SCBlocks.grassNutrition, 1, NutritionUtils.DEPLETED_NUTRITION_LEVEL + 1)
        });

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(
                        new ItemStack(BTWBlocks.planter ),
                        getContents(SCBlockIDs.GRASS_PLANTER_ID + j, i),
                        new Object[] {
                                new ItemStack( SCBlockIDs.GRASS_PLANTER_ID + j, 1, i)
                        });
            }
        }

        RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(
                new ItemStack(BTWBlocks.planter ),
                new ItemStack(BTWBlocks.looseDirt, 1, 0),
                new Object[] {
                        new ItemStack( BTWBlocks.planterWithSoil, 1, 0)
                });

        RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(
                new ItemStack(BTWBlocks.planter ),
                new ItemStack(Block.slowSand, 1),
                new Object[] {
                        new ItemStack( BTWBlocks.planter, 1, PlanterBlock.TYPE_SOUL_SAND)
                });
    }

    public static ItemStack getContents(int blockID, int metadata){
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

    private static void initHayRecipes() {

        RecipeManager.addPistonPackingRecipe(SCBlocks.hayBale,
                new ItemStack(SCItems.cuttings, 8));

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

        if (!SocksCropsAddon.isDecoInstalled()){
            RecipeManager.addPistonPackingRecipe(SCBlocks.strawBale,
                    new ItemStack(BTWItems.straw, 8));

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


    private static void initHollowLogRecipes() {
        for (int i = 0; i < 4; i++) {
            RecipeManager.addLogChoppingRecipe(new ItemStack(BTWItems.sawDust, 2),
                    new ItemStack[]{
                            new ItemStack(BTWItems.bark, 1, i),
                    },
                    new ItemStack(SCBlocks.hollowLog, 1, i));
        }

        RecipeManager.addLogChoppingRecipe(new ItemStack(BTWItems.sawDust, 2),
                new ItemStack[]{
                        new ItemStack(BTWItems.bark, 1, 4),
                },
                new ItemStack(SCBlocks.hollowLog2, 1, 0));

        if(SocksCropsAddon.isDecoInstalled()){

            for (int i = 0; i < 4; i++) {
                RecipeManager.addLogChoppingRecipe(new ItemStack(BTWItems.sawDust, 2),
                        new ItemStack[] {
                                new ItemStack(BTWItems.bark, 1, i + 5),
                        },
                        new ItemStack(SCBlocks.decoHollowLog, 1, i));
            }

            for (int i = 0; i < 4; i++) {
                RecipeManager.addLogChoppingRecipe(new ItemStack(BTWItems.sawDust, 2),
                        new ItemStack[] {
                                new ItemStack(BTWItems.bark, 1, i + 9),
                        },
                        new ItemStack(SCBlocks.decoHollowLog2, 1, i));
            }

            for (int i = 0; i < 2; i++) {
                RecipeManager.addLogChoppingRecipe(new ItemStack(BTWItems.sawDust, 2),
                        new ItemStack[] {
                                new ItemStack(BTWItems.bark, 1, i + 13),
                        },
                        new ItemStack(SCBlocks.decoHollowLog3, 1, i));
            }
        }
    }

}
