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
    
}
