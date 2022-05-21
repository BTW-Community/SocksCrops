package net.minecraft.src;

public class SCDefs {
	
	// --- BLOCK ID's --- //
	// SocksCrops: 2600 - 2999
	
	//Tile Entities
//	private static int
//		id_cuttingBoard = 2600,
//		id_storageJar = 2601,
//		id_fishTrap = 2602,
//		id_composter = 2603;
//		id_cookingPot = 2604,
//		id_juicer = 2605,
//		id_barrel = 2506,
//		id_mixer = 2607;
	
	//Logs
	private static int
		id_damagedLog = 2620,
		id_mossyLog = 2621;
	
	//Dirts
	private static int
		id_grassNutrition = 2622,
		id_dirtNutrition = 2623,
		id_dirtLooseNutrition = 2624;
	
	//Farmland
	private static int
		id_farmlandNutrition0 = 2625,
		id_farmlandNutrition1 = 2626,
		id_farmlandNutrition2 = 2627,
		id_farmlandNutrition3 = 2628,
		
		id_farmlandNutrition0Fertilized = 2629,
		id_farmlandNutrition1Fertilized = 2630,
		id_farmlandNutrition2Fertilized = 2631,
		id_farmlandNutrition3Fertilized = 2632,
		
		id_farmlandNutrition0Dung = 2633,
		id_farmlandNutrition1Dung = 2634,
		id_farmlandNutrition2Dung = 2635,
		id_farmlandNutrition3Dung = 2636;
		
//		id_farmlandNutrition0Straw = 2637,
//		id_farmlandNutrition1Straw = 2638,
//		id_farmlandNutrition2Straw = 2639,
//		id_farmlandNutrition3Straw = 2640;
	
//		id_farmlandNutrition0Straw = 2641,
//		id_farmlandNutrition1Straw = 2642,
//		id_farmlandNutrition2Straw = 2643,
//		id_farmlandNutrition3Straw = 2644;

	//Decorative plants
	private static int
		id_compostBlock = 2645,
		id_shortPlant = 2646,
		id_tallPlant = 2647,
		id_clover = 2648,
		id_mossCarpet = 2649,
		id_lilyRose = 2650,
		id_rocks = 2651,
		id_rocksSandstone = 2652;
		
	
	//Pumpkins
	private static int
		id_pumpkinStem = 2660,
		id_pumpkinVine = 2661,
		id_pumpkinVineFlowering = 2662,
		id_pumpkinOrange = 2663,
		id_pumpkinGreen = 2664,
		id_pumpkinYellow = 2665,
		id_pumpkinWhite = 2666,
		id_pumpkinHarvested = 2667,
		id_pumpkinCarved = 2668,
		id_pumpkinCarvedDead = 2669,
		id_pumpkinJack = 2670;
		
	//Melons
	private static int	
		id_melonStem = 2671,
		id_melonVine = 2672,
		id_melonVineFlowering = 2673,	
		id_melonWater = 2674,
		id_melonCanary = 2675,
		id_melonHoneydew = 2676,
		id_melonCantaloupe = 2677,
		id_melonHarvested = 2678,
		id_melonCanaryHarvested = 2679,
	
		id_gourdVineDead = 2680,
		id_gourdStemDead = 2681;

	
	//Bamboo
//	private static int	
//		id_bambooShoot = 2680,
//		id_bambooRoot = 2681,
//		id_bambooStalk = 2682;
	
	//Bushes
//	private static int
//		id_sweetberryBush = 2683,
//		id_blueberryBush = 2684;
	
	private static int maxID = 2999;
	
	// --- ITEM ID's --- //
	// 31000 - 31299
	
	//Tools
//	private static int
//		id_knifeStone = 31000,
//		id_knifeIron = 31001;
	
	//Pumpkin & Melon
	private static int
		id_melonCanarySlice = 31010,
		id_melonHoneydewSlice = 31011,
		id_melonCantaloupeSlice = 31012,
		id_pumpkinSliceRaw = 31013,
		id_pumpkinSliceRoasted = 31014,
		id_pumpkinSliceBoiled = 31015,
		id_pumpkinSoup = 31016;
	
	//Bamboo
//	private static int
//		id_bambooItem = 31020;
	
	//Berry Stuff
//	private static int
//		id_sweetberry = 31030,
//		id_sweetberrySapling = 31031,
//		
//		id_blueberry = 31032,
//		id_blueberrySapling = 31033,
//	
//		id_sweetberryPieRaw = 31034,
//		id_sweetberryPieCooked = 31035,
//		id_sweetberryPieSlice = 31036,
//		
//		id_blueberryPieRaw = 31037,
//		id_blueberryPieCooked = 31038,
//		id_blueberryPieSlice = 31039;
	
