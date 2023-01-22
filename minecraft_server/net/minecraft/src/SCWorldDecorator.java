package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldDecorator {

	public static void decorateWorld(FCIBiomeDecorator decorator, World world, Random random, int x, int z,	BiomeGenBase biome)
	{
    	int i;
    	int xPos;
    	int yPos;
    	int zPos;
    	
        //Tall Grass
        for (i = 0; i < 1; ++i)
        {
            xPos = x + random.nextInt(16) + 8;
            yPos = random.nextInt(128);
            zPos = z + random.nextInt(16) + 8;
            WorldGenerator var6 =  new SCWorldGenDoubleGrass(16,SCDefs.doubleTallGrass.blockID, SCBlockDoubleTallGrass.GRASS);
            var6.generate(world, random, xPos, yPos, zPos);
        }
        
        //Tall Fern
        for (i = 0; i < 1; ++i)
        {
            xPos = x + random.nextInt(16) + 8;
            yPos = random.nextInt(128);
            zPos = z + random.nextInt(16) + 8;
            WorldGenerator var6 =  new SCWorldGenDoubleGrass(8,SCDefs.doubleTallGrass.blockID, SCBlockDoubleTallGrass.FERN);
            var6.generate(world, random, xPos, yPos, zPos);
        }

        //Sunflower
        if (random.nextInt(16) == 0)
        {
        	for (i = 0; i < 2; ++i)
            {
        		xPos = x + random.nextInt(16) + 8;
        		yPos = random.nextInt(128);
        		zPos = z + random.nextInt(16) + 8;
        		WorldGenerator var6 =  new SCWorldGenSunflower(16,SCDefs.doubleTallGrass.blockID, SCBlockDoubleTallGrass.SUNFLOWER);
        		var6.generate(world, random, xPos, yPos, zPos);
            }
        }
        
        // Clover
        for (i = 0; i < 2; ++i)
        {
        	//Clover Variants
            if (random.nextInt(6) == 0)
            {
            	int randomType = random.nextInt(3) + 1;
            	
            	xPos = x + random.nextInt(16) + 8;
            	yPos = random.nextInt(128);
                zPos = z + random.nextInt(16) + 8;
                (new SCWorldGenClover(SCDefs.clover.blockID, randomType)).generate(world, random, xPos, yPos, zPos);
            }
            else
            {
            	xPos = x + random.nextInt(16) + 8;
            	yPos = random.nextInt(128);
            	zPos = z + random.nextInt(16) + 8;
            	(new SCWorldGenClover(SCDefs.clover.blockID, 0)).generate(world, random, xPos, yPos, zPos);

            }
        }
    	
    	//Flower lilys
    	if ( random.nextInt(4) == 0 )
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
    	if ( random.nextFloat() <= 0.05F )
		{
			for (i = 0; i < 32; ++i)
			{
				xPos = x + random.nextInt(16) + 4;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 4;
				(new SCWorldGenBamboo()).generate(world, random, xPos, yPos, zPos);
			}
		}
    	
    	//Bushes
    	if ( random.nextInt(3) == 0 )
		{
			for (i = 0; i < 4; ++i)
			{
				//Sweetberry
				xPos = x + random.nextInt(16) + 4;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 4;
				(new SCWorldGenBerryBush(SCDefs.sweetberryBush)).generate(world, random, xPos, yPos, zPos);
				
				//Blueberry
				xPos = x + random.nextInt(16) + 4;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 4;
				(new SCWorldGenBerryBush(SCDefs.blueberryBush)).generate(world, random, xPos, yPos, zPos);
			}
		}
    	
    	//Wild Crops
    	if ( random.nextInt(32) == 0 )
		{
            
            int[] cropTypes = {SCBlockWildFlowerCrop.CARROT, SCBlockWildFlowerCrop.POTATO, SCBlockWildFlowerCrop.ONION, SCBlockWildFlowerCrop.SALAD};
        	int crop = cropTypes[random.nextInt(cropTypes.length)];
    		
			for (i = 0; i < 8; ++i)
			{
				xPos = x + random.nextInt(16) + 8;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 8; 
				new SCWorldGenWildCrops(32, SCDefs.wildCropFlower, crop).generate(world, random, xPos, yPos, zPos);
			}
		}
    	
    	boolean isJungle = biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleHills || biome == BiomeGenBase.riverJungle;
    	
    	//Hollow Logs
    	if ( isJungle && random.nextInt(2) == 0 )
		{
			for (i = 0; i < 8; ++i)
			{
				//Sweetberry
				xPos = x + random.nextInt(16) + 4;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 4;
				(new SCWorldGenHollowLogs()).generate(world, random, xPos, yPos, zPos);
			}
		}
    	else if ( !isJungle && random.nextInt(2) == 0 )
		{
			for (i = 0; i < 8; ++i)
			{
				//Sweetberry
				xPos = x + random.nextInt(16) + 4;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 4;
				(new SCWorldGenHollowLogs()).generate(world, random, xPos, yPos, zPos);
			}
		}
    	
    	//SideShrooms
    	if ( random.nextInt(2) == 0 )
		{
			for (i = 0; i < 64; ++i)
			{
				xPos = x + random.nextInt(16) + 8;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 8;
				(new SCWorldGenSideShrooms()).generate(world, random, xPos, yPos, zPos);
			}
		}
    	
    	//Rice
    	if ( random.nextInt(8) == 0 )
		{
            for (i = 0; i < 8; ++i)
            {
            	xPos = x + random.nextInt(16) + 8;
            	zPos = z + random.nextInt(16) + 8;

                for (yPos = random.nextInt(128); yPos > 0 && world.getBlockId(xPos, yPos - 1, zPos) == 0; --yPos)
                {
                    
                }

                (new SCWorldGenRice()).generate(world, random, xPos, yPos, zPos);
            }
		}
    	
    	for (i = 0; i < 2; i++)
        {
            if ( random.nextInt(8) == 0 && biome == BiomeGenBase.beachDesert)
            {
                xPos = x + random.nextInt(16) + 8;               
                zPos = z + random.nextInt(16) + 8;
                
                yPos = world.getHeightValue(xPos, zPos);
                
                new SCWorldGenPalmTreeSmall(false, true).generate(world, random, xPos, yPos, zPos);
            }
        }
    	
        // Clover
    	if ( random.nextInt(2) == 0 )
		{
			for (i = 0; i < 16; ++i)
			{
				xPos = x + random.nextInt(16) + 4;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 4;
				(new SCWorldGenRocks(16)).generate(world, random, xPos, yPos, zPos);
			}
		}
    	
	}
}
