package btw.community.sockthing.sockscrops.block;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.SocksCropsAddon;
import btw.community.sockthing.sockscrops.block.blocks.*;
import btw.community.sockthing.sockscrops.block.tileentities.FlowerPotTileEntity;
import btw.community.sockthing.sockscrops.block.tileentities.HayDryingTileEntity;
import btw.community.sockthing.sockscrops.block.tileentities.LargeFlowerPotTileEntity;
import btw.community.sockthing.sockscrops.item.SCItemIDs;
import btw.community.sockthing.sockscrops.item.items.DoubleTallWaterPlantItem;
import btw.community.sockthing.sockscrops.item.items.FlowerLilyItem;
import btw.community.sockthing.sockscrops.item.items.SideShroomItemBlock;
import btw.community.sockthing.sockscrops.item.items.WaterPlantItem;
import btw.community.sockthing.sockscrops.mixins.PlaceAsBlockItemAccessor;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import btw.item.items.PlaceAsBlockItem;
import net.minecraft.src.*;

public class SCBlocks {

//    2600 - 2629 Nutrition
//    2630 - 2049 Planters
//    2650 - 2689 Plants
//    2690 - 2694 Moss
//    2695 - 2699 Hollow Logs
//    2700 - 2704 Berries
//    2705 - 2799 Crops
//    2800 - 2849 Misc
//    2850 - 2899 Tile Entities

    //----------- Grass -----------//
    public static Block grassNutrition;

    //----------- Farmland -----------//
    public static Block farmlandFullNutrition;
    public static Block farmlandReducedNutrition;
    public static Block farmlandLowNutrition;
    public static Block farmlandDepletedNutrition;

    public static Block farmlandFertilizedFullNutrition;
    public static Block farmlandFertilizedReducedNutrition;
    public static Block farmlandFertilizedLowNutrition;
    public static Block farmlandFertilizedDepletedNutrition;

    public static Block farmlandDungFullNutrition;
    public static Block farmlandDungReducedNutrition;
    public static Block farmlandDungLowNutrition;
    public static Block farmlandDungDepletedNutrition;

    public static Block farmlandMulchFullNutrition;
    public static Block farmlandMulchReducedNutrition;
    public static Block farmlandMulchLowNutrition;
    public static Block farmlandMulchDepletedNutrition;

    //----------- Planters -----------//
    public static Block planterGrass;
    public static Block planterFarmland;
    public static Block planterFarmlandFertilized;
    public static Block planterFarmlandDung;

    //----------- Plants -----------//
    public static Block tallFlower;
    public static Block doubleTallPlant;
    public static Block shortGrass;
    public static Block multiFlower;
    public static Block clover;
    public static Block cloverPurple;
    public static Block cloverWhite;
    public static Block cloverRed;
    public static Block sideShroom;
    public static Block flowerLily;
    public static Block shortWaterPlant;
    public static Block tallWaterPlant;

    //----------- Decorative  -----------//
    public static Block grownWicker;
    public static Block grownSlats;
    public static Block grownGrate;
    public static Block grownIronBars;
    public static Block grownWroughtBars;
    public static Block mossCarpet;
    public static Block mossBlock;
    public static Block hollowLog;
    public static Block hollowLog2;
    public static Block decoHollowLog;
    public static Block decoHollowLog2;
    public static Block decoHollowLog3;

    //----------- Bushes  -----------//
    public static Block sweetberryBush;
    public static Block blueberryBush;

    //----------- Sunflower  -----------//
    public static Block sunflowerCrop;
    public static Block sunflowerTopCrop;

    //----------- Hay  -----------//
    public static Block dryingHay;
    public static Block driedHay;
    public static Block hayBale;
    public static Block strawBale;
    public static Block smallPackedBlock;
    public static Block largeFlowerpot;
    public static Block flowerPot;



    public static void initBlocks() {
        initVanillaOverrides();

        initTileEnityMapping();
        initPlantBlocks();
        initCropBlocks();
        initBushBlocks();
        initNutritionBlocks();
        initHayBlocks();
        initPackedBlocks();
        initMossBlocks();
        initHollowLogs();
        initGrownPanes();
        initFlowerPots();
    }

