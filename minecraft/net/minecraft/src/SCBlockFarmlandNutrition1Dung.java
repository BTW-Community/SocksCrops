//copied from FCBlockFarmlandFertilized

package net.minecraft.src;

public class SCBlockFarmlandNutrition1Dung extends SCBlockFarmlandNutrition1 {

	protected SCBlockFarmlandNutrition1Dung(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockFarmlandDung");
	}
	
	@Override
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{
		// revert back to unfertilized soil and go down a nutrition stage
		
		int iMetadata = world.getBlockMetadata( i, j, k );
		
		world.setBlockAndMetadataWithNotify( i, j, k, 
			SCDefs.farmlandNutrition1.blockID, iMetadata );
		
		// decrease nutrition of nutrient block
		attemptToConvertNutritionBlockAround(world, i, j, k, plantBlock);
	}
	
	@Override
	public float GetPlantGrowthOnMultiplier( World world, int i, int j, int k, Block plantBlock )
	{
		return getNutritionMultiplier();
	}
	
	@Override
	public boolean GetIsFertilizedForPlantGrowth( World world, int i, int j, int k )
	{
		return false;
	}
	
	@Override
    protected boolean IsFertilized( IBlockAccess blockAccess, int i, int j, int k )
	{
		return true;
	}
	
	@Override
    protected boolean IsDunged( IBlockAccess blockAccess, int i, int j, int k )
	{
		return true;
	}
	
	protected Icon dungOverlayDry;
	protected Icon dungOverlayWet;
	
	private boolean secondPass;
	
	@Override
    public void registerIcons( IconRegister register )
    {
		super.registerIcons(register);
		
		dungOverlayDry = register.registerIcon("SCBlockFarmlandDungedOverlay_dry");
		dungOverlayWet = register.registerIcon("SCBlockFarmlandDungedOverlay_wet");
    }
	
	@Override
	public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		if (secondPass) {
			return getBlockTextureSecondPass(par1iBlockAccess, par2, par3, par4, par5);
		}
		else return super.getBlockTexture(par1iBlockAccess, par2, par3, par4, par5);
	}
	
	
	private Icon getBlockTextureSecondPass(IBlockAccess blockAccess, int i, int j, int k, int side) {
		if (side == 1) 
		{
			if (IsHydrated(blockAccess.getBlockMetadata(i, j, k)))
			{
				return dungOverlayWet;
			}
			else return dungOverlayWet;
		}
		else return null;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int i, int j, int k, int side) {
		if (secondPass)
		{
			if (side != 1) return false;
			else return true;
		}
		else return super.shouldSideBeRendered(blockAccess, i, j, k, side);
	}
	
	@Override
	public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean firstPassResult) {
		secondPass = true;
		
		renderer.renderStandardBlock(this, i, j, k);

		secondPass = false;
	}

}
