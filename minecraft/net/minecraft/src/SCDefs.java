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
		id_grapeBlock = 4007,
		
		//Pumpkin
		id_pumpkinStem = 4008,
		
		id_pumpkinVine = 4009,
		id_pumpkinVineSleeping = 4023,
		
		id_pumpkinVineFlowering = 4010,
		id_pumpkinVineFloweringSleeping = 4024,
		
		id_pumpkinFresh = 4011,
		id_pumpkinFreshSleeping = 4025,
		
		//Apple
		id_appleCrop = 4012,
		id_appleSapling = 4013,
		id_appleLeaves = 4014,
		id_appleLog = 4015,
		id_appleStump = 4016,
		id_appleItem = 4017,
		id_appleLeavesDead = 4018,
		
		//Bamboo
		id_bambooShoot = 4019,
		id_bambooRoot = 4020,
		id_bambooStalk = 4021,
		id_bambooRootLeaves = 4022,
		
		id_cuttingBoard = 4050;
		//end 4096
	
	//ITEM ID's
	private static int
		id_grape = 31000,
		id_grapeSeeds = 31001,
		
		id_pumpkinSeeds = 31002,
		
		id_appleSeeds = 31003,
		
		id_bambooItem = 31004,
		
		id_knife = 31005;
		//end 32000
	
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
		

	public static void addDefinitions() {
		
		addTileEntityDefs();
		
		knife = new SCItemKnife(id_knife - 256);
		
		cuttingBoard = new SCBlockCuttingBoard(id_cuttingBoard);
		Item.itemsList[cuttingBoard.blockID] = new ItemBlock(cuttingBoard.blockID - 256);
		
		
		
		fence = new SCBlockFence(id_fence);
		Item.itemsList[fence.blockID] = new ItemBlock(fence.blockID - 256);	
		
		fenceRope = new SCBlockFenceRope(id_fenceRope);
		Item.itemsList[fenceRope.blockID] = new ItemBlock(fenceRope.blockID - 256);	
		
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


	private static void addTileEntityDefs() {
		//Custom entities
		TileEntity.addMapping(SCTileEntityCuttingBoard.class, "SCCuttingBoard");
		
		
	}
}
