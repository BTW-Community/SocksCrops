package net.minecraft.src;

import java.util.Random;

public class SCBlockGourdVineDownDead extends SCBlockGourdVineDown {

	protected SCBlockGourdVineDownDead(int iBlockID, int stemBlock) {
		super(iBlockID, stemBlock, 0, 0, 0, leavesTex, topTex, bottomTex);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if (!this.canBlockStay(world, i, j, k))
		{
			world.setBlock(i, j, k, 0);
		}
	}
	
	@Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
		if ( IsFullyGrown(world, i, j, k) )
		{
			return CanGrowOnBlock( world, i, j - 1, k );
		}
		else return true;
    }

	
	
}
