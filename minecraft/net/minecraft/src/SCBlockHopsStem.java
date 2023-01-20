package net.minecraft.src;

public class SCBlockHopsStem extends SCBlockVineCropBase {

	protected SCBlockHopsStem(int iBlockID, String name) {
		super(iBlockID, name);
	}

	@Override
	protected Block getConvertedBlock() {
		
		return SCDefs.hopsVine;
	}

	@Override
	protected Item getFruitDropped() {
		// TODO Auto-generated method stub
		return SCDefs.hops;
	}

	@Override
	protected void growVineAbove(World world, int x, int y, int z) {
		world.setBlockAndMetadataWithNotify(x, y + 1, z, SCDefs.hopsVine.blockID, 8);
		
	}

	@Override
	protected boolean shouldRenderWeeds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean shouldRenderRope() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean shouldRenderLeaves(int meta) {
		// TODO Auto-generated method stub
		return GetGrowthLevel(meta) > 3;
	}

	@Override
	protected int getMaxGrowthStage() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	protected int GetCropItemID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int GetSeedItemID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k ) || blockOn instanceof SCBlockHopsStem;
    }
    
	protected double getLeavesWidthOffset(int meta) {
		
		int growthLevel = GetGrowthLevel(meta);
		
		switch (growthLevel) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			return 6D / 16D;
			
		case 5:
			return 5D / 16D;
			
		case 6:
			return 4D / 16D;
			
		case 7:
			return 3D / 16D;
			
		default:
			return 3D / 16D;
		}
		
		
	}

	public boolean IsBlockHydratedForPlantGrowthOn( World world, int i, int j, int k )
	{
		return true;
	}
	
}
