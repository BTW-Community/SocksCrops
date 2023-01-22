package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinGrowingGreen extends SCBlockPumpkinGrowing {

	protected SCBlockPumpkinGrowingGreen(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID, int sleepingFruit)
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID, sleepingFruit);
		
		setUnlocalizedName("SCBlockPumpkinGrowingGreen");
	}

	@Override
	protected int getPossessedMetaForGrowthLevel(int growthLevel)
	{
		
		for (int i = 0; i < 4; i++) {
			if (growthLevel == i) 
			{
				//shift for if different pumpkin type: eg. i + 4 for green Pumpkins
				return i + 4; 
			}
		}
		return 0;
	}
	
	protected int getMetaHarvested(int growthLevel)
	{		
		if (growthLevel == 3 )
		{
			return 7; 
		}
		else if (growthLevel == 2)
		{
			return 6;
		}
		else if (growthLevel == 1)
		{
			return 5;
		}
		else return 4;
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
	{
		return this.GetBlockBoundsFromPoolBasedOnState(blockAccess.getBlockMetadata(i, j, k));
	}
	
	private AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(int meta)
	{
		int growthLevel = this.GetGrowthLevel(meta);
		
		//init BB
		AxisAlignedBB pumpkinBounds;
		
		//Orange
		if (growthLevel == 0)
		{
			return GetGourdBounds(10, 6, 10);
		}
		else if (growthLevel == 1)
		{
			return GetGourdBounds(12, 8, 12);
		}
		else if (growthLevel == 2)
		{
			return GetGourdBounds(14, 9, 14);
		}
		else return GetGourdBounds(16, 10, 16);
	}	
	
	//--- Render ---//
	private boolean vinePass;
	
	protected Icon[] greenIcon;
	protected Icon[] greenIconTop;
	protected Icon[] connectorIcon;

}
