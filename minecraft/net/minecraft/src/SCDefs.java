package net.minecraft.src;

public class SCDefs {
	
	//BLOCK ID's
	private static int
		//Grapes
		id_fence = 4000,
		id_fenceRope = 4001,
		id_grapeCrop = 4002,
		id_grapeStem = 4003,
		id_grapeLeaves = 4004,
		id_grapeDropLeaves = 4005,
		id_grapeVine = 4006,
		id_grapeBlock = 4007;
	
	private static int	
		//Pumpkin
		id_pumpkinStem = 4008,
		
		id_pumpkinVine = 4009,
		id_pumpkinVineSleeping = 4023,
		
		id_pumpkinVineFlowering = 4010,
		id_pumpkinVineFloweringSleeping = 4024,
		
		id_pumpkinFresh = 4011,
		id_pumpkinFreshSleeping = 4025;
		
		
	private static int
		//Apple
		id_appleCrop = 4012,
		id_appleSapling = 4013,
		id_appleLeaves = 4014,
		id_appleLog = 4015,
		id_appleStump = 4016,
		id_appleItem = 4017,
		id_appleLeavesDead = 4018;
		

		
	private static int	
		//Bamboo
		id_bambooShoot = 4019,
		id_bambooRoot = 4020,
		id_bambooStalk = 4021,
		id_bambooRootLeaves = 4022;
	
	private static int	
		id_cuttingBoard = 4050;
	
	
	public static int	
		id_damagedLog = 4057,
		id_mossyLog = 4058,
		id_compostBlock = 4059;
	
	private static int	
		id_farmlandNutrition3 = 4060, //ie normal farmland
		id_farmlandNutrition3Fertilized = 4064, //normal farmland fertilized
		id_farmlandNutrition3Dung = 4068,
		
		id_farmlandNutrition2 = 4061, 
		id_farmlandNutrition2Fertilized = 4065,
		id_farmlandNutrition2Dung = 4069,

		id_farmlandNutrition1 = 4062, 
		id_farmlandNutrition1Fertilized = 4066,
		id_farmlandNutrition1Dung = 4070,
		
		id_farmlandNutrition0 = 4063, 
		id_farmlandNutrition0Fertilized = 4067,
		id_farmlandNutrition0Dung = 4071;
	
	
	private static int id_grassNutrition = 4077;
	private static int id_dirtNutrition = 4078;
	private static int id_dirtLooseNutrition = 4079;

		//end 4096

	
	//ITEM ID's
	private static int
		id_grape = 31000,
		id_grapeSeeds = 31001,
		
		id_pumpkinSeeds = 31002,
		
		id_appleSeeds = 31003,
		
		id_bambooItem = 31004,
		
		id_knife = 31005,
		
		id_stumpRemover = 31006;
		
//		id_seedBook = 30999;
		//end 32000
	
	//Loose Dirt
	
	public static Block dirtLooseNutrition;
	public static Block grassNutrition;
	public static Block dirtNutrition;
	
	public static Block farmlandNutrition3, farmlandNutrition3Fertilized, farmlandNutrition3Dung;
	public static Block farmlandNutrition2, farmlandNutrition2Fertilized, farmlandNutrition2Dung;
	public static Block farmlandNutrition1, farmlandNutrition1Fertilized, farmlandNutrition1Dung;
	public static Block farmlandNutrition0, farmlandNutrition0Fertilized, farmlandNutrition0Dung;
	
	public static Block damagedLog;
	public static Block mossyLog;
	public static Block compostBlock;
	
	
	//rope
	public static Block fence;
	public static Block fenceRope;
	
	//grape
	public static Block grapeCrop;
	public static Block grapeStem;
	public static Block grapeLeaves;
	public static Block grapeDropLeaves;
	public static Block grapeVine;
	public static Block grapeBlock;
	
	public static Item grape;
	public static Item grapeSeeds;
	
	public static Block pumpkinFresh, pumpkinFreshSleeping,
						pumpkinStem,
						pumpkinVine, pumpkinVineSleeping,
						pumpkinVineFlowering, pumpkinVineFloweringSleeping;
	
	public static Item pumpkinSeeds;
	
