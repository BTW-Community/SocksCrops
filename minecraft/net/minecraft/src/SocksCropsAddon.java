package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;

public class SocksCropsAddon extends FCAddOn {
    public static final String ADDON_NAME = "Sock's Crops";
    public static final String ADDON_VERSION = "1.0.0";
    public static final String LANGUAGE_PREFIX = "SC";
   
    public static SocksCropsAddon instance = new SocksCropsAddon();
	
    public SocksCropsAddon()
    {
        super(ADDON_NAME, ADDON_VERSION, LANGUAGE_PREFIX);
    }
	
	@Override
	public void Initialize()
	{		
    	FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
    	
    	SCDecoIntegration.init();
    	SCBTAIntegration.init();
    	
    	SCDefs.addDefinitions();
    	
    	SCRecipes.addRecipes();
    	
    	SCTrades.addTrades();
    	
    	//System.out.println("BTA INSTALLED: " + SCBTAIntegration.isBTAInstalled());
	}
	
	
	@Override
	public void PostInitialize()
	{
		addLiquids();

		FCAddOnHandler.LogMessage(this.getName() + " Initialized");
	}
	
	private void addLiquids() {
		// WATER
		SCUtilsLiquids.addLiquid(
				Item.bucketWater, 0,
				Block.waterStill, 0,
				Item.potion, 0,
				Item.bucketWater, 0,
				0x0000ff, "water");
		// MILKS
		SCUtilsLiquids.addLiquid(
				Item.bucketMilk, 0,
				FCBetterThanWolves.fcBlockMilk, 0,
				SCDefs.bottleWithLiquid, SCUtilsLiquids.MILK,
				Item.bucketMilk, 0,
				0xffffff, "milk");
		SCUtilsLiquids.addLiquid(
				FCBetterThanWolves.fcItemBucketMilkChocolate, 0,
				FCBetterThanWolves.fcBlockMilkChocolate, 0,
				SCDefs.bottleWithLiquid, SCUtilsLiquids.CHOCOLATE_MILK,
				FCBetterThanWolves.fcItemBucketMilkChocolate, 0,
				0x927562, "chocolateMilk");
		SCUtilsLiquids.addLiquid(
				SCDefs.coconutSlice, 0,
				SCDefs.liquidBlocks[SCUtilsLiquids.COCONUT_MILK], 0,
				SCDefs.bottleWithLiquid, SCUtilsLiquids.COCONUT_MILK,
				SCDefs.bucketWithLiquid, SCUtilsLiquids.COCONUT_MILK,
				0xf2e0c3, "coconutMilk");
		
		// JUICES
		SCUtilsLiquids.addLiquid(
				Item.appleRed, 0,
				SCDefs.liquidBlocks[SCUtilsLiquids.APPLE_JUICE], 0,
				SCDefs.bottleWithLiquid, SCUtilsLiquids.APPLE_JUICE,
				SCDefs.bucketWithLiquid, SCUtilsLiquids.APPLE_JUICE,
				0xe9ba55, "appleJuice");		
		SCUtilsLiquids.addLiquid(
				SCDefs.cherry, 0,
				SCDefs.liquidBlocks[SCUtilsLiquids.CHERRY_JUICE], 0,
				SCDefs.bottleWithLiquid, SCUtilsLiquids.CHERRY_JUICE,
				SCDefs.bucketWithLiquid, SCUtilsLiquids.CHERRY_JUICE,
				0x440b0b, "cherryJuice");
		SCUtilsLiquids.addLiquid(
				SCDefs.lemon, 0,
				SCDefs.liquidBlocks[SCUtilsLiquids.LEMON_JUICE], 0,
				SCDefs.bottleWithLiquid, SCUtilsLiquids.LEMON_JUICE,
				SCDefs.bucketWithLiquid, SCUtilsLiquids.LEMON_JUICE,
				0xdceb10, "lemonJuice");
		
		// OILS
		SCUtilsLiquids.addLiquid(
				SCDefs.olive, 0,
				SCDefs.liquidBlocks[SCUtilsLiquids.OLIVE_OIL], 0,
				SCDefs.bottleWithLiquid, SCUtilsLiquids.OLIVE_OIL,
				SCDefs.bucketWithLiquid, SCUtilsLiquids.OLIVE_OIL,
				0xb3d118, "oliveOil");
		SCUtilsLiquids.addLiquid(
				SCDefs.sunflowerSeeds, 0,
				SCDefs.liquidBlocks[SCUtilsLiquids.SUNFLOWER_OIL], 0,
				SCDefs.bottleWithLiquid, SCUtilsLiquids.SUNFLOWER_OIL,
				SCDefs.bucketWithLiquid, SCUtilsLiquids.SUNFLOWER_OIL,
				0xc6b217, "sunflowerOil");
		
		
//		for (ItemStack liquid : SCUtilsLiquids.liquidList)
//		{
//			System.out.println("Block: " + liquid.itemID + " | Meta: " + liquid.getItemDamage());
//		}

	}

	@Override
	public void decorateWorld(FCIBiomeDecorator decorator, World world, Random rand, int x, int z, BiomeGenBase biome) {
		
		SCWorldDecorator.decorateWorld(decorator, world, rand, x, z, biome);
		
		//debugRivers(world, x, z);
	}
	
