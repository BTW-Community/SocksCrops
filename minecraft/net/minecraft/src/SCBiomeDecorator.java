package net.minecraft.src;

import java.util.Random;

public class SCBiomeDecorator {

	public static void decorateWorld(FCIBiomeDecorator decorator, World world, Random random, int x, int z,	BiomeGenBase biome)
	{
    	int i;
    	int xPos;
    	int yPos;
    	int zPos;
    	
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
	}

}
