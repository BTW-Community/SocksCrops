package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockCropsDailyGrowth extends FCBlockCropsDailyGrowth {

	protected String name;
	
	protected SCBlockCropsDailyGrowth(int iBlockID, String name) {
		super(iBlockID);
		this.name = name;
		
		setUnlocalizedName(name);
	}
	
    @Override
    public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.25F;
    }
	
    @Override
    protected void AttemptToGrow(World world, int x, int y, int z, Random rand) {
        int timeOfDay = (int)(world.worldInfo.getWorldTime() % 24000L);
        
        if (timeOfDay > 14000 && timeOfDay < 22000) {
        	// night
        	if (GetHasGrownToday(world, x, y, z)) {
        		SetHasGrownToday(world, x, y, z, false);
        	}
        }
        else {
	    	if (!GetHasGrownToday(world, x, y, z) && GetWeedsGrowthLevel(world, x, y, z) == 0 && canGrowAtCurrentLightLevel(world, x, y, z)) {
		        Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];
		        
		        if (blockBelow != null && blockBelow.IsBlockHydratedForPlantGrowthOn(world, x, y - 1, z)) {
		    		float growthChance = GetBaseGrowthChance(world, x, y, z);
		    		
			    	if (blockBelow.GetIsFertilizedForPlantGrowth(world, x, y - 1, z)) {
			    		growthChance *= 2F;
			    	}
			    	
		            if (rand.nextFloat() <= growthChance) {
		            	IncrementGrowthLevel(world, x, y, z);
		            	UpdateFlagForGrownToday(world, x, y, z);
		            }
		        }
		    }
        }
    }

	protected abstract int getMaxGrowthStage();
    
	protected void UpdateFlagForGrownToday( World world, int i, int j, int k )
	{
    	// fertilized crops can grow twice in a day
		
        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
        
        if ( blockBelow != null )
        {
	    	if ( !blockBelow.GetIsFertilizedForPlantGrowth( world, i, j - 1, k ) ||
	    		!GetHasGrownToday(world, i, j, k) )  //GetGrowthLevel( world, i, j, k ) % 2 == 0 )
	    	{
	    		SetHasGrownToday( world, i, j, k, true );
	    	}
        }
	}
	
    protected int GetGrowthLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return GetGrowthLevel( blockAccess.getBlockMetadata( i, j, k ) );
    }
    
    protected int GetGrowthLevel( int iMetadata )
    {
    	return iMetadata & 7;
    }
    
    protected void SetGrowthLevel( World world, int i, int j, int k, int iLevel )
    {
    	int iMetadata = world.getBlockMetadata( i, j, k ) & (~7); // filter out old level   	
    	
    	world.setBlockMetadataWithNotify( i, j, k, iMetadata | iLevel );
    }
    
    protected void SetGrowthLevelNoNotify( World world, int i, int j, int k, int iLevel )
    {
    	int iMetadata = world.getBlockMetadata( i, j, k ) & (~7); // filter out old level   	
    	
    	world.setBlockMetadata( i, j, k, iMetadata | iLevel );
    }
    
    protected boolean IsFullyGrown( World world, int i, int j, int k )
    {
    	return IsFullyGrown( world.getBlockMetadata( i, j, k ) );
    }
    
    protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) >= getMaxGrowthStage();
    }

    protected boolean GetHasGrownToday( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return GetHasGrownToday( blockAccess.getBlockMetadata( i, j, k ) );
    }
    
    protected boolean GetHasGrownToday( int iMetadata )
    {
    	return iMetadata >= 8;
    }
    
    protected void SetHasGrownToday( World world, int i, int j, int k, boolean bHasGrown )
    {
    	int iMetadata = SetHasGrownToday( world.getBlockMetadata( i, j, k ), bHasGrown );
    	
    	// intentionally no notify as this does not affect the visual state and should
    	// not trigger Buddy
    	
    	world.setBlockMetadata( i, j, k, iMetadata );
    }
        
    protected int SetHasGrownToday( int iMetadata, boolean bHasGrown )
    {
    	if ( bHasGrown )
    	{
    		iMetadata += 8;//|= 8;
    	}
    	else
    	{
    		iMetadata -= 8; //&= (~8);
    	}
    	
    	return iMetadata;
    }
    
    @Override
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( 
    	IBlockAccess blockAccess, int i, int j, int k )
    {
        float dVerticalOffset = 0;
        Block blockBelow = Block.blocksList[blockAccess.getBlockId( i, j - 1, k )];
        
//        if ( blockBelow != null )
//        {
//        	dVerticalOffset = blockBelow.GroundCoverRestingOnVisualOffset( 
//        		blockAccess, i, j - 1, k );
//        }
        
        int iMetadata = blockAccess.getBlockMetadata( i, j, k );
        
        if ( IsFullyGrown( iMetadata ) )
        {
        	return AxisAlignedBB.getAABBPool().getAABB(
                0.5D - m_dBoundsHalfWidth, 0D + dVerticalOffset, 0.5D - m_dBoundsHalfWidth, 
            	0.5D + m_dBoundsHalfWidth, 1D + dVerticalOffset, 0.5D + m_dBoundsHalfWidth );
        }
        else
        {
        	int iGrowthLevel = GetGrowthLevel( iMetadata );
        	double dBoundsHeight = ( 1 + iGrowthLevel ) / 4D;
    		
        	int iWeedsGrowthLevel = GetWeedsGrowthLevel( blockAccess, i, j, k );
        	
        	if ( iWeedsGrowthLevel > 0 )
        	{
        		dBoundsHeight = Math.max( dBoundsHeight, 
        			FCBlockWeeds.GetWeedsBoundsHeight( iWeedsGrowthLevel ) );
        	}
        	
        	return AxisAlignedBB.getAABBPool().getAABB(         	
                0.5D - m_dBoundsHalfWidth, 0F + dVerticalOffset, 0.5D - m_dBoundsHalfWidth, 
        		0.5D + m_dBoundsHalfWidth, dBoundsHeight + dVerticalOffset, 
        		0.5D + m_dBoundsHalfWidth );
        }            
    }
}
