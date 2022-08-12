package net.minecraft.src;

import java.util.Random;

public class SCBlockMelonCanaryGrowing extends SCBlockMelonGrowing {

	protected SCBlockMelonCanaryGrowing(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID)
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
		
		setUnlocalizedName("SCBlockMelonCanaryGrowing");
	}
	
	@Override
	protected int getPossessedMetaForGrowthLevel(int growthLevel)
	{
		return 0; //Can't be possessed. see canBePossessed() in super
	}
	
	@Override
	protected int getMetaHarvested(int growthLevel)
	{
		return 0; //unused as we are overriding convertBlock() below
	}
	
	@Override
	protected void convertBlock(World world, int i, int j, int k)
	{	
		int meta = world.getBlockMetadata(i, j, k);
		
		world.setBlockAndMetadata(i, j, k, convertedBlockID , meta);
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		
		return SCDefs.melonCanaryHarvested.blockID;
	}

	@Override
	public int damageDropped(int meta)
	{
		if (this.GetGrowthLevel(meta) == 3)
		{
			return 12;
		}
		else if (this.GetGrowthLevel(meta) == 2)
		{
			return 8;
		}
		else if (this.GetGrowthLevel(meta) == 1)
		{
			return 4;
		}
		else return 0;
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
		int dir = this.getDirection(meta);
		//init BB
		AxisAlignedBB pumpkinBounds;
		
		//young
		if (growthLevel == 0)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(6, 4, 4);
			}
			else return GetGourdBounds(4, 4, 6);
		}
		//teen
		else if (growthLevel == 1)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(10, 6, 6);
			}
			else return GetGourdBounds(6, 6, 10);
		}
		//adult
		else if (growthLevel == 2)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(12, 8, 8);
			}
			else return GetGourdBounds(8, 8, 12);
		}
		//mature
		else if (dir == 1 || dir == 3)
		{
			return GetGourdBounds(16, 10, 10);
		}
		else return GetGourdBounds(10, 10, 16);
	}	

}
