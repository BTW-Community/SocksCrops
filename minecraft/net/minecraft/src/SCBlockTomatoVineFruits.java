package net.minecraft.src;

import java.util.Random;

public class SCBlockTomatoVineFruits extends SCBlockTomatoVine {

	protected SCBlockTomatoVineFruits(int iBlockID, String name) {
		super(iBlockID, name);
	}
	
	protected Block getConvertedBlock() {
		
		return SCDefs.tomatoVineFruits;
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
		return meta < 15 ;
	}
	
	@Override
	protected boolean shouldRenderRope() {

		return true;
	}
	
	@Override
	protected boolean shouldRenderFlower(int blockMetadata) {
		return GetGrowthLevel(blockMetadata) == 0;
	}
	
	private Icon[] fruits = new Icon[4];	

	@Override
	public void registerIcons(IconRegister register) {

		super.registerIcons(register);
		
		for (int i = 0; i < 4; i++)
		{
			fruits[i] = register.registerIcon("SCBlockTomato_" + i);
		}
		
	}
	
	@Override
	protected Icon getFlowerIcon(IBlockAccess blockAccess, int i, int j, int k) {
		
		return flowerIcon;
	}
	
	@Override
	protected Icon getFruitIcons(IBlockAccess blockAccess, int i, int j, int k) {
		
		return  fruits[GetGrowthLevel(blockAccess, i, j, k) & 3];
	}
	
	@Override
	protected Icon getCropIcons(IBlockAccess blockAccess, int i, int j, int k) {
		
		return cropIcons[3];
	}
	
	protected Icon getLeavesIcon(IBlockAccess blockAccess, int i, int j, int k)
	{
        int blockIDAbove = blockAccess.getBlockId(i, j + 1, k);
        Block blockAbove = Block.blocksList[blockIDAbove];
        
        if ( blockAbove != null && blockAbove instanceof SCBlockTomatoVine )
        {
        	return vineLeaves;
        }
		
		return leavesIcons[3];
	}
	


	

}
