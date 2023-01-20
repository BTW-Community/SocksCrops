package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenDoubleGrass extends WorldGenerator
{
    /** Stores ID for WorldGenTallGrass */
    private int tallGrassID;
    private int tallGrassMetadata;
	private int times;

    public SCWorldGenDoubleGrass(int times, int id, int meta)
    {
        this.tallGrassID = id;
        this.tallGrassMetadata = meta;
        this.times = times;
    }
    
	private static ArrayList<BiomeGenBase> validBiomeList = new ArrayList();
	
	public static boolean isBiomeValid(BiomeGenBase biome) {
		return validBiomeList.contains(biome);
	}
	
	public static void addBiomeToGenerator(BiomeGenBase biome) {
		validBiomeList.add(biome);
	}
	
	static {
		SCWorldGenDoubleGrass.addBiomeToGenerator(BiomeGenBase.taiga);
		SCWorldGenDoubleGrass.addBiomeToGenerator(BiomeGenBase.taigaHills);
		SCWorldGenDoubleGrass.addBiomeToGenerator(BiomeGenBase.forest);
		SCWorldGenDoubleGrass.addBiomeToGenerator(BiomeGenBase.forestHills);
		SCWorldGenDoubleGrass.addBiomeToGenerator(BiomeGenBase.jungle);
		SCWorldGenDoubleGrass.addBiomeToGenerator(BiomeGenBase.jungleHills);
	}

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var11;
        

        for (boolean var6 = false; ((var11 = par1World.getBlockId(par3, par4, par5)) == 0 || var11 == Block.leaves.blockID) && par4 > 0; --par4)
        {
            ;
        }

        for (int var7 = 0; var7 < this.times; ++var7)
        {
            int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

        	BiomeGenBase currentBiome = par1World.getBiomeGenForCoords( var8, var10 );
        	
            boolean isValidBiome = isBiomeValid(currentBiome);
        	
            
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
