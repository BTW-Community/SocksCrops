package net.minecraft.src;

public class SCBlockWeeds extends FCBlockWeeds {

	protected SCBlockWeeds(int iBlockID) {
		super(iBlockID);
	}
	
	@Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
		int iBlockOnID = world.getBlockId( i, j, k );
		
		return Block.blocksList[iBlockOnID] instanceof FCBlockFarmlandBase;
    }

}
