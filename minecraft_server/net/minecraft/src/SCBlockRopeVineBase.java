package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockRopeVineBase extends SCBlockRopeCropBase {

	protected SCBlockRopeVineBase(int iBlockID, String name) {
		super(iBlockID, name);
	}
	
    @Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
//    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
//    	
//    	return blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k );
    	
    	return true;
    
    }
    
    @Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
//    	Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
//    	Block blockAbove = Block.blocksList[world.getBlockId( i, j + 1, k )];
//        
//    	if (blockBelow instanceof SCBlockRopeCropBase && ( blockAbove instanceof SCBlockFenceRope || blockAbove instanceof SCBlockRopeVineBase) )
//    	{
//    		return true;
//    	}
//    	
//    	return false;
    	
    	return true;
        
    }
    
    protected void AttemptToGrow(World world, int x, int y, int z, Random rand) {
    	if (GetWeedsGrowthLevel(world, x, y, z) == 0 && canGrowAtCurrentLightLevel(world, x, y, z)) {
	        Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];
	        
	        if (blockBelow != null) {
	    		float fGrowthChance = GetBaseGrowthChance(world, x, y, z) *
	    			blockBelow.GetPlantGrowthOnMultiplier(world, x, y - 1, z, this);
	    		
	            if (rand.nextFloat() <= fGrowthChance) {
	            	IncrementGrowthLevel(world, x, y, z);
	            }
	        }
	    }
    }
    
    @Override
    protected boolean UpdateIfBlockStays( World world, int i, int j, int k ) 
    {
        if ( !canBlockStay( world, i, j, k ) )
        {
//        	if (dropsFruit(world.getBlockMetadata( i, j, k )))
//        	{
//        		 dropBlockAsItem( world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
//        	}
           
            world.setBlockToAir( i, j, k );
            
            return false;
        }
        
        return true;
    }
}
