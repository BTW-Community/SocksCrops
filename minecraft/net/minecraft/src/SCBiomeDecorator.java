package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCBiomeDecorator {

	public static void decorateWorld(FCIBiomeDecorator decorator, World world, Random random, int x, int z,	BiomeGenBase biome)
	{
		//debugRivers(world, x, z); TODO check if commented out
		
    	int i;
    	int xPos;
    	int yPos;
    	int zPos;
    	
    	// Short Grass
        for (i = 0; i < 1; ++i)
        {
            xPos = x + random.nextInt(16) + 8;
            yPos = random.nextInt(128);
            zPos = z + random.nextInt(16) + 8;
            WorldGenerator var6 =  new WorldGenTallGrass(SCDefs.shortPlant.blockID, 0);
            var6.generate(world, random, xPos, yPos, zPos);
        }
        
        //Tall Grass
        for (i = 0; i < 1; ++i)
        {
            xPos = x + random.nextInt(16) + 8;
            yPos = random.nextInt(128);
            zPos = z + random.nextInt(16) + 8;
            WorldGenerator var6 =  new SCWorldGenDoubleGrass(32,SCDefs.tallPlant.blockID, 0);
            var6.generate(world, random, xPos, yPos, zPos);
        }
        
        //Tall Fern
        for (i = 0; i < 1; ++i)
        {
            xPos = x + random.nextInt(16) + 8;
            yPos = random.nextInt(128);
            zPos = z + random.nextInt(16) + 8;
            WorldGenerator var6 =  new SCWorldGenDoubleGrass(16,SCDefs.tallPlant.blockID, 1);
            var6.generate(world, random, xPos, yPos, zPos);
        }

        // Clover
        for (i = 0; i < 4; ++i)
        {
        	xPos = x + random.nextInt(16) + 8;
        	yPos = random.nextInt(128);
        	zPos = z + random.nextInt(16) + 8;
        	(new SCWorldGenClover(SCDefs.clover.blockID, 0)).generate(world, random, xPos, yPos, zPos);

        	//Clover Variants
            if (random.nextInt(4) == 0)
            {
            	int meta = random.nextInt(3) + 1;
            	
            	xPos = x + random.nextInt(16) + 8;
            	yPos = random.nextInt(128);
                zPos = z + random.nextInt(16) + 8;
                (new SCWorldGenClover(SCDefs.clover.blockID, meta)).generate(world, random, xPos, yPos, zPos);
            }
        }
    	
    	//Flower lilys
    	if ( random.nextInt(3) == 0 )
		{
            for (i = 0; i < 1; ++i)
            {
            	xPos = x + random.nextInt(16) + 8;
            	zPos = z + random.nextInt(16) + 8;

                for (yPos = random.nextInt(128); yPos > 0 && world.getBlockId(xPos, yPos - 1, zPos) == 0; --yPos)
                {
                    
                }

                (new SCWorldGenLilyRose()).generate(world, random, xPos, yPos, zPos);
            }
		}
    	
    	//Bamboo
    	if ( random.nextInt(8) == 0 )
		{
			for (i = 0; i < 32; ++i)
			{
				xPos = x + random.nextInt(16) + 8;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 8;
				(new SCWorldGenBamboo()).generate(world, random, xPos, yPos, zPos);
			}
		}
    	
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
		        	
		            if ( currentBiome instanceof SCBiomeGenRiverBase ) 
		            {
		            	if ( currentBiome instanceof SCBiomeGenRiverDesert )  block = Block.blockGold;
		            	
		            	if ( currentBiome instanceof SCBiomeGenRiverExtremeHills )  block = Block.blockEmerald;
		            	
		            	if ( currentBiome instanceof SCBiomeGenRiverForest )  block = Block.blockIron;
		            	
		            	if ( currentBiome instanceof SCBiomeGenRiverFrozen )  block = Block.glass;
		            	
		            	if ( currentBiome instanceof SCBiomeGenRiverSwamp )  block = Block.brick;
		            	
		            	if ( currentBiome instanceof SCBiomeGenRiverTaiga )  block = Block.blockDiamond;
		            	
		            	if ( currentBiome instanceof SCBiomeGenRiverJungle )  block = Block.blockRedstone;
		            	
		            	if ( currentBiome instanceof SCBiomeGenRiverPlains )  block = Block.blockNetherQuartz;
	
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
