package net.minecraft.src;

public class SCDefs {
	
	// --- BLOCK ID's --- //
	// SC FROM 2500 - 2999
	
	//Fence & Rope
	private static int
		id_fence = 2500,
		id_fenceRope = 2501;
	
	//Farmland
	private static int	
		id_farmlandNutrition3 = 2510, //ie normal farmland
		id_farmlandNutrition3Fertilized = 2514, //normal farmland fertilized
		id_farmlandNutrition3Dung = 2518,
		
		id_farmlandNutrition2 = 2511, 
		id_farmlandNutrition2Fertilized = 2515,
		id_farmlandNutrition2Dung = 2519,
	
		id_farmlandNutrition1 = 2512, 
		id_farmlandNutrition1Fertilized = 2516,
		id_farmlandNutrition1Dung = 2520,
		
		id_farmlandNutrition0 = 2513, 
		id_farmlandNutrition0Fertilized = 2517,
		id_farmlandNutrition0Dung = 2521;
		
	//Grass, Dirt and Loose Dirt
	private static int id_grassNutrition = 2522;
	private static int id_dirtNutrition = 2523;
	private static int id_dirtLooseNutrition = 2524;
	
	//Decomposing Logs & Compost
	public static int	
		id_damagedLog = 2530,
		id_mossyLog = 2531,
		id_compostBlock = 2532;
	
	//Pumpkins
	private static int	
		id_pumpkinStem = 2540,
		id_pumpkinVine = 2541,
		id_pumpkinVineSleeping = 2542,		
		id_pumpkinVineFlowering = 2543,
		id_pumpkinVineFloweringSleeping = 2544,		
		id_pumpkinFresh = 2545,
		id_pumpkinFreshSleeping = 2546;

	//Bamboo
	private static int	
		id_bambooShoot = 2550,
		id_bambooRoot = 2551,
		id_bambooStalk = 2552,
		id_bambooRootLeaves = 2553;
	
	//Grapes
	private static int
		id_grapeCrop = 2660,
		id_grapeStem = 2661,
		id_grapeLeaves = 2662,
		id_grapeDropLeaves = 2663,
		id_grapeVine = 2664,
		id_grapeBlock = 2665;

	// SC ENDS AT 2999

	// --- ITEM ID's --- //
	// SC STARTS AT 31000
	
	//Pumpkin & Melon
	private static int		
		id_pumpkinSeeds = 31010,
		id_melonSeeds = 31011;
	
	//Bamboo
	private static int
		id_bambooItem = 31020;

	// ENDS 32000
	
	// --- Block & Item --- //
	
	//Dirts
	public static Block dirtLooseNutrition;
	public static Block grassNutrition;
	public static Block dirtNutrition;
	
	//Farmland
	public static Block farmlandNutrition3, farmlandNutrition3Fertilized, farmlandNutrition3Dung;
	public static Block farmlandNutrition2, farmlandNutrition2Fertilized, farmlandNutrition2Dung;
	public static Block farmlandNutrition1, farmlandNutrition1Fertilized, farmlandNutrition1Dung;
	public static Block farmlandNutrition0, farmlandNutrition0Fertilized, farmlandNutrition0Dung;
	
	//Decomposing
	public static Block damagedLog;
	public static Block mossyLog;
	public static Block compostBlock;
	
	//Fence & Rope
	public static Block fence;
	public static Block fenceRope;

	//Pumpkin & Melon
	public static Block pumpkinFresh, pumpkinFreshSleeping,
						pumpkinStem,
						pumpkinVine, pumpkinVineSleeping,
						pumpkinVineFlowering, pumpkinVineFloweringSleeping;
	
	public static Item pumpkinSeeds;
	
	//Bamboo
	public static Block bambooShoot, bambooRoot, bambooStalk;
	public static Item bambooItem;
	
	//Grape
	public static Block grapeCrop;
	public static Block grapeStem;
	public static Block grapeLeaves;
	public static Block grapeDropLeaves;
	public static Block grapeVine;
	public static Block grapeBlock;
	
	public static Item grape;
	public static Item grapeSeeds;
	

