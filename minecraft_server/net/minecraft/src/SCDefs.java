package net.minecraft.src;

public class SCDefs {
	
	// --- BLOCK ID's --- //
	// SocksCrops: 2600 - 2999
	
	//Tile Entities
	private static int
		id_choppingBoard = 2600,
		id_storageJar = 2601,
		id_fishTrap = 2602,
		id_composter = 2603,
		id_flowerPot = 2604,
		id_waterPot = 2605;
//		id_cookingPot = 2604,
//		id_juicer = 2605,
//		id_barrel = 2506,
//		id_mixer = 2607;
		
	//Dirts
	private static int
		id_grassNutrition = 2620,
		id_dirtNutrition = 2621,
		id_dirtLooseNutrition = 2622;
	
	//Farmland
	private static int
		id_farmlandNutrition0 = 2623,
		id_farmlandNutrition1 = 2624,
		id_farmlandNutrition2 = 2625,
		id_farmlandNutrition3 = 2626,
		
		id_farmlandNutrition0Fertilized = 2627,
		id_farmlandNutrition1Fertilized = 2628,
		id_farmlandNutrition2Fertilized = 2629,
		id_farmlandNutrition3Fertilized = 2630,
		
		id_farmlandNutrition0Dung = 2631,
		id_farmlandNutrition1Dung = 2632,
		id_farmlandNutrition2Dung = 2633,
		id_farmlandNutrition3Dung = 2634;
		
//		id_farmlandNutrition0Straw = 2635,
//		id_farmlandNutrition1Straw = 2636,
//		id_farmlandNutrition2Straw = 2637,
//		id_farmlandNutrition3Straw = 2638;
	
//		id_farmlandNutrition0Straw = 2639,
//		id_farmlandNutrition1Straw = 2640,
//		id_farmlandNutrition2Straw = 2641,
//		id_farmlandNutrition3Straw = 2642;

	//Logs
	private static int
		id_hollowLog = 2650,
		id_damagedLog = 2651,
		id_mossyLog = 2652;
	
	//Decorative plants
	private static int
		id_compostBlock = 2655,
		id_shortPlant = 2656,
		id_tallPlant = 2657,
		id_clover = 2658,
		id_mossBlock = 2659,
		id_mossCarpet = 2660,
		id_lilyRose = 2661,
		id_rocks = 2662,
		id_rocksSandstone = 2663;
		
	
	//Pumpkins
	private static int
		id_pumpkinStem = 2670,
		id_pumpkinVine = 2671,
		id_pumpkinVineFlowering = 2672,
		id_pumpkinOrange = 2673,
		id_pumpkinGreen = 2674,
		id_pumpkinYellow = 2675,
		id_pumpkinWhite = 2676,
		id_pumpkinHarvested = 2677,
		id_pumpkinCarved = 2678,
		id_pumpkinCarvedDead = 2679,
		id_pumpkinJack = 2680;
		
	//Melons
	private static int	
		id_melonStem = 2681,
		id_melonVine = 2682,
		id_melonVineFlowering = 2683,	
		id_melonWater = 2684,
		id_melonCanary = 2685,
		id_melonHoneydew = 2686,
		id_melonCantaloupe = 2687,
		id_melonHarvested = 2688,
		id_melonCanaryHarvested = 2689,
	
		id_gourdVineDead = 2690,
		id_gourdStemDead = 2691;

	//Bamboo
	private static int	
		id_bambooShoot = 2692,
		id_bambooRoot = 2693,
		id_bambooStalk = 2694,
		id_bambooPacked = 2695;
	
	//Pies
	private static int
		id_pieRaw = 2700,
		id_pieCooked = 2701;
	
	//Bushes
	private static int
		id_sweetberryBush = 2705,
		id_blueberryBush = 2706;
	
	private static int
		id_wildCarrotCrop = 2710,
		id_wildCarrotCropHighYield = 2711,
		id_wildCarrotCropSapling = 2712,
		id_wildPotatoCrop = 2713,
		id_wildPotatoCropHighYield = 2714,
		id_wildPotatoCropSapling = 2715;
	
	private static int maxID = 2999;
	
	// --- ITEM ID's --- //
	// 31000 - 31299
	
	//Tools
	private static int
		id_knifeStone = 31000,
		id_knifeIron = 31001,
		id_knifeDiamond = 31002,
		id_waterPotEmpty = 31003;
		
