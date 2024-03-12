package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenWildCrops {
	
	private int count;
	private Block plant;
	private int type;

    public SCWorldGenWildCrops(int count, Block plant, int type, ArrayList<Integer> validBiomeList) {
    	this.count = count;
    	this.plant = plant;
    	this.type = type;
    	this.validBiomeList = validBiomeList;
    }
	
	private static ArrayList<Integer> validBiomeList = new ArrayList();
	
	public static boolean isValidBiome(int biome) {
		return validBiomeList.contains(biome);
	}

	public boolean generate(World world, Random rand, int x, int y, int z)
    {
    	BiomeGenBase currentBiome = world.getBiomeGenForCoords( x, z );
        boolean isValidBiome = isValidBiome(currentBiome.biomeID);
    	
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
                    if (type != SCBlockWildFlowerCrop.POTATO )
                    {                    	
                    	world.setBlock(plantX, plantY, plantZ, plant.blockID, type, 2);
                    }
                    //XHills
                    else if (type == SCBlockWildFlowerCrop.POTATO && plantY < 83  )
                    {
                    	
                    	world.setBlock(plantX, plantY, plantZ, plant.blockID, type, 2);
                    }
                }
            }
        }
        
        return true;
    }
}