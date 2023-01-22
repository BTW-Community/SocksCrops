package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockGourdVineFloweringBase extends SCBlockGourdVine {
	
	private static String texConnector;
	private static String texVine;
	protected int vineBlock;
	protected int stemBlock;

	protected SCBlockGourdVineFloweringBase(int iBlockID, int vineBlock, int stemBlock, int convertedBlockID) {
		super(iBlockID, vineBlock, stemBlock, convertedBlockID, 0, texVine, texConnector);

		this.vineBlock = vineBlock;
		this.convertedBlockID = convertedBlockID;
	}
	
	protected float GetBaseGrowthChance()
    {
    	return 0.2F;
    }
	
	protected float GetFruitGrowthChance() {
		return 0.25F;
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
		if (!this.canBlockStay(world, i, j, k))
		{
			world.setBlockAndMetadataWithNotify(i, j, k, convertedBlockID, world.getBlockMetadata(i, j, k));
		}
		else
		{
			if ( checkTimeOfDay(world) ) //daytime
			{
				if (!IsFullyGrown( world, i, j, k)) 
				{
					if (rand.nextFloat() <= this.GetBaseGrowthChance())
					{
						this.attemptToGrow(world, i, j, k, rand);
					}
				}
//				else
//				{
//					//set mature
//					int dir = this.getDirection(world.getBlockMetadata(i, j, k));
//					world.setBlockAndMetadata(i, j, k, vineBlock, dir + 12);
//				}
				
			}

		}
    }
	
	protected void attemptToGrow(World world, int i, int j, int k, Random rand) {
		
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockAndMetadataWithNotify(i, j, k, this.blockID, meta + 4);
		
		if (rand.nextFloat() <= this.GetFruitGrowthChance())
		{
			if (this.growFruit(world, i, j, k, rand))
			{
				
				//set mature
				int dir = this.getDirection(world.getBlockMetadata(i, j, k));
				world.setBlockAndMetadataWithNotify(i, j, k, this.blockID, dir + 12);
			}
		}

	}

	protected abstract boolean growFruit(World world, int i, int j, int k, Random random);
	
	
	protected boolean CanGrowFruitAt( World world, int i, int j, int k )
    {
		int blockID = world.getBlockId( i, j, k );		
		Block block = Block.blocksList[blockID];
		
		//if the block at the targetPos is null or isReplaceable()
        if ( (block == null || FCUtilsWorld.IsReplaceableBlock( world, i, j, k) )
        		//but not a stem, vine or flowering vine
        		&& ( blockID != this.blockID || blockID != this.vineBlock || blockID != this.stemBlock ) )
        {
			// CanGrowOnBlock() to allow vine to grow on tilled earth and such
			if ( CanGrowOnBlock( world, i, j - 1, k ) )
            {				
				return true;
            }
        }
        
        return false;
    }   
	
}
