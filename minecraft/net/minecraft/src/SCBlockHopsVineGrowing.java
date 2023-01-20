package net.minecraft.src;

import java.util.Random;

public class SCBlockHopsVineGrowing extends SCBlockHopsVine {

	protected SCBlockHopsVineGrowing(int iBlockID, String name) {
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
	
	private boolean canFlower() {
		return false;
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
	protected boolean shouldRenderRope() {

		return true;
	}
	
	@Override
	protected boolean shouldRenderFlower(int blockMetadata) {
		return false;
	}
	
    protected void RenderFruit(RenderBlocks renderer, int i, int j, int k)
	{
        Tessellator tessellator = Tessellator.instance;
        
        tessellator.setBrightness( getMixedBrightnessForBlock( renderer.blockAccess, i, j, k ) );
        tessellator.setColorOpaque_F( 1F, 1F, 1F );
        
        double dVerticalOffset = 0D;
        Block blockBelow = Block.blocksList[renderer.blockAccess.getBlockId( i, j - 1, k )];
        
//        if ( blockBelow != null )
//        {
//        	dVerticalOffset = blockBelow.GroundCoverRestingOnVisualOffset( 
//        		renderer.blockAccess, i, j - 1, k );
//        }
        
        RenderCrossHatch( renderer, i, j, k, getFruitIcons(renderer.blockAccess, i, j, k),
            	getLeavesWidthOffset(renderer.blockAccess.getBlockMetadata(i, j, k)), dVerticalOffset );
        
	}
	
	private Icon[] fruits = new Icon[4];	

	@Override
	public void registerIcons(IconRegister register) {

		super.registerIcons(register);
		
		for (int i = 0; i < 4; i++)
		{
			fruits[i] = register.registerIcon("SCBlockHops_flower_" + i);
		}
		
	}

	@Override
	protected Icon getFruitIcons(IBlockAccess blockAccess, int i, int j, int k) {
		
		return  fruits[GetGrowthLevel(blockAccess, i, j, k) & 3];
	}
	
	protected Icon getLeavesIcon(IBlockAccess blockAccess, int i, int j, int k)
	{
        int blockIDAbove = blockAccess.getBlockId(i, j + 1, k);
        Block blockAbove = Block.blocksList[blockIDAbove];
        
        if ( blockAbove != null && blockAbove instanceof SCBlockHopsVine )
        {
        	return vineLeaves;
        }
		
		return leavesIcons[3];
	}

}
