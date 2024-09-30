package btw.community.sockthing.sockscrops.block;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.block.blocks.*;
import btw.community.sockthing.sockscrops.block.tileentities.HayDryingTileEntity;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import net.minecraft.src.*;

public class SCBlocks {

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
    public static Block doubleTallGrass;

    //----------- Hay  -----------//
    public static Block dryingHay;
    public static Block driedHay;
    public static Block hayBale;
    public static Block strawBale;

    public static void initBlocks() {
        initTileEnityMapping();
        initPlantBlocks();
        initNutritionBlocks();
        initHayBlocks();
    }

    private static void initTileEnityMapping() {
        TileEntity.addMapping(HayDryingTileEntity.class, "SCHayDrying");
    }

    private static void initPlantBlocks() {
        doubleTallGrass = new DoubleTallGrassBlock(SCBlockIDs.DOUBLE_TALL_GRASS_ID, "scBlockDoubleTallPlant");
        Item.itemsList[doubleTallGrass.blockID] = new ItemMultiTextureTile(SCBlockIDs.DOUBLE_TALL_GRASS_ID - 256, doubleTallGrass,
                new String[]{"grass", "fern"});
    }

    private static void initNutritionBlocks() {
        initDirtRelatedBlocks();
        initFarmlandBlocks();
        initPlanterBlocks();
    }

    private static void initDirtRelatedBlocks() {
        //----------- Grass -----------//
        grassNutrition = new GrassNutritionBlock(SCBlockIDs.GRASS_NUTRITION_ID, "scBlockGrassNutrition");
        Item.itemsList[grassNutrition.blockID] = new ItemMultiTextureTile(SCBlockIDs.GRASS_NUTRITION_ID - 256, grassNutrition,
                NutritionUtils.GRASS_TEXTURE_NAMES);

        //----------- Dirt -----------//
        Item.itemsList[Block.dirt.blockID] = new ItemMultiTextureTile(Block.dirt.blockID - 256, Block.dirt,
                NutritionUtils.DIRT_TEXTURE_NAMES);

        Item.itemsList[BTWBlocks.looseDirt.blockID] = new ItemMultiTextureTile(BTWBlocks.looseDirt.blockID - 256, BTWBlocks.looseDirt,
                NutritionUtils.LOOSE_DIRT_TEXTURE_NAMES);

        //----------- Vanilla Farmland Overrides -----------//
        Item.itemsList[BTWBlocks.farmland.blockID].setCreativeTab(null);
        Item.itemsList[BTWBlocks.fertilizedFarmland.blockID].setCreativeTab(null);
    }

    private static void initFarmlandBlocks() {
        //----------- Farmland -----------//
        farmlandFullNutrition = new FarmlandFullNutritionBlock(SCBlockIDs.FARMLAND_FULL_NUTRITION_ID, "scBlockFarmlandNutrition_full");
        Item.itemsList[farmlandFullNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_FULL_NUTRITION_ID - 256);

        farmlandReducedNutrition = new FarmlandReducedNutritionBlock(SCBlockIDs.FARMLAND_REDUCED_NUTRITION_ID, "scBlockFarmlandNutrition_reduced");
        Item.itemsList[farmlandReducedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_REDUCED_NUTRITION_ID - 256);

        farmlandLowNutrition = new FarmlandLowNutritionBlock(SCBlockIDs.FARMLAND_LOW_NUTRITION_ID, "scBlockFarmlandNutrition_low");
        Item.itemsList[farmlandLowNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_LOW_NUTRITION_ID - 256);

        farmlandDepletedNutrition = new FarmlandDepletedNutritionBlock(SCBlockIDs.FARMLAND_DEPLETED_NUTRITION_ID, "scBlockFarmlandNutrition_depleted");
        Item.itemsList[farmlandDepletedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_DEPLETED_NUTRITION_ID - 256);

        //----------- Farmland Fertilized -----------//
        farmlandFertilizedFullNutrition = new FarmlandFertilizedFullNutritionBlock(SCBlockIDs.FARMLAND_FERTILIZED_FULL_NUTRITION_ID, "scBlockFarmlandFertilizedNutrition_full");
        Item.itemsList[farmlandFertilizedFullNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_FERTILIZED_FULL_NUTRITION_ID - 256);

        farmlandFertilizedReducedNutrition = new FarmlandFertilizedReducedNutritionBlock(SCBlockIDs.FARMLAND_FERTILIZED_REDUCED_NUTRITION_ID, "scBlockFarmlandFertilizedNutrition_reduced");
        Item.itemsList[farmlandFertilizedReducedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_FERTILIZED_REDUCED_NUTRITION_ID - 256);

        farmlandFertilizedLowNutrition = new FarmlandFertilizedLowNutritionBlock(SCBlockIDs.FARMLAND_FERTILIZED_LOW_NUTRITION_ID, "scBlockFarmlandFertilizedNutrition_low");
        Item.itemsList[farmlandFertilizedLowNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_FERTILIZED_LOW_NUTRITION_ID - 256);

        farmlandFertilizedDepletedNutrition = new FarmlandFertilizedDepletedNutritionBlock(SCBlockIDs.FARMLAND_FERTILIZED_DEPLETED_NUTRITION_ID, "scBlockFarmlandFertilizedNutrition_depleted");
        Item.itemsList[farmlandFertilizedDepletedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_FERTILIZED_DEPLETED_NUTRITION_ID - 256);

        //FARMLAND DUNG
        farmlandDungFullNutrition = new FarmlandDungFullNutritionBlock(SCBlockIDs.FARMLAND_DUNG_FULL_NUTRITION_ID, "scBlockFarmlandDungNutrition_full");
        Item.itemsList[farmlandDungFullNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_DUNG_FULL_NUTRITION_ID - 256);

        farmlandDungReducedNutrition = new FarmlandDungReducedNutritionBlock(SCBlockIDs.FARMLAND_DUNG_REDUCED_NUTRITION_ID, "scBlockFarmlandDungNutrition_reduced");
        Item.itemsList[farmlandDungReducedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_DUNG_REDUCED_NUTRITION_ID - 256);

        farmlandDungLowNutrition = new FarmlandDungLowNutritionBlock(SCBlockIDs.FARMLAND_DUNG_LOW_NUTRITION_ID, "scBlockFarmlandDungNutrition_low");
        Item.itemsList[farmlandDungLowNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_DUNG_LOW_NUTRITION_ID - 256);

        farmlandDungDepletedNutrition = new FarmlandDungDepletedNutritionBlock(SCBlockIDs.FARMLAND_DUNG_DEPLETED_NUTRITION_ID, "scBlockFarmlandDungNutrition_depleted");
        Item.itemsList[farmlandDungDepletedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_DUNG_DEPLETED_NUTRITION_ID - 256);

        //FARMLAND MULCH
        farmlandMulchFullNutrition = new FarmlandMulchFullNutritionBlock(SCBlockIDs.FARMLAND_MULCH_FULL_NUTRITION_ID, "scBlockFarmlandMulchNutrition_full");
        Item.itemsList[farmlandMulchFullNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_MULCH_FULL_NUTRITION_ID - 256);

        farmlandMulchReducedNutrition = new FarmlandMulchReducedNutritionBlock(SCBlockIDs.FARMLAND_MULCH_REDUCED_NUTRITION_ID, "scBlockFarmlandMulchNutrition_reduced");
        Item.itemsList[farmlandMulchReducedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_MULCH_REDUCED_NUTRITION_ID - 256);

        farmlandMulchLowNutrition = new FarmlandMulchLowNutritionBlock(SCBlockIDs.FARMLAND_MULCH_LOW_NUTRITION_ID, "scBlockFarmlandMulchNutrition_low");
        Item.itemsList[farmlandMulchLowNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_MULCH_LOW_NUTRITION_ID - 256);

        farmlandMulchDepletedNutrition = new FarmlandMulchDepletedNutritionBlock(SCBlockIDs.FARMLAND_MULCH_DEPLETED_NUTRITION_ID, "scBlockFarmlandMulchNutrition_depleted");
        Item.itemsList[farmlandMulchDepletedNutrition.blockID] = new ItemBlock(SCBlockIDs.FARMLAND_MULCH_DEPLETED_NUTRITION_ID - 256);
    }