    private static void initNutritionBlocks() {
        initDirtRelatedBlocks();
        initFarmlandBlocks();
        initPlanterBlocks();
    }

    private static void initVanillaOverrides() {
        //----------- Dandilion -----------//
        Item.itemsList[Block.plantYellow.blockID] = new ItemMultiTextureTile(Block.plantYellow.blockID - 256, Block.plantYellow,
                new String[]{"flower", "dandilion"});

        //----------- Dirt -----------//
        Item.itemsList[Block.dirt.blockID] = new ItemMultiTextureTile(Block.dirt.blockID - 256, Block.dirt,
                NutritionUtils.DIRT_TEXTURE_NAMES);

        Item.itemsList[BTWBlocks.looseDirt.blockID] = new ItemMultiTextureTile(BTWBlocks.looseDirt.blockID - 256, BTWBlocks.looseDirt,
                NutritionUtils.LOOSE_DIRT_TEXTURE_NAMES);

        //----------- Vanilla Farmland Overrides -----------//
        Item.itemsList[BTWBlocks.farmland.blockID].setCreativeTab(null);
        Item.itemsList[BTWBlocks.fertilizedFarmland.blockID].setCreativeTab(null);

    }

    private static void initTileEnityMapping() {
        TileEntity.addMapping(HayDryingTileEntity.class, "SCHayDrying");
        TileEntity.addMapping(LargeFlowerPotTileEntity.class, "SCLargeFlowerpot");
        TileEntity.addMapping(FlowerPotTileEntity.class, "SCFlowerPot");
    }

    private static void initPlantBlocks() {
        if (!SocksCropsAddon.isDecoInstalled()){
            doubleTallPlant = new DoubleTallPlantBlock(SCBlockIDs.DOUBLE_TALL_PLANT_ID, "tall_plant");
            Item.itemsList[doubleTallPlant.blockID] = new ItemMultiTextureTile(SCBlockIDs.DOUBLE_TALL_PLANT_ID - 256, doubleTallPlant,
                    DoubleTallPlantBlock.types);
        }

        tallFlower = new DoubleTallFlowerBlock(SCBlockIDs.DOUBLE_TALL_FLOWER_ID, "tall_flower");
//        Item.itemsList[tallFlower.blockID] = new ItemMultiTextureTile(SCBlockIDs.TALL_FLOWER_ID - 256, tallFlower,
//                new String[]{"", "", "sunflower"});

        shortGrass = new ShortGrassBlock(SCBlockIDs.SHORT_GRASS_ID, "short_grass");
        Item.itemsList[shortGrass.blockID] = new ItemMultiTextureTile(SCBlockIDs.SHORT_GRASS_ID - 256, shortGrass,
                new String[]{"", "grass"});

//        multiFlower = new MultiFlowerBlock(SCBlockIDs.MUTLIFLOWER_ID);
//        Item.itemsList[multiFlower.blockID] = new ItemBlock(SCBlockIDs.MUTLIFLOWER_ID - 256);

        clover = new CloverBlock(SCBlockIDs.CLOVER_ID, 0, "clover");
        Item.itemsList[clover.blockID] = new ItemBlock(SCBlockIDs.CLOVER_ID - 256);

        cloverPurple = new CloverBlock(SCBlockIDs.PURPLE_CLOVER_ID, 1, "clover_purple");
        Item.itemsList[cloverPurple.blockID] = new ItemBlock(SCBlockIDs.PURPLE_CLOVER_ID - 256);

        cloverWhite = new CloverBlock(SCBlockIDs.WHITE_CLOVER_ID, 2, "clover_white");
        Item.itemsList[cloverWhite.blockID] = new ItemBlock(SCBlockIDs.WHITE_CLOVER_ID - 256);

        cloverRed = new CloverBlock(SCBlockIDs.RED_CLOVER_ID, 3, "clover_red");
        Item.itemsList[cloverRed.blockID] = new ItemBlock(SCBlockIDs.RED_CLOVER_ID - 256);

        sideShroom = new SideShroomBlock(SCBlockIDs.SIDESHROOM_ID, "sideshroom");
        Item.itemsList[sideShroom.blockID] = new SideShroomItemBlock(SCBlockIDs.SIDESHROOM_ID - 256, sideShroom, SideShroomItemBlock.names);

        flowerLily = new FlowerLilyBlock(SCBlockIDs.FLOWER_LILY_ID,  "lilyRose");
        Item.itemsList[flowerLily.blockID] = new FlowerLilyItem(SCBlockIDs.FLOWER_LILY_ID - 256, flowerLily, FlowerLilyBlock.types);

        shortWaterPlant = new WaterPlantBlock(SCBlockIDs.SHORT_WATER_PLANT_ID,  "waterPlantShort");
        Item.itemsList[shortWaterPlant.blockID] = new WaterPlantItem(SCBlockIDs.SHORT_WATER_PLANT_ID - 256, shortWaterPlant, WaterPlantBlock.types);

        tallWaterPlant = new DoubleTallWaterPlantBlock(SCBlockIDs.TALL_WATER_PLANT_ID,  "waterPlantTall");
        Item.itemsList[tallWaterPlant.blockID] = new DoubleTallWaterPlantItem(SCBlockIDs.TALL_WATER_PLANT_ID - 256, tallWaterPlant, DoubleTallWaterPlantBlock.types);
    }

