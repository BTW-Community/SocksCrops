package net.minecraft.src;

public class SocksCropsDefs {
	//assign ID number
	private static int
		id_wildCarrotBlock = 4000,
		id_berryBush = 4001,
		id_goatSkull = 4002,
		id_saladCrop = 4004,
		id_waterlilyPlant = 4005,
		id_riceCrop = 4008,
		id_riceRoots = 4009,
		id_crockPot = 4010;
		//end 4096
	private static int
		id_knifeStone = 31000,
		id_wildCarrot = 31001,
		id_berries = 31002,
		id_berrySapling = 31003,
		id_salad = 31004,
		id_saladSeeds = 31005,
		id_goatSkullItem = 31006,
		id_waterlily = 30999,
		id_rice = 31007;
		
		//end 32000
	
	public static int socksCropsCrockPotContainerID = 240;
	
	public static Block waterlilyPlant;
	public static Item waterlily;
	
	//add block
	public static Block goatSkull;
	//add crops
	public static Block wildCarrotBlock;
	
	public static Block berryBush;
	public static Block saladCrop;
	
	public static Block crockPot;
	
	//add item
	public static Item knifeStone;
	public static Item goatSkullItem;
	//add food item
	public static Item berries;
	public static Item salad;
	//add seed food item
	public static Item wildCarrot;
	
	//add seed item
	public static Item berrySapling;
	public static Item saladSeeds;
	

	public static Block riceCrop;
	public static Item rice;
	public static Block riceRoots;
	
	
	
	
	//definitions
	public static void addDefinitions() {
		wildCarrotBlock = new SocksCropsBlockWildCarrot(id_wildCarrotBlock - 256);
		saladCrop = new SocksCropsBlockSalad(id_saladCrop - 256);
		
		goatSkull = new SocksMobsBlockSkullGoat(id_goatSkull - 256);
		Item.itemsList[goatSkull.blockID] = new ItemBlock(goatSkull.blockID - 256);
		
		//goatSkullItem = new SocksMobsItemSkullGoat(id_goatSkullItem - 256);
		
		berryBush = new SocksCropsBlockBushBerry(id_berryBush);
		Item.itemsList[berryBush.blockID] = new ItemBlock(berryBush.blockID - 256);

		berries = new SocksCropsItemBerries(id_berries - 256);
		salad = new SocksCropsItemSalad(id_salad - 256);
		
		berrySapling = new SocksCropsItemBerrySapling(id_berrySapling - 256, id_berryBush);
		saladSeeds = new SocksCropsItemSaladSeeds(id_saladSeeds - 256);
		
		
		
		wildCarrot = new SocksCropsItemWildCarrot(id_wildCarrot  - 256);

		
		knifeStone = new SocksCropsItemKnifeStone(id_knifeStone  - 256);
		
		waterlilyPlant = new SocksCropsBlockLilyPad(id_waterlilyPlant - 256);
		waterlily = new SocksCropsItemLilyPad(id_waterlily - 256);
		
		rice = new SocksCropsItemRice(id_rice  - 256);
		riceCrop = new SocksCropsBlockRiceCrop(id_riceCrop - 256);
		
		riceRoots = new SocksCropsBlockRiceRoots(id_riceRoots - 256);

		
		crockPot = new SocksCropsBlockCrockPot(id_crockPot);
		Item.itemsList[crockPot.blockID] = new ItemBlock(crockPot.blockID - 256);

	}

} 