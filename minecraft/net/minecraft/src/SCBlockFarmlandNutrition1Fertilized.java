//copied from FCBlockFarmlandFertilized

package net.minecraft.src;

public class SCBlockFarmlandNutrition1Fertilized extends SCBlockFarmlandNutrition1 {

	protected SCBlockFarmlandNutrition1Fertilized(int iBlockID) {
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
	
	@Override
    public void registerIcons( IconRegister register )
    {
		blockIcon = register.registerIcon( "SCBlockDirtLooseDry_2" );
		blockIconWet = register.registerIcon( "SCBlockDirtLooseWet_2" );
		
        m_iconTopWet = register.registerIcon( "SCBlockFarmlandFertilizedWet_2" );
        m_iconTopDry = register.registerIcon( "SCBlockFarmlandFertilizedDry_2" );
    }
}