    private static void initCropBlocks() {
        sunflowerCrop = new SunflowerCropBlock(SCBlockIDs.SUNFLOWER_CROP_ID, "sunflower_bottom");
        sunflowerTopCrop = new SunflowerCropTopBlock(SCBlockIDs.SUNFLOWER_CROP_TOP_ID, "sunflower_top");
    }

    private static void initBushBlocks() {
        sweetberryBush = new BerryBushBlock(SCBlockIDs.SWEETBERRY_BUSH_ID, SCItemIDs.SWEETBERRY_ID, SCItemIDs.SWEETBERRY_ROOTS_ID,
                "sweetberry");

        blueberryBush = new BerryBushBlock(SCBlockIDs.BLUEBERRY_BUSH_ID, SCItemIDs.BLUEBERRY_ID, SCItemIDs.BLUEBERRY_ROOTS_ID,
                "blueberry");
    }

    private static void initHayBlocks() {
        dryingHay = new HayDryingBlock(SCBlockIDs.DRYING_HAY_ID, "hay_drying");
        Item.itemsList[dryingHay.blockID] = new ItemBlock(SCBlockIDs.DRYING_HAY_ID - 256);

        driedHay = new HayDriedBlock(SCBlockIDs.DRIED_HAY_ID, "hay_dried");
        Item.itemsList[driedHay.blockID] = new ItemBlock(SCBlockIDs.DRIED_HAY_ID - 256);

        hayBale = new HayBaleBlock(SCBlockIDs.HAY_BALE_ID, "bale_hay");
        Item.itemsList[hayBale.blockID] = new ItemBlock(SCBlockIDs.HAY_BALE_ID - 256);

        if (!SocksCropsAddon.isDecoInstalled()){
            strawBale = new StrawBaleBlock(SCBlockIDs.STRAW_BALE_ID, "bale_straw");
            Item.itemsList[strawBale.blockID] = new ItemBlock(SCBlockIDs.STRAW_BALE_ID - 256);
        }
    }

    private static void initGrownPanes() {
//        grownGrate = new GrownPaneBlock(SCBlockIDs.GRATE_GROWN_ID, "grown_grate",
//                "fcBlockGrate", "fcBlockGrate",
//                BTWBlocks.gratePane.blockID, 0);
    }

    private static void initMossBlocks() {
        mossCarpet = new MossCarpetBlock(SCBlockIDs.MOSS_CARPET_ID);
        Item.itemsList[mossCarpet.blockID] = new PlaceAsBlockItem(SCBlockIDs.MOSS_CARPET_ID - 256, mossCarpet.blockID)
                .setUnlocalizedName("moss_carpet").setCreativeTab(CreativeTabs.tabDecorations);

        mossBlock = new MossBlock(SCBlockIDs.MOSS_BLOCK_ID, "moss_block");
        Item.itemsList[mossBlock.blockID] = new ItemBlock(SCBlockIDs.MOSS_BLOCK_ID - 256);
    }

