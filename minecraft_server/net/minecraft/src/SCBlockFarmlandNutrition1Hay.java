package net.minecraft.src;

import java.util.Random;

public class SCBlockFarmlandNutrition1Hay extends SCBlockFarmlandNutrition1 {

	protected SCBlockFarmlandNutrition1Hay(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockFarmlandHay");
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
		//turn loose if tall grass has grown
//		int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
//		
//        if ( iTimeOfDay > 14000 && iTimeOfDay < 22000 ) //night
//        {
//    		if ( rand.nextFloat() <= 0.5F )
//    		{
//    			if( world.getBlockId(i, j + 1, k ) == Block.tallGrass.blockID )
//    			{
//    				setLooseDirt(world, i, j, k);
//    			}
//    		}
//        }
		
		//hydration from super super
		if ( HasIrrigatingBlocks( world, i, j, k ) || world.IsRainingAtPos( i, j + 1, k ) )
        {
        	SetFullyHydrated( world, i, j, k );
        }
        else if ( IsHydrated( world, i, j, k ) )
    	{
        	if (!hasNutritionBlockAround(world, i, j, k))
        	{
        		DryIncrementally( world, i, j, k );
        		//System.out.println("No wood, going to dry out");
        	}
        	else; // System.out.println("I stay wet, I have wood nearby");
    	}
        else
        {
        	CheckForSoilReversion( world, i, j, k );
        }
		//Weeds from super
		if ( !CheckForSnowReversion( world, i, j, k, rand ) )
		{
			UpdateWeedGrowth( world, i, j, k, rand );
		}

    } 
	
	public void UpdateWeedGrowth( World world, int i, int j, int k, Random rand )
    {
		//FCMOD code modified
		if ( world.getBlockId( i, j, k ) == blockID )
		{
			int iWeedsLevel = GetWeedsGrowthLevel( world, i, j, k );
	        int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
			
			if ( world.isAirBlock( i, j + 1, k ) )
			{
		        if ( iTimeOfDay > 14000 && iTimeOfDay < 22000 )
		        {
		        	// night
		        	
		        	//SCADDON: change
		        	
//	        		if ( rand.nextInt( 20 ) == 0 && 
	        		if ( rand.nextFloat() <= getWeedsGrowthChance() && 
	        			world.GetBlockNaturalLightValueMaximum( i, j + 1, k ) >= 
        				m_iLightLevelForWeedGrowth )
	        		{
	        			// only start to grow on empty earth if there's potential for natural light
	        			// to avoid weirdness with weeds popping up deep underground and such
	        			
		        		world.setBlockWithNotify( i, j + 1, k, 
			        		FCBetterThanWolves.fcBlockWeeds.blockID ); 
//		        			Block.oreDiamond.blockID ); 

		        		
		        		SetWeedsGrowthLevel( world, i, j, k, 1 );
	        		}
		        }
			}
			else if ( CanWeedsShareSpaceWithBlockAt( world, i, j + 1, k ) )
			{
		        if ( iTimeOfDay > 14000 && iTimeOfDay < 22000 )
		        {
		        	// night
		        	
		        	if ( iWeedsLevel == 0  )
		        	{
		        		//SCADDON: Change
//		        		if ( rand.nextInt( 20 ) == 0 )
		        		if ( rand.nextFloat() <= getWeedsGrowthChance() )
		        		{
			        		SetWeedsGrowthLevel( world, i, j, k, 1 );
		        		}
		        	}
		        	else if ( iWeedsLevel % 2 == 0 )
		        	{
		        		// weeds are only allowed to grow one stage per day, so this flags
		        		// them for the next day's growth.
		        		
		        		SetWeedsGrowthLevel( world, i, j, k, iWeedsLevel + 1 );
		        	}
		        }
		        else
		        {
		        	// day(ish)
		        	
		        	if ( world.GetBlockNaturalLightValue( i, j + 1, k ) >= 
		        		m_iLightLevelForWeedGrowth )
		        	{
			        	if ( iWeedsLevel == 7 )
			        	{
//			        		SetWeedsGrowthLevel( world, i, j, k, 0 );
	
//			        		world.setBlockAndMetadataWithNotify( i, j + 1, k, Block.tallGrass.blockID, 1 );
			        	}
			        	else if ( iWeedsLevel % 2 == 1 )
			        	{
			        		SetWeedsGrowthLevel( world, i, j, k, iWeedsLevel + 1 );
			        	}
		        	}
		        }
			}
			else if ( iWeedsLevel > 0 )
			{
				SetWeedsGrowthLevel( world, i, j, k, 0 );
			}
		}
    }

	@Override
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{
		// decrease nutrition of nutrient block
		attemptToConvertNutritionBlockAround(world, i, j, k, plantBlock);
		
		// decrease nutrition - moved to NotifyOfPlantAboveRemoved
	}
	
	@Override
	public void NotifyOfPlantAboveRemoved( World world, int i, int j, int k, Block plantBlock )
	{
		// decrease nutrition
    	if ( world.getBlockId( i, j + 1, k ) != Block.tallGrass.blockID )
    	{
    		world.setBlockAndMetadataWithNotify( i, j, k, 
    				SCDefs.dirtLooseNutrition.blockID, 3);
    	}
	}
	
	
	@Override
	public boolean GetIsFertilizedForPlantGrowth( World world, int i, int j, int k )
	{
		return false;
	}
	
	@Override
    protected boolean IsFertilized( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}
	
	@Override
    protected boolean IsDunged( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}
}
