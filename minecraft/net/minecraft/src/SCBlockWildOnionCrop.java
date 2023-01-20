package net.minecraft.src;

public class SCBlockWildOnionCrop extends SCBlockWildCrop {

	protected SCBlockWildOnionCrop(int iBlockID, String name) {
		super(iBlockID, name);
	}

	@Override
	protected int getMaxGrowthLevel() {
		return 7;
	}

	@Override
	protected int GetCropItemID() {
		return SCDefs.wildOnion.itemID;
	}

}
