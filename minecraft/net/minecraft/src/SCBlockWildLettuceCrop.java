package net.minecraft.src;

import java.util.Random;

public class SCBlockWildLettuceCrop extends SCBlockWildCrop {

	protected SCBlockWildLettuceCrop(int iBlockID, String name) {
		super(iBlockID, name);
	}

	@Override
	protected int GetCropItemID() 
	{
		return SCDefs.wildLettuce.itemID;
	}

	@Override
	protected int getMaxGrowthLevel()
	{
		return 7; //ie 8 growthstages in total
	}
	    
    protected boolean rendersAsCrossHatch(RenderBlocks renderer, int i, int j, int k)
    {
    	return false;
    }
	
    protected Icon[] salad = new Icon[8];
    
    @Override
    public void registerIcons(IconRegister register) {
    	
    	for (int i = 0; i < 8; i++) {
			salad[i] = register.registerIcon("SCBlockWildLettuceCrop_" + i);
		
			cropIcons[i] = register.registerIcon("SCBlockWildLettuceFlower_" + i);
    	
    	}
    }
    @Override
    protected void renderWildCrop(RenderBlocks renderer, int i, int j, int k, double dVerticalOffset)
    {
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	int growthStage = GetGrowthLevel(meta);
    	
    	SCUtilsRender.renderBlockSaladWithTexture(renderer, this, i, j, k, salad[growthStage]);
    	
    	if (growthStage > 0)
    	{
    		SCUtilsRender.renderBlockCropsAtAngleWithTexture(renderer, this, i, j, k, salad[growthStage - 1], 2/16F);
    	}
    	
    	
    }


	
}
