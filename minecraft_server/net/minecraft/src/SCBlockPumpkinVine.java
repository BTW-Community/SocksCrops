package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinVine extends BlockDirectional {
	

	protected SCBlockPumpkinVine(int iBlockID) {
		super( iBlockID, Material.plants );
		this.setTickRandomly(true);
		
		setUnlocalizedName("SCBlockPumpkinVine");
		// TODO Auto-generated constructor stub
	}
	
	protected int GetGrowthLevel( World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta <= 3) 
		{
			return 0;
		}
		else if (meta > 3 && meta <= 7) 
		{
			return 1;
		}
		else if (meta > 7 && meta <= 11) 
		{
			return 2;
		} 
		else return 3;
		
	}
	
	protected int GetGrowthLevel( int meta) {
		
		if (meta <= 3) 
		{
			return 0;
		}
		else if (meta > 3 && meta <= 7) 
		{
			return 1;
		}
		else if (meta > 7 && meta <= 11) 
		{
			return 2;
		} 
		else return 3;
		
	}
	
	protected int GetGrowthLevel( IBlockAccess blockAccess, int x, int y, int z) {
		int meta = blockAccess.getBlockMetadata(x, y, z);
		if (meta <= 3) 
		{
			return 0;
		}
		else if (meta > 3 && meta <= 7) 
		{
			return 1;
		}
		else if (meta > 7 && meta <= 11) 
		{
			return 2;
		} 
		else return 3;
		
	}
	
	@Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k ) ;
    }
	
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k ) || blockOn.CanWildVegetationGrowOnBlock(world, i, j, k);
    }
    
    static public int GetOppositeFacing( int iFacing )
	{
		if (iFacing == 0)
		{
			return 2;
		}
		else if (iFacing == 2)
		{
			return 0;
		}
		else if (iFacing == 1)
		{
			return 3;
		}
		else return 1; //if Facing = 3
	}
    
    
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {	
		int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
		
		if ( iTimeOfDay > 24000 || iTimeOfDay > 0 && iTimeOfDay < 14000 ) //daytime
	    {
	        if ( this.canBlockStay( world, i, j, k ) )
	        {
	        	
	        	// no plants can grow in the end
	        	//System.out.println("VINE: update tick");
	        	//System.out.println("VINE: my meta is " + world.getBlockMetadata(i, j, k));
	        	
	        	int GrowthLevel = GetGrowthLevel(world, i, j, k);
	        	//System.out.println("VINE: my GrowthLevel is " + GrowthLevel);
		        if (GrowthLevel > 0 && GrowthLevel < 3 ) {
		        	
		        	this.AttemptToGrowVine(world, i, j, k, rand);
	
		        }
	        	if (GrowthLevel < 3) 
	        	{
	        		this.AttemptToGrow(world, i, j, k, rand);
	        		//System.out.println("VINE: MAY I GROW?");
	        	}
		        if (GrowthLevel == 2) //Mature
		        {
		        	this.AttemptToFlower(world, i, j, k, rand);
		        	//System.out.println("VINE: FLOWER?");
		        }
		        
		        
		        
	        }
	    }
    }
	
	protected void AttemptToFlower(World world, int i, int j, int k, Random random) {
		
		int dir = this.getDirection(world.getBlockMetadata(i, j, k));
		
		if (random.nextInt() <= 0.5F) {
			world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.pumpkinVineFlowering.blockID, dir);
        	// TODO CHANGE BACK TO THIS: world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.pumpkinVineFloweringSleeping.blockID, dir +12);
        	//System.out.println("VINE: FLOOOOWER");
        }
		
	}

	protected void AttemptToGrow(World world, int i, int j, int k, Random random) {
		int GrowthLevel = GetGrowthLevel(world, i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
        
        if (GrowthLevel < 3 && random.nextInt() <= 0.99F) {
        	world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.pumpkinVine.blockID ,meta + 4);
        	//TODO CHANGE BACK TO THIS world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.pumpkinVineSleeping.blockID ,meta + 4); //increase a growth stage and set sleeping
        	//System.out.println("VINE: YES");
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
	            		SCDefs.pumpkinVine.blockID, 2 ); //TODO CHANGE BACK TO SLEEPING
	    		
	    	} else if (iTargetFacing == 3)
	    	{
	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	            		SCDefs.pumpkinVine.blockID, 0 );//TODO CHANGE BACK TO SLEEPING
	    		
	    	} else if (iTargetFacing == 4)
	    	{
	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	            		SCDefs.pumpkinVine.blockID, 1 );//TODO CHANGE BACK TO SLEEPING
	    	} else if (iTargetFacing == 5)
	    	{
	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	            		SCDefs.pumpkinVine.blockID, 3 );//TODO CHANGE BACK TO SLEEPING
	    	}
	    }

	}
	
	public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.5F; // 0.5F = 50% 
    }
	
	public float GetVineGrowthChance( World world, int i, int j, int k )
    {
    	return 0.5F;
    }
	
	protected void AttemptToGrowVine( World world, int i, int j, int k, Random rand )
	{
		float vineGrowthChance = GetVineGrowthChance(world, i, j, k);
		
		if ( rand.nextFloat() <= vineGrowthChance ) 
		{
    		GrowVineAdjacent(world, i, j, k, rand);
        	
		}
	}
	
    /** TODO remove this later, it's for testing
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
    {
        int playerRotation = ((MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, playerRotation);
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

	
    protected boolean HasSpaceToGrow( World world, int i, int j, int k )
    {
        for ( int iTargetFacing = 2; iTargetFacing <= 5; iTargetFacing++ )
        {
        	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
        	
            targetPos.AddFacingAsOffset( iTargetFacing );
            
            if ( CanGrowVineAt( world, targetPos.i, targetPos.j, targetPos.k ) )
            {
            	//System.out.println("VINE: CanGrowVineAt");
            	return true;
            }
        }
        
        //System.out.println("VINE: Can NOT GrowVineAt");
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
