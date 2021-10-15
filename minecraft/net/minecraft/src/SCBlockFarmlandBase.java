package net.minecraft.src;

import java.util.Random;

import com.prupe.mcpatcher.cc.ColorizeBlock;

public abstract class SCBlockFarmlandBase extends FCBlockFarmland{

	protected SCBlockFarmlandBase(int iBlockID) {
		super(iBlockID);
	}
	
	abstract float getNutritionMultiplier();
	
	public void attemptToConvertNutritionBlockAround(World world, int i, int j, int k, Block plantBlock)
	{
		Random random = new Random();
		
		if (random.nextFloat() <= 0.5 && hasNutritionBlockAround(world, i, j, k)) {
			convertNutritionBlockAround(world, i, j, k);
		}
	}
	
	@Override
	public abstract float GetPlantGrowthOnMultiplier( World world, int i, int j, int k, Block plantBlock );
	
	protected void convertNutritionBlockAround(World world, int i, int j, int k) {
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
                    				SCDefs.id_damagedLog, iMetadata ); 
                    		//System.out.println("converting stump to damaged log");
                    	}
                    	else if (world.getBlockId(iTempI, iTempJ, iTempK) == SCDefs.id_damagedLog)
                    	{
                    		world.setBlockAndMetadataWithNotify( iTempI, iTempJ, iTempK, 
                    				SCDefs.id_mossyLog, iMetadata);
                    		//System.out.println("converting damaged log to mossy log");
                    	}
                    	else if (world.getBlockId(iTempI, iTempJ, iTempK) == SCDefs.id_mossyLog)
                    	{
                    		world.setBlockAndMetadataWithNotify( iTempI, iTempJ, iTempK, 
                    				SCDefs.id_compostBlock, 0);
                    		//System.out.println("converting mossy into compost");
                    	}
                    	else if (world.getBlockId(iTempI, iTempJ, iTempK) == SCDefs.id_compostBlock)
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

	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
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
	
	protected boolean hasNutritionBlockAround( World world, int i, int j, int k )
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
	
	protected boolean hasValidNutrition(World world, int iTempI, int iTempJ, int iTempK) {
		
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
	

	private boolean hasValidBlockNextToNutritionBlock(World world, int iTempI, int iTempJ, int iTempK) {

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

	protected boolean isValidNutritionBlock(World world, int iTempI, int iTempJ, int iTempK) {
		boolean dungId = world.getBlockId(iTempI, iTempJ, iTempK) == FCBetterThanWolves.fcBlockAestheticOpaqueEarth.blockID;
		boolean dungMeta = world.getBlockMetadata(iTempI, iTempJ, iTempK) == FCBlockAestheticOpaqueEarth.m_iSubtypeDung;
		
		boolean woodID = world.getBlockId(iTempI, iTempJ, iTempK) == Block.wood.blockID;
		boolean stumpMeta = world.getBlockMetadata(iTempI, iTempJ, iTempK) >= 12;
		
		boolean isMossyLog = world.getBlockId(iTempI, iTempJ, iTempK) == SCDefs.id_damagedLog;
		boolean isCompost = world.getBlockId(iTempI, iTempJ, iTempK) == SCDefs.id_compostBlock;
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
