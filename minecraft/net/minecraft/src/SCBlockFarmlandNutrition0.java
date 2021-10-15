package net.minecraft.src;

import java.util.Random;

public class SCBlockFarmlandNutrition0 extends SCBlockFarmlandBase {

	protected SCBlockFarmlandNutrition0(int iBlockID) {
		super(iBlockID);
		this.setUnlocalizedName("SCBlockFarmlandNutrition_0");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public float GetPlantGrowthOnMultiplier(World world, int i, int j, int k, Block plantBlock) {
		return getNutritionMultiplier();
	}
	
	@Override
	float getNutritionMultiplier() {
		return 0.25F;
	}
	
	@Override
	protected void setLooseDirt(World world, int i, int j, int k) {
		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID , 3);
	}


	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iNeighborBlockID )
    {
       // super.onNeighborBlockChange( world, i, j, k, iNeighborBlockID );
        
        if ( world.getBlockMaterial( i, j + 1, k ).isSolid() || 
        	CanFallIntoBlockAtPos( world, i, j - 1, k ) )
        {
            world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 3 );
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
		// decrease nutrition of nutrient block
		attemptToConvertNutritionBlockAround(world, i, j, k, plantBlock);
	}

	@Override
    protected void SetFertilized( World world, int i, int j, int k )
    {
    	int iTargetBlockMetadata = world.getBlockMetadata( i, j, k );
    	
    	world.setBlockAndMetadataWithNotify( i, j, k, 
    			SCDefs.farmlandNutrition0Fertilized.blockID, iTargetBlockMetadata );
    }
	
	@Override
	public void OnVegetationAboveGrazed( World world, int i, int j, int k, EntityAnimal animal )
	{
        if ( animal.GetDisruptsEarthOnGraze() )
        {
        	world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 3);
        	
        	NotifyNeighborsBlockDisrupted( world, i, j, k );
        }
	}
	
	protected void CheckForSoilReversion( World world, int i, int j, int k )
	{
		if ( !DoesBlockAbovePreventSoilReversion( world, i, j, k ) )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 3);
		}
	}
	
	@Override
    public void onFallenUpon( World world, int i, int j, int k, Entity entity, float fFallDist )
    {
		// min fall dist of 0.75 (previously 0.5) so that the player can safely 
		// step off slabs without potentially ruining crops
		
        if ( !world.isRemote && world.rand.nextFloat() < fFallDist - 0.75F )
        {
        	world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 3);
        }
    }

	protected Icon blockIconWet;
    
    public void registerIcons( IconRegister register )
    {
		    	
    	blockIcon = register.registerIcon( "SCBlockDirtLooseDry_3" );
    	blockIconWet = register.registerIcon( "SCBlockDirtLooseWet_3" );
		
        m_iconTopWet = register.registerIcon( "SCBlockFarmlandWet_3" );
        m_iconTopDry = register.registerIcon( "SCBlockFarmlandDry_3" );
    }
    
	@Override
    public Icon getIcon( int iSide, int iMetadata )
    {
		if ( iSide == 1 )
		{
			if ( IsHydrated( iMetadata ) )
			{
				return m_iconTopWet; 
			}				
			
			return m_iconTopDry;
		}
		
		if ( IsHydrated( iMetadata ) )
		{
			return blockIconWet; 
		}
		else return blockIcon;
    }
    
	@Override
    public int idPicked( World world, int i, int j, int k )
    {
        return this.blockID;
    }

}
