package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class SCBlockSunflowerTop extends FCBlockCrops {

	protected SCBlockSunflowerTop(int par1) {
		super(par1);
		setUnlocalizedName("SCBlockSunflowerTop");
		
        InitBlockBounds( 0.5D - m_dBoundsHalfWidth, 0D, 0.5D - m_dBoundsHalfWidth, 
            	0.5D + m_dBoundsHalfWidth, 1D, 0.5D + m_dBoundsHalfWidth );
	}
	
    public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.5F;
    }
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		
		return GetFixedBlockBoundsFromPool();
	}

	@Override
	protected int GetGrowthLevel(int meta) {
		
		if (meta <= 3)
		{
			return 0;
		}
		else if (meta > 3 && meta <= 7)
		{
			return 1;
		}
		else if (meta > 7 && meta <= 11)
		{
			return 2;
		}
		else return 3;
	}
	
	@Override
	protected boolean IsFullyGrown(int iMetadata) {
		return GetGrowthLevel(iMetadata) > 2;
	}

	
	public static int updateRotationForTime( World world )
	{
		int timeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );

		boolean isMorning = timeOfDay >= 0 && timeOfDay < 3000;
		boolean isPreNoon = timeOfDay >= 3000 && timeOfDay < 6000;
		boolean isAfterNoon = timeOfDay >= 6000 && timeOfDay < 9000;
		boolean isEvening = timeOfDay >= 9000 && timeOfDay < 12000;

		boolean earlyNight = timeOfDay >= 12000 && timeOfDay < 15000;
		boolean preMoon = timeOfDay >= 15000 && timeOfDay < 18000;
		boolean afterMoon = timeOfDay >= 18000 && timeOfDay < 21000;
		boolean lateNight = timeOfDay >= 21000 && timeOfDay < 24000;
		
		int rotation = 0;

		if (isMorning) rotation = 0;
		else if (isPreNoon) rotation = 1;
		else if (isAfterNoon) rotation = 2;
		else if (isEvening) rotation = 3;
		else if (earlyNight) rotation = 3;
		else if (preMoon) rotation = 2;
		else if (afterMoon) rotation = 1;
		else if (lateNight) rotation = 0;
		
		return rotation;
	}
	
	@Override
	public void updateTick( World world, int i, int j, int k, Random rand )
	{
		int meta = world.getBlockMetadata(i, j, k);
		int rotation = updateRotationForTime(world);
		int growthStage = GetGrowthLevel(meta);
		
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
    		if (world.provider.dimensionId != 1 && !IsFullyGrown(meta))
    		{
    			AttemptToGrow( world, i, j, k, rand );
    			world.setBlockAndMetadata(i, j, k, this.blockID, growthStage + rotation);
    		}
        }
	}
	
    protected void AttemptToGrow(World world, int x, int y, int z, Random rand) {
    	if (GetWeedsGrowthLevel(world, x, y, z) == 0 && canGrowAtCurrentLightLevel(world, x, y, z)) {
	        Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];
	        
	        if (blockBelow != null && blockBelow.IsBlockHydratedForPlantGrowthOn(world, x, y - 1, z)) {
	    		float fGrowthChance = GetBaseGrowthChance(world, x, y, z) *
	    			blockBelow.GetPlantGrowthOnMultiplier(world, x, y - 2, z, this);
	    		
	            if (rand.nextFloat() <= fGrowthChance) {
	            	System.out.println("Growing");
	            	
	            	IncrementGrowthLevel(world, x, y, z);
	            }
	        }
	    }
    }
    
    protected void IncrementGrowthLevel( World world, int i, int j, int k )
    {
    	int iGrowthLevel = GetGrowthLevel( world, i, j, k ) + 1;
    	
        SetGrowthLevel( world, i, j, k, iGrowthLevel );
        
        if ( IsFullyGrown( world, i, j, k ) )
        {
        	Block blockBelow = Block.blocksList[world.getBlockId( i, j - 2, k )];
        	
        	if ( blockBelow != null )
        	{
        		blockBelow.NotifyOfFullStagePlantGrowthOn( world, i, j - 2, k, this );
        	}
        }
    }
	
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && world.getBlockId(i, j, k) == SCDefs.sunflowerCrop.blockID;
    }
		
	private Icon front[] = new Icon[4];
	private Icon back[] = new Icon[4];
	private Icon stem[] = new Icon[4];
	
	@Override
	protected int GetCropItemID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int GetSeedItemID() {
		// TODO Auto-generated method stub
		return 0;
	}


}