	// Other Pies
	private static int
		id_pieBase = 31040,
		id_pumpkinPieSlice = 31041,
		id_cakeSlice = 31042;
	
//	private static int
//		id_appleSlice = 31042,
//		id_applePieRaw = 31043,
//		id_applePieCooked = 31044,
//		id_applePieSlice = 31045;
	
	// Misc Fruit
//	private static int
//		id_fruitSaladBerries = 31050,
//		id_fruitSaladMelon = 31051;
	

	
	// --- Blocks ---
	
//	public static Block cuttingBoard;
//	public static Block storageJar;
//	public static Block fishTrap;
//	public static Block composter;
//	public static Block cookingPot;
//	public static Block juicer;
//	public static Block barrel;
//	public static Block mixer;
	
	public static Block grassNutrition;
	public static Block dirtNutrition;
	public static Block dirtLooseNutrition;
	
	public static Block farmlandNutrition0, farmlandNutrition0Fertilized, farmlandNutrition0Dung; // farmlandNutrition0Straw;
	public static Block farmlandNutrition1, farmlandNutrition1Fertilized, farmlandNutrition1Dung; // farmlandNutrition1Straw;
	public static Block farmlandNutrition2, farmlandNutrition2Fertilized, farmlandNutrition2Dung; // farmlandNutrition2Straw;
	public static Block farmlandNutrition3, farmlandNutrition3Fertilized, farmlandNutrition3Dung; // farmlandNutrition3Straw;
	
	public static Block damagedLog;
	public static Block mossyLog;
	
	public static Block compostBlock;
	public static Block shortPlant;
	public static Block tallPlant;
	
	public static Block clover;	
	public static Block mossCarpet;
	public static Block lilyRose;
	public static Block rocks;
	public static Block rocksSandstone;

	public static Block pumpkinStem;
	public static Block pumpkinVine;
	public static Block pumpkinVineFlowering;
	public static Block pumpkinOrange, pumpkinGreen, pumpkinYellow, pumpkinWhite;
	public static Block pumpkinHarvested;
	public static Block pumpkinCarved;
	public static Block pumpkinCarvedDead;
	public static Block pumpkinJack;
					
	
	
	public static Block melonStem;
	public static Block melonVine;
	public static Block melonVineFlowering;
	public static Block melonWater, melonCanary, melonHoneydew, melonCantaloupe;
	public static Block melonHarvested;
	public static Block melonCanaryHarvested;
	
	public static Block gourdVineDead;
	public static Block gourdStemDead;
	
//	public static Block bambooShoot, bambooRoot, bambooStalk;
//	
//	public static Block sweetberryBush;
//	public static Block blueberryBush;
	
	
	// --- Items ---
	
//	// Tools
//	public static Item knifeStone, knifeIron;
//	
//	// Gourds
	public static Item melonCanarySlice, melonHoneydewSlice, melonCantaloupeSlice;
	public static Item pumpkinSliceRaw, pumpkinSliceRoasted, pumpkinSliceBoiled;
	
//	public static Item pumpkinSoup;
//	
//	// Bamboo	
//	public static Item bambooItem;
//	
//	// Berries
//	public static Item sweetberry;
//	public static Item sweetberrySapling;
//	
//	public static Item blueberry;
//	public static Item blueberrySapling;
//	
//	public static Item sweetberryPieRaw;
//	public static Item sweetberryPieCooked;
//	public static Item sweetberryPieSlice;
//	
//	public static Item blueberryPieRaw;
//	public static Item blueberryPieCooked;
//	public static Item blueberryPieSlice;
//	
	// Other Pies
	public static Item pieBase;
	public static Item pumpkinPieSlice;
	public static Item cakeSlice;
//	
//	// Misc Food
//	public static Item appleSlice;
//	public static Item fruitBowlBerries;

	
	public static void addDefinitions()
	{
		//Nutrition
		addDirtReplacements();
		addGrassDef();
		addFarmlandDefs();
		
		//Decomposting
		addDecompostingDefs();
		
		//Deco Plants
		addDecorativeDefs();
		
		//Gourds
		addPumpkinDefs();
		addPumpkinItemsDefs();
		addMelonDefs();
		addMelonItemsDefs();
		
		addPieDefs();
	}	

