package net.minecraft.src;

import java.util.Random;

public class SCBlockFarmlandNutrition2 extends SCBlockFarmlandBase {

	protected SCBlockFarmlandNutrition2(int iBlockID) {
		super(iBlockID);
		this.setUnlocalizedName("SCBlockFarmlandNutrition");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public float GetPlantGrowthOnMultiplier(World world, int i, int j, int k, Block plantBlock) {
		return getNutritionMultiplier();
	}
	
	@Override
	float getNutritionMultiplier() {
		return 0.75F;
	}
	
	public float getWeedsGrowthChance() {
		return 0.10F; //default was 1 in 20 ie 0.05F
	}
	
	@Override
	protected void setLooseDirt(World world, int i, int j, int k) {
		world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID , 1);
	}
	
	protected void SetDung( World world, int i, int j, int k )
	{
	    	int iTargetBlockMetadata = world.getBlockMetadata( i, j, k );
	    	
	    	world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.farmlandNutrition2Dung.blockID, iTargetBlockMetadata );
	}

	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
	{
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileSand.itemID, 3, 0, fChanceOfDrop );
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileGravel.itemID, 3, 0, fChanceOfDrop );
		return true;
	}
	
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iNeighborBlockID )
    {
       // super.onNeighborBlockChange( world, i, j, k, iNeighborBlockID );
        
        if ( world.getBlockMaterial( i, j + 1, k ).isSolid() || 
        	CanFallIntoBlockAtPos( world, i, j - 1, k ) )
        {
            world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1 );
        }
        else if ( GetWeedsGrowthLevel( world, i, j, k ) > 0 && 
        	!CanWeedsShareSpaceWithBlockAt( world, i, j + 1, k ) )
        {
        	// the weeds we had above us are no longer possible
        	
			SetWeedsGrowthLevel( world, i, j, k, 0 );
        }
    }
	
	@Override
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{
		// decrease nutrition
		
		int iMetadata = world.getBlockMetadata( i, j, k );
		
		world.setBlockWithNotify( i, j, k,
				SCDefs.farmlandNutrition1.blockID);
		
		// decrease nutrition of nutrient block
		attemptToConvertNutritionBlockAround(world, i, j, k, plantBlock);
		
	}
	
	@Override
    protected void SetFertilized( World world, int i, int j, int k )
    {
    	int iTargetBlockMetadata = world.getBlockMetadata( i, j, k );
    	
    	world.setBlockAndMetadataWithNotify( i, j, k, 
    			SCDefs.farmlandNutrition2Fertilized.blockID, iTargetBlockMetadata );
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

}
