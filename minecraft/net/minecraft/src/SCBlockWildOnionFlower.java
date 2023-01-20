package net.minecraft.src;

public class SCBlockWildOnionFlower extends SCBlockWildCropFlowering {

	protected SCBlockWildOnionFlower(int iBlockID, String name) {
		super(iBlockID, name);
	}
	
	@Override
	protected int getMaxGrowthLevel() {
		return 7;
	}

	@Override
	protected int GetSeedItemID() {
		return SCDefs.wildOnionSeeds.itemID;
	}


	@Override
	protected int GetCropItemID() {
		return SCDefs.wildOnionSeeds.itemID;
	}

}
