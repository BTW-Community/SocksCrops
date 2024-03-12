package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenClover extends WorldGenerator
{
    /** The ID of the plant block used in this plant generator. */
    private int plantBlockId;
    private int plantMeta;
    
	private static ArrayList<Integer> validBiomeList = new ArrayList();
	
    public SCWorldGenClover(int par1, int meta, ArrayList<Integer> validBiomeList)
    {
        this.plantBlockId = par1;
        this.plantMeta = meta;
        
        this.validBiomeList = validBiomeList;
    }
    
	public static boolean isBiomeValid(int biomeID) {
		return validBiomeList.contains(biomeID);
	}

    public boolean generate(World world, Random random, int x, int y, int z)
    {

        for (int var6 = 0; var6 < 32; ++var6)
        {        	
            int var7 = x + random.nextInt(8) - random.nextInt(8);
            int var8 = y + random.nextInt(4) - random.nextInt(4);
            int var9 = z + random.nextInt(8) - random.nextInt(8);
            
        	BiomeGenBase currentBiome = world.getBiomeGenForCoords( x, z );
        	
            boolean isValidBiome = isBiomeValid(currentBiome.biomeID);
        	
            
            if ( !isValidBiome )
            {
            	// must occur after all random number generation to avoid messing up world gen
            	
            	continue;
            }
            // FCMOD: Change
            /*
            if (par1World.isAirBlock(var7, var8, var9) && (!par1World.provider.hasNoSky || var8 < 127) && Block.blocksList[this.plantBlockId].canBlockStay(par1World, var7, var8, var9))
            */
            if (world.isAirBlock(var7, var8, var9) && (!world.provider.hasNoSky || var8 < 127) && 
            	Block.blocksList[this.plantBlockId].CanBlockStayDuringGenerate(world, var7, var8, var9))
        	// END FCMOD
            {
            	
                world.setBlock(var7, var8, var9, this.plantBlockId, plantMeta, 2);
            }
        }

        return true;
    }
}
