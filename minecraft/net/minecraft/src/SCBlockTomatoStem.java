package net.minecraft.src;

public class SCBlockTomatoStem extends SCBlockVineCropBase {

	protected SCBlockTomatoStem(int iBlockID, String name) {
		super(iBlockID, name);
	}
	
	@Override
	protected void growVineAbove(World world, int x, int y, int z) 
	{
		world.setBlockAndMetadataWithNotify(x, y + 1, z, SCDefs.tomatoVine.blockID, 8);
		
		SetGrowthLevel(world, x, y, z, 4);
	}
	
	@Override
	protected int getMaxGrowthStage() {
		return 3;
	}

	@Override
	protected int GetCropItemID() {
		return 0;
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}
	
	protected Block getConvertedBlock() {
		
		return SCDefs.tomatoCropFruits;
	}

	protected Item getFruitDropped() {
		return SCDefs.tomato;
	}
	
	@Override
	protected boolean shouldRenderRope() {
		
		return false;
	}

	@Override
	protected boolean shouldRenderLeaves(int meta)
	{
		return GetGrowthLevel(meta) > 3;
	}
	
	public boolean IsBlockHydratedForPlantGrowthOn( World world, int i, int j, int k )
	{
		return true;
	}
	
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k ) || blockOn instanceof SCBlockTomatoStem;
    }

	@Override
	protected boolean shouldRenderWeeds() {
		return true;
	}

}
