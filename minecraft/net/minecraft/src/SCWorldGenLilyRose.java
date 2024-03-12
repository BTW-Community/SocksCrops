package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenLilyRose extends WorldGenerator
{
	
	private static ArrayList<Integer> validBiomeList = new ArrayList();
    
	public SCWorldGenLilyRose(ArrayList<Integer> validBiomeList)
    {
        this.validBiomeList = validBiomeList;
    }
    
	public static boolean isBiomeValid(int biomeID) {
		return validBiomeList.contains(biomeID);
	}
	
    public boolean generate(World par1World, Random par2Random, int x, int y, int z)
    {    	
    	BiomeGenBase currentBiome = par1World.getBiomeGenForCoords( x, z );
    	
        boolean isValidBiome = isBiomeValid(currentBiome.biomeID);
    	
        for (int var6 = 0; var6 < 10; ++var6)
        {
            int var7 = x + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = y + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = z + par2Random.nextInt(8) - par2Random.nextInt(8);
            int whiteChance = par2Random.nextInt(8);
            
            if (par1World.isAirBlock(var7, var8, var9) && SCDefs.lilyRose.canPlaceBlockAt(par1World, var7, var8, var9))
            {
                if ( !isValidBiome )
                {
                	// must occur after all random number generation to avoid messing up world gen
                	
                	continue;
                }
            	
                if (whiteChance == 0)
                {
                	par1World.setBlock(var7, var8, var9, SCDefs.lilyRose.blockID, SCBlockLilyRose.WHITE, 2);
                }
                else par1World.setBlock(var7, var8, var9, SCDefs.lilyRose.blockID, SCBlockLilyRose.PINK, 2);
            }
        }

        return true;
    }
}
