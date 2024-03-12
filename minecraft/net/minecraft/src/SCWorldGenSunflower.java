package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenSunflower extends WorldGenerator
{
    /** Stores ID for WorldGenTallGrass */
    private int tallGrassID;
    private int tallGrassMetadata;
	private int times;
	
	private static ArrayList<Integer> validBiomeList;

    public SCWorldGenSunflower(int times, int id, int meta, ArrayList<Integer> validBiomeList)
    {
        this.tallGrassID = id;
        this.tallGrassMetadata = meta;
        this.times = times;
        
        this.validBiomeList = validBiomeList;
    }
    
	public static boolean isBiomeValid(int biomeID) {
		return validBiomeList.contains(biomeID);
	}

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var7 = 0; var7 < this.times; ++var7)
        {
            int var8 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par4 + par2Random.nextInt(2) - par2Random.nextInt(2);
            int var10 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);

        	BiomeGenBase currentBiome = par1World.getBiomeGenForCoords( var8, var10 );
        	
            boolean isValidBiome = isBiomeValid(currentBiome.biomeID);
           
            if (var9 < 60)
            	return false;
            
            if ( !isValidBiome )
            {
            	// must occur after all random number generation to avoid messing up world gen
            	
            	continue;
            }
            
            if (par1World.isAirBlock(var8, var9, var10) && par1World.isAirBlock(var8, var9 + 1, var10) 
            		&& Block.blocksList[this.tallGrassID].canBlockStay(par1World, var8, var9, var10))
            {
                par1World.setBlock(var8, var9, var10, this.tallGrassID, this.tallGrassMetadata, 2);
                par1World.setBlock(var8, var9 + 1, var10, this.tallGrassID, this.tallGrassMetadata + 8, 2);
            }
        }

        return true;
    }
}
