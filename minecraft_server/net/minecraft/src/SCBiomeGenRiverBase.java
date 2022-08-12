package net.minecraft.src;

import java.util.Random;

public abstract class SCBiomeGenRiverBase extends BiomeGenRiver
{
    public SCBiomeGenRiverBase(int par1)
    {
        super(par1);
    }
    
    @Override
	public void decorate(World world, Random rand, int x, int z)
    {
        super.decorate(world, rand, x, z);

    }
    
	public void decorateRocks(World world, Random rand, int x, int z, Block block)
	{
        for (int var6 = 0; var6 < 6; ++var6)
        {
    		int posX = x + rand.nextInt(4) - rand.nextInt(4);
            int posZ = z + rand.nextInt(4) - rand.nextInt(4);
            int posY = world.getHeightValue(posX, posZ);
            
            //get the random number from coordinates
            int cash = cash( posX,posY, posZ );
            
            //limit the cash to 0 - 3
            cash = cash & 3;
            
            int randomSmallRock = cash;
            int randomLargeRock = cash + 4;
            int randomSmallRockMossy = cash + 8;
            int randomLargeRockMossy = cash + 12;

            if (posY > 62 && posY < 75 & world.isAirBlock(posX, posY, posZ) && block.canPlaceBlockAt(world, posX, posY, posZ))
            {
            	if (rand.nextInt(3) > 0)
        		{
            		if (rand.nextInt(10) > 0)
            		{
            			world.setBlock(posX, posY, posZ, block.blockID, randomSmallRock, 2);
            		}
            		else world.setBlock(posX, posY, posZ, block.blockID, randomSmallRockMossy, 2);
        		}
        		else
        		{
            		if (rand.nextInt(10) > 0)
            		{
            			world.setBlock(posX, posY, posZ, block.blockID, randomLargeRock, 2);
            		}
        			world.setBlock(posX, posY, posZ, block.blockID, randomLargeRockMossy, 2);
        		}
            }

        }
	}
	
	// https://stackoverflow.com/a/37221804
	// cash stands for chaos hash
	private static int cash(int x, int y, int z)
    {   
    	int h = y + x*374761393 + z*668265263; //all constants are prime
    	h = (h^(h >> 13))*1274126177;
       return h^(h >> 16);
	}
}