	private static void addDirtReplacements()
	{		
		//New Loose Dirt
		FCBetterThanWolves.fcBlockDirtLoose = Block.replaceBlock(FCBetterThanWolves.fcBlockDirtLoose.blockID, SCBlockDirtLooseNutrition.class, SocksCropsAddon.instance);
		Item.itemsList[FCBetterThanWolves.fcBlockDirtLoose.blockID] = new ItemMultiTextureTile(FCBetterThanWolves.fcBlockDirtLoose.blockID - 256, FCBetterThanWolves.fcBlockDirtLoose, SCBlockDirtLooseNutrition.nutritionLevelNames);
		
		//New Dirt		
		Block.dirt = Block.replaceBlock(Block.dirt.blockID, SCBlockDirtNutrition.class, SocksCropsAddon.instance);
		Item.itemsList[Block.dirt.blockID] = new ItemMultiTextureTile(Block.dirt.blockID - 256, Block.dirt, SCBlockDirtNutrition.nutritionLevelNames);
		
		//Replacing FC's weeds with my own to make them grow on my farmland
		FCBetterThanWolves.fcBlockWeeds = (FCBlockWeeds) Block.replaceBlock(FCBetterThanWolves.fcBlockWeeds.blockID, SCBlockWeeds.class, SocksCropsAddon.instance);
	}

	private static void addGrassDef()
	{
		grassNutrition = new SCBlockGrassNutrition(id_grassNutrition);
		Item.itemsList[grassNutrition.blockID] = new ItemMultiTextureTile(id_grassNutrition - 256, grassNutrition, SCBlockGrassNutrition.nutritionLevelNames);
	}
	
	private static void addFarmlandDefs()
	{
		//Farmland Nutrition 3
		farmlandNutrition3 = new SCBlockFarmlandNutrition3(id_farmlandNutrition3);
		Item.itemsList[farmlandNutrition3.blockID] = new ItemBlock(id_farmlandNutrition3 - 256);
		
		farmlandNutrition3Fertilized = new SCBlockFarmlandNutrition3Fertilized(id_farmlandNutrition3Fertilized);
		Item.itemsList[farmlandNutrition3Fertilized.blockID] = new ItemBlock(id_farmlandNutrition3Fertilized - 256);
		
		farmlandNutrition3Dung = new SCBlockFarmlandNutrition3Dung(id_farmlandNutrition3Dung);
		Item.itemsList[farmlandNutrition3Dung.blockID] = new ItemBlock(id_farmlandNutrition3Dung - 256);
		
		//Farmland Nutrition 2                                                                                                                                                        
		farmlandNutrition2 = new SCBlockFarmlandNutrition2(id_farmlandNutrition2);
		Item.itemsList[farmlandNutrition2.blockID] = new ItemBlock(id_farmlandNutrition2 - 256);
		                                                                                                                                                                              
		farmlandNutrition2Fertilized = new SCBlockFarmlandNutrition2Fertilized(id_farmlandNutrition2Fertilized);
		Item.itemsList[farmlandNutrition2Fertilized.blockID] = new ItemBlock(id_farmlandNutrition2Fertilized - 256);
		
		farmlandNutrition2Dung = new SCBlockFarmlandNutrition2Dung(id_farmlandNutrition2Dung);
		Item.itemsList[farmlandNutrition2Dung.blockID] = new ItemBlock(id_farmlandNutrition2Dung - 256);
		
		//Farmland Nutrition 1		                                                                                                                                                  
		farmlandNutrition1 = new SCBlockFarmlandNutrition1(id_farmlandNutrition1);                                                                                                    
		Item.itemsList[farmlandNutrition1.blockID] = new ItemBlock(id_farmlandNutrition1 - 256);                                                                                 
		                                                                                                                                                                              
		farmlandNutrition1Fertilized = new SCBlockFarmlandNutrition1Fertilized(id_farmlandNutrition1Fertilized);                                                                      
		Item.itemsList[farmlandNutrition1Fertilized.blockID] = new ItemBlock(id_farmlandNutrition1Fertilized - 256);    
		
		farmlandNutrition1Dung = new SCBlockFarmlandNutrition1Dung(id_farmlandNutrition1Dung);
		Item.itemsList[farmlandNutrition1Dung.blockID] = new ItemBlock(id_farmlandNutrition1Dung - 256);
		                                                                                                                                                                              
		//Farmland Nutrition 0		                                                                                                                                                  
		farmlandNutrition0 = new SCBlockFarmlandNutrition0(id_farmlandNutrition0);
		Item.itemsList[farmlandNutrition0.blockID] = new ItemBlock(id_farmlandNutrition0 - 256);
		                                                                                                                                                                              
		farmlandNutrition0Fertilized = new SCBlockFarmlandNutrition0Fertilized(id_farmlandNutrition0Fertilized);                                                                      
		Item.itemsList[farmlandNutrition0Fertilized.blockID] = new ItemBlock(id_farmlandNutrition0Fertilized - 256);
		
		farmlandNutrition0Dung = new SCBlockFarmlandNutrition0Dung(id_farmlandNutrition0Dung);
		Item.itemsList[farmlandNutrition0Dung.blockID] = new ItemBlock(id_farmlandNutrition0Dung - 256);
	}

