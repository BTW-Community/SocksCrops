//copied from FCBlockFarmlandFertilized

package net.minecraft.src;

public class SCBlockFarmlandNutrition3Dung extends SCBlockFarmlandNutrition3 {

	protected SCBlockFarmlandNutrition3Dung(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockFarmlandDung");
	}

	
	@Override
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{
		// revert back to unfertilized soil and go down a nutrition stage
		
		int iMetadata = world.getBlockMetadata( i, j, k );
		
		world.setBlockAndMetadataWithNotify( i, j, k, 
			SCDefs.farmlandNutrition3.blockID, iMetadata );
		
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
	
	@Override
    public void registerIcons( IconRegister register )
    {
		blockIcon = register.registerIcon( "SCBlockDirtLooseDry_0" );
		blockIconWet = register.registerIcon( "SCBlockDirtLooseWet_0" );
		
        m_iconTopWet = register.registerIcon( "SCBlockFarmlandDungWet_0" );
        m_iconTopDry = register.registerIcon( "SCBlockFarmlandDungDry_0" );
    }
}