	//apple
	public static Block appleCrop;
	public static Block appleSapling;
	public static Block appleLeaves;
	public static Block appleLog;
	public static Block appleStump;
	public static Block appleItem;
	
	public static Item appleSeeds;
	
	//bamboo
	public static Block bambooShoot, bambooRoot, bambooStalk;
	public static Item bambooItem;
	
	public static Item knife;
	public static Block cuttingBoard;
	
	public static Item seedBook;
	
	
	
	
	//From Decomanager
	public static int ReplaceBlockID(Block block)
	{
		Block.blocksList[block.blockID] = null;
		return block.blockID;
	}

	public static void addDefinitions() {
		
		addTileEntityDefs();
		
		//Farmland Nutrition 3
		farmlandNutrition3 = new SCBlockFarmlandNutrition3(id_farmlandNutrition3);
		Item.itemsList[farmlandNutrition3.blockID] = new ItemBlock(farmlandNutrition3.blockID - 256);
		
		farmlandNutrition3Fertilized = new SCBlockFarmlandNutrition3Fertilized(id_farmlandNutrition3Fertilized);
		Item.itemsList[farmlandNutrition3Fertilized.blockID] = new ItemBlock(farmlandNutrition3Fertilized.blockID - 256);
		
		farmlandNutrition3Dung = new SCBlockFarmlandNutrition3Dung(id_farmlandNutrition3Dung);
		Item.itemsList[farmlandNutrition3Dung.blockID] = new ItemBlock(farmlandNutrition3Dung.blockID - 256);
		
		//Farmland Nutrition 2                                                                                                                                                        
		farmlandNutrition2 = new SCBlockFarmlandNutrition2(id_farmlandNutrition2);
		Item.itemsList[farmlandNutrition2.blockID] = new ItemBlock(farmlandNutrition2.blockID - 256);
		                                                                                                                                                                              
		farmlandNutrition2Fertilized = new SCBlockFarmlandNutrition2Fertilized(id_farmlandNutrition2Fertilized);
		Item.itemsList[farmlandNutrition2Fertilized.blockID] = new ItemBlock(farmlandNutrition2Fertilized.blockID - 256);
		
		farmlandNutrition2Dung = new SCBlockFarmlandNutrition2Dung(id_farmlandNutrition2Dung);
		Item.itemsList[farmlandNutrition2Dung.blockID] = new ItemBlock(farmlandNutrition2Dung.blockID - 256);
		
		//Farmland Nutrition 1		                                                                                                                                                  
		farmlandNutrition1 = new SCBlockFarmlandNutrition1(id_farmlandNutrition1);                                                                                                    
		Item.itemsList[farmlandNutrition1.blockID] = new ItemBlock(farmlandNutrition1.blockID - 256);                                                                                 
		                                                                                                                                                                              
		farmlandNutrition1Fertilized = new SCBlockFarmlandNutrition1Fertilized(id_farmlandNutrition1Fertilized);                                                                      
		Item.itemsList[farmlandNutrition1Fertilized.blockID] = new ItemBlock(farmlandNutrition1Fertilized.blockID - 256);    
		
		farmlandNutrition1Dung = new SCBlockFarmlandNutrition1Dung(id_farmlandNutrition1Dung);
		Item.itemsList[farmlandNutrition1Dung.blockID] = new ItemBlock(farmlandNutrition1Dung.blockID - 256);
		                                                                                                                                                                              
		//Farmland Nutrition 0		                                                                                                                                                  
		farmlandNutrition0 = new SCBlockFarmlandNutrition0(id_farmlandNutrition0);
		Item.itemsList[farmlandNutrition0.blockID] = new ItemBlock(farmlandNutrition0.blockID - 256);
		                                                                                                                                                                              
		farmlandNutrition0Fertilized = new SCBlockFarmlandNutrition0Fertilized(id_farmlandNutrition0Fertilized);                                                                      
		Item.itemsList[farmlandNutrition0Fertilized.blockID] = new ItemBlock(farmlandNutrition0Fertilized.blockID - 256);
		
		farmlandNutrition0Dung = new SCBlockFarmlandNutrition0Dung(id_farmlandNutrition0Dung);
		Item.itemsList[farmlandNutrition0Dung.blockID] = new ItemBlock(farmlandNutrition0Dung.blockID - 256);
		
		//New Loose Dirt
		FCBetterThanWolves.fcBlockDirtLoose = new SCBlockDirtLooseNutrition(ReplaceBlockID(FCBetterThanWolves.fcBlockDirtLoose)); //replaces FC's loose dirt
		
		dirtLooseNutrition = new SCBlockDirtLooseNutrition(id_dirtLooseNutrition);
		Item.itemsList[dirtLooseNutrition.blockID] = new ItemMultiTextureTile(dirtLooseNutrition.blockID - 256, dirtLooseNutrition, SCBlockDirtLooseNutrition.nutritionLevelTextures);
		
		//New Grass		
		Block.grass = new SCBlockGrassNutrition(ReplaceBlockID(Block.grass)); //replaces FC's grass
		
		grassNutrition = new SCBlockGrassNutrition(id_grassNutrition);
		Item.itemsList[grassNutrition.blockID] = new ItemMultiTextureTile(grassNutrition.blockID - 256, grassNutrition, SCBlockGrassNutrition.nutritionLevelTextures);
		
		//New Dirt		
		Block.dirt = new SCBlockDirtNutrition(ReplaceBlockID(Block.dirt)); //replaces FC's dirt
		
		dirtNutrition = new SCBlockDirtNutrition(id_dirtNutrition);
		Item.itemsList[dirtNutrition.blockID] = new ItemMultiTextureTile(dirtNutrition.blockID - 256, dirtNutrition, SCBlockDirtNutrition.nutritionLevelTextures);
		
		
		//log
		damagedLog = new SCBlockDamagedLog(id_damagedLog);
		Item.itemsList[damagedLog.blockID] = new ItemMultiTextureTile(damagedLog.blockID - 256, damagedLog, SCBlockDamagedLog.treeTextureTypes);
		
		mossyLog = new SCBlockMossyLog(id_mossyLog);
		Item.itemsList[mossyLog.blockID] = new ItemMultiTextureTile(mossyLog.blockID - 256, mossyLog, SCBlockMossyLog.treeTextureTypes);
		
		compostBlock = new SCBlockCompost(id_compostBlock);
		Item.itemsList[compostBlock.blockID] = new ItemBlock(compostBlock.blockID - 256);
		
		//Kitchen
		knife = new SCItemKnife(id_knife - 256);
		
		cuttingBoard = new SCBlockCuttingBoard(id_cuttingBoard);
		Item.itemsList[cuttingBoard.blockID] = new ItemBlock(cuttingBoard.blockID - 256);
		
		//Fence and Rope		
		fence = new SCBlockFence(id_fence);
		Item.itemsList[fence.blockID] = new ItemBlock(fence.blockID - 256);	
		
		fenceRope = new SCBlockFenceRope(id_fenceRope);
		Item.itemsList[fenceRope.blockID] = new ItemBlock(fenceRope.blockID - 256);	
		
		//Grapes
		grapeCrop = new SCBlockGrapeCrop(id_grapeCrop);
		Item.itemsList[grapeCrop.blockID] = new ItemBlock(grapeCrop.blockID - 256);	
		
		grapeStem = new SCBlockGrapeStem(id_grapeStem);
		Item.itemsList[grapeStem.blockID] = new ItemBlock(grapeStem.blockID - 256);
		
		grapeLeaves = new SCBlockGrapeLeaves(id_grapeLeaves);
		Item.itemsList[grapeLeaves.blockID] = new ItemBlock(grapeLeaves.blockID - 256);
		
		grapeDropLeaves = new SCBlockGrapeDropLeaves(id_grapeDropLeaves);
		Item.itemsList[grapeDropLeaves.blockID] = new ItemBlock(grapeDropLeaves.blockID - 256);
		
		grapeVine = new SCBlockGrapeVine(id_grapeVine);
		Item.itemsList[grapeVine.blockID] = new ItemBlock(grapeVine.blockID - 256);
		
		grapeBlock = new SCBlockGrape(id_grapeBlock);
		Item.itemsList[grapeBlock.blockID] = new ItemBlock(grapeVine.blockID - 256);
		
		grape = new SCItemGrape(id_grape - 256);
		
		grapeSeeds = new SCItemGrapeSeeds(id_grapeSeeds - 256);
				
		//PUMPKIN
		pumpkinStem = new SCBlockPumpkinStem(id_pumpkinStem);
		Item.itemsList[pumpkinStem.blockID] = new ItemBlock(pumpkinStem.blockID - 256);
		
		pumpkinVine = new SCBlockPumpkinVine(id_pumpkinVine);
		Item.itemsList[pumpkinVine.blockID] = new ItemBlock(pumpkinVine.blockID - 256);
		
		pumpkinVineSleeping = new SCBlockPumpkinVineSleeping(id_pumpkinVineSleeping);
		Item.itemsList[pumpkinVineSleeping.blockID] = new ItemBlock(pumpkinVineSleeping.blockID - 256);
		
		pumpkinFresh = new SCBlockPumpkinFresh(id_pumpkinFresh);
		Item.itemsList[pumpkinFresh.blockID] = new ItemBlock(pumpkinFresh.blockID - 256);
		
		pumpkinVineFlowering = new SCBlockPumpkinVineFlowering(id_pumpkinVineFlowering, FCBetterThanWolves.fcBlockPumpkinFresh);
		Item.itemsList[pumpkinVineFlowering.blockID] = new ItemBlock(pumpkinVineFlowering.blockID - 256);
		
		pumpkinVineFloweringSleeping = new SCBlockPumpkinVineFloweringSleeping(id_pumpkinVineFloweringSleeping, FCBetterThanWolves.fcBlockPumpkinFresh);
		Item.itemsList[pumpkinVineFloweringSleeping.blockID] = new ItemBlock(pumpkinVineFloweringSleeping.blockID - 256);
		
		pumpkinSeeds = new SCItemPumpkinSeeds(id_pumpkinSeeds - 256);
		
		//APPLE
		appleCrop = new SCBlockAppleCrop(id_appleCrop);
		Item.itemsList[appleCrop.blockID] = new ItemBlock(appleCrop.blockID - 256);
		
		appleSapling = new SCBlockAppleSapling(id_appleSapling);
		Item.itemsList[appleSapling.blockID] = new ItemBlock(appleSapling.blockID - 256);
		
		appleLeaves = new SCBlockAppleLeaves(id_appleLeaves);
		Item.itemsList[appleLeaves.blockID] = new ItemBlock(appleLeaves.blockID - 256);
		
		appleLog = new SCBlockAppleLog(id_appleLog);
		Item.itemsList[appleLog.blockID] = new ItemBlock(appleLog.blockID - 256);
		
		appleStump = new SCBlockAppleStump(id_appleStump);
		Item.itemsList[appleStump.blockID] = new ItemBlock(appleStump.blockID - 256);
		
//		appleItem = new SCBlockAppleItem(id_appleItem);
//		Item.itemsList[appleItem.blockID] = new ItemBlock(appleItem.blockID - 256);
		
		appleSeeds = new SCItemAppleSeeds(id_appleSeeds - 256);
		
		//BAMBOO
		bambooShoot = new SCBlockBambooShoot(id_bambooShoot);
		Item.itemsList[bambooShoot.blockID] = new ItemBlock(bambooShoot.blockID - 256);

		bambooRoot = new SCBlockBambooRoot(id_bambooRoot);
		Item.itemsList[bambooRoot.blockID] = new ItemBlock(bambooRoot.blockID - 256);
		
		//simple block just for the X textures on Bamboo Root
		//bambooRootLeaves = new Block(id_bambooRootLeaves, Material.leaves).setUnlocalizedName("SCBlockBambooRootsLeaves");
		
		bambooStalk = new SCBlockBambooStalk(id_bambooStalk);
		Item.itemsList[bambooStalk.blockID] = new ItemBlock(bambooStalk.blockID - 256);
		
		bambooItem = new SCItemBamboo(id_bambooItem - 256);

		
	}
	
	public static void addTileEntityDefs() {
		//Custom entities
		TileEntity.addMapping(SCTileEntityCuttingBoard.class, "SCCuttingBoard");

	}
}
