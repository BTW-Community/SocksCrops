//copied from FCBlockFarmlandFertilized

package net.minecraft.src;

public class SCBlockFarmlandNutrition0Fertilized extends SCBlockFarmlandNutrition0 {

	protected SCBlockFarmlandNutrition0Fertilized(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockFarmlandFertilized");
	}
	
	@Override
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{
		// revert back to unfertilized soil
		
		int iMetadata = world.getBlockMetadata( i, j, k );
		
		world.setBlockAndMetadataWithNotify( i, j, k, 
			SCDefs.farmlandNutrition0.blockID, iMetadata );
		
		// decrease nutrition of nutrient block
		attemptToConvertNutritionBlockAround(world, i, j, k, plantBlock);
	}
	
	@Override
	public float GetPlantGrowthOnMultiplier( World world, int i, int j, int k, Block plantBlock )
	{
		return 2F * getNutritionMultiplier();
	}
	
	@Override
	public boolean GetIsFertilizedForPlantGrowth( World world, int i, int j, int k )
	{
		return true;
	}
	
	@Override
    protected boolean IsFertilized( IBlockAccess blockAccess, int i, int j, int k )
	{
		return true;
	}
	
	protected Icon fertilizerOverlayDry;
	protected Icon fertilizerOverlayWet;
	
	private boolean secondPass;
	
	@Override
    public void registerIcons( IconRegister register )
    {
		super.registerIcons(register);
		
		fertilizerOverlayDry = register.registerIcon("SCBlockFarmlandFertilizedOverlay_dry");
		fertilizerOverlayWet = register.registerIcon("SCBlockFarmlandFertilizedOverlay_wet");
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
				return fertilizerOverlayWet;
			}
			else return fertilizerOverlayDry;
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
	
	@Override
	public void RenderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
		
		super.RenderBlockAsItem(renderBlocks, iItemDamage, fBrightness);
		
		FCClientUtilsRender.RenderInvBlockWithTexture(renderBlocks, this, -0.5F, -0.5F, -0.5F, fertilizerOverlayDry);
	}
}