	private static void addDecompostingDefs()
	{
		damagedLog = new SCBlockDamagedLog(id_damagedLog);
		Item.itemsList[damagedLog.blockID] = new ItemMultiTextureTile(id_damagedLog - 256, damagedLog, SCBlockDamagedLog.treeTextures);
		
		mossyLog = new SCBlockMossyLog(id_mossyLog);
		Item.itemsList[mossyLog.blockID] = new ItemMultiTextureTile(id_mossyLog - 256, mossyLog, SCBlockMossyLog.treeTextures);
		
		compostBlock = new SCBlockCompost(id_compostBlock);
		Item.itemsList[compostBlock.blockID] = new ItemBlock(id_compostBlock - 256);		
	}

	private static void addDecorativeDefs()
	{
		tallPlant = new SCBlockTallPlant(id_tallPlant);
		Item.itemsList[tallPlant.blockID] = new ItemMultiTextureTile(tallPlant.blockID - 256, tallPlant, new String[] {"grass","fern"});
		
		shortPlant = new SCBlockShortPlant (id_shortPlant);
		Item.itemsList[shortPlant.blockID] = new ItemMultiTextureTile(id_shortPlant - 256, shortPlant, new String[] {"shortGrass", "tallGrass"});
		
		clover = new SCBlockClover (id_clover);
		Item.itemsList[clover.blockID] = new ItemMultiTextureTile(id_clover - 256, clover, new String [] {"clover", "cloverPurple", "cloverWhite", "cloverRed"});
		
		mossCarpet = new SCBlockMoss(id_mossCarpet);
		Item.itemsList[mossCarpet.blockID] = new ItemBlock(id_mossCarpet - 256);
		
		lilyRose = new SCBlockLilyRose(id_lilyRose);
		Item.itemsList[lilyRose.blockID] = new ItemBlock(id_lilyRose - 256);
		
		rocks = new SCBlockRocks (id_rocks, "stone", "SCBlockRocksStone");
		Item.itemsList[rocks.blockID] = new SCItemRocks(id_rocks - 256, rocks, "SCItemRocksStone",
				new String[]{
					"smallRock", "largeRock",
					"smallRockMossy", "largeRockMossy"
					
				});
		
		rocksSandstone = new SCBlockRocks (id_rocksSandstone, "sandstone_bottom", "SCBlockRocksSandstone");
		Item.itemsList[rocksSandstone.blockID] = new SCItemRocks(id_rocksSandstone - 256, rocksSandstone, "SCItemRocksSandstone",
				new String[] {
					"smallRock", "largeRock",
					"smallRockMossy", "largeRockMossy"
				});
	}

