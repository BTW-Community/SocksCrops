package net.minecraft.src;

public class SCBlockGrapeCrop extends SCBlockCropsDailyGrowth {
	
	private int stemBlock;
	
	protected SCBlockGrapeCrop(int iBlockID, int stemBlock, String name) {
		super(iBlockID, name);
		this.stemBlock = stemBlock;
		setUnlocalizedName(name);
	}

	@Override
	protected int GetCropItemID() {
		return 0;
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}
	
	@Override
	protected int getMaxGrowthStage()
	{
		return 3;
	}

	private void growStem(World world, int i, int j, int k) 
	{
		world.setBlockToAir(i, j, k);
				
		world.setBlockAndMetadataWithNotify(i, j, k, stemBlock, 0);
	}
	
    protected void IncrementGrowthLevel( World world, int i, int j, int k )
    {
    	int iGrowthLevel = GetGrowthLevel( world, i, j, k ) + 1;
    	
        SetGrowthLevel( world, i, j, k, iGrowthLevel );
        
        if ( IsFullyGrown( world, i, j, k ) )
        {
        	Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
        	
        	if ( blockBelow != null )
        	{
        		blockBelow.NotifyOfFullStagePlantGrowthOn( world, i, j - 1, k, this );
        		
        		//Added
        		growStem(world, i, j, k);
        	}
        }
    }
    
	
}
