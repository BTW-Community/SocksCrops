package net.minecraft.src;

import java.util.Random;

public class SCBlockTomatoCrop extends SCBlockRopeCropBase {

	protected SCBlockTomatoCrop(int iBlockID, String name) {
		super(iBlockID, name);
	}
	
    protected void AttemptToGrow(World world, int x, int y, int z, Random rand) {
    	if (GetWeedsGrowthLevel(world, x, y, z) == 0 && canGrowAtCurrentLightLevel(world, x, y, z)) {
	        Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];
	      
	        
	        if (blockBelow != null && blockBelow.IsBlockHydratedForPlantGrowthOn(world, x, y - 1, z)) {
	    		float fGrowthChance = GetBaseGrowthChance(world, x, y, z) *
	    			blockBelow.GetPlantGrowthOnMultiplier(world, x, y - 1, z, this);
	    		
	            if (rand.nextFloat() <= fGrowthChance) {
	            	IncrementGrowthLevel(world, x, y, z);
	            	
	            	if (GetGrowthLevel(world, x, y, z) == 3 && getIsBlockAboveRope(world, x, y + 1, z) )
	            	{
	            		growLeavesAbove(world, x, y, z);
	            		world.setBlockMetadataWithNotify(x, y, z, 4);
	            	}
	            }
	        }
	    }
    }
	
    protected boolean getIsBlockAboveRope(World world, int x, int y, int z) {
		
    	if (world.getBlockId(x, y, z) == SCDefs.fenceRope.blockID)
    	{
    		  Block rope = Block.blocksList[world.getBlockId(x, y, z)];
    		  
    		  return !((SCBlockFenceRope)rope).GetExtendsAlongAxis(world, x, y, z, 0) && !((SCBlockFenceRope)rope).GetExtendsAlongAxis(world, x, y, z, 2);
    	}
    	
		return false;
	}

	private void growLeavesAbove(World world, int x, int y, int z) 
	{
		world.setBlockAndMetadataWithNotify(x, y + 1, z, SCDefs.tomatoVine.blockID, 0);		
	}

	public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.5F;
    }

	@Override
	public int idPicked(World world, int i, int j, int k) {
		return 0;// TODO SCDefs.tomatoSeeds.itemID;
	}

	@Override
    protected int GetGrowthLevel( int iMetadata )
    {
    	return iMetadata & 3;
    }

    protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) >= 3;
    }
    
//    protected boolean GetHasGrownToday( int iMetadata )
//    {
//    	return ( iMetadata & 8 ) != 0;
//    }

	@Override
	protected int GetCropItemID() {
		return 0; //TODO SCDefs.tomato.itemID;
	}

	private Icon[] leavesIcon = new Icon[4];
		
	@Override
	public void registerIcons(IconRegister register) {
		super.registerIcons(register);
		
		for(int i = 0; i < leavesIcon.length; i++)
		{
			leavesIcon[i] = register.registerIcon(name + "_leaves_" + i);
		}
	}
	
    @Override
    public Icon getIcon(int side, int meta) 
    {
//    	int growthStage = GetGrowthLevel(meta);
    	return cropIcon[meta];
    }
		
	@Override
	public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {
		
//		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
//		int growthStage = GetGrowthLevel(meta);
//		int leavesGrowthStage = growthStage & 3;
//		Icon[] leaves = leavesIcon;
//		
//		int idAbove = renderer.blockAccess.getBlockId(i, j + 1, k);
//		Block blockAbove = Block.blocksList[idAbove];
//		
//		if (blockAbove instanceof SCBlockTomatoVine)
//		{
//			SCUtilsRender.renderBlockCropsWithOffsetWithTexture(renderer, this, i, j, k, leavesIcon[leavesGrowthStage], 0/16F);
//		}
		
	}

}