	private boolean isBTAInstalled(){
		return FCAddOnHandler.isModInstalled("Better Terrain");
	}
	
	private boolean isBBInstalled(){
		return FCAddOnHandler.isModInstalled("Better Biomes");
	}
	
	@Override
	public void addDesertWellLoot(ArrayList<FCUtilsRandomItemStack> basketLoot) {
		// item ID, item damage, min stack size, max stack size, weight
		basketLoot.add( new FCUtilsRandomItemStack(SCDefs.hopSeeds.itemID, 0, 1, 2, 10));
	}
	
	@Override
	public void addDesertPyramidLoot(ArrayList<WeightedRandomChestContent> chestLoot, ArrayList<WeightedRandomChestContent> lootedChestLoot) {
		// item ID, item damage, min stack size, max stack size, weight
		
		//Grapes
		lootedChestLoot.add( new WeightedRandomChestContent( SCDefs.redGrapeSeeds.itemID, 0, 1, 1, 3 ) );
		lootedChestLoot.add( new WeightedRandomChestContent( SCDefs.whiteGrapeSeeds.itemID, 0, 1, 1, 3 ) );
		
		chestLoot.add( new WeightedRandomChestContent( SCDefs.redGrapes.itemID, 0, 1, 2, 10 ) );
		chestLoot.add( new WeightedRandomChestContent( SCDefs.whiteGrapes.itemID, 0, 1, 2, 10 ) );
	}
	
	@Override
	public void addJunglePyramidLoot(ArrayList<WeightedRandomChestContent> chestLoot, ArrayList<WeightedRandomChestContent> lootedChestLoot) {
		// item ID, item damage, min stack size, max stack size, weight
		
		//Tomato
		lootedChestLoot.add( new WeightedRandomChestContent( SCDefs.tomatoSeeds.itemID, 0, 1, 1, 3 ) );
		
		chestLoot.add( new WeightedRandomChestContent( SCDefs.tomato.itemID, 0, 1, 2, 10 ) );
	}

    @Override
    public boolean ClientPlayCustomAuxFX(Minecraft mcInstance, World world, EntityPlayer player, int iFXID, int i, int j, int k, int iFXSpecificData)
    {
    	SCCustomAuxFX.playGourdAuxFX(mcInstance, world, player, iFXID, i,j,k, iFXSpecificData);
    	SCCustomAuxFX.playChoppingBoardAuxFX(mcInstance, world, player, iFXID, i,j,k, iFXSpecificData);
    	
    	return true;
	}
    
    @Override
    public EntityFX spawnCustomParticle(World world, String particleType, double x, double y, double z, double velX, double velY, double velZ)
    {
    	Entity entity = null;
    	
    	if (particleType.startsWith("juice_"))
        {
            String[] var28 = particleType.split("_", 2);
            int color = Integer.decode(var28[1]); //Integer.parseInt(var28[1]);
            entity = new SCEntityJuiceDropParticleFX(world, x, y, z, color);//  EntityList.createEntityOfType(SCEntityJuiceDropParticleFX.class, world, x, y, z, color);
        }
    	
    	return (EntityFX)entity;
    }
	
	private static void debugRivers(World world,int x,int z) {
		
    	int i;
    	int xPos;
    	int yPos;
    	int zPos;
		
	    for (i = 0; i < 16; ++i)
	    {
	   	    for (int j = 0;j < 16; ++j)
	          {
		        	xPos = x + i;
		        	zPos = z + j;
		        	
		        	BiomeGenBase currentBiome = world.getBiomeGenForCoords(xPos, zPos );
		        	Block block = Block.blockLapis;
		        	
		        	if (currentBiome == BiomeGenBase.ocean) 
		        	{
		        		block = Block.glass;
		        		
		        		world.setBlock(xPos, 80, zPos, block.blockID, 0, 2);
		        	}
		        	
//		            if ( currentBiome instanceof BiomeGenRiver ) 
//		            {
//		            	if ( currentBiome == BiomeGenBase.riverDesert )  block = Block.blockGold;
//		            	
//		            	if ( currentBiome == BiomeGenBase.riverExtremeHills )  block = Block.blockEmerald;
//		            	
//		            	if ( currentBiome == BiomeGenBase.riverForest )  block = Block.blockIron;
//		            	
//		            	if ( currentBiome == BiomeGenBase.frozenRiver)  block = Block.glass;
//		            	
//		            	if ( currentBiome == BiomeGenBase.riverSwamp)  block = Block.brick;
//		            	
//		            	if ( currentBiome == BiomeGenBase.riverTaiga )  block = Block.blockDiamond;
//		            	
//		            	if ( currentBiome == BiomeGenBase.riverJungle )  block = Block.blockRedstone;
//		            	
//		            	if ( currentBiome == BiomeGenBase.riverPlains )  block = Block.blockNetherQuartz;
//	
//		            	world.setBlock(xPos, 80, zPos, block.blockID, 0, 2);
//		            }		            
//		            else if ( currentBiome instanceof BiomeGenBeach ) 
//		            {
//		            	block = Block.whiteStone;
//		            	
//		            	world.setBlock(xPos, 80, zPos, block.blockID, 0, 2);
//		            }
	          }	   
	    }
	}
}