    private static void initHollowLogs() {
        hollowLog = new HollowLogBlock(SCBlockIDs.HOLLOW_LOG_ID, "hollow_log",
                HollowLogBlock.treeTextureTop,
                HollowLogBlock.treeTextureInner,
                HollowLogBlock.treeTextureSide);
        Item.itemsList[hollowLog.blockID] = new ItemMultiTextureTile(SCBlockIDs.HOLLOW_LOG_ID - 256, hollowLog, HollowLogBlock.treeTypes);

        hollowLog2 = new HollowLogBlock(SCBlockIDs.HOLLOW_LOG_2_ID, "hollow_log",
                HollowLogBlock.treeTextureTop2,
                HollowLogBlock.treeTextureInner2,
                HollowLogBlock.treeTextureSide2);
        Item.itemsList[hollowLog2.blockID] = new ItemMultiTextureTile(SCBlockIDs.HOLLOW_LOG_2_ID - 256, hollowLog2, HollowLogBlock.treeTypes2);

        if (SocksCropsAddon.isDecoInstalled())
        {
            decoHollowLog = new HollowLogBlock(SCBlockIDs.DECO_HOLLOW_LOG_ID, "hollow_log",
                    new String[]{"hollow_log_darkoak_top","hollow_log_acacia_top","hollow_log_mahogany_top","hollow_log_mangrove_top"},
                    new String[]{"decoBlockStrippedDarkOak_side","decoBlockStrippedAcacia_side","decoBlockStrippedMahogany_side","decoBlockStrippedMangrove_side"},
                    new String[]{"decoBlockLogDarkOak_side","decoBlockLogAcacia_side","decoBlockLogMahogany_side","decoBlockLogMangrove_side"});
            Item.itemsList[decoHollowLog.blockID] = new ItemMultiTextureTile(SCBlockIDs.DECO_HOLLOW_LOG_ID - 256, decoHollowLog, HollowLogBlock.decoTreeTypes);

            decoHollowLog2 = new HollowLogBlock(SCBlockIDs.DECO_HOLLOW_LOG_2_ID, "hollow_log",
                    new String[]{"hollow_log_hazel_top","hollow_log_fir_top","hollow_log_aspen_top","hollow_log_willow_top"},
                    new String[]{"decoBlockStrippedHazel_side","decoBlockStrippedFir_side","decoBlockStrippedAspen_side","decoBlockStrippedWillow_side"},
                    new String[]{"decoBlockLogHazel_side","decoBlockLogFir_side","decoBlockLogAspen_side","decoBlockLogWillow_side"});
            Item.itemsList[decoHollowLog2.blockID] = new ItemMultiTextureTile(SCBlockIDs.DECO_HOLLOW_LOG_2_ID - 256, decoHollowLog2, HollowLogBlock.decoTreeTypes2);

            decoHollowLog3 = new HollowLogBlock(SCBlockIDs.DECO_HOLLOW_LOG_3_ID, "hollow_log",
                    new String[]{"hollow_log_cherry_top","hollow_log_redwood_top"},
                    new String[]{"decoBlockStrippedCherry_side","decoBlockStrippedRedwood_side"},
                    new String[]{"decoBlockLogCherry_side","decoBlockLogRedwood_side"});
            Item.itemsList[decoHollowLog3.blockID] = new ItemMultiTextureTile(SCBlockIDs.DECO_HOLLOW_LOG_3_ID - 256, decoHollowLog3, HollowLogBlock.decoTreeTypes3);

        }
    }

    private static void initPackedBlocks() {
        smallPackedBlock = new PackedBlock(SCBlockIDs.SMALL_PACKED_BLOCK_ID, Material.leaves,"packed");

        Item.itemsList[smallPackedBlock.blockID] = new ItemMultiTextureTile(SCBlockIDs.SMALL_PACKED_BLOCK_ID - 256, smallPackedBlock,
                PackedBlock.types);
    }

