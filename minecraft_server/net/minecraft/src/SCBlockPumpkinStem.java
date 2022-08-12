package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinStem extends FCBlockCrops {
	
	
	
	private static final double m_dWidth = 0.25D;
	private static final double m_dHalfWidth = ( m_dWidth / 2D );
	
	protected SCBlockPumpkinStem(int iBlockID) {
		super( iBlockID);
		
    	setHardness( 0F );
    	
    	SetBuoyant();
    	
        InitBlockBounds( 0.5F - m_dHalfWidth, 0F, 0.5F - m_dHalfWidth, 
        	0.5F + m_dHalfWidth, 0.25F, 0.5F + m_dHalfWidth );
        
        setStepSound( soundGrassFootstep );
        
        setUnlocalizedName( "pumpkinStem" ); 
	}
	
	@Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k );
    }
	

	@Override
	protected int GetCropItemID() {
		return 0;
	}

	@Override
	protected int GetSeedItemID() {
		return Item.pumpkinSeeds.itemID;
	}
	
	@Override
	public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.5F; // 0.5F = 50% 
    }
	
	public float GetVineGrowthChance( World world, int i, int j, int k )
    {
    	return 0.5F;
    }
	
	@Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k ) && blockOn.CanWildVegetationGrowOnBlock(world, i, j, k);
    }
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	//System.out.println("stem: update tick");
        	//System.out.println("my meta is:" + world.getBlockMetadata(i, j, k));
        	
	        if ( world.provider.dimensionId != 1 && !IsFullyGrown( world, i, j, k ) )
	        {
	        	this.AttemptToGrow( world, i, j, k, rand );
	        }
	        
	        int GrowthLevel = GetGrowthLevel(world, i, j, k);
	        
	        if (GrowthLevel > 3 && GrowthLevel < 8  ) {
	        	
	        	this.AttemptToGrowVine(world, i, j, k, rand);
	        }
	        
	        
        }
    }
	
	private void GrowVineAdjacent( World world, int i, int j, int k, Random rand ) {
		FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
	    int iTargetFacing = 0;
	    
	    if ( HasSpaceToGrow( world, i, j, k ) )
	    {
	    	// if the plant doesn't have space around it to grow, 
	    	// the fruit will crush its own stem
	    	
	        iTargetFacing = rand.nextInt( 4 ) +2;
	    	
	        targetPos.AddFacingAsOffset( iTargetFacing );
	    }
	    
	    if ( CanGrowVineAt( world, targetPos.i, targetPos.j, targetPos.k ) )
	    {	
	    	if (iTargetFacing == 2)
	    	{
	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	            		SCDefs.pumpkinVine.blockID, 2 ); //TODO change back to Sleeping
	    		
	    	} else if (iTargetFacing == 3)
	    	{
	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	            		SCDefs.pumpkinVine.blockID, 0 );//TODO change back to Sleeping
	    		
	    	} else if (iTargetFacing == 4)
	    	{
	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	            		SCDefs.pumpkinVine.blockID, 1 );//TODO change back to Sleeping
	    	} else if (iTargetFacing == 5)
	    	{
	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	            		SCDefs.pumpkinVine.blockID, 3 );//TODO change back to Sleeping
	    	}
	    }

	}
	
	protected void AttemptToGrowVine( World world, int i, int j, int k, Random rand )
	{
		float vineGrowthChance = GetVineGrowthChance(world, i, j, k);
		
		if ( rand.nextFloat() <= vineGrowthChance ) 
		{
    		GrowVineAdjacent(world, i, j, k, rand);
        	
		}
	}
	
	@Override
	protected void AttemptToGrow( World world, int i, int j, int k, Random rand ) 
    {
		int GrowthLevel = GetGrowthLevel(world, i, j, k);
		
    	if ( GetWeedsGrowthLevel( world, i, j, k ) == 0 &&  
    		world.GetBlockNaturalLightValue( i, j + 1, k ) >= GetLightLevelForGrowth() )
	    {
	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
	        
	        if ( blockBelow != null && 
	        	blockBelow.IsBlockHydratedForPlantGrowthOn( world, i, j - 1, k ) )
	        {
	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k ) *
	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );
	    		
	    		
	    		
	    		//System.out.println("stem: growth chance: " + fGrowthChance);

	            if ( rand.nextFloat() <= fGrowthChance ) 
				{
	            	//Grow stem
	            	//System.out.println("stem: growing!");
	            	
	            	IncrementGrowthLevel( world, i, j, k );
	  
		            //System.out.println("stem growth is:" + GrowthLevel);
		            
	            	
				}
	        }
	    }
    }
	
	protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) >= 7;
    }
	
	protected int GetGrowthLevel( int iMetadata )
    {
    	return iMetadata & 7;
    }
	
	protected boolean HasSpaceToGrow( World world, int i, int j, int k )
    {
        for ( int iTargetFacing = 2; iTargetFacing <= 5; iTargetFacing++ )
        {
        	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
        	
            targetPos.AddFacingAsOffset( iTargetFacing );
            
            if ( CanGrowVineAt( world, targetPos.i, targetPos.j, targetPos.k ) )
            {
            	//System.out.println("CanGrowVineAt");
            	return true;
            }
        }
        
        //System.out.println("Can NOT GrowVineAt");
        return false;
    }
	
	protected boolean CanGrowVineAt( World world, int i, int j, int k )
    {
		int iBlockID = world.getBlockId( i, j, k );		
		Block block = Block.blocksList[iBlockID];
		
        if ( FCUtilsWorld.IsReplaceableBlock( world, i, j, k ) ||
        	block == null &&
    		iBlockID != Block.cocoaPlant.blockID && 
    		iBlockID != SCDefs.pumpkinStem.blockID && 
    		iBlockID != SCDefs.pumpkinVine.blockID && 
    		iBlockID != SCDefs.pumpkinVineFlowering.blockID )
        {
			// CanGrowOnBlock() to allow fruit to grow on tilled earth and such
			if ( world.doesBlockHaveSolidTopSurface( i, j - 1, k ) ||
				CanGrowOnBlock( world, i, j - 1, k ) ) 
            {				
				return true;
            }
        }
        
        return false;
    }

	
	
	

}
