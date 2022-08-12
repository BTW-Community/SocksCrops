package net.minecraft.src;

import java.util.Random;

public class SCBiomeGenRiverPlains extends SCBiomeGenRiverBase
{
    public SCBiomeGenRiverPlains(int par1)
    {
        super(par1);
    }
	
	@Override
	public void decorate(World world, Random rand, int x, int z)
    {
        super.decorate(world, rand, x, z);
        
        decorateRocks(world,rand,x,z, SCDefs.rocks);
    }
}