	public static void addDefinitions() {
		
		addFenceAndRopeDefs();
		
		addDirtDefs();
		addDirtReplacements();
		addFarmlandDefs();
		
		addDecomposingDefs();
		
		addPumpkinDefs();
		addBambooDefs();
		addGrapeDefs();
		
	}
	
	private static void addGrapeDefs() {
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
		
	}

	private static void addBambooDefs() {
		//BAMBOO
		bambooShoot = new SCBlockBambooShoot(id_bambooShoot);
		Item.itemsList[bambooShoot.blockID] = new ItemBlock(bambooShoot.blockID - 256);
		
		bambooRoot = new SCBlockBambooRoot(id_bambooRoot);
		Item.itemsList[bambooRoot.blockID] = new ItemBlock(bambooRoot.blockID - 256);
		
		bambooStalk = new SCBlockBambooStalk(id_bambooStalk);
		Item.itemsList[bambooStalk.blockID] = new ItemBlock(bambooStalk.blockID - 256);
		
		bambooItem = new SCItemBamboo(id_bambooItem - 256);
		
	}

	private static void addPumpkinDefs() {
		
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
		
	}

	private static void addFenceAndRopeDefs() {
		
		fence = new SCBlockFence(id_fence);
		Item.itemsList[fence.blockID] = new ItemBlock(fence.blockID - 256);	
		
		fenceRope = new SCBlockFenceRope(id_fenceRope);
		Item.itemsList[fenceRope.blockID] = new ItemBlock(fenceRope.blockID - 256);	
		
	}

	private static void addDecomposingDefs() {
		
		damagedLog = new SCBlockDamagedLog(id_damagedLog);
		Item.itemsList[damagedLog.blockID] = new ItemMultiTextureTile(damagedLog.blockID - 256, damagedLog, SCBlockDamagedLog.treeTextureTypes);
		
		mossyLog = new SCBlockMossyLog(id_mossyLog);
		Item.itemsList[mossyLog.blockID] = new ItemMultiTextureTile(mossyLog.blockID - 256, mossyLog, SCBlockMossyLog.treeTextureTypes);
		
		compostBlock = new SCBlockCompost(id_compostBlock);
		Item.itemsList[compostBlock.blockID] = new ItemBlock(compostBlock.blockID - 256);
		
	}

	private static void addDirtReplacements() {
		
		//New Loose Dirt
		//FCBetterThanWolves.fcBlockDirtLoose = new SCBlockDirtLooseNutrition(ReplaceBlockID(FCBetterThanWolves.fcBlockDirtLoose)); //replaces FC's loose dirt
		//New Grass		
		//Block.grass = new SCBlockGrassNutrition(ReplaceBlockID(Block.grass)); //replaces FC's grass
		//New Dirt		
		//Block.dirt = new SCBlockDirtNutrition(ReplaceBlockID(Block.dirt)); //replaces FC's dirt
	}

	private static void addDirtDefs() {
		
		dirtLooseNutrition = new SCBlockDirtLooseNutrition(id_dirtLooseNutrition);
		Item.itemsList[dirtLooseNutrition.blockID] = new ItemMultiTextureTile(dirtLooseNutrition.blockID - 256, dirtLooseNutrition, SCBlockDirtLooseNutrition.nutritionLevelTextures);
		
		grassNutrition = new SCBlockGrassNutrition(id_grassNutrition);
		Item.itemsList[grassNutrition.blockID] = new ItemMultiTextureTile(grassNutrition.blockID - 256, grassNutrition, SCBlockGrassNutrition.nutritionLevelTextures);
		
		dirtNutrition = new SCBlockDirtNutrition(id_dirtNutrition);
		Item.itemsList[dirtNutrition.blockID] = new ItemMultiTextureTile(dirtNutrition.blockID - 256, dirtNutrition, SCBlockDirtNutrition.nutritionLevelTextures);
		
	}

	private static void addFarmlandDefs() {
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
		
	}

	public static void addTileEntityDefs() {
		//Custom entities
		//TileEntity.addMapping(SCTileEntityCuttingBoard.class, "SCCuttingBoard");

	}
}