	//Pumpkin & Melon
	private static int
		id_melonCanarySlice = 31010,
		id_melonHoneydewSlice = 31011,
		id_melonCantaloupeSlice = 31012,
		id_pumpkinSliceRaw = 31013,
		id_pumpkinSliceRoasted = 31014,
		id_pumpkinSliceBoiled = 31015;
	
	//Bamboo
	private static int
		id_bambooItem = 31020;
	
	//Moss
	private static int
		id_mossBall = 31029;
	
	private static int
		id_salmonRaw = 31030,
		id_salmonCooked = 31031,
		id_codRaw = 31032,
		id_codCooked = 31033,
		id_tropicalRaw = 31034,
		id_tropicalCooked = 31035;
	
	// Other Pies
	private static int
		id_pieCrust = 31040,
		id_pumpkinPieSlice = 31041,
		id_cakeSlice = 31042;
	
	//Berry Stuff
	private static int
		id_sweetberry = 31043,
		id_sweetberrySapling = 31044,
		
		id_sweetberryPieRaw = 31045,
		id_sweetberryPieCooked = 31046,
		id_sweetberryPieSlice = 31047,
		
		id_blueberry = 31048,
		id_blueberrySapling = 31049,
		
		id_blueberryPieRaw = 31050,
		id_blueberryPieCooked = 31051,
		id_blueberryPieSlice = 31052,
		
		id_berryBowl = 31053;
	
	private static int
		id_wildCarrot = 31060,
		id_wildCarrotSeed = 31061,
		id_wildCarrotRoot = 31062,
		id_wildCarrotTop = 31063,
		id_wildPotato = 31064,
		id_wildPotatoCut = 31065,
		id_potatoCut=31066;
	
	private static int
		id_beefPattyRaw =  31070,
		id_beefPattyCooked = 31071,     
		id_breadSlice = 31072,
		id_burger = 31073,
		id_burgerEgg = 31074;
	
	// --- Blocks --- //
	
	public static Block choppingBoard;
	public static Block storageJar;
	public static Block fishTrap;
	public static Block composter;
	public static Block flowerPot;
	public static Block waterPot;
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
	
	public static Block hollowLog;

	public static Block damagedLog;
	public static Block mossyLog;
	
	public static Block compostBlock;
	public static Block shortPlant;
	public static Block tallPlant;
	
	public static Block clover;	
	public static Block mossBlock;
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
	
	public static Block bambooShoot, bambooRoot, bambooStalk;
	public static Block bambooPacked;
	
	public static Block pieRaw;
	public static Block pieCooked;
	
	public static Block sweetberryBush;
	public static Block blueberryBush;
	
	public static Block wildCarrotCrop;
	public static Block wildCarrotCropHighYield;
	public static Block wildCarrotCropSapling;
	public static Block wildPotatoCrop;
	public static Block wildPotatoCropHighYield;
	public static Block wildPotatoCropSapling;
	
	// --- Items ---
	
	//
	public static Item waterPotEmpty;
	
	// Tools
	public static Item knifeStone, knifeIron, knifeDiamond;
	
	// Gourds
	public static Item melonCanarySlice, melonHoneydewSlice, melonCantaloupeSlice;
	public static Item pumpkinSliceRaw, pumpkinSliceRoasted, pumpkinSliceBoiled;
	
	// Bamboo	
	public static Item bambooItem;
	
	public static Item salmonRaw;
	public static Item salmonCooked;
	public static Item codRaw;
	public static Item codCooked;
	public static Item tropicalRaw;
	public static Item tropicalCooked;
	
	// Other Pies
	public static Item pieCrust;
	public static Item pumpkinPieSlice;
	public static Item cakeSlice;
	
	// Berries
	public static Item sweetberry;
	public static Item sweetberrySapling;
	
	public static Item sweetberryPieRaw;
	public static Item sweetberryPieCooked;
	public static Item sweetberryPieSlice;
	
	public static Item blueberry;
	public static Item blueberrySapling;	

	public static Item blueberryPieRaw;
	public static Item blueberryPieCooked;
	public static Item blueberryPieSlice;
	
	public static Item berryBowl;
	
	public static Item wildCarrot;
	public static Item wildCarrotSeeds;
	
	public static Item wildCarrotRoot;
	public static Item wildCarrotTop;
	
	public static Item wildPotato;	
	public static Item wildPotatoCut;	
	
	public static Item potatoCut;
	
	public static Item beefPattyRaw;
	public static Item beefPattyCooked;
                
	public static Item breadSlice;
       
	public static Item burger;
	public static Item burgerEgg;
	
