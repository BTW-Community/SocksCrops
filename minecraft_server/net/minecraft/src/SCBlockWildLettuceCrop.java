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

}
