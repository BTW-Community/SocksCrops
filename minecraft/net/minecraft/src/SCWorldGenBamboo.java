//SCADDON: copied from WorldGenReeds

package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenBamboo extends WorldGenerator
{
	private static ArrayList<Integer> validBiomeList = new ArrayList();
	
	public static boolean isBiomeValid(int biomeID) {
		return validBiomeList.contains(biomeID);
	}

    public SCWorldGenBamboo(ArrayList<Integer> validBiomeList)
    {
        this.validBiomeList = validBiomeList;
    }
	
	public boolean generate(World world, Random random, int x, int y, int z)
    {
    	BiomeGenBase currentBiome = world.getBiomeGenForCoords( x, z );
    	
        boolean isValidBiome = isBiomeValid(currentBiome.biomeID);
        
//        System.out.println("Bamboo start");
        
        for (int var6 = 0; var6 < 64; ++var6)
        {
            int plantX = x + random.nextInt(4) - random.nextInt(4);
            int plantY = y + random.nextInt(2) - random.nextInt(2);
            int plantZ = z + random.nextInt(4) - random.nextInt(4);

            if (world.isAirBlock(plantX, y, plantZ) && world.isAirBlock(plantX, y + 1, plantZ))
            {

                int metaHeight =  11 + random.nextInt(5);
                
                int height = metaHeight - random.nextInt(8); //2 + par2Random.nextInt(7) + par2Random.nextInt(8);
                
                if ( !isValidBiome )
                {
                	// must occur after all random number generation to avoid messing up world gen
                	
                	continue;
                }
                                
                for (int newHeight = 0; newHeight < height; ++newHeight)
                {
                	Block bambooBlock;
                	
                	if (newHeight == 0) {
                		bambooBlock = SCDefs.bambooRoot;
                	}
                	else {
                		bambooBlock = SCDefs.bambooStalk;
                	}
                	                	
                	if (world.isAirBlock(plantX, plantY + newHeight, plantZ) && bambooBlock.canBlockStay(world, plantX, plantY + newHeight, plantZ))
                	{
                		world.setBlock(plantX, plantY + newHeight, plantZ, bambooBlock.blockID, metaHeight, 2);
                	}
                	else
                	{
                		break;
                	}
                	
                }


            }
        }

        return true;
    }

}