package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinGrowingWhite extends SCBlockPumpkinGrowing {

	protected SCBlockPumpkinGrowingWhite(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID, int sleepingFruit)
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID, sleepingFruit);
		
		setUnlocalizedName("SCBlockPumpkinGrowingWhite");
	}

	@Override
	protected int getPossessedMetaForGrowthLevel(int growthLevel)
	{
		for (int i = 0; i < 4; i++) {
			if (growthLevel == i) 
			{
				//shift for if different pumpkin type: eg. i + 4 for green Pumpkins
				return i + 12; 
			}
		}
		return 0;
	}
	
	protected int getMetaHarvested(int growthLevel)
	{		
		if (growthLevel == 3 )
		{
			return 15; 
		}
		else if (growthLevel == 2)
		{
			return 14;
		}
		else if (growthLevel == 1)
		{
			return 13;
		}
		else return 12;
	}
	
	//--- Render ---//
	
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
			return GetGourdBounds(6, 4, 6);
		}
		else if (growthLevel == 1)
		{
			return GetGourdBounds(8, 5, 8);
		}
		else if (growthLevel == 2)
		{
			return GetGourdBounds(10, 6, 10);
		}
		else return GetGourdBounds(12, 8, 12);
	}	
	
	private boolean vinePass;
	

	protected Icon[] orangeIcon;
	protected Icon[] orangeIconTop;
	protected Icon[] connectorIcon;

}
