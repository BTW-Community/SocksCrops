package net.minecraft.src;

import java.util.Random;

public class SCBlockSunflower extends BlockFlower {

	protected SCBlockSunflower(int id) {
		super(id);
		
		setUnlocalizedName("SCBlockSunflower");
	}
	
	
	@Override
	public void updateTick( World world, int i, int j, int k, Random rand )
	{

		int meta = world.getBlockMetadata(i, j, k);
		
		if (meta < 5)
		{
			world.setBlockAndMetadata(i, j, k, this.blockID, meta + 1);
		}
		else if (meta == 5 && world.getBlockId(i, j +1, k) == 0)
		{
			world.setBlockAndMetadata(i, j + 1, k, SCDefs.sunflowerTopCrop.blockID, 0);
		}
		
		

		//AttemptToGrow( world, i, j, k, rand );

	}
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int neighbourID) {
		//super.onNeighborBlockChange(world, i, j, k, neighbourID);
		
		int meta = world.getBlockMetadata(i, j, k);
		
		if (world.getBlockId(i, j + 1, k) != SCDefs.sunflowerTopCrop.blockID )
		{
			world.setBlockToAir(i, j, k);
			//FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, this.blockID, meta);
		}
		
		
	}
	
//	@Override
//	protected int GetCropItemID() {
//		return 0;
//	}
//
//	@Override
//	protected int GetSeedItemID() {
//		return 0;
//	}
	
	// --- CLIENT SIDE --- //
	
	private Icon stem[] = new Icon[5];
	
	@Override
	public void registerIcons(IconRegister register)
	{
		for (int i = 0; i < stem.length; i++) {
			stem[i] = register.registerIcon("SCBlockFlowerSunflower_bottom_" + i);
		}
		
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k); 
		if (meta < 5)
		{
			SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, stem[meta], false);
		}
		else SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, stem[4], false);
		return true;
	}

}
