package net.minecraft.src;

import java.util.Random;

public class SCBlockGourdVine extends BlockDirectional {
	
	protected int floweringBlock;
	protected int stemBlock;
	protected int hangingVine;
	
	int convertedBlockID;

	protected SCBlockGourdVine(int iBlockID, int floweringBlock, int stemBlock, int convertedBlockID, int hangingVine, String texVine, String texConnector) {
		super( iBlockID, Material.plants );
		
		this.floweringBlock = floweringBlock;
		this.stemBlock = stemBlock;
		this.convertedBlockID = convertedBlockID;
		this.hangingVine = hangingVine;
		
		this.texVine = texVine;
		this.texConnector = texConnector;
		
		this.setTickRandomly(true);
		
		setHardness( 0.1F );

		setStepSound( soundGrassFootstep );

	}
	
	protected float GetBaseGrowthChance()
    {
    	return 0.2F; //0.1f too low
    }
	
	protected float GetFlowerChance() {
		return 0.25F;
	}
	
	protected float GetVineGrowthChance()
    {
    	return 0.125F; //was 0.2f
    }
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {	
		if (!this.canBlockStay(world, i, j, k))
		{
			this.convert(world, i, j, k);
		}
		else
		{
			if (!IsFullyGrown( world, i, j, k) && checkTimeOfDay(world)) //daytime
			{
				int growthStage = this.GetGrowthLevel(world, i, j, k);
				
				if (rand.nextFloat() <= this.GetBaseGrowthChance())
		    	{
		    		this.grow(world, i, j, k, rand);
		    	}
				
				if ( growthStage > 0 && rand.nextFloat() <= this.GetVineGrowthChance())
		    	{
		    		this.attemptToGrowVine(world, i, j, k, rand);
		    	}	
				
				if (growthStage == 2 && rand.nextFloat() <= this.GetFlowerChance())
		    	{
		    		this.attemptToFlower(world, i, j, k, rand);
		    	}
				
			}

		}
    }
	
	protected boolean checkTimeOfDay(World world) {
		int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
		return (iTimeOfDay > 24000 || iTimeOfDay > 0 && iTimeOfDay < 14000 );
	}
	
	//Thanks to Hiracho for help with this method
  	protected void attemptToGrowVine(World world, int i, int j, int k, Random random)
  	{
  		int dir = this.getDirection(world.getBlockMetadata(i, j, k));

          int sideA = dir;
          int sideB = Direction.rotateLeft[sideA];
          int sideC = Direction.footInvisibleFaceRemap[sideB];

          int sideFinal;
          float randomFloat = random.nextFloat();
          
          if (randomFloat < 0.80)
          {
              sideFinal=sideA;
          }
          else if (randomFloat < 0.80 + 0.1)
          {
              sideFinal = sideB;
          }
          else
          {
              sideFinal = sideC;
          }
  		
  		int offsetI = Direction.offsetX[sideFinal];
  		int offsetK = Direction.offsetZ[sideFinal];
  		
  		
  		int finalI = i + offsetI;
  		int finalK = k + offsetK;
  		
  		if( CanGrowVineAt( world, finalI, j, finalK ) )
  		{
  			world.setBlockAndMetadata(finalI, j, finalK, this.blockID, sideFinal);
  		}
  		else
  		{
  			int id = world.getBlockId(finalI, j, finalK);
  			Block block = Block.blocksList[id];
  			
  			int idBelow = world.getBlockId(finalI, j - 1, finalK);
  			Block blockBelow = Block.blocksList[idBelow];
  			
  			//world.setBlockAndMetadata(finalI, j - 1, finalK, Block.blockDiamond.blockID, sideFinal);
  			
  			if (world.getBlockId(finalI, j, finalK) == 0 && world.getBlockId(finalI, j - 1, finalK) == 0)
  			{
  				world.setBlockAndMetadata(finalI, j - 1, finalK, hangingVine, sideFinal);
  			}
  		}

  	}

	protected void grow(World world, int i, int j, int k, Random random) {
		
		int meta = world.getBlockMetadata(i, j, k);        
		world.setBlockAndMetadataWithNotify(i, j, k, this.blockID ,meta + 4);

	}
	
	protected void attemptToFlower(World world, int i, int j, int k, Random random) {
		
		int dir = this.getDirection(world.getBlockMetadata(i, j, k));		
		world.setBlockAndMetadataWithNotify(i, j, k, this.floweringBlock, dir);
	}
	
    private void becomePossessed(World world, int i, int j, int k, Random rand)
    {
    	int meta = world.getBlockMetadata(i, j, k);
		int dir = this.getDirection(meta);
		int growthLevel = this.GetGrowthLevel(meta);
		
		if (growthLevel == 0)
		{
			world.setBlock(i, j, k, 0);
			
		}
		else {
			for (int d = 0; d < 4; d++) {
				world.setBlockAndMetadata(i, j, k, convertedBlockID, dir + 12);
				
			}	
		}
		
	}
    
	protected int getPossessedMetaForGrowthLevel(int growthLevel) {
		
		for (int i = 0; i < 4; i++) {
			if (growthLevel == i) 
			{
				return i; 
			}
		}
		return 0;
	}

