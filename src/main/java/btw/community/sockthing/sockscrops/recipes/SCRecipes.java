package btw.community.sockthing.sockscrops.recipes;

import btw.block.BTWBlocks;
import btw.block.blocks.PlanterBlock;
import btw.community.sockthing.sockscrops.SocksCropsAddon;
import btw.community.sockthing.sockscrops.block.SCBlockIDs;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.PackedBlock;
import btw.community.sockthing.sockscrops.block.tileentities.DecoBlockIDs;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.item.items.BambooProgressiveItem;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import btw.crafting.recipe.RecipeManager;
import btw.inventory.util.InventoryUtils;
import btw.item.BTWItems;
import net.minecraft.src.*;

public class SCRecipes extends SCRecipeHelper {
    public static void initRecipes() {
        removeRecipes();
        if (SocksCropsAddon.isDecoInstalled()) initOverrideDecoRecipes();

        initKnifeRecipes();
        initPlanterRecipes();
        initHayRecipes();
        initMossRecipes();
        initSunflowerRecipes();
        initFlowerpotRecipes();
        initBambooRecipes();
        initFishTrapRecipes();

        initFoodRecipes();
        initCampfireRecipes();
        initOvenRecipes();
        initCauldronRecipes();

        initPackingRecipes();
        initLogChoppingRecipes();
        initHopperFilteringRecipes();
    }


    private static void removeRecipes() {
        //Remove old Farmland Planter Recipe
        RecipeManager.removeVanillaShapelessRecipe(new ItemStack(BTWBlocks.planterWithSoil), new Object[]{
                new ItemStack(BTWBlocks.planter),
                new ItemStack(BTWBlocks.looseDirt)
        });

        RecipeManager.removeVanillaRecipe(new ItemStack(Item.flowerPot, 1), new Object[] {"# #", " # ", '#', Item.brick});
    }

    private static void initOverrideDecoRecipes() {

        Item[] chisels = new Item[]{BTWItems.pointyStick, BTWItems.sharpStone, BTWItems.ironChisel, BTWItems.diamondChisel};

        //Remove Carved Pumpkins
        for (int i = 0; i < chisels.length; i++){
//            RecipeManager.removeShapelessRecipeWithSecondaryOutputIndicator(new ItemStack(Block.pumpkin),
//                    new ItemStack(Item.pumpkinSeeds, 4),
//                    new ItemStack[] {
//                            new ItemStack(BTWBlocks.freshPumpkin),
//                            new ItemStack(chisels[i])
//                    });

            RecipeManager.removeVanillaShapelessRecipe(new ItemStack(DecoBlockIDs.CARVED_PUMPKIN_ID, 1, 0),
                    new ItemStack[] {
                            new ItemStack(Block.pumpkin),
                            new ItemStack(chisels[i])
                    });
            RecipeManager.removeVanillaShapelessRecipe(new ItemStack(DecoBlockIDs.CARVED_PUMPKIN_ID, 1, 1),
                    new ItemStack[] {
                            new ItemStack(DecoBlockIDs.CARVED_PUMPKIN_ID, 1, 0),
                            new ItemStack(chisels[i])
                    });
            RecipeManager.removeVanillaShapelessRecipe(new ItemStack(DecoBlockIDs.CARVED_PUMPKIN_ID, 1, 2),
                    new ItemStack[] {
                            new ItemStack(DecoBlockIDs.CARVED_PUMPKIN_ID, 1, 1),
                            new ItemStack(chisels[i])
                    });
        }

        //Add carved
        for (int i = 0; i < chisels.length; i++){
            RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(new ItemStack(Block.pumpkin),
                    new ItemStack(Item.pumpkinSeeds, 4),
                    new ItemStack[] {
                            new ItemStack(BTWBlocks.freshPumpkin),
                            new ItemStack(chisels[i], 1, InventoryUtils.IGNORE_METADATA)
                    });

            RecipeManager.removeVanillaShapelessRecipe(new ItemStack(DecoBlockIDs.CARVED_PUMPKIN_ID, 1, 0),
                    new ItemStack[] {
                            new ItemStack(Block.pumpkin),
                            new ItemStack(chisels[i], 1, InventoryUtils.IGNORE_METADATA)
                    });
            RecipeManager.removeVanillaShapelessRecipe(new ItemStack(DecoBlockIDs.CARVED_PUMPKIN_ID, 1, 1),
                    new ItemStack[] {
                            new ItemStack(DecoBlockIDs.CARVED_PUMPKIN_ID, 1, 0),
                            new ItemStack(chisels[i], 1, InventoryUtils.IGNORE_METADATA)
                    });
            RecipeManager.removeVanillaShapelessRecipe(new ItemStack(DecoBlockIDs.CARVED_PUMPKIN_ID, 1, 2),
                    new ItemStack[] {
                            new ItemStack(DecoBlockIDs.CARVED_PUMPKIN_ID, 1, 1),
                            new ItemStack(chisels[i], 1, InventoryUtils.IGNORE_METADATA)
                    });
        }
    }

