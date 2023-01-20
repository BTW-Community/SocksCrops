package net.minecraft.src;

import java.util.Random;

public class SCBlockWildCarrotCrop extends SCBlockWildCrop {

	protected SCBlockWildCarrotCrop(int iBlockID, String name) {
		super(iBlockID, name);
	}

	@Override
	protected int GetCropItemID() 
	{
		return SCDefs.wildCarrot.itemID;
	}

	@Override
	protected int getMaxGrowthLevel()
	{
		return 7; //ie 8 growthstages in total
	}


	
}
