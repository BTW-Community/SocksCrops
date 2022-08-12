package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinGrowingOrange extends SCBlockPumpkinGrowing {

	protected SCBlockPumpkinGrowingOrange(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID)
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
		
		setUnlocalizedName("SCBlockPumpkinGrowingOrange");
	}

	protected int getMetaHarvested(int growthLevel)
	{
		if (growthLevel == 3 )
		{
			return 3;
		}
		else if (growthLevel == 2 )
		{
			return 2;
		}
		else if (growthLevel == 1 )
		{
			return 1;
		}
		else return 0;
	}
	
	@Override
	protected int getPossessedMetaForGrowthLevel(int growthLevel) {
		
		for (int i = 0; i < 4; i++) {
			if (growthLevel == i) 
			{
				//shift for if different pumpkin type: eg. i + 4 for green Pumpkins
				return i;
			}
		}
		return 0;
	}
	
	
	@Override
	public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k)
	{
		int meta = blockAccess.getBlockMetadata(i, j, k);
		int growthLevel = GetGrowthLevel(meta);
		
		if (growthLevel == 3) return true;
		else return false;
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
			return GetGourdBounds(12, 12, 12);
		}
		else return GetGourdBounds(16, 16, 16);
	}	
}
