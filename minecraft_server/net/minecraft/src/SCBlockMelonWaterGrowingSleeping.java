package net.minecraft.src;

import java.util.Random;

public class SCBlockMelonWaterGrowingSleeping extends SCBlockMelonWaterGrowing {
	
	protected int awakeFruit;
	
	protected SCBlockMelonWaterGrowingSleeping(int iBlockID, int stemBlock, int vineBlock, int flowerBlock,
			int convertedBlockID, int awakeFruit) {
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID, awakeFruit);
		
		this.awakeFruit = awakeFruit;
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{	
		if (!canBlockStay(world, i, j, k))
		{
			this.convertBlock(world, i, j, k); //converts the block to the non growing/harvested version
			//super.updateTick(world, i, j, k, random); //check falling, we don't as the converted block handles the falling
		}
		else
		{
			if ( this.canBePossessed() && random.nextFloat() <= getPossesionChance() && hasPortalInRange(world, i, j, k) )
		    {
				this.becomePossessed(world, i, j, k, random);
		    }
			else if ( !isDaytime(world) && !IsFullyGrown( world, i, j, k) && random.nextFloat() <= this.GetBaseGrowthChance() ) //daytime
			{
				this.grow(world, i, j, k, random);
			}
		}				
	}
	
	protected void grow(World world, int i, int j, int k, Random random)
	{
		int meta = world.getBlockMetadata(i, j, k);   
		
		if (GetGrowthLevel(meta) < 3)
		{
			world.setBlockAndMetadataWithNotify(i, j, k, this.awakeFruit, meta);
		}
	}
}