	private static void addPumpkinDefs()
	{
		//Growing
		pumpkinOrange = new SCBlockPumpkinGrowingOrange(id_pumpkinOrange, id_pumpkinStem, id_pumpkinVine, id_pumpkinVineFlowering, id_pumpkinHarvested);
		Item.itemsList[pumpkinOrange.blockID] = new ItemBlock(id_pumpkinOrange - 256);
		
		pumpkinGreen = new SCBlockPumpkinGrowingGreen(id_pumpkinGreen, id_pumpkinStem, id_pumpkinVine, id_pumpkinVineFlowering, id_pumpkinHarvested);
		Item.itemsList[pumpkinGreen.blockID] = new ItemBlock(id_pumpkinGreen - 256);
		
		pumpkinYellow = new SCBlockPumpkinGrowingYellow(id_pumpkinYellow, id_pumpkinStem, id_pumpkinVine, id_pumpkinVineFlowering, id_pumpkinHarvested);
		Item.itemsList[pumpkinYellow.blockID] = new ItemBlock(id_pumpkinYellow - 256);
		
		pumpkinWhite = new SCBlockPumpkinGrowingWhite(id_pumpkinWhite, id_pumpkinStem, id_pumpkinVine, id_pumpkinVineFlowering, id_pumpkinHarvested);
		Item.itemsList[pumpkinWhite.blockID] = new ItemBlock(id_pumpkinWhite- 256);
		
		//Harvested
		pumpkinHarvested = new SCBlockPumpkinHarvested(id_pumpkinHarvested);
		Item.itemsList[pumpkinHarvested.blockID] = new ItemMultiTextureTile(id_pumpkinHarvested - 256, pumpkinHarvested, new String[] {
				"orange_0", "orange_1", "orange_2", "orange_3", 
				"green_0", "green_1", "green_2", "green_3",
				"yellow_0", "yellow_1", "yellow_2", "yellow_3",
				"white_0", "white_1", "white_2", "white_3"
		});

		//Carved
		pumpkinCarved = new SCBlockPumpkinCarved(id_pumpkinCarved);
		Item.itemsList[pumpkinCarved.blockID] = new ItemMultiTextureTile(id_pumpkinCarved - 256, pumpkinCarved, new String[] {
				"orange", "green", "yellow", "white"
		});
		
		pumpkinCarvedDead = new SCBlockPumpkinCarvedDead(id_pumpkinCarvedDead);
		Item.itemsList[pumpkinCarvedDead.blockID] = new ItemMultiTextureTile(id_pumpkinCarvedDead - 256, pumpkinCarvedDead, new String[] {
				"orange", "green", "yellow", "white"
		});
		
		pumpkinJack = new SCBlockPumpkinJack(id_pumpkinJack);
		Item.itemsList[pumpkinJack.blockID] = new ItemMultiTextureTile(id_pumpkinJack - 256, pumpkinJack, new String[] {
				"orange", "green", "yellow", "white"
		});
		
		//Vine
		pumpkinVine = new SCBlockGourdVine(id_pumpkinVine, id_pumpkinVineFlowering, id_pumpkinStem, id_gourdVineDead, "SCBlockPumpkinVine_", "SCBlockPumpkinVineConnector_");
		Item.itemsList[pumpkinVine.blockID] = new ItemBlock(id_pumpkinVine - 256);
		
		gourdVineDead = new SCBlockGourdVineDead(id_gourdVineDead, id_pumpkinVineFlowering, id_pumpkinStem);
		Item.itemsList[gourdVineDead.blockID] = new ItemBlock(id_gourdVineDead - 256);
		
		//Flower
		pumpkinVineFlowering = new SCBlockPumpkinVineFlowering(id_pumpkinVineFlowering, id_pumpkinVine, id_pumpkinStem, pumpkinOrange, pumpkinGreen, pumpkinYellow, pumpkinWhite, id_gourdVineDead);
		Item.itemsList[pumpkinVineFlowering.blockID] = new ItemBlock(id_pumpkinVineFlowering - 256);

		
		pumpkinStem = new SCBlockGourdStem(id_pumpkinStem, id_pumpkinVine, id_pumpkinVineFlowering, gourdStemDead);
		Item.itemsList[pumpkinStem.blockID] = new ItemBlock(id_pumpkinStem - 256);
		
		gourdStemDead = new SCBlockGourdStemDead(id_gourdStemDead);
		Item.itemsList[pumpkinStem.blockID] = new ItemBlock(id_gourdStemDead - 256);
	
	}
	
	private static void addPumpkinItemsDefs()
	{
		Item.pumpkinSeeds = Item.replaceItem(Item.pumpkinSeeds.itemID, FCItemSeedFood.class, SocksCropsAddon.instance, 1, 0F, id_pumpkinStem).setUnlocalizedName( "seeds_pumpkin" );
		
		pumpkinSliceRaw = new Item ( id_pumpkinSliceRaw - 256).setUnlocalizedName("SCItemPumpkinSlice").SetBuoyant().setCreativeTab(CreativeTabs.tabFood);
		pumpkinSliceRoasted = new FCItemFoodHighRes(id_pumpkinSliceRoasted - 256, 2, 0F, false, "SCItemPumpkinSlice_roasted");
		pumpkinSliceBoiled = new FCItemFoodHighRes(id_pumpkinSliceBoiled - 256, 2, 0F, false, "SCItemPumpkinSlice_boiled");	
	}
	
