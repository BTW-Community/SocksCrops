package net.minecraft.src;

import java.util.Random;


public abstract class SCBlockFarmlandBase extends FCBlockFarmland{

	protected SCBlockFarmlandBase(int iBlockID) {
		super(iBlockID);
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
		//turn loose if tall grass has grown
		int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
		
        if ( iTimeOfDay > 14000 && iTimeOfDay < 22000 ) //night
        {
    		if ( rand.nextFloat() <= 0.5F )
    		{
    			if( world.getBlockId(i, j + 1, k ) == SCDefs.shortPlant.blockID && world.getBlockMetadata(i, j, k) == SCBlockShortPlant.TALLGRASS )
    			{
    				setLooseDirt(world, i, j, k);
    			}
    		}
        }
		
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
	
	@Override
    public void onEntityCollidedWithBlock( World world, int i, int j, int k, Entity entity )
    {
		//From FCBlockFarmland
		if ( !world.isRemote && !IsFertilized( world, i, j, k ) &&
			entity.isEntityAlive() && entity instanceof EntityItem )
		{
			EntityItem entityItem = (EntityItem)entity;
			ItemStack stack = entityItem.getEntityItem();			
			
			if ( stack.itemID == Item.dyePowder.itemID && stack.getItemDamage() == 15 ) // bone meal
			{
				stack.stackSize--;
				
				if ( stack.stackSize <= 0 )
				{
					entityItem.setDead();
				}
				
				SetFertilized( world, i, j, k );
            	
	            world.playSoundEffect( i + 0.5D, j + 0.5D, k + 0.5D, "random.pop", 0.25F, 
            		( ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.7F + 1F ) * 2F );
			}
			else if ( stack.itemID == FCBetterThanWolves.fcItemDung.itemID)
			{
				stack.stackSize--;
				
				if ( stack.stackSize <= 0 )
				{
					entityItem.setDead();
				}
				
				SetDung(world, i, j, k);
            	
	            world.playSoundEffect( i + 0.5D, j + 0.5D, k + 0.5D, "random.pop", 0.25F, 
            		( ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.7F + 1F ) * 2F );
			}
		}
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
	
	protected abstract void SetDung(World world, int i, int j, int k);

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
			        		SetWeedsGrowthLevel( world, i, j, k, 0 );
	
			        		world.setBlockAndMetadataWithNotify( i, j + 1, k, SCDefs.shortPlant.blockID, SCBlockShortPlant.TALLGRASS );
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

	abstract float getNutritionMultiplier();
	
	abstract float getWeedsGrowthChance();
	
	public static void attemptToConvertNutritionBlockAround(World world, int i, int j, int k, Block plantBlock)
	{
		Random random = new Random();
		
		if (random.nextFloat() <= 0.5 && hasNutritionBlockAround(world, i, j, k)) {
			convertNutritionBlockAround(world, i, j, k);
		}
	}
	
	
	
	@Override
	public abstract float GetPlantGrowthOnMultiplier( World world, int i, int j, int k, Block plantBlock );
	
	protected static void convertNutritionBlockAround(World world, int i, int j, int k) {
		//return super.hasNutritionBlockAround(world, i, j, k);
		//System.out.println("trying to find nutrition block");
			
    	int iHorizontalRange = 1;
    	
        for ( int iTempI = i - iHorizontalRange; iTempI <= i + iHorizontalRange; iTempI++ )
        {
            for ( int iTempJ = j - iHorizontalRange; iTempJ <= j; iTempJ++ )
            {
                for ( int iTempK = k - iHorizontalRange; iTempK <= k + iHorizontalRange; iTempK++ )
                {
//                    if ( isValidNutritionBlock(world, iTempI, iTempJ, iTempK) )
//                    {
                    	int iMetadata = world.getBlockMetadata( iTempI, iTempJ, iTempK );
                		
                    	if (world.getBlockId(iTempI, iTempJ, iTempK) == Block.wood.blockID && world.getBlockMetadata(iTempI, iTempJ, iTempK) >= 12) //only stump
                    	{
                    		world.setBlockAndMetadataWithNotify( iTempI, iTempJ, iTempK, 
                    				SCDefs.damagedLog.blockID, iMetadata ); 
                    		//System.out.println("converting stump to damaged log");
                    	}
                    	else if (world.getBlockId(iTempI, iTempJ, iTempK) == SCDefs.damagedLog.blockID)
                    	{
                    		world.setBlockAndMetadataWithNotify( iTempI, iTempJ, iTempK, 
                    				SCDefs.mossyLog.blockID, iMetadata);
                    		//System.out.println("converting damaged log to mossy log");
                    	}
                    	else if (world.getBlockId(iTempI, iTempJ, iTempK) == SCDefs.mossyLog.blockID)
                    	{
                    		world.setBlockAndMetadataWithNotify( iTempI, iTempJ, iTempK, 
                    				SCDefs.compostBlock.blockID, 0);
                    		//System.out.println("converting mossy into compost");
                    	}
                    	else if (world.getBlockId(iTempI, iTempJ, iTempK) == SCDefs.compostBlock.blockID)
                    	{
                    		world.setBlockAndMetadataWithNotify( iTempI, iTempJ, iTempK, 
                    				SCDefs.dirtLooseNutrition.blockID, 0);
                    		//System.out.println("converting compost into dirt");
                    	}
                        //return true;
//                    }
                }
            }
        }

        //return false;
	}
	
	@Override
	public void NotifyOfPlantAboveRemoved( World world, int i, int j, int k, Block plantBlock )
	{
    	// don't untill on weed growth
    	
    	if ( world.getBlockId( i, j + 1, k ) != Block.tallGrass.blockID )
    	{
    		setLooseDirt(world, i, j, k);    		
    	}

	}
	
	protected abstract void setLooseDirt(World world, int i, int j, int k);


	
	protected static boolean hasNutritionBlockAround( World world, int i, int j, int k )
    {
    	int iHorizontalRange = 1;
    	
        for ( int iTempI = i - iHorizontalRange; iTempI <= i + iHorizontalRange; iTempI++ )
        {
            for ( int iTempJ = j - iHorizontalRange; iTempJ <= j ; iTempJ++ )
            {
                for ( int iTempK = k - iHorizontalRange; iTempK <= k + iHorizontalRange; iTempK++ )
                {
                	//System.out.println(world.getBlockId(iTempI, iTempJ, iTempK));
                    if ( hasValidNutrition(world, iTempI, iTempJ, iTempK) )
                    {
                    	//System.out.println("true");
                        return true;
                    }
                }
            }
        }

        return false;
    }
	
	protected static boolean hasValidNutrition(World world, int iTempI, int iTempJ, int iTempK) {
		
    	int blockID = world.getBlockId( iTempI, iTempJ, iTempK );    	
    	int blockAboveID = world.getBlockId(iTempI, iTempJ + 1, iTempK);
    	Block blockAbove = Block.blocksList[blockAboveID];
    	
    	
    	
    	if (isValidNutritionBlock(world, iTempI, iTempJ, iTempK)) {
    		
    		if ( blockAbove != null && ( blockAbove instanceof SCBlockFarmlandBase ) || ( blockAbove instanceof SCBlockDirtLooseBase ) || ( blockAbove instanceof SCBlockDirtNutrition ) )
    		{
    			//Check for fullblock next to and below
    			if (hasValidBlockNextToNutritionBlock(world, iTempI, iTempJ, iTempK));
				{
					return true;
				}
    		}
    		else return false;

    	}
    	else return false;
	}
	

	private static boolean hasValidBlockNextToNutritionBlock(World world, int iTempI, int iTempJ, int iTempK) {

		//boolean blockAbove = world.isBlockNormalCube(iTempI, iTempJ + 1, iTempK); we are already checking if above is farmland
		boolean blockBelow = world.isBlockNormalCube(iTempI, iTempJ - 1, iTempK);
		boolean blockN = world.isBlockNormalCube(iTempI, iTempJ, iTempK - 1);
		boolean blockS = world.isBlockNormalCube(iTempI, iTempJ, iTempK + 1);
		boolean blockW = world.isBlockNormalCube(iTempI - 1, iTempJ, iTempK);
		boolean blockE = world.isBlockNormalCube(iTempI + 1, iTempJ, iTempK);
		
		boolean isNormal = blockBelow || blockN || blockS || blockW || blockE;
		
		int blockBelowID = world.getBlockId(iTempI, iTempJ - 1, iTempK);
		int blockNID = world.getBlockId(iTempI, iTempJ, iTempK - 1);
		int blockSID = world.getBlockId(iTempI, iTempJ, iTempK + 1);
		int blockWID = world.getBlockId(iTempI - 1, iTempJ, iTempK);
		int blockEID = world.getBlockId(iTempI + 1, iTempJ, iTempK);

		boolean isFarmland =  Block.blocksList[blockBelowID] instanceof SCBlockFarmlandBase || 
				 Block.blocksList[blockNID] instanceof SCBlockFarmlandBase || 
				 Block.blocksList[blockSID] instanceof SCBlockFarmlandBase || 
				 Block.blocksList[blockWID] instanceof SCBlockFarmlandBase || 
				 Block.blocksList[blockEID] instanceof SCBlockFarmlandBase;
		
		boolean blockAdjacent =  isNormal || isFarmland;
		
		return blockAdjacent;
	}

	protected static boolean isValidNutritionBlock(World world, int iTempI, int iTempJ, int iTempK) {
		boolean dungId = world.getBlockId(iTempI, iTempJ, iTempK) == FCBetterThanWolves.fcBlockAestheticOpaqueEarth.blockID;
		boolean dungMeta = world.getBlockMetadata(iTempI, iTempJ, iTempK) == FCBlockAestheticOpaqueEarth.m_iSubtypeDung;
		
		boolean woodID = world.getBlockId(iTempI, iTempJ, iTempK) == Block.wood.blockID;
		boolean stumpMeta = world.getBlockMetadata(iTempI, iTempJ, iTempK) >= 12;
		
		boolean isMossyLog = world.getBlockId(iTempI, iTempJ, iTempK) == SCDefs.damagedLog.blockID;
		boolean isCompost = world.getBlockId(iTempI, iTempJ, iTempK) == SCDefs.compostBlock.blockID;
		boolean isDung = dungId && dungMeta;
		boolean isStump = woodID && stumpMeta;
		
		boolean validNutritionBlock = isStump || isDung || isMossyLog || isCompost;

		return validNutritionBlock;
	}

	@Override
    protected boolean IsFertilized( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}
	
	//Dung
    protected boolean IsDunged( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}
   

}
