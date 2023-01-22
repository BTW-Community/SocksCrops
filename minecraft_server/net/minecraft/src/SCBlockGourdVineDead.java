package net.minecraft.src;

import java.util.Random;

public class SCBlockGourdVineDead extends SCBlockGourdVine {

	private static String texVine;
	private static String texConnector;

	protected SCBlockGourdVineDead(int iBlockID, int floweringBlock, int stemBlock) {
		super(iBlockID, floweringBlock, stemBlock, 0, 0, texVine, texConnector);
		
		setHardness( 0F );
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
        return CanGrowOnBlock( world, i, j - 1, k );
    }


}
