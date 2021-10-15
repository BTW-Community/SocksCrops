package net.minecraft.src;

import java.util.Random;

public class SCBlockFarmlandNutrition3 extends SCBlockFarmlandBase {

	protected SCBlockFarmlandNutrition3(int iBlockID) {
		super(iBlockID);
		this.setUnlocalizedName("SCBlockFarmlandNutrition_3");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public float GetPlantGrowthOnMultiplier(World world, int i, int j, int k, Block plantBlock) {
		return getNutritionMultiplier();
	}
	
	@Override
	float getNutritionMultiplier() {
		return 1.0F;
	}
	
	@Override
	protected void setLooseDirt(World world, int i, int j, int k) {
		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID , 0);
	}
	
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer par5EntityPlayer,
			int par6, float par7, float par8, float par9) {
		
		EntityPlayer entityItem = par5EntityPlayer;
		ItemStack stack = entityItem.getCurrentEquippedItem();
		
		// dung dat shit
		if ( stack != null && stack.itemID == FCBetterThanWolves.fcItemDung.itemID
				&& !IsFertilized(world, i, j, k)
				&& !IsDunged(world, i, j, k)) 
		{
			stack.stackSize--;
			
			SetDung( world, i, j, k );
        	
            world.playSoundEffect( i + 0.5D, j + 0.5D, k + 0.5D, "random.pop", 0.25F, 
        		( ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.7F + 1F ) * 2F );
            return true;
		}
		else return false;
	}
	
	protected void SetDung( World world, int i, int j, int k )
	    {
	    	int iTargetBlockMetadata = world.getBlockMetadata( i, j, k );
	    	
	    	world.setBlockAndMetadataWithNotify( i, j, k, 
	    		SCDefs.farmlandNutrition3Dung.blockID, iTargetBlockMetadata );
	}

	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iNeighborBlockID )
    {
        //super.onNeighborBlockChange( world, i, j, k, iNeighborBlockID );
        
        if ( world.getBlockMaterial( i, j + 1, k ).isSolid() || 
        	CanFallIntoBlockAtPos( world, i, j - 1, k ) )
        {
            world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 0 );
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
		
		// decrease nutrition
		if (IsDunged(world, i, j, k) ) {
			int iMetadata = world.getBlockMetadata( i, j, k );
			
			world.setBlockWithNotify( i, j, k, 
					SCDefs.farmlandNutrition3.blockID );
		}else {
			int iMetadata = world.getBlockMetadata( i, j, k );
			
			world.setBlockWithNotify( i, j, k, 
					SCDefs.farmlandNutrition2.blockID );
		}
	}

	
	

	@Override
    protected void SetFertilized( World world, int i, int j, int k )
    {
    	int iTargetBlockMetadata = world.getBlockMetadata( i, j, k );
    	
    	world.setBlockAndMetadataWithNotify( i, j, k, 
    			SCDefs.farmlandNutrition3Fertilized.blockID, iTargetBlockMetadata );
    }
	
	protected void CheckForSoilReversion( World world, int i, int j, int k )
	{
		if ( !DoesBlockAbovePreventSoilReversion( world, i, j, k ) )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 0);
		}
	}

	
	@Override
	public void OnVegetationAboveGrazed( World world, int i, int j, int k, EntityAnimal animal )
	{
        if ( animal.GetDisruptsEarthOnGraze() )
        {
        	world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 0);
        	
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
        	world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.dirtLooseNutrition.blockID, 0);
        }
    }

	protected Icon blockIconWet;
    
    public void registerIcons( IconRegister register )
    {
		    	
    	blockIcon = register.registerIcon( "SCBlockDirtLooseDry_0" );
    	blockIconWet = register.registerIcon( "SCBlockDirtLooseWet_0" );
		
        m_iconTopWet = register.registerIcon( "SCBlockFarmlandWet_0" );
        m_iconTopDry = register.registerIcon( "SCBlockFarmlandDry_0" );
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
	
	@Override
	public int idDropped(int iMetadata, Random rand, int iFortuneModifier) {
		return this.blockID;
	}


	
	

}
