package net.minecraft.src;

public class SCBlockWildCarrotFlower extends SCBlockWildCropFlowering {

	protected SCBlockWildCarrotFlower(int iBlockID, String name) {
		super(iBlockID, name);
	}

	@Override
	protected int getMaxGrowthLevel() {
		return 7;
	}

	@Override
	protected int GetCropItemID() 
	{
		return SCDefs.wildCarrotSeeds.itemID;
	}
	
	@Override
	protected int GetSeedItemID() {
		return SCDefs.wildCarrotSeeds.itemID;
	}

}
