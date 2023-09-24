package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenRice extends WorldGenerator
{
	
	private static ArrayList<BiomeGenBase> validBiomeList = new ArrayList();
	
	public static boolean isBiomeValid(BiomeGenBase biome) {
		return validBiomeList.contains(biome);
	}
	
	public static void addBiomeToGenerator(BiomeGenBase biome) {
		validBiomeList.add(biome);
	}
	
	static {
		SCWorldGenRice.addBiomeToGenerator(BiomeGenBase.riverDesert);
	}
	
    public boolean generate(World par1World, Random random, int x, int y, int z)
    {
    	
    	BiomeGenBase currentBiome = par1World.getBiomeGenForCoords( x, z );
    	
        boolean isValidBiome = isBiomeValid(currentBiome);
    	
        for (int var6 = 0; var6 < 10; ++var6)
        {
            int var7 = x + random.nextInt(8) - random.nextInt(8);
            int var8 = y;// + random.nextInt(4) - random.nextInt(4);
            int var9 = z + random.nextInt(8) - random.nextInt(8);
            
            if (par1World.isAirBlock(var7, var8, var9) && SCDefs.riceCrop.canPlaceBlockAt(par1World, var7, var8, var9))
            {
                if ( !isValidBiome )
                {
                	// must occur after all random number generation to avoid messing up world gen
                	
                	continue;
                }
            	
                par1World.setBlock(var7, var8, var9, SCDefs.riceCrop.blockID, 4 + random.nextInt(4), 2);
            }
        }

        return true;
    }
}
