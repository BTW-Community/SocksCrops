package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenWildCrops {
	
	private int count;
	private Block plant;
	private int type;
	
    public SCWorldGenWildCrops(int count, Block plant, int type) {
    	this.count = count;
    	this.plant = plant;
    	this.type = type;
    }
	
	private static ArrayList<BiomeGenBase> validBiome = new ArrayList();
	
	public static boolean isValidBiome(BiomeGenBase biome) {
		return validBiome.contains(biome);
	}

	static {
		validBiome.add(BiomeGenBase.plains);
		validBiome.add(BiomeGenBase.extremeHills);
	}
	
	public boolean generate(World world, Random rand, int x, int y, int z)
    {
    	BiomeGenBase currentBiome = world.getBiomeGenForCoords( x, z );
        boolean isValidBiome = isValidBiome(currentBiome);
    	
        for (int var6 = 0; var6 < count; ++var6)
        {
            int plantX = x + rand.nextInt(4) - rand.nextInt(4);
            int plantY = y + rand.nextInt(2) - rand.nextInt(2);
            int plantZ = z + rand.nextInt(4) - rand.nextInt(4);

            if (world.isAirBlock(plantX, plantY, plantZ) && plantY < 127)
            {                                
                if ( !isValidBiome )
                {
                	// must occur after all random number generation to avoid messing up world gen
                	continue;
                }
                
                if (plant.canBlockStay(world, plantX, plantY, plantZ) && plantY > 60)
                {  
                	//Plains
                    if (type != SCBlockWildFlowerCrop.POTATO && currentBiome == BiomeGenBase.plains)
                    {                    	
                    	world.setBlock(plantX, plantY, plantZ, plant.blockID, type, 2);
                    }
                    //XHills
                    else if (type == SCBlockWildFlowerCrop.POTATO && plantY < 83 && currentBiome == BiomeGenBase.extremeHills )
                    {
                    	
                    	world.setBlock(plantX, plantY, plantZ, plant.blockID, type, 2);
                    }
                }
            }
        }
        
        return true;
    }
}