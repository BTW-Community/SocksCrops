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
	
	private Icon[] riceTop = new Icon[8];
	private Icon[] riceBottom = new Icon[8];
	
    @Override
    public void registerIcons( IconRegister register )
    {
    	riceTop[0] = register.registerIcon( "SCBlockCropRiceTop_0" );
    	riceTop[1] = register.registerIcon( "SCBlockCropRiceTop_1" );
    	riceTop[2] = register.registerIcon( "SCBlockCropRiceTop_2" );
    	riceTop[3] = register.registerIcon( "SCBlockCropRiceTop_3" );
    	riceTop[4] = register.registerIcon( "SCBlockCropRiceTop_4" );
    	riceTop[5] = register.registerIcon( "SCBlockCropRiceTop_5" );
    	riceTop[6] = register.registerIcon( "SCBlockCropRiceTop_6" );
    	riceTop[7] = register.registerIcon( "SCBlockCropRiceTop_7" );
    	
    	riceBottom[0] = register.registerIcon( "SCBlockCropRiceBottom_0" );
    	riceBottom[1] = register.registerIcon( "SCBlockCropRiceBottom_1" );
    	riceBottom[2] = register.registerIcon( "SCBlockCropRiceBottom_2" );
    	riceBottom[3] = register.registerIcon( "SCBlockCropRiceBottom_3" );
    	riceBottom[4] = register.registerIcon( "SCBlockCropRiceBottom_4" );
    	riceBottom[5] = register.registerIcon( "SCBlockCropRiceBottom_5" );
    	riceBottom[6] = register.registerIcon( "SCBlockCropRiceBottom_6" );
    	riceBottom[7] = register.registerIcon( "SCBlockCropRiceBottom_7" );

    }
    
    @Override
    public Icon getIcon(int side, int meta) {

    	if (meta < 8)
    	{
    		return blockIcon = riceTop[meta];
    	}
    	else return blockIcon = riceTop[meta / 2];
    }
    
    public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z)
    {
    	int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
    	
        renderer.setRenderBounds(this.GetBlockBoundsFromPoolBasedOnState(renderer.blockAccess, x, y, z));

        SCUtilsRender.RenderCrossedSquaresWithTextureAndOffset(renderer, this, x, y, z, riceTop[GetGrowthLevel(meta)], false);

        SCUtilsRender.RenderCrossedSquaresWithTextureAndOffset(renderer, this, x, y - 1, z, riceBottom[GetGrowthLevel(meta)], false);
        return true;
    }

}