	public static Item mossBall;

	private static String[] overrideDeco = new String[] { SCDecoIntegration.DECOADDON };
	
	public static void addTileEntityDefinitions()
	{
		addTileEntityDefs();
		addTileEntityMapping();
	}
	
	public static void addDefinitions()
	{
		//Tools
		addToolDefs();
		
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
		
		addBambooDefs();
		
		addFishDefs();
		
		addBerryDefs();
		
		addBurgerDefs();
		
		addDomesticDefs();
		addWildDefs();		
	}

	private static void addTileEntityMapping()
	{
		TileEntity.addMapping(SCTileEntityFishTrap.class, "SCFishTrap");
		TileEntity.addMapping(SCTileEntityChoppingBoard.class, "SCChoppingBoard");
		TileEntity.addMapping(SCTileEntityComposter.class, "SCComposter");
		TileEntity.addMapping(SCTileEntityStorageJar.class, "SCStorageJar");
		TileEntity.addMapping(SCTileEntityFlowerPot.class, "SCFlowerPot");
		TileEntity.addMapping(SCTileEntityWaterPot.class, "SCWaterPot");

	}
	
	private static void addTileEntityDefs()
	{
		fishTrap = new SCBlockFishTrap(id_fishTrap);
		Item.itemsList[fishTrap.blockID] = new ItemBlock(fishTrap.blockID - 256)
				.setMaxStackSize( 1 );
		
		choppingBoard = new SCBlockChoppingBoard(id_choppingBoard);
		Item.itemsList[choppingBoard.blockID] =( new ItemReed( id_choppingBoard - 256, choppingBoard ) )
				.SetBuoyant()
				.SetIncineratedInCrucible()
				.setMaxStackSize( 1 )
				.setUnlocalizedName( "SCItemChoppingBoard_spruce" )
				.setCreativeTab( CreativeTabs.tabDecorations );
		
		composter = new SCBlockComposter(id_composter);
		Item.itemsList[composter.blockID] = new ItemBlock(composter.blockID - 256);
		
		storageJar = new SCBlockStorageJar(id_storageJar);
		Item.itemsList[storageJar.blockID] = new SCItemBlockStorageJar(id_storageJar - 256);	
		
		flowerPot = new SCBlockFlowerPot(id_flowerPot);
		Item.replaceItem(Item.flowerPot.itemID, SCItemFlowerPot.class, overrideDeco,SocksCropsAddon.instance);
		
		waterPot = new SCBlockWaterPot(id_waterPot);
		Item.itemsList[waterPot.blockID] = new FCItemPlacesAsBlock (id_waterPot - 256, id_waterPot, SCBlockWaterPot.water).setUnlocalizedName("SCItemPot_water").setCreativeTab(CreativeTabs.tabDecorations);
		
		waterPotEmpty = new SCItemWaterPotEmpty (id_waterPotEmpty - 256, id_waterPot, SCBlockWaterPot.empty).setUnlocalizedName("SCItemPot_empty").setCreativeTab(CreativeTabs.tabDecorations);
	}

	private static void addToolDefs()
	{
		knifeStone = new SCItemKnife(id_knifeStone - 256, EnumToolMaterial.STONE, "SCItemKnife_stone");		
		knifeIron = new SCItemKnife(id_knifeIron - 256, EnumToolMaterial.IRON, "SCItemKnife_iron");			
		knifeDiamond = new SCItemKnife(id_knifeDiamond - 256, EnumToolMaterial.EMERALD, "SCItemKnife_diamond");			
	}

