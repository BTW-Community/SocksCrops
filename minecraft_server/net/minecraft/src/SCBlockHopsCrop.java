package net.minecraft.src;

import java.util.Random;

public class SCBlockHopsCrop extends FCBlockCrops {

	protected SCBlockHopsCrop(int iBlockID) {
		super(iBlockID);
	    setHardness( 0.2F );
	    SetAxesEffectiveOn( true );        
	        
	    SetBuoyant();        
	    SetFireProperties( FCEnumFlammability.CROPS );
			
		setUnlocalizedName("SCBlockHopsCrop");
	}

	@Override
	protected int GetCropItemID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int GetSeedItemID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
    public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.5F;
    }
    
    @Override
    public void updateTick( World world, int i, int j, int k, Random random )
    {
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	
	        if ( world.provider.dimensionId != 1 )
	        {
                int meta = world.getBlockMetadata( i, j, k );
                
                if ( !IsFullyGrown(meta) )
                {
                	AttemptToGrow( world, i, j, k, random );
                }
                else
                {
                	int blockAbove = world.getBlockId( i, j + 1, k );
                	
                	if (blockAbove == SCDefs.fenceRope.blockID)
                	{
                		attemptToGrowLeavesAbove( world, i, j, k, random );
                	}
                }
	        }
        }
    }
    
    protected void attemptToGrowLeavesAbove( World world, int i, int j, int k, Random rand )
    {
    	if ( world.GetBlockNaturalLightValue( i, j + 1, k ) >= GetLightLevelForGrowth() )
	    {
	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
	        
	        if ( blockBelow != null && blockBelow.IsBlockHydratedForPlantGrowthOn( world, i, j - 1, k ) )
	        {
	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k ) *
	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );
	    		
	            if ( rand.nextFloat() <= fGrowthChance )
	            {
	            	int meta = world.getBlockMetadata(i, j + 1, k);
	            	world.setBlockAndMetadataWithNotify(i, j + 1, k, SCDefs.hopsVine.blockID, meta);
	            }
	        }
	    }
    }
    
    protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) >= 3;
    }
    
    private Icon[] stem = new Icon [4];
    private Icon[] leaves = new Icon [3];
    private boolean leavesPass;
    

}
