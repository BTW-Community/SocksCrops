package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenClover extends WorldGenerator
{
    /** The ID of the plant block used in this plant generator. */
    private int plantBlockId;
    private int plantMeta;

    public SCWorldGenClover(int par1, int meta)
    {
        this.plantBlockId = par1;
        this.plantMeta = meta;
    }
    
	private static ArrayList<BiomeGenBase> validBiomeList = new ArrayList();
	
	public static boolean isBiomeValid(BiomeGenBase biome) {
		return validBiomeList.contains(biome);
	}
	
	public static void addBiomeToGenerator(BiomeGenBase biome) {
		validBiomeList.add(biome);
	}
	
	static {
		SCWorldGenClover.addBiomeToGenerator(BiomeGenBase.forest);
		SCWorldGenClover.addBiomeToGenerator(BiomeGenBase.forestHills);
		SCWorldGenClover.addBiomeToGenerator(BiomeGenBase.plains);
	}

    public boolean generate(World world, Random random, int x, int y, int z)
    {

        for (int var6 = 0; var6 < 64; ++var6)
        {        	
            int var7 = x + random.nextInt(8) - random.nextInt(8);
            int var8 = y + random.nextInt(4) - random.nextInt(4);
            int var9 = z + random.nextInt(8) - random.nextInt(8);
            
        	BiomeGenBase currentBiome = world.getBiomeGenForCoords( x, z );
        	
            boolean isValidBiome = isBiomeValid(currentBiome);
        	
            
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