    private static void initFlowerPots() {
        largeFlowerpot = new LargeFlowerpotBlock(SCBlockIDs.LARGE_FLOWERPOT_ID, "large_flowerpot");

        flowerPot = new FlowerpotBlock(SCBlockIDs.FLOWERPOT_ID);
    }

    private static void initDirtRelatedBlocks() {
        //----------- Grass -----------//
        grassNutrition = new GrassNutritionBlock(SCBlockIDs.GRASS_NUTRITION_ID, "grassNutrition");
        Item.itemsList[grassNutrition.blockID] = new ItemMultiTextureTile(SCBlockIDs.GRASS_NUTRITION_ID - 256, grassNutrition,
                NutritionUtils.GRASS_TEXTURE_NAMES);
    }

    private static void initFarmlandBlocks() {

        //----------- Farmland -----------//
        farmlandFullNutrition = new FarmlandFullNutritionBlock(SCBlockIDs.FARMLAND_FULL_NUTRITION_ID, "farmlandNutrition_full");
        Item.itemsList[farmlandFullNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_FULL_NUTRITION_ID - 256);

        farmlandReducedNutrition = new FarmlandReducedNutritionBlock(SCBlockIDs.FARMLAND_REDUCED_NUTRITION_ID, "farmlandNutrition_reduced");
        Item.itemsList[farmlandReducedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_REDUCED_NUTRITION_ID - 256);

        farmlandLowNutrition = new FarmlandLowNutritionBlock(SCBlockIDs.FARMLAND_LOW_NUTRITION_ID, "farmlandNutrition_low");
        Item.itemsList[farmlandLowNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_LOW_NUTRITION_ID - 256);

        farmlandDepletedNutrition = new FarmlandDepletedNutritionBlock(SCBlockIDs.FARMLAND_DEPLETED_NUTRITION_ID, "farmlandNutrition_depleted");
        Item.itemsList[farmlandDepletedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_DEPLETED_NUTRITION_ID - 256);

        //----------- Farmland Fertilized -----------//
        farmlandFertilizedFullNutrition = new FarmlandFertilizedFullNutritionBlock(SCBlockIDs.FARMLAND_FERTILIZED_FULL_NUTRITION_ID, "farmlandFertilizedNutrition_full");
        Item.itemsList[farmlandFertilizedFullNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_FERTILIZED_FULL_NUTRITION_ID - 256);

        farmlandFertilizedReducedNutrition = new FarmlandFertilizedReducedNutritionBlock(SCBlockIDs.FARMLAND_FERTILIZED_REDUCED_NUTRITION_ID, "farmlandFertilizedNutrition_reduced");
        Item.itemsList[farmlandFertilizedReducedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_FERTILIZED_REDUCED_NUTRITION_ID - 256);

        farmlandFertilizedLowNutrition = new FarmlandFertilizedLowNutritionBlock(SCBlockIDs.FARMLAND_FERTILIZED_LOW_NUTRITION_ID, "farmlandFertilizedNutrition_low");
        Item.itemsList[farmlandFertilizedLowNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_FERTILIZED_LOW_NUTRITION_ID - 256);

        farmlandFertilizedDepletedNutrition = new FarmlandFertilizedDepletedNutritionBlock(SCBlockIDs.FARMLAND_FERTILIZED_DEPLETED_NUTRITION_ID, "farmlandFertilizedNutrition_depleted");
        Item.itemsList[farmlandFertilizedDepletedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_FERTILIZED_DEPLETED_NUTRITION_ID - 256);

        //FARMLAND DUNG
        farmlandDungFullNutrition = new FarmlandDungFullNutritionBlock(SCBlockIDs.FARMLAND_DUNG_FULL_NUTRITION_ID, "farmlandDungNutrition_full");
        Item.itemsList[farmlandDungFullNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_DUNG_FULL_NUTRITION_ID - 256);

        farmlandDungReducedNutrition = new FarmlandDungReducedNutritionBlock(SCBlockIDs.FARMLAND_DUNG_REDUCED_NUTRITION_ID, "farmlandDungNutrition_reduced");
        Item.itemsList[farmlandDungReducedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_DUNG_REDUCED_NUTRITION_ID - 256);

        farmlandDungLowNutrition = new FarmlandDungLowNutritionBlock(SCBlockIDs.FARMLAND_DUNG_LOW_NUTRITION_ID, "farmlandDungNutrition_low");
        Item.itemsList[farmlandDungLowNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_DUNG_LOW_NUTRITION_ID - 256);

        farmlandDungDepletedNutrition = new FarmlandDungDepletedNutritionBlock(SCBlockIDs.FARMLAND_DUNG_DEPLETED_NUTRITION_ID, "farmlandDungNutrition_depleted");
        Item.itemsList[farmlandDungDepletedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_DUNG_DEPLETED_NUTRITION_ID - 256);

        //FARMLAND MULCH
        farmlandMulchFullNutrition = new FarmlandMulchFullNutritionBlock(SCBlockIDs.FARMLAND_MULCH_FULL_NUTRITION_ID, "farmlandMulchNutrition_full");
        Item.itemsList[farmlandMulchFullNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_MULCH_FULL_NUTRITION_ID - 256);

        farmlandMulchReducedNutrition = new FarmlandMulchReducedNutritionBlock(SCBlockIDs.FARMLAND_MULCH_REDUCED_NUTRITION_ID, "farmlandMulchNutrition_reduced");
        Item.itemsList[farmlandMulchReducedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_MULCH_REDUCED_NUTRITION_ID - 256);

        farmlandMulchLowNutrition = new FarmlandMulchLowNutritionBlock(SCBlockIDs.FARMLAND_MULCH_LOW_NUTRITION_ID, "farmlandMulchNutrition_low");
        Item.itemsList[farmlandMulchLowNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_MULCH_LOW_NUTRITION_ID - 256);

        farmlandMulchDepletedNutrition = new FarmlandMulchDepletedNutritionBlock(SCBlockIDs.FARMLAND_MULCH_DEPLETED_NUTRITION_ID, "farmlandMulchNutrition_depleted");
        Item.itemsList[farmlandMulchDepletedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_MULCH_DEPLETED_NUTRITION_ID - 256);
    }

