package net.minecraft.src;

public class SCBlockWickerBasket extends FCBlockBasketWicker {

	public SCBlockWickerBasket(int iBlockID) {
		super(iBlockID);
	}

	@Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
		Block block = Block.blocksList[world.getBlockId(i, j - 1, k)];
		
		if ( world.getBlockId(i, j - 1, k) == Block.cauldron.blockID || block instanceof FCBlockVanillaCauldron)
        {
        	return  world.getBlockId(i, j, k) == 0 || blocksList[world.getBlockId(i, j, k)].blockMaterial.isReplaceable();
        }
		
        if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
		{
			return false;
		} 
		
        return super.canPlaceBlockAt( world, i, j, k );
    }
	
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {    	
        if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) && world.getBlockId(i, j - 1, k) != Block.cauldron.blockID  && world.getBlockId(i, j - 1, k) != SCDefs.barrel.blockID )
        {
            dropBlockAsItem( world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
            
            world.setBlockToAir( i, j, k );
        }
    }
	
}
