package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockWildCrop extends FCBlockCrops {

	protected SCBlockWildCrop(int iBlockID, String name) {
		super(iBlockID);
		setUnlocalizedName(name);
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}
	
	// --- GrowthLevel --- //
	
	protected abstract int getMaxGrowthLevel();
	
    protected int GetGrowthLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return GetGrowthLevel( blockAccess.getBlockMetadata( i, j, k ) );
    }
    
    protected int GetGrowthLevel( int iMetadata )
    {
    	return iMetadata & getMaxGrowthLevel();
    }
    
    protected void SetGrowthLevel( World world, int i, int j, int k, int iLevel )
    {
    	int iMetadata = world.getBlockMetadata( i, j, k ) & (~getMaxGrowthLevel()); // filter out old level   	
    	
    	world.setBlockMetadataWithNotify( i, j, k, iMetadata | iLevel );
    }
    
    protected void SetGrowthLevelNoNotify( World world, int i, int j, int k, int iLevel )
    {
    	int iMetadata = world.getBlockMetadata( i, j, k ) & (~getMaxGrowthLevel()); // filter out old level   	
    	
    	world.setBlockMetadata( i, j, k, iMetadata | iLevel );
    }
    
    protected boolean IsFullyGrown( World world, int i, int j, int k )
    {
    	return IsFullyGrown( world.getBlockMetadata( i, j, k ) );
    }
    
    protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) >= getMaxGrowthLevel();
    }
    
    protected int GetLightLevelForGrowth() {
    	return 9;
    }
    	
	//----------- Client Side Functionality -----------//
	
	protected Icon[] cropIcons = new Icon[getMaxGrowthLevel() + 1];
	
	public Icon getIcon(int par1, int meta)
    {
		int growthStage = GetGrowthLevel(meta);
		
		return cropIcons[growthStage];
		
    }
	
}
