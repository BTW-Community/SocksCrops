package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenWildCrops {

    public SCWorldGenWildCrops() {}
	
	private static ArrayList<BiomeGenBase> validBiome = new ArrayList();
	
	public static boolean isValidBiome(BiomeGenBase biome) {
		return validBiome.contains(biome);
	}

	static {
		validBiome.add(BiomeGenBase.plains);
	}
	
	public boolean generate(World world, Random rand, int x, int y, int z)
    {
    	BiomeGenBase currentBiome = world.getBiomeGenForCoords( x, z );
    	
        boolean isValidBiome = isValidBiome(currentBiome);
        
        int randCrop = rand.nextInt(2);
        
        for (int var6 = 0; var6 < 8; ++var6)
        {
            int plantX = x + rand.nextInt(4) - rand.nextInt(4);
            int plantY = y;
            int plantZ = z + rand.nextInt(4) - rand.nextInt(4);

            if (world.isAirBlock(plantX, plantY, plantZ))
            {                                
                
                
                if ( !isValidBiome )
                {
                	// must occur after all random number generation to avoid messing up world gen
                	
                	continue;
                }
                
                Block wildCrop;
                
                if (randCrop == 0) wildCrop = SCDefs.wildCarrotCrop;
                else wildCrop = SCDefs.wildPotatoCrop;
                
                if (wildCrop.canBlockStay(world, plantX, plantY, plantZ))
                {
                    if ( isValidBiome(currentBiome))
                    {
                    	world.setBlock(plantX, plantY, plantZ, wildCrop.blockID, 15, 2);
                    }
                }


            }
        }

        return true;
    }
}