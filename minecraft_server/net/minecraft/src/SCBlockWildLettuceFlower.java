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

}
