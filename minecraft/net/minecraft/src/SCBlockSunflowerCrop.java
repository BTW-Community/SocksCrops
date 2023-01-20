package net.minecraft.src;

public class SCBlockSunflowerCrop extends SCBlockSunflowerBase {

	protected SCBlockSunflowerCrop(int iBlockID, String name) {
		super(iBlockID, name);
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

	@Override
	protected boolean isTopBlock() {
		
		return false;
	}
	
	private Icon stem;
	
	@Override
	public void registerIcons(IconRegister register)
	{
		super.registerIcons(register);
		
		stem = register.registerIcon(name + "_4");
	}
	
	protected void renderStem(RenderBlocks renderer, int i, int j, int k, int meta) {
		
		if (renderer.blockAccess.getBlockId(i, j + 1, k) == SCDefs.sunflowerTopCrop.blockID)
		{
			SCUtilsRender.RenderCrossedSquaresWithTextureAndOffset(renderer, this, i, j, k, stem, false);
		}
		else SCUtilsRender.RenderCrossedSquaresWithTextureAndOffset(renderer, this, i, j, k, crop[meta], false);
	}

}
