package net.minecraft.src;

public class SCBlockWildLettuceFlower extends SCBlockWildLettuceCrop {

	protected SCBlockWildLettuceFlower(int iBlockID, String name) {
		super(iBlockID, name);
	}

	@Override
	protected int getMaxGrowthLevel() {
		return 7;
	}

	@Override
	protected int GetCropItemID() 
	{
		return SCDefs.wildLettuceSeeds.itemID;
	}
	
	@Override
	protected int GetSeedItemID() {
		return SCDefs.wildLettuceSeeds.itemID;
	}
	
    @Override
    protected void renderWildCrop(RenderBlocks renderer, int i, int j, int k, double dVerticalOffset)
    {
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	int growthStage = GetGrowthLevel(meta);
    	
    	//Flower
		SCUtilsRender.renderCrossedSquaresWithTexture(renderer, this, i, j, k, cropIcons[growthStage], false);
    	
		if (growthStage > 3)
		{
			growthStage = 4;
		}   
		
		//Outer
    	SCUtilsRender.renderBlockSaladWithTexture(renderer, this, i, j, k, salad[growthStage + 1]);
    	
    	if (growthStage > 0)
    	{
    		//Inner
    		SCUtilsRender.renderBlockCropsAtAngleWithTexture(renderer, this, i, j, k, salad[growthStage - 1], 2/16F);
    	}
    	

    	
    }
    

}