	private static void addDirtReplacements()
	{		
		//New Loose Dirt
		FCBetterThanWolves.fcBlockDirtLoose = Block.replaceBlock(FCBetterThanWolves.fcBlockDirtLoose.blockID, SCBlockDirtLooseNutrition.class, overrideDeco, SocksCropsAddon.instance);
		Item.itemsList[FCBetterThanWolves.fcBlockDirtLoose.blockID] = new ItemMultiTextureTile(FCBetterThanWolves.fcBlockDirtLoose.blockID - 256, FCBetterThanWolves.fcBlockDirtLoose, SCBlockDirtLooseNutrition.nutritionLevelNames);
		
		//New Dirt		
		Block.dirt = Block.replaceBlock(Block.dirt.blockID, SCBlockDirtNutrition.class, overrideDeco, SocksCropsAddon.instance);
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
		hollowLog = new SCBlockHollowLog(id_hollowLog);
		Item.itemsList[hollowLog.blockID] = new ItemMultiTextureTile(id_hollowLog - 256, hollowLog, SCBlockHollowLog.treeNames);
		
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
		
		mossBall = new Item(id_mossBall - 256).setUnlocalizedName("SCItemMossBall").setCreativeTab(CreativeTabs.tabMaterials);
		
		mossBlock = new SCBlockMoss(id_mossBlock);
		Item.itemsList[mossBlock.blockID] = new ItemBlock(id_mossBlock - 256);
				
		mossCarpet = new SCBlockMossCarpet(id_mossCarpet);
		Item.itemsList[mossCarpet.blockID] = new FCItemPlacesAsBlock(id_mossCarpet - 256).setUnlocalizedName("SCItemMossCarpet").setCreativeTab(CreativeTabs.tabDecorations);;
		
		lilyRose = new SCBlockLilyRose(id_lilyRose);
		Item.itemsList[lilyRose.blockID] = new SCItemBlockLily(id_lilyRose - 256, lilyRose);
		
		//copied from deco to make waterlily placeable
		Item.itemsList[Block.waterlily.blockID] = new SCItemBlockLily(Block.waterlily.blockID - 256, Block.waterlily);
		
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
				})
				.setMaxStackSize( 16 );

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
				})
				.setMaxStackSize( 16 );
		
		melonCanaryHarvested = new SCBlockMelonCanaryHarvested(id_melonCanaryHarvested);
		Item.itemsList[melonCanaryHarvested.blockID] = new ItemMultiTextureTile(id_melonCanaryHarvested - 256, melonCanaryHarvested, new String[] {
				"canary_0", "", "", "",
				"canary_1", "", "", "",
				"canary_2", "", "", "",
				"canary_3", "", "", ""
				})
				.setMaxStackSize( 16 );;
		
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
		// Cake		
		Block.cake = Block.replaceBlock(Block.cake.blockID, SCBlockCake.class, SocksCropsAddon.instance);
		cakeSlice = new FCItemFoodHighRes ( id_cakeSlice - 256, 4, 2.5F, false, "SCItemCakeSlice").setAlwaysEdible();
		
		//Pie
		pieRaw = new SCBlockPieRaw(id_pieRaw);
		Item.itemsList[pieRaw.blockID] = new ItemMultiTextureTile(id_pieRaw - 256, pieRaw, new String[] {
				"sweet", "sweet", "sweet", "sweet",
				"blue", "blue", "blue", "blue",});
		
		pieCooked = new SCBlockPieCooked(id_pieCooked);
		Item.itemsList[pieCooked.blockID] = new ItemMultiTextureTile(id_pieCooked - 256, pieCooked, new String[] {
				"pumpkin", "pumpkin", "pumpkin", "pumpkin",
				"sweet", "sweet", "sweet", "sweet",
				"blue", "blue", "blue", "blue",});
		
		// Pumpkin
		pumpkinPieSlice = new FCItemFoodHighRes ( id_pumpkinPieSlice - 256, 2, 2.5F, false, "SCItemPieSlice_pumpkin").setAlwaysEdible();
		
		Item.pumpkinPie = Item.replaceItem( Item.pumpkinPie.itemID, SCItemPie.class, SocksCropsAddon.instance, 3, 2.5F, false, "pumpkinPie",
				id_pieCooked, SCBlockPieCooked.subtypePumpkin);
		
		//Berry Pies
		sweetberryPieRaw = new FCItemPlacesAsBlock(id_sweetberryPieRaw - 256, id_pieRaw, SCBlockPieRaw.subtypeSweetberry, "SCItemPieRaw_sweetberry").SetBuoyant().setCreativeTab(CreativeTabs.tabFood);	
		
		blueberryPieRaw = new FCItemPlacesAsBlock(id_blueberryPieRaw - 256, id_pieRaw, SCBlockPieRaw.subtypeBlueberry, "SCItemPieRaw_blueberry").SetBuoyant().setCreativeTab(CreativeTabs.tabFood);	
		
		sweetberryPieCooked = new SCItemPie(id_sweetberryPieCooked - 256, 3, 2.5F, false, "SCItemPieCooked_sweetberry",
				id_pieCooked, SCBlockPieCooked.subtypeSweetberry);
		
		blueberryPieCooked = new SCItemPie(id_blueberryPieCooked - 256, 3, 2.5F, false, "SCItemPieCooked_blueberry",
				id_pieCooked, SCBlockPieCooked.subtypeBlueberry);
		
		sweetberryPieSlice = new FCItemFoodHighRes ( id_sweetberryPieSlice - 256, 2, 2.5F, false, "SCItemPieSlice_sweetberry").setAlwaysEdible();
		blueberryPieSlice = new FCItemFoodHighRes ( id_blueberryPieSlice - 256, 2, 2.5F, false, "SCItemPieSlice_blueberry").setAlwaysEdible();

		//Crust
		pieCrust = new Item(id_pieCrust - 256).SetBuoyant().setCreativeTab(CreativeTabs.tabFood).setUnlocalizedName("SCItemPieRaw_crust");
	}

	private static void addBambooDefs()
	{
		bambooShoot = new SCBlockBambooShoot(id_bambooShoot);
		Item.itemsList[bambooShoot.blockID] = new ItemBlock(id_bambooShoot - 256);
		
		bambooRoot = new SCBlockBambooRoot(id_bambooRoot);
		Item.itemsList[bambooRoot.blockID] = new SCItemBlockBambooRoot(id_bambooRoot - 256).setUnlocalizedName("SCItemBambooRoot_display");
		
		bambooStalk = new SCBlockBambooStalk(id_bambooStalk);
		Item.itemsList[bambooStalk.blockID] = new ItemBlock(id_bambooStalk - 256);
		
		bambooPacked = new FCBlockDirectional(id_bambooPacked, Material.wood, new String[] {"SCBlockBambooPacked_top"}, new String[] {"SCBlockBambooPacked_side"})
				.setUnlocalizedName("SCBlockBambooPacked")
				.setCreativeTab(CreativeTabs.tabBlock);
		Item.itemsList[bambooPacked.blockID] = new ItemBlock(id_bambooPacked - 256);
		
		bambooItem = new SCItemBamboo(id_bambooItem - 256);
	}
	
	private static void addFishDefs()
	{
		salmonRaw = new FCItemFood(id_salmonRaw - 256, 3, 0.25F, false, "SCItemFishSalmon_raw").SetStandardFoodPoisoningEffect();
		salmonCooked = new FCItemFood(id_salmonCooked - 256, 4, 0.25F, false, "SCItemFishSalmon_cooked");
		
		codRaw = new FCItemFood(id_codRaw - 256, 3, 0.25F, false, "SCItemFishCod_raw").SetStandardFoodPoisoningEffect();
		codCooked = new FCItemFood(id_codCooked - 256, 4, 0.25F, false, "SCItemFishCod_cooked");
		
		tropicalRaw = new FCItemFood(id_tropicalRaw - 256, 3, 0.25F, false, "SCItemFishTropical_raw").SetStandardFoodPoisoningEffect();
		tropicalCooked = new FCItemFood(id_tropicalCooked - 256, 4, 0.25F, false, "SCItemFishTropical_cooked");
	}
	
	private static void addBerryDefs()
	{
		//Sweetberry
		sweetberryBush = new SCBlockBerryBush(id_sweetberryBush, id_sweetberry, id_sweetberrySapling, "SCBlockBushSweetberry");
		Item.itemsList[sweetberryBush.blockID] = new ItemMultiTextureTile(id_sweetberryBush - 256, sweetberryBush, new String[] {
				"sweet_0", "sweet_0", "sweet_0", "sweet_0",	"sweet_0", "sweet_0" });
		
		sweetberrySapling = new FCItemSeeds(id_sweetberrySapling - 256, id_sweetberryBush)
				.setCreativeTab(CreativeTabs.tabDecorations)
				.setUnlocalizedName( "SCItemBushSapling_sweetberry" );
		
		sweetberry = new FCItemFood(id_sweetberry - 256, 1, 0.0F, false, "SCItemSweetberry");
		
		//Blueberry
		blueberryBush = new SCBlockBerryBush(id_blueberryBush, id_blueberry, id_blueberrySapling, "SCBlockBushBlueberry");
		Item.itemsList[blueberryBush.blockID] = new ItemMultiTextureTile(id_blueberryBush - 256, blueberryBush, new String[] {
				"blue_0", "blue_0", "blue_0", "blue_0",	"blue_0", "blue_0" });
		
		blueberrySapling = new FCItemSeeds(id_blueberrySapling - 256, id_blueberryBush)
				.setCreativeTab(CreativeTabs.tabDecorations)
				.setUnlocalizedName( "SCItemBushSapling_blueberry" );
		
		blueberry = new FCItemFood(id_blueberry - 256, 1, 0.0F, false, "SCItemBlueberry");
		
		berryBowl = new FCItemSoup(id_berryBowl - 256, 2, 0.25F, false, "SCItemFruitBowl_berries");
	}
	
	private static void addDomesticDefs()
	{		
		potatoCut = ( new FCItemSeedFood(id_potatoCut - 256, 1, 0F, Block.potato.blockID ) )
				.SetFilterableProperties( Item.m_iFilterable_Small )
				.SetAsBasicPigFood()
				.setUnlocalizedName( "SCItemPotatoCut" );
	}
	

	private static void addWildDefs()
	{
		/* CARROTS */
		
		wildCarrot = ( new FCItemFood( id_wildCarrot - 256, 3, 0F, false, "SCItemWildCarrot"))
				.SetFilterableProperties( Item.m_iFilterable_Small )
				.SetAsBasicPigFood();
		
		wildCarrotRoot = ( new FCItemFood( id_wildCarrotRoot - 256, 3, 0F, false, "SCItemWildCarrotRoot"))
				.SetFilterableProperties( Item.m_iFilterable_Small )
				.SetAsBasicPigFood();
		
		wildCarrotTop = new Item(id_wildCarrotTop - 256).setUnlocalizedName("SCItemWildCarrotCut");
		
		wildCarrotSeeds = new FCItemSeeds(id_wildCarrotSeed - 256, id_wildCarrotCrop)
				.setUnlocalizedName("SCItemWildCarrotSeeds");

		wildCarrotCrop = new SCBlockCropWildCarrot(id_wildCarrotCrop);
		wildCarrotCropHighYield = new SCBlockCropWildCarrotHighYield(id_wildCarrotCropHighYield);
		
		wildCarrotCropSapling =  new SCBlockCropWildCarrotSapling(id_wildCarrotCropSapling);
		Item.itemsList[wildCarrotCropSapling.blockID] = new FCItemSeeds(id_wildCarrotCropSapling - 256, id_wildCarrotCropHighYield)
				.setUnlocalizedName("SCItemWildCarrotSapling")
				.setCreativeTab(CreativeTabs.tabDecorations);
		
		/* POTATOES */
		
		wildPotato = ( new FCItemSeedFood( id_wildPotato - 256, 3, 0F, id_wildPotatoCrop ) )
				.SetFilterableProperties( Item.m_iFilterable_Small )
				.SetAsBasicPigFood()
				.setUnlocalizedName( "SCItemWildPotato" );
		
		wildPotatoCut = ( new FCItemSeedFood(id_wildPotatoCut - 256, 1, 0F, id_wildPotatoCrop ) )
				.SetFilterableProperties( Item.m_iFilterable_Small )
				.SetAsBasicPigFood()
				.setUnlocalizedName( "SCItemWildPotatoCut" );
		
		wildPotatoCrop = new SCBlockCropWildPotato(id_wildPotatoCrop);
		wildPotatoCropHighYield = new SCBlockCropWildPotatoHighYield(id_wildPotatoCropHighYield);
		
		wildPotatoCropSapling =  new SCBlockCropWildPotatoSapling(id_wildPotatoCropSapling);
		Item.itemsList[wildPotatoCropSapling.blockID] = new FCItemSeeds(id_wildPotatoCropSapling - 256, id_wildPotatoCropHighYield)
				.setUnlocalizedName("SCItemWildPotatoSapling")
				.setCreativeTab(CreativeTabs.tabDecorations);

	}

	private static void addBurgerDefs()
	{
		beefPattyRaw = new FCItemFood(id_beefPattyRaw - 256, 2, 0.25F, true, "SCItemBeefPatty_raw").SetStandardFoodPoisoningEffect();
		beefPattyCooked = new FCItemFood(id_beefPattyCooked - 256, 3, 0.25F, true, "SCItemBeefPatty_cooked");
		//beefRaw = ( new FCItemFood( 107, 4, 0.25F, true, "beefRaw", true )
		//beefCooked = ( new ItemFood( 108, 5,  0.25F, true ) ).setUnlocal
		
		breadSlice = new FCItemFoodHighRes(id_breadSlice - 256, 6, 0.25F, false, "SCItemBreadSlice");
		//bread = ( new ItemFood( 41, 3, 0.25F, false )
		
		burger = new FCItemFood(id_burger - 256, 5, 0.25F, false, "SCItemBurger");
		burgerEgg = new FCItemFood(id_burgerEgg - 256, 5, 0.5F, false, "SCItemBurger_egg");
	}
}