    private static void initPlanterBlocks() {
        planterGrass = new PlanterGrassBlock(SCBlockIDs.GRASS_PLANTER_ID, "planter_grass");
        Item.itemsList[planterGrass.blockID] = new ItemMultiTextureTile(SCBlockIDs.GRASS_PLANTER_ID - 256, planterGrass,
                NutritionUtils.GRASS_PLANTER_TEXTURE_NAMES);

        planterFarmland = new PlanterFarmlandBlock(SCBlockIDs.FARMLAND_PLANTER_ID, "planter_farmland");
        Item.itemsList[planterFarmland.blockID] = new ItemMultiTextureTile(SCBlockIDs.FARMLAND_PLANTER_ID - 256, planterFarmland,
                NutritionUtils.PLANTER_TEXTURE_NAMES);

        planterFarmlandFertilized = new PlanterFarmlandFertilizedBlock(SCBlockIDs.FERTILIZED_PLANTER_ID, "planter_farmland_fertilized");
        Item.itemsList[planterFarmlandFertilized.blockID] = new ItemMultiTextureTile(SCBlockIDs.FERTILIZED_PLANTER_ID - 256, planterFarmlandFertilized,
                NutritionUtils.PLANTER_TEXTURE_NAMES);

        planterFarmlandDung = new PlanterFarmlandDungBlock(SCBlockIDs.DUNG_PLANTER_ID, "planter_farmland_dung");
        Item.itemsList[planterFarmlandDung.blockID] = new ItemMultiTextureTile(SCBlockIDs.DUNG_PLANTER_ID - 256, planterFarmlandDung,
                NutritionUtils.PLANTER_TEXTURE_NAMES);
    }
}
