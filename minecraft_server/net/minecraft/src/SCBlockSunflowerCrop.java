package net.minecraft.src;

public class SCBlockSunflowerCrop extends SCBlockSunflowerBase {

	protected SCBlockSunflowerCrop(int iBlockID, String name) {
		super(iBlockID, name);
	}

	@Override
	protected int getMaxGrowthStage() {
		return 3;
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
	protected boolean isTopBlock() {
		
		return false;
	}
	
	private Icon stem;

}
