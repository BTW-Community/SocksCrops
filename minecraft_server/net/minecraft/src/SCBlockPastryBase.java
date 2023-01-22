package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockPastryBase extends Block {

	protected SCBlockPastryBase(int par1, Material par2Material) {
		super(par1, par2Material);
		
		setStepSound( FCBetterThanWolves.fcStepSoundSquish );
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
    	if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
    	{
            dropBlockAsItem( world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
            
            world.setBlockToAir( i, j, k );
    	}
    }
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {

		if ( !canBlockStay(world,x,y,z))
		{
			return false;
		}
		else return super.canPlaceBlockAt(world, x, y, z);
	}
	
	@Override
    public void onBlockAdded( World world, int i, int j, int k )
    {
		// check beneath for valid block due to piston pushing not sending a notify
		if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
		{
	        dropBlockAsItem(world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
	        world.setBlockWithNotify(i, j, k, 0);
		}
    }

	//Copied from FCBlockUnfiredPottery
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {
    	if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
    	{
    		// Unfired pottery can both be pushed by pistons and needs to rest on a block, which can create weird
    		// interactions if the block below is pushed at the same time as this one, 
    		// creating a ghost block on the client. Delay the popping of the block to next tick prevent this.

    		if( !world.IsUpdatePendingThisTickForBlock( i, j, k, blockID ) )
    		{
    			world.scheduleBlockUpdate( i, j, k, blockID, 1 );
    		}
    	}
    }
	
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, x, y - 1, z, 1, true );
    }
	
	@Override
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return true;
    }
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
}
