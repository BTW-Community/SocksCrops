package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockSunflowerBase extends FCBlockCrops {

	protected String name;
	
	protected SCBlockSunflowerBase(int iBlockID, String name) {
		super(iBlockID);
		setUnlocalizedName(name);
		
		this.name = name;
	}
	
	@Override
	public int idDropped(int iMetadata, Random rand, int iFortuneModifier) {
		
		return 0;
	}
	
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k ) || blockOn instanceof SCBlockSunflowerBase;
    }
	
	@Override
    public void harvestBlock( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
		super.harvestBlock( world, player, i, j, k, iMetadata );
		
        if( !world.isRemote )
        {
        	if ( isTopBlock() && world.getBlockId( i, j - 1, k ) == SCDefs.sunflowerCrop.blockID )
        	{
        		if ( player.getCurrentEquippedItem() != null && (player.getCurrentEquippedItem().getItem() instanceof FCItemShears))
        		{
        			FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, SCDefs.sunflower.itemID, 0);
        			player.getCurrentEquippedItem().damageItem(1, player);
        		}        		
       			
    			world.setBlockToAir( i, j - 1, k );
        	}
        }
    }
	
	protected abstract boolean isTopBlock();

	protected abstract int getMaxGrowthStage();

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
        		
        		if (!isTopBlock())
            	{
        			int meta = updateRotationForTime(world); 
        			world.setBlockAndMetadata(i, j + 1, k, SCDefs.sunflowerTopCrop.blockID, meta);
            	}
        	}        	
        }
    }
    
    public static int updateRotationForTime( World world )
	{
		int timeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );

		boolean isMorning = timeOfDay >= 0 && timeOfDay < 3000;
		boolean isPreNoon = timeOfDay >= 3000 && timeOfDay < 6000;
		boolean isAfterNoon = timeOfDay >= 6000 && timeOfDay < 9000;
		boolean isEvening = timeOfDay >= 9000 && timeOfDay < 12000;

		boolean earlyNight = timeOfDay >= 12000 && timeOfDay < 15000;
		boolean preMoon = timeOfDay >= 15000 && timeOfDay < 18000;
		boolean afterMoon = timeOfDay >= 18000 && timeOfDay < 21000;
		boolean lateNight = timeOfDay >= 21000 && timeOfDay < 24000;
		
		int rotation = 0;

		if (isMorning) rotation = 0;
		else if (isPreNoon) rotation = 1;
		else if (isAfterNoon) rotation = 2;
		else if (isEvening) rotation = 3;
		else if (earlyNight) rotation = 3;
		else if (preMoon) rotation = 2;
		else if (afterMoon) rotation = 1;
		else if (lateNight) rotation = 0;
		
		return rotation;
	}
    
    @Override
    public boolean IsBlockHydratedForPlantGrowthOn(World world, int i, int j, int k) {
    	return !isTopBlock();
    }
	
	protected int GetGrowthLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return GetGrowthLevel( blockAccess.getBlockMetadata( i, j, k ) );
    }
    
    protected int GetGrowthLevel( int meta )
    {
    	return meta & 7;
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
        	double dBoundsHeight = ( 1 + iGrowthLevel ) / 8D;
    		
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
    
	protected Icon crop[] = new Icon[4];
	protected Icon front[] = new Icon[4];
	protected Icon back[] = new Icon[4];
	
	@Override
	public void registerIcons(IconRegister register)
	{
		for (int i = 0; i < crop.length; i++) {
			crop[i] = register.registerIcon(name + "_" + i);
			
			front[i] = register.registerIcon("SCBlockFlowerSunflower_front_" + i);
			back[i] = register.registerIcon("SCBlockFlowerSunflower_back_" + i);			
		}
		
		blockIcon = crop[3];
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k); 
		
		renderStem(renderer, i, j, k, meta);
		
		if (isTopBlock())
		{
			renderFlower(renderer, i, j, k, meta);
		}
		
		return true;
	}

	private void renderFlower(RenderBlocks renderer, int i, int j, int k, int meta)
	{
		float flowerHeight = -4/16F + (1/16F * GetGrowthLevel(meta) );
		int growthLevel = GetGrowthLevel(meta);
		
		if (growthLevel < 2)
		{
			SCUtilsRender.renderSunflowerPlaneWithTexturesAndRotation(renderer, this, i, j, k, front[GetGrowthLevel(meta)], flowerHeight, meta & 3);
			SCUtilsRender.renderBackSunflowerPlaneWithTexturesAndRotation(renderer, this, i, j, k, back[GetGrowthLevel(meta)], flowerHeight, meta & 3);
		}
		else
		{
			SCUtilsRender.renderSunflowerPlaneWithTexturesAndRotation(renderer, this, i, j, k, back[GetGrowthLevel(meta)], flowerHeight, growthLevel);
			SCUtilsRender.renderBackSunflowerPlaneWithTexturesAndRotation(renderer, this, i, j, k, front[GetGrowthLevel(meta)], flowerHeight, growthLevel);
		}
	}

	protected void renderStem(RenderBlocks renderer, int i, int j, int k, int meta)
	{
		int growthLevel = GetGrowthLevel(meta);
		
		SCUtilsRender.RenderCrossedSquaresWithTextureAndOffset(renderer, this, i, j, k, crop[growthLevel], false);
	}
	

}
