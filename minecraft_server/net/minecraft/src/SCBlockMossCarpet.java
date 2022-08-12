package net.minecraft.src;

public class SCBlockMossCarpet extends FCBlockGroundCover {

	protected SCBlockMossCarpet(int blockID)
	{
		super(blockID, Material.plants);
		SetFireProperties( FCEnumFlammability.LEAVES );
		setStepSound(soundGrassFootstep);
		setUnlocalizedName("SCBlockMossCarpet");
		
	}

    @Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
    	int iBlockBelowID = world.getBlockId( i, j - 1, k );
    	Block blockBelow = Block.blocksList[iBlockBelowID];
    	
    	if ( blockBelow != null )
    	{
    		return blockBelow.CanGroundCoverRestOnBlock( world, i, j - 1, k ) || blockBelow.HasLargeCenterHardPointToFacing(world, i, j -1, k, 1);
    	}
    	
    	return false;
    }

}
