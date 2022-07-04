package net.minecraft.src;

import java.util.Random;

public class SCBlockCropWildCarrotFlower extends SCBlockCropWildCarrot {

	public SCBlockCropWildCarrotFlower(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockCropWildCarrotFlower");
	}
	
	@Override
	protected int GetCropItemID() {
		return SCDefs.wildCarrot.itemID;
	}

	@Override
	protected int GetSeedItemID() {
		return SCDefs.wildCarrotSeeds.itemID;
	}
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
    	
    }

}