	protected boolean hasPortalInRange( World world, int i, int j, int k )
    {
    	int portalRange = 16;
    	
        for ( int iTempI = i - portalRange; iTempI <= i + portalRange; iTempI++ )
        {
            for ( int iTempJ = j - portalRange; iTempJ <= j + portalRange; iTempJ++ )
            {
                for ( int iTempK = k - portalRange; iTempK <= k + portalRange; iTempK++ )
                {
                    if ( world.getBlockId( iTempI, iTempJ, iTempK ) == Block.portal.blockID )
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
	
	protected void convert(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int growthLevel = this.GetGrowthLevel(meta);
		int blockID = world.getBlockId(i, j, k);
		
		if (growthLevel == 0)
		{
			world.setBlock(i, j, k, 0);
			
		}
		else world.setBlockAndMetadataWithNotify(i, j, k, convertedBlockID, meta);
	}
	

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int par5) {
		if (!this.canBlockStay(world, i, j, k))
		{
			int meta = world.getBlockMetadata(i, j, k);
			int growthLevel = this.GetGrowthLevel(meta);
			
			if (growthLevel == 0) 
			{
				world.setBlock(i, j, k, 0);
				
			}
			else world.setBlockAndMetadataWithNotify(i, j, k, convertedBlockID, meta);
		}
	}

	@Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k ) && hasStemFacing(world, i, j, k);
    }
	
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && (world.doesBlockHaveSolidTopSurface( i, j, k ) || blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k ));
    }
	
	protected boolean hasStemFacing( World world, int i, int j, int k )
    {
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );

	    int dir = this.getDirection(world.getBlockMetadata(i, j, k));
	    
	    
	    int oppositeFacing = Direction.footInvisibleFaceRemap[dir];
	    int iTargetFacing = Direction.directionToFacing[oppositeFacing];
	    
	    targetPos.AddFacingAsOffset( iTargetFacing );
	    
	    int targetBlockID = world.getBlockId(targetPos.i, targetPos.j, targetPos.k);
	    Block block = Block.blocksList[targetBlockID];
	    	
	    if (block instanceof SCBlockGourdVineDead)
	    {
	    	return false;
	    }
	    
	    if (block instanceof SCBlockGourdVine || targetBlockID == this.stemBlock)
	    {
	    	//System.out.println(block.blockID);
	    	return true;
	    	
	    }
	    else return false;
	}

	
	protected boolean CanGrowVineAt( World world, int i, int j, int k )
    {
		int blockID = world.getBlockId( i, j, k );		
		Block block = Block.blocksList[blockID];
		
		//if the block at the targetPos is null or isReplaceable()
        if ( (block == null || FCUtilsWorld.IsReplaceableBlock( world, i, j, k) )
        		
        	//but not a stem, vine or flowering vine
        	&& ( blockID != this.blockID || blockID != this.stemBlock || blockID != this.floweringBlock || blockID != hangingVine) ) {
                
        	// CanGrowOnBlock() to allow vine to grow on tilled earth and such
        	if ( CanGrowOnBlock( world, i, j - 1, k ) && !world.isAirBlock(i, j - 1, k))
	        {				
        		return true;
	        }
        }
        
        return false;
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
	
	protected int GetGrowthLevel( IBlockAccess blockAccess, int x, int y, int z)
	{		
		return GetGrowthLevel(blockAccess.getBlockMetadata(x, y, z));		
	}
	
	protected int GetGrowthLevel( World world, int x, int y, int z) {

		return GetGrowthLevel(world.getBlockMetadata(x, y, z));		
	}
	
	protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) == 3;
    }	
	
	protected boolean IsFullyGrown(World world, int i, int j, int k) {
		return IsFullyGrown(world.getBlockMetadata(i, j, k));
	}
	
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock() {
        return false;
    }

	//RENDER 
	
	public Icon[] vineIcons;
	public Icon[] connectorIcons;
	
	public String texVine;
	public String texConnector;


    /**
     * 
     * @param x width along xAxis in px
     * @param y height along yAxis in px
     * @param z width along zAxis in px
     * @return AxisAlignedBB of the gourd
     */
	protected AxisAlignedBB GetVineBounds(int x, int y, int z)
	{
		double xSize = x/16D;
		double ySize = y/16D;
		double zSize = z/16D;	
		
    	AxisAlignedBB pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
    			8/16D - xSize/2, 0.0D, 8/16D - zSize/2, 
    			8/16D + xSize/2, ySize, 8/16D + zSize/2);
    	
    	return pumpkinBox;
	}
	
	protected AxisAlignedBB GetVineBounds(int x, int y, int z, int centerX, int centerZ)
	{
		double xSize = x/16D;
		double ySize = y/16D;
		double zSize = z/16D;	
		
    	AxisAlignedBB pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
    			centerX/16D - xSize/2, 0.0D, centerZ/16D - zSize/2, 
    			centerX/16D + xSize/2, ySize, centerZ/16D + zSize/2);
    	
    	return pumpkinBox;
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int i, int j, int k )
	{
		return null;
	}
	
    @Override
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k)
    {
    	int growthLevel = this.GetGrowthLevel(blockAccess, i, j, k);
    	int dir = this.getDirection(blockAccess.getBlockMetadata(i, j, k));
    	
    	if (growthLevel == 0)
		{
    		if (dir == 1) 
    		{
    			return GetVineBounds(14, 8, 4, 16, 8);
    		}
    		else if (dir == 2) 
    		{
    			return GetVineBounds(4, 8, 14, 8, 16); //"north"
    		}
    		else if (dir == 3) 
    		{
    			return GetVineBounds(14, 8, 4, 0, 8);
    		}
    		else return GetVineBounds(4, 8, 14, 8, 0); //"south"

		}
		else if (growthLevel == 1)
		{
			return GetVineBounds(6, 6, 6);
		}
		else if (growthLevel == 2)
		{
			return GetVineBounds(10, 8, 10);
		}
		else return GetVineBounds(12, 8, 12);
    		
    }

}
