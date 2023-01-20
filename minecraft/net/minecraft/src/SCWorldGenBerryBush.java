package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenBerryBush {
	
    /** The ID of the plant block used in this plant generator. */
    private Block plantBlock;

    public SCWorldGenBerryBush(Block par1)
    {
        this.plantBlock = par1;
    }
	
	private static ArrayList<BiomeGenBase> validForestBiome = new ArrayList();
	private static ArrayList<BiomeGenBase> validTaigaBiome = new ArrayList();
	
	public static boolean isForestBiome(BiomeGenBase biome) {
		return validForestBiome.contains(biome);
	}
	
	public static boolean isTagiaBiome(BiomeGenBase biome) {
		return validTaigaBiome.contains(biome);
	}
	
	public static boolean isBiomeValid(BiomeGenBase biome)
	{
		return isForestBiome(biome) || isTagiaBiome(biome);
	}
	
	static {
		validForestBiome.add(BiomeGenBase.forest);
		validForestBiome.add(BiomeGenBase.forestHills);
		
		validTaigaBiome.add(BiomeGenBase.taiga);
		validTaigaBiome.add(BiomeGenBase.taigaHills);
		validTaigaBiome.add(BiomeGenBase.icePlains);
	}
	
	private void debug(boolean boo, int plantX, int plantY, int plantZ)
	{
		if (boo)
		{
            //temp console output for locating of generated bushes
          String s_berryBush = null;                    
          if (this.plantBlock.blockID == SCDefs.sweetberryBush.blockID) s_berryBush = "Sweetberry";
         	else if (this.plantBlock.blockID == SCDefs.blueberryBush.blockID) s_berryBush = "Blueberry";
                
         	// System.out.println(s_berryBush + " Bush is at: x= " + plantX + ", y=" + plantY + ", z=" + plantZ);
		}
	}
	
	public boolean generate(World par1World, Random par2Random, int x, int y, int z)
    {
    	BiomeGenBase currentBiome = par1World.getBiomeGenForCoords( x, z );
    	
        boolean isValidBiome = isBiomeValid(currentBiome);
        
        for (int var6 = 0; var6 < 4; ++var6)
        {
            int plantX = x + par2Random.nextInt(4) - par2Random.nextInt(4);
            int plantY = y;
            int plantZ = z + par2Random.nextInt(4) - par2Random.nextInt(4);

            if (par1World.isAirBlock(plantX, plantY, plantZ))
            {                
                int newMeta = 1 + par2Random.nextInt(5);
                int hasSnow = par2Random.nextInt(2);
                
                if ( !isValidBiome )
                {
                	// must occur after all random number generation to avoid messing up world gen
                	
                	continue;
                }
                Block berryBush = this.plantBlock;
                
                if (berryBush.canBlockStay(par1World, plantX, plantY, plantZ)) {
                    if ( isForestBiome(currentBiome))
                    {
                    	if ( berryBush == SCDefs.blueberryBush)
                    	{
                    		par1World.setBlock(plantX, plantY, plantZ, this.plantBlock.blockID, newMeta, 2);
                    		
                    		debug(false, plantX, plantY, plantZ);
                    	}
                    }
                    else if ( isTagiaBiome(currentBiome))
                    {
                    	if ( berryBush == SCDefs.sweetberryBush)
                    	{
                    		par1World.setBlock(plantX, plantY, plantZ, this.plantBlock.blockID, newMeta, 2);
                    		
                    		if (hasSnow == 1)
                    		{
                    			par1World.setBlock(plantX, plantY + 1, plantZ, Block.snow.blockID, 0, 2);
                    		}
                    		
                    		debug(false, plantX, plantY, plantZ);
                    	}
                    }
                }


            }
        }

        return true;
    }
}