	private static void addMelonDefs()
	{
		//Growing (Types: Watermelon (Green/Red),  Canary melon (Yellow/White), Honeydew (Light Green), Cantaloupe (Green/Orange)
		melonWater = new SCBlockMelonWaterGrowing(id_melonWater, id_melonStem, id_melonVine, id_melonVineFlowering, id_melonHarvested);
		Item.itemsList[melonWater.blockID] = new ItemBlock(id_melonWater - 256);
		
		melonCanary = new SCBlockMelonCanaryGrowing(id_melonCanary, id_melonStem, id_melonVine, id_melonVineFlowering, id_melonCanaryHarvested);
		Item.itemsList[melonCanary.blockID] = new ItemBlock(id_melonCanary - 256);
		
		melonHoneydew = new SCBlockMelonHoneydewGrowing(id_melonHoneydew, id_melonStem, id_melonVine, id_melonVineFlowering, id_melonHarvested);
		Item.itemsList[melonHoneydew.blockID] = new ItemBlock(id_melonHoneydew - 256);
		
		melonCantaloupe = new SCBlockMelonCantaloupeGrowing(id_melonCantaloupe, id_melonStem, id_melonVine, id_melonVineFlowering, id_melonHarvested);
		Item.itemsList[melonCantaloupe.blockID] = new ItemBlock(id_melonCantaloupe - 256);
		
		//Harvested
		melonHarvested = new SCBlockMelonHarvested(id_melonHarvested);
		Item.itemsList[melonHarvested.blockID] = new ItemMultiTextureTile(id_melonHarvested - 256, melonHarvested, new String[] {
				"water_0", "water_1", "water_2", "water_3", //Water
				"honeydew_0", "honeydew_1", "honeydew_2", "honeydew_3", //Honeydew
				"cantaloupe_0", "cantaloupe_1", "cantaloupe_2", "cantaloupe_3" //Cantaloupe
		});
		
		melonCanaryHarvested = new SCBlockMelonCanaryHarvested(id_melonCanaryHarvested);
		Item.itemsList[melonCanaryHarvested.blockID] = new ItemMultiTextureTile(id_melonCanaryHarvested - 256, melonCanaryHarvested, new String[] {
				"canary_0", "", "", "",
				"canary_3", "", "", ""
		});
		
		//Vine
		melonVine = new SCBlockGourdVine(id_melonVine, id_melonVineFlowering, id_melonStem, id_gourdVineDead, "SCBlockPumpkinVine_", "SCBlockPumpkinVineConnector_");
		Item.itemsList[melonVine.blockID] = new ItemBlock(id_melonVine - 256);
		
		//Flower
		melonVineFlowering = new SCBlockMelonVineFlowering(id_melonVineFlowering, id_melonVine, id_melonStem, melonWater, melonHoneydew, melonCantaloupe, melonCanary, id_gourdVineDead);
		Item.itemsList[melonVineFlowering.blockID] = new ItemBlock(id_melonVineFlowering - 256);
		
		//Stem
		melonStem = new SCBlockGourdStem(id_melonStem, id_melonVine, id_melonVineFlowering, gourdStemDead);
		Item.itemsList[melonStem.blockID] = new ItemBlock(id_melonStem - 256);		
	}

	private static void addMelonItemsDefs()
	{
		Item.melonSeeds = Item.replaceItem(Item.melonSeeds.itemID, FCItemSeeds.class, SocksCropsAddon.instance, id_melonStem).setUnlocalizedName( "seeds_melon" );

	    Item.melon = Item.replaceItem( Item.melon.itemID, SCItemMelonSlice.class, SocksCropsAddon.instance, "melon");	    	    
	    melonCanarySlice = new SCItemMelonSlice( id_melonCanarySlice - 256, "SCItemMelonYellowSlice");
	    melonHoneydewSlice = new SCItemMelonSlice( id_melonHoneydewSlice - 256, "SCItemMelonWhiteSlice");
	    melonCantaloupeSlice = new SCItemMelonSlice( id_melonCantaloupeSlice - 256, "SCItemMelonGreenSlice");
	}

	private static void addPieDefs()
	{
		pieBase = new Item(id_pieBase - 256).setCreativeTab(CreativeTabs.tabFood).setUnlocalizedName("SCItemPieBaseRaw");
		pumpkinPieSlice = new FCItemFood ( id_pumpkinPieSlice - 256, 1, 2.5F, false, "SCItemPumpkinPieSlice").setAlwaysEdible();
		cakeSlice = new FCItemFood ( id_cakeSlice - 256, 1, 2.5F, false, "SCItemCakeSlice").setAlwaysEdible();
	}

}
