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
    
	protected Icon[] cropIcons = new Icon[4];
	
	@Override
	public void registerIcons(IconRegister register) {
		
		for(int i = 0; i < cropIcons.length; i++)
		{
			cropIcons[i] = register.registerIcon(name + "_" + i);
		}
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		
		int growthStage = GetGrowthLevel(meta);
		return cropIcons[growthStage];
	}
	
    protected void RenderCrops( RenderBlocks renderer, int i, int j, int k )
    {
        Tessellator tessellator = Tessellator.instance;
        
        tessellator.setBrightness( getMixedBrightnessForBlock( renderer.blockAccess, i, j, k ) );
        tessellator.setColorOpaque_F( 1F, 1F, 1F );
        
        double dVerticalOffset = 0D;
        Block blockBelow = Block.blocksList[renderer.blockAccess.getBlockId( i, j - 1, k )];
        
        if ( blockBelow != null )
        {
        	dVerticalOffset = blockBelow.GroundCoverRestingOnVisualOffset( 
        		renderer.blockAccess, i, j - 1, k );
        }
        
        int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
        SCUtilsRender.renderCrossedSquaresWithVerticalOffset(renderer, this, i, j, k, getIcon(0, meta), dVerticalOffset);
    }
}
