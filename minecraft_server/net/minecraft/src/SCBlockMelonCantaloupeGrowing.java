package net.minecraft.src;

import java.util.Random;

public class SCBlockMelonCantaloupeGrowing extends SCBlockMelonGrowing {

	protected SCBlockMelonCantaloupeGrowing(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID, int sleepingFruit)
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID, sleepingFruit);
		
		setUnlocalizedName("SCBlockMelonHoneydewGrowing");
	}
	
	@Override
	protected int getPossessedMetaForGrowthLevel(int growthLevel)
	{
		return 0; //Can't be possessed. see canBePossessed() in super
	}
	
	protected int getMetaHarvested(int growthLevel)
	{
		if (growthLevel == 3 )
		{
			return 11; 
		}
		else if (growthLevel == 2)
		{
			return 10;
		}
		else if (growthLevel == 1)
		{
			return 9;
		}
		else return 8;
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
			return GetGourdBounds(6, 6, 6);
		}
		else if (growthLevel == 1)
		{
			return GetGourdBounds(8, 8, 8);
		}
		else if (growthLevel == 2)
		{
			return GetGourdBounds(10, 10, 10);
		}
		else return GetGourdBounds(12, 12, 12);
	}	

}
