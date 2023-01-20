package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockRopeCropBase extends FCBlockCrops {

	protected String name;
	
	protected SCBlockRopeCropBase(int iBlockID, String name) {
		super(iBlockID);
		setUnlocalizedName(name);
		this.name = name;
	}

	@Override
	protected int GetSeedItemID()
	{
		return 0;
	}
    
    protected Icon[] cropIcon = new Icon[8];
    
    @Override
    public void registerIcons(IconRegister register)
    {
    	for (int i = 0; i < cropIcon.length; i++)
    	{
    		cropIcon[i] = register.registerIcon(name + "_" + i);
    	}
    }
    
    @Override
    public Icon getIcon(int side, int meta) 
    {
    	int growthStage = GetGrowthLevel(meta);
    	return cropIcon[growthStage];
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
    
    @Override
    public abstract void RenderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult);
	
}