    private static void initFoodRecipes() {
        // Berries
        RecipeManager.addShapelessRecipe(new ItemStack(SCItems.berryBowl),
                new ItemStack[]{
                        new ItemStack(Item.bowlEmpty),
                        new ItemStack(Item.sugar),
                        new ItemStack(SCItems.sweetberry),
                        new ItemStack(SCItems.blueberry),
                });
    }

    private static void initBambooRecipes() {

        //Bamboo Weaving
        RecipeManager.addRecipe( new ItemStack( SCItems.bambooProgressiveItem, 1,
                BambooProgressiveItem.BAMBOO_WEAVING_MAX_DAMAGE - 1 ), new Object[] {
                "#S#",
                "SSS",
                "#S#",
                '#', SCItems.bamboo,
                'S', Item.stick
        } );

        //Packed
        RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.smallPackedBlock, 1, PackedBlock.BAMBOO),
                new ItemStack[]{
                        new ItemStack(SCItems.bamboo),
                        new ItemStack(SCItems.bamboo),
                        new ItemStack(SCItems.bamboo),
                        new ItemStack(SCItems.bamboo),
                        new ItemStack(SCItems.bamboo),
                        new ItemStack(SCItems.bamboo),
                        new ItemStack(SCItems.bamboo),
                        new ItemStack(SCItems.bamboo)
                });

        RecipeManager.addShapelessRecipe(new ItemStack(SCBlocks.smallPackedBlock, 1, PackedBlock.STRIPPED_BAMBOO),
                new ItemStack[]{
                        new ItemStack(SCItems.strippedBamboo),
                        new ItemStack(SCItems.strippedBamboo),
                        new ItemStack(SCItems.strippedBamboo),
                        new ItemStack(SCItems.strippedBamboo),
                        new ItemStack(SCItems.strippedBamboo),
                        new ItemStack(SCItems.strippedBamboo),
                        new ItemStack(SCItems.strippedBamboo),
                        new ItemStack(SCItems.strippedBamboo)
                });

        //Bamboo Grate
        /*
        RecipeManager.addRecipe( new ItemStack( SCBlocks.bambooGrate),
                new Object[] {
                        "#B#",
                        "BSB",
                        "#B#",
                        '#', Item.silk,
                        'B', SCItems.bamboo,
                        'S', Item.stick
                } );


        RecipeManager.addRecipe( new ItemStack( SCBlocks.bambooGrate),
                new Object[] {
                        "#B#",
                        "BSB",
                        "#B#",
                        '#', BTWItems.hempFibers,
                        'B', SCItems.bamboo,
                        'S', Item.stick
                } );
        */
    }

    private static void initFishTrapRecipes() {
        Item[] strings = { Item.silk, BTWItems.hempFibers, BTWItems.sinew };
        Item[] hooks = { BTWItems.ironNugget, BTWItems.boneFishHook };

        for (Item hook : hooks) {
            for (Item string : strings) {

                RecipeManager.addRecipe(new ItemStack(SCBlocks.fishTrap),
                        new Object[] {
                                "SBS",
                                "BFB",
                                "SBS",
                                'B', new ItemStack(SCItems.bambooWeave),
                                'S', new ItemStack(string),
                                'F', new ItemStack(hook), });
            }
        }
    }

    private static void initFlowerpotRecipes() {

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

    private static void initSunflowerRecipes() {
        RecipeManager.addShapelessRecipe( new ItemStack( SCItems.sunflowerSeeds, 2 ), new Object[] {
                new ItemStack( SCItems.sunflower )
        } );
    }

    private static void initKnifeRecipes() {
        addKnifeCraftingRecipes(SCItems.ironKnife, Item.ingotIron, Item.stick, BTWItems.ironNugget, 6);
        addKnifeCraftingRecipes(SCItems.goldKnife, Item.ingotGold, Item.stick, Item.goldNugget, 6);
        addKnifeCraftingRecipes(SCItems.diamondKnife, BTWItems.diamondIngot, Item.stick, BTWItems.diamondIngot, 1);
        addKnifeCraftingRecipes(SCItems.steelKnife, BTWItems.soulforgedSteelIngot, BTWItems.haft, BTWItems.soulforgedSteelIngot, 1);
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
                        getPlanterContents(SCBlockIDs.GRASS_PLANTER_ID + j, i),
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

    private static void initHayRecipes() {

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

    private static void initLogChoppingRecipes() {
        //Stripped Bamboo
        RecipeManager.addLogChoppingRecipe(new ItemStack(SCItems.strippedBamboo),
                null,
                new ItemStack(SCItems.bamboo));

        //Hollow Logs
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

    private static void initHopperFilteringRecipes() {
        //Sunflower
        RecipeManager.addHopperFilteringRecipe(new ItemStack(SCItems.sunflowerSeeds, 2),
                new ItemStack(SCItems.sunflower),
                new ItemStack(BTWBlocks.wickerPane));
    }

    private static void initPackingRecipes() {
        //Packed Blocks
        RecipeManager.addPistonPackingRecipe(SCBlocks.smallPackedBlock, PackedBlock.SUGAR_CANE,
                new ItemStack(Item.reed, 8));

        RecipeManager.addPistonPackingRecipe(SCBlocks.smallPackedBlock, PackedBlock.SHAFTS,
                new ItemStack(Item.stick, 8));

        RecipeManager.addPistonPackingRecipe(SCBlocks.smallPackedBlock, PackedBlock.BAMBOO,
                new ItemStack(SCItems.bamboo, 8));

        RecipeManager.addPistonPackingRecipe(SCBlocks.smallPackedBlock, PackedBlock.STRIPPED_BAMBOO,
                new ItemStack(SCItems.strippedBamboo, 8));

        //Moss
        RecipeManager.addPistonPackingRecipe(SCBlocks.mossBlock,
                new ItemStack(SCItems.mossBall, 8));

        //Hay & Straw
        RecipeManager.addPistonPackingRecipe(SCBlocks.hayBale,
                new ItemStack(SCItems.cuttings, 8));

        if (!SocksCropsAddon.isDecoInstalled()) {
            RecipeManager.addPistonPackingRecipe(SCBlocks.strawBale,
                    new ItemStack(BTWItems.straw, 8));
        }
    }

    static Item[] rawFish = new Item[]{SCItems.cod, SCItems.salmon, SCItems.tropicalFish};
    static Item[] cookedFish = new Item[]{SCItems.codCooked, SCItems.salmonCooked, SCItems.tropicalFishCooked};

    private static void initCampfireRecipes() {
        //Fish
        for (int i = 0; i < rawFish.length; i++) {
            RecipeManager.addCampfireRecipe(rawFish[i].itemID, new ItemStack(cookedFish[i]));
        }
    }

    private static void initOvenRecipes() {
        //Bamboo
        FurnaceRecipes.smelting().addSmelting(SCBlocks.bambooShoot.blockID, new ItemStack(SCItems.boiledBambooShoot), 0);

        //Fish
        for (int i = 0; i < rawFish.length; i++) {
            FurnaceRecipes.smelting().addSmelting(rawFish[i].itemID, new ItemStack(cookedFish[i]), 0);
        }
    }

    private static void initCauldronRecipes() {
        //Bamboo
        RecipeManager.addCauldronRecipe(
                new ItemStack(SCItems.boiledBambooShoot, 1),
                new ItemStack[] {
                        new ItemStack(SCBlocks.bambooShoot)
                });

        //Fish
        for (int i = 0; i < rawFish.length; i++) {
            RecipeManager.addCauldronRecipe(
                    new ItemStack(cookedFish[i], 1),
                    new ItemStack[] {
                            new ItemStack(rawFish[i])
                    });

            RecipeManager.addCauldronRecipe(
                    new ItemStack[] {
                            new ItemStack(BTWItems.chowder, 2),
                            new ItemStack(Item.bucketEmpty)
                    },

                    new ItemStack[] {
                            new ItemStack(Item.bucketMilk),
                            new ItemStack(cookedFish[i]),
                            new ItemStack(Item.bowlEmpty, 2)
                    });
        }
    }
}
