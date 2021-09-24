package net.minecraft.src;

import java.util.Random;

public class WorldGenBush extends WorldGenerator
{
    public boolean generate(World par1World, Random par2Random, int x, int y, int z)
    {
        int var7 = 0; //counter = 0
        
        BiomeGenBase var6 = par1World.getBiomeGenForCoords(x, z);
        boolean biomes = 
        		var6 == BiomeGenBase.forest ||
        		var6 == BiomeGenBase.forestHills ||
        		var6 == BiomeGenBase.taiga ||
        		var6 == BiomeGenBase.taigaHills ||
        		var6 == BiomeGenBase.frozenRiver  ||
        		var6 == BiomeGenBase.river;
        
        if (biomes)
        {
	        for (int var9 = 0; var9 < 128; ++var9) //do 64 times
	        {
	            int newX = x + par2Random.nextInt(8) - par2Random.nextInt(8); //new x at random offset
	            int newY = y + par2Random.nextInt(4) - par2Random.nextInt(4);
	            int newZ = z + par2Random.nextInt(8) - par2Random.nextInt(8);
	
	            if (par1World.isAirBlock(newX, newY, newZ) && par1World.getBlockId(newX, newY - 1, newZ) == Block.grass.blockID && SocksCropsDefs.berryBush.canPlaceBlockAt(par1World, newX, newY, newZ))
	            {
	                //int meta = par2Random.nextInt(3); //random metadata from 0 - 4
	            	int min = 2;
	            	int max = 4;
	                int meta = par2Random.nextInt(max-min) + min;
	                	
	                if (var7 < 5) //if counter is less than 3 ie. plant 3 pumpkins
	                {
	                	
	                    par1World.setBlock(newX, newY, newZ, SocksCropsDefs.berryBush.blockID, meta, 2);
	                    System.out.println("Generated Berry Bush at X: "+newX+" Y: "+newY+ " Z: "+newZ) ;
	                    
	                    ++var7; // add to counter
	                    
	                }
	            }
	        }
        }

        return true;
    }

}
