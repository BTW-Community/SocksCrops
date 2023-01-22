//copied from FCBlockFarmlandFertilized

package net.minecraft.src;

import java.util.Random;

public class SCBlockFarmlandNutrition3Fertilized extends FCBlockFarmlandFertilized {

	public SCBlockFarmlandNutrition3Fertilized(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockFarmlandFertilized");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	private void setLooseDirt(World world, int i, int j, int k) {
		world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0 );

	}
	
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iNeighborBlockID )
    {
        //super.onNeighborBlockChange( world, i, j, k, iNeighborBlockID );
        
        if ( world.getBlockMaterial( i, j + 1, k ).isSolid() || 
        	CanFallIntoBlockAtPos( world, i, j - 1, k ) )
        {
            setLooseDirt(world, i, j, k);
        }
        else if ( GetWeedsGrowthLevel( world, i, j, k ) > 0 && 
        	!CanWeedsShareSpaceWithBlockAt( world, i, j + 1, k ) )
        {
        	// the weeds we had above us are no longer possible
        	
			SetWeedsGrowthLevel( world, i, j, k, 0 );
        }
    }
	
	protected void CheckForSoilReversion( World world, int i, int j, int k )
	{
		if ( !DoesBlockAbovePreventSoilReversion( world, i, j, k ) )
		{
			setLooseDirt(world, i, j, k);
		}
	}

	
	@Override
	public void OnVegetationAboveGrazed( World world, int i, int j, int k, EntityAnimal animal )
	{
        if ( animal.GetDisruptsEarthOnGraze() )
        {
        	setLooseDirt(world, i, j, k);
        	
        	NotifyNeighborsBlockDisrupted( world, i, j, k );
        }
	}
	
	@Override
    public void onFallenUpon( World world, int i, int j, int k, Entity entity, float fFallDist )
    {
		// min fall dist of 0.75 (previously 0.5) so that the player can safely 
		// step off slabs without potentially ruining crops
		
        if ( !world.isRemote && world.rand.nextFloat() < fFallDist - 0.75F )
        {
        	setLooseDirt(world, i, j, k);
        }
    }

	
	@Override
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{	
		// decrease nutrition of nutrient block
		SCBlockFarmlandBase.attemptToConvertNutritionBlockAround(world, i, j, k, plantBlock);
		
		// revert back to unfertilized soil and go down a nutrition stage
		
		int iMetadata = world.getBlockMetadata( i, j, k );
		
		world.setBlockAndMetadataWithNotify( i, j, k, 
			SCDefs.farmlandNutrition2.blockID, iMetadata );
		
		
	}

	
	@Override
	public int idDropped(int iMetadata, Random rand, int iFortuneModifier) {
		return this.blockID;
	}
	
	@Override
	public float GetPlantGrowthOnMultiplier( World world, int i, int j, int k, Block plantBlock )
	{
		return 2F * getNutritionMultiplier();
	}
	
	private float getNutritionMultiplier() {
		return 1.0F;
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
	
	
}
