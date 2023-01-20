package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockWildCrop extends FCBlockCrops {

	protected SCBlockWildCrop(int iBlockID, String name) {
		super(iBlockID);
		setUnlocalizedName(name);
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}
	
	// --- GrowthLevel --- //
	
	protected abstract int getMaxGrowthLevel();
	
    protected int GetGrowthLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return GetGrowthLevel( blockAccess.getBlockMetadata( i, j, k ) );
    }
    
    protected int GetGrowthLevel( int iMetadata )
    {
    	return iMetadata & getMaxGrowthLevel();
    }
    
    protected void SetGrowthLevel( World world, int i, int j, int k, int iLevel )
    {
    	int iMetadata = world.getBlockMetadata( i, j, k ) & (~getMaxGrowthLevel()); // filter out old level   	
    	
    	world.setBlockMetadataWithNotify( i, j, k, iMetadata | iLevel );
    }
    
    protected void SetGrowthLevelNoNotify( World world, int i, int j, int k, int iLevel )
    {
    	int iMetadata = world.getBlockMetadata( i, j, k ) & (~getMaxGrowthLevel()); // filter out old level   	
    	
    	world.setBlockMetadata( i, j, k, iMetadata | iLevel );
    }
    
    protected boolean IsFullyGrown( World world, int i, int j, int k )
    {
    	return IsFullyGrown( world.getBlockMetadata( i, j, k ) );
    }
    
    protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) >= getMaxGrowthLevel();
    }
    
    protected int GetLightLevelForGrowth() {
    	return 9;
    }
    	
	//----------- Client Side Functionality -----------//
	
	protected Icon[] cropIcons = new Icon[getMaxGrowthLevel() + 1];
	
	public Icon getIcon(int par1, int meta)
    {
		int growthStage = GetGrowthLevel(meta);
		
		return cropIcons[growthStage];
		
    }
	
	public void registerIcons(IconRegister register)
    {
        for (int growthStage = 0; growthStage < this.cropIcons.length; ++growthStage)
        {
            this.cropIcons[growthStage] = register.registerIcon(this.getUnlocalizedName2() + "_" + growthStage);
        }
    }
    
    protected boolean rendersAsCrossHatch(RenderBlocks renderer, int i, int j, int k)
    {
    	return true;
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
        
        if (rendersAsCrossHatch(renderer, i, j, k))
        {
            RenderCrossHatch( renderer, i, j, k, getBlockTexture( renderer.blockAccess, i, j, k, 0 ), 
            	4D / 16D, dVerticalOffset );
        }
        else
        {
        	renderWildCrop( renderer, i, j, k, dVerticalOffset);
        }

    }

    /**
     * Only gets called if renderAsCrossHatch() is false
     * @param renderer
     * @param i
     * @param j
     * @param k
     * @param dVerticalOffset
     */
	protected void renderWildCrop(RenderBlocks renderer, int i, int j, int k, double dVerticalOffset)
	{
		
	}
}