    private static void initPlanterBlocks() {
        planterGrass = new PlanterGrassBlock(SCBlockIDs.GRASS_PLANTER_ID, "scBlockPlanter_grass");
        Item.itemsList[planterGrass.blockID] = new ItemMultiTextureTile(SCBlockIDs.GRASS_PLANTER_ID - 256, planterGrass,
                NutritionUtils.GRASS_PLANTER_TEXTURE_NAMES);

        planterFarmland = new PlanterFarmlandBlock(SCBlockIDs.FARMLAND_PLANTER_ID, "scBlockPlanter_farmland");
        Item.itemsList[planterFarmland.blockID] = new ItemMultiTextureTile(SCBlockIDs.FARMLAND_PLANTER_ID - 256, planterFarmland,
                NutritionUtils.PLANTER_TEXTURE_NAMES);

        planterFarmlandFertilized = new PlanterFarmlandFertilizedBlock(SCBlockIDs.FERTILIZED_PLANTER_ID, "scBlockPlanter_farmland_fertilized");
        Item.itemsList[planterFarmlandFertilized.blockID] = new ItemMultiTextureTile(SCBlockIDs.FERTILIZED_PLANTER_ID - 256, planterFarmlandFertilized,
                NutritionUtils.PLANTER_TEXTURE_NAMES);

        planterFarmlandDung = new PlanterFarmlandDungBlock(SCBlockIDs.DUNG_PLANTER_ID, "scBlockPlanter_farmland_dung");
        Item.itemsList[planterFarmlandDung.blockID] = new ItemMultiTextureTile(SCBlockIDs.DUNG_PLANTER_ID - 256, planterFarmlandDung,
                NutritionUtils.PLANTER_TEXTURE_NAMES);
    }

    private static void initHayBlocks() {
        dryingHay = new HayDryingBlock(SCBlockIDs.DRYING_HAY_ID, "scBlockHay_drying");
        Item.itemsList[dryingHay.blockID] = new ItemBlock(SCBlockIDs.DRYING_HAY_ID - 256);

        driedHay = new HayDriedBlock(SCBlockIDs.DRIED_HAY_ID, "scBlockHay_dried");
        Item.itemsList[driedHay.blockID] = new ItemBlock(SCBlockIDs.DRIED_HAY_ID - 256);

        hayBale = new HayBaleBlock(SCBlockIDs.HAY_BALE_ID, "scBlockBale_hay");
        Item.itemsList[hayBale.blockID] = new ItemBlock(SCBlockIDs.HAY_BALE_ID - 256);

        strawBale = new StrawBaleBlock(SCBlockIDs.STRAW_BALE_ID, "scBlockBale_straw");
        Item.itemsList[strawBale.blockID] = new ItemBlock(SCBlockIDs.STRAW_BALE_ID - 256);
    }
}
