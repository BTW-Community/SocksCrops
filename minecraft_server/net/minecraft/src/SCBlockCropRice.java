package net.minecraft.src;

import java.util.Random;

public class SCBlockCropRice extends FCBlockCrops {

	protected SCBlockCropRice(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlocCropRice");
	}

    public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.2F;
    }
    
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	
	        if ( world.provider.dimensionId != 1 )
	        {
	        	if (!IsFullyGrown( world, i, j, k ))
	        	{
	        		AttemptToGrow( world, i, j, k, rand );
	        	}
	        }
        }
    }
    
    @Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iNeighborBlockID )
    {
        //super.onNeighborBlockChange( world, i, j, k, iNeighborBlockID );
        
        UpdateIfBlockStays( world, i, j, k );
    }
    
    protected boolean UpdateIfBlockStays( World world, int i, int j, int k ) 
    {   
    	
        if ( !canBlockStay( world, i, j, k ) )
        {
            dropBlockAsItem( world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
            
            world.setBlockToAir( i, j, k );
 
            
            return false;
        }
        
        return true;
    }
	
    protected void AttemptToGrow( World world, int i, int j, int k, Random rand )
    {
    	if (   
    		world.GetBlockNaturalLightValue( i, j + 1, k ) >= GetLightLevelForGrowth() ) //GetWeedsGrowthLevel( world, i, j, k ) == 0 &&
	    {
	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
	        Block blockBelowThat = Block.blocksList[world.getBlockId( i, j - 2, k )];
	        
	        if ( blockBelow.blockID == Block.waterStill.blockID && ( blockBelowThat.blockMaterial == Material.ground || blockBelowThat.blockMaterial == Material.sand ) )
	        {
	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k ) *
	    			blockBelowThat.GetPlantGrowthOnMultiplier( world, i, j - 2, k, this ); //-1
	    		
	            if ( rand.nextFloat() <= fGrowthChance )
	            {
	            	IncrementGrowthLevel( world, i, j, k );
	            	
	            	if (GetGrowthLevel(world.getBlockMetadata(i, j, k)) == 7)
	            	{
	            		
	            		if (world.getBlockId(i, j - 2, k) == 0) return;
	            		
	            				
	            		if (world.getBlockId(i, j - 2, k) == SCDefs.compostBlock.blockID)
	            		{
	            			world.setBlockAndMetadata(i, j - 2, k, SCDefs.dirtNutrition.blockID, 0);
	            		}
	            		else if (world.getBlockId(i, j - 2, k) == SCDefs.dirtNutrition.blockID)
	            		{
	            			int meta = world.getBlockMetadata(i, j - 2, k);
	            		    
	            		    if (meta < 3)
	            		    {
	            		    	meta += 1;
	            		    }
	            		    
	            		    world.setBlockAndMetadata(i, j - 2, k, SCDefs.dirtNutrition.blockID, meta);
	            		}
	            		
	            		
	            	}
	            }
	        }
	    }
    }
    
    @Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return this.CanGrowOnBlock( world, i, j - 1, k ); 
    }
    
    @Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k );
    }
    
    @Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];	
    	
    	if (blockOn != null && blockBelow != null && blockOn.blockMaterial == Material.water  && ( blockBelow.blockMaterial == Material.ground || blockBelow.blockMaterial == Material.sand ) ) //&& blockOn.blockID == Block.waterStill.blockID
    		return true;
    	
    	else return false;
    }    

	@Override
	protected int GetCropItemID()
	{
		return SCDefs.riceBundle.itemID;
	}

	@Override
	protected int GetSeedItemID()
	{
		return 0;
	}
	
	@Override
	public boolean CanWeedsGrowInBlock( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}	
}