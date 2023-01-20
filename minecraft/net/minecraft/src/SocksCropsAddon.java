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
    	
    	SCDefs.addDefinitions();
    	
    	SCRecipes.addRecipes();
	}
	
	@Override
	public void decorateWorld(FCIBiomeDecorator decorator, World world, Random rand, int x, int z, BiomeGenBase biome) {
		
		SCWorldDecorator.decorateWorld(decorator, world, rand, x, z, biome);
		
		//debugRivers(world, x, y);
	}
	
	@Override
	public void addDesertPyramidLoot(ArrayList<WeightedRandomChestContent> chestLoot, ArrayList<WeightedRandomChestContent> lootedChestLoot) {
		// item ID, item damage, min stack size, max stack size, weight
		
		//Grapes
		chestLoot.add( new WeightedRandomChestContent( SCDefs.redGrapes.itemID, 0, 1, 2, 5 ) );
	}
	
	@Override
	public void addJunglePyramidLoot(ArrayList<WeightedRandomChestContent> chestLoot, ArrayList<WeightedRandomChestContent> lootedChestLoot) {
		// item ID, item damage, min stack size, max stack size, weight
		
		//Tomato
		chestLoot.add( new WeightedRandomChestContent( SCDefs.tomatoSeeds.itemID, 0, 1, 2, 5 ) );
	}

    @Override
    public boolean ClientPlayCustomAuxFX(Minecraft mcInstance, World world, EntityPlayer player, int iFXID, int i, int j, int k, int iFXSpecificData)
    {
    	SCCustomAuxFX.playGourdAuxFX(mcInstance, world, player, iFXID, i,j,k, iFXSpecificData);
    	SCCustomAuxFX.playChoppingBoardAuxFX(mcInstance, world, player, iFXID, i,j,k, iFXSpecificData);
    	
    	return true;
	}
	
	@Override
	public void PostInitialize()
	{		
		FCAddOnHandler.LogMessage(this.getName() + " Initialized");
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
		        	
		            if ( currentBiome instanceof BiomeGenRiver ) 
		            {
		            	if ( currentBiome == BiomeGenBase.riverDesert )  block = Block.blockGold;
		            	
		            	if ( currentBiome == BiomeGenBase.riverExtremeHills )  block = Block.blockEmerald;
		            	
		            	if ( currentBiome == BiomeGenBase.riverForest )  block = Block.blockIron;
		            	
		            	if ( currentBiome == BiomeGenBase.frozenRiver)  block = Block.glass;
		            	
		            	if ( currentBiome == BiomeGenBase.riverSwamp)  block = Block.brick;
		            	
		            	if ( currentBiome == BiomeGenBase.riverTaiga )  block = Block.blockDiamond;
		            	
		            	if ( currentBiome == BiomeGenBase.riverJungle )  block = Block.blockRedstone;
		            	
		            	if ( currentBiome == BiomeGenBase.riverPlains )  block = Block.blockNetherQuartz;
	
		            	world.setBlock(xPos, 80, zPos, block.blockID, 0, 2);
		            }
		            
		            else if ( currentBiome instanceof BiomeGenBeach ) 
		            {
		            	block = Block.whiteStone;
		            	
		            	world.setBlock(xPos, 80, zPos, block.blockID, 0, 2);
		            }
	          }	   
	    }
	}
}
