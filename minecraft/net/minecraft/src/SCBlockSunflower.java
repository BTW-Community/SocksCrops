package net.minecraft.src;

import java.util.Random;

public class SCBlockSunflower extends FCBlockCrops {

	protected SCBlockSunflower(int id) {
		super(id);
        InitBlockBounds( 0.5D - m_dBoundsHalfWidth, 0D, 0.5D - m_dBoundsHalfWidth, 
            	0.5D + m_dBoundsHalfWidth, 1D, 0.5D + m_dBoundsHalfWidth );
		setUnlocalizedName("SCBlockSunflower");
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		
		return GetFixedBlockBoundsFromPool();
	}
	
	@Override
	protected int GetCropItemID() {
		return 0;
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}
	
    public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 1F;
    }
    
    @Override
    public boolean IsBlockHydratedForPlantGrowthOn(World world, int i, int j, int k) {
    	return true;
    }
	
    protected int GetGrowthLevel( int iMetadata )
    {
    	return iMetadata & 3;
    }
    
    @Override
    protected boolean IsFullyGrown(int iMetadata) {

    	return iMetadata >= 3;
    }
        
    @Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
    	 return CanGrowOnBlock( world, i, j - 1, k );
    }
    
    @Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k );
    }
    
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock(world, i, j, k);
    }
    
    @Override    
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
    	int meta = world.getBlockMetadata(i, j, k);
    	
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	
	        if ( world.provider.dimensionId != 1 )
	        {
	        	if (!IsFullyGrown( world, i, j, k ))
	        	{
	        		AttemptToGrow( world, i, j, k, rand );
	        	}
	        	
//	        	int rotation = SCBlockSunflowerTop.updateRotationForTime(world);
//    			world.setBlockAndMetadata(i, j + 1, k, SCDefs.sunflowerTopCrop.blockID, rotation);
	        }
        }
        
    }
	
	// --- CLIENT SIDE --- //
	
	private Icon stem[] = new Icon[5];
	
	@Override
	public void registerIcons(IconRegister register)
	{
		for (int i = 0; i < stem.length; i++) {
			stem[i] = register.registerIcon("SCBlockFlowerSunflower_bottom_" + i);
		}
		
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k); 
		
		if (renderer.blockAccess.getBlockId(i, j + 1, k) == SCDefs.sunflowerTopCrop.blockID)
		{
			SCUtilsRender.RenderCrossedSquaresWithTextureAndOffset(renderer, this, i, j, k, stem[4], false);
		}
		else SCUtilsRender.RenderCrossedSquaresWithTextureAndOffset(renderer, this, i, j, k, stem[meta], false);
		
		return true;
	}
}
