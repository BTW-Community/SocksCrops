package net.minecraft.src;

import java.util.Random;

public class SCBlockTomatoStemFruits extends SCBlockTomatoStem {

	protected SCBlockTomatoStemFruits(int iBlockID, String name) {
		super(iBlockID, name);
	}	
	
	protected boolean isFruitRipe(World world, int i, int j, int k) {
		return GetGrowthLevel(world, i, j, k) == 3;
	}
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	
	        if ( world.provider.dimensionId != 1 )
	        {
	        	if (!IsFullyGrown( world, i, j, k ) )
	        	{
	        		AttemptToGrow( world, i, j, k, rand );
	        	}
	        }
        }
    }
	
	protected boolean canGrowVineAbove(World world, int x, int y, int z) 
	{
		return false;
	}
	
	@Override
	protected boolean shouldRenderLeaves(int meta) {
		return true;
	}
	
	@Override
	protected boolean shouldRenderFruit(int meta) {
		return GetGrowthLevel(meta) <= 3 ;
	}
	
	@Override
	protected boolean shouldRenderFlower(int blockMetadata) {
		return GetGrowthLevel(blockMetadata) == 0;
	}	
}
