package net.minecraft.src;

import java.util.Random;

public class SCBlockSunflowerTopCrop extends SCBlockSunflowerBase {

	protected SCBlockSunflowerTopCrop(int iBlockID, String name) {
		super(iBlockID, name);
	}
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
    	int meta = world.getBlockMetadata(i, j, k);
		int rotation = damageToData(meta)[0];
		int growthLevel = damageToData(meta)[1];
		
		System.out.println("Rot: " + rotation + " - GrowthLevel: " + growthLevel);
    	
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	
	        if ( world.provider.dimensionId != 1 && !IsFullyGrown( world, i, j, k ) )
	        {
	        	setFlowerRotation(world, i, j, k);
	        	
	        	AttemptToGrow( world, i, j, k, rand );
	        }
	        
	        
        }
    }

	protected void setFlowerRotation(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);		
		int rotation = updateRotationForTime(world); 
		int growthLevel = damageToData(meta)[1];
		
		int newMeta = dataToDamage(rotation, growthLevel);
		
		world.setBlockMetadataWithNotify(i, j, k, newMeta);
	}
    
    protected void AttemptToGrow(World world, int x, int y, int z, Random rand) {
    	if (GetWeedsGrowthLevel(world, x, y, z) == 0 && canGrowAtCurrentLightLevel(world, x, y, z)) {
	        Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];
	        
	        if (blockBelow != null && blockBelow.IsBlockHydratedForPlantGrowthOn(world, x, y - 1, z)) {
	    		float fGrowthChance = GetBaseGrowthChance(world, x, y, z) *
	    			blockBelow.GetPlantGrowthOnMultiplier(world, x, y - 2, z, this);
	    		
	            if (rand.nextFloat() <= fGrowthChance) {
	            	IncrementGrowthLevel(world, x, y, z);
	            }
	        }
	    }
    }
    
    protected void IncrementGrowthLevel( World world, int i, int j, int k )
    {    	
    	int meta = world.getBlockMetadata(i, j, k);
    	int rotation = updateRotationForTime(world);
    	int growthLevel = damageToData(meta)[1];
    	
    	int newMeta = dataToDamage(rotation, growthLevel + 1);
    	
    	world.setBlockMetadataWithNotify( i, j, k, newMeta);
    }
    
	@Override
	public int GetWeedsGrowthLevel( IBlockAccess blockAccess, int i, int j, int k )
	{
		int iBlockBelowID = blockAccess.getBlockId( i, j - 2, k );
		Block blockBelow = Block.blocksList[iBlockBelowID];
		
		if ( blockBelow != null && iBlockBelowID != blockID )
		{
			return blockBelow.GetWeedsGrowthLevel( blockAccess, i, j - 2, k );
		}
		
		return 0;
	}


	
	@Override
	protected int getMaxGrowthStage() {
		return 3;
	}

	@Override
	protected int GetCropItemID() {
		return SCDefs.sunflower.itemID;
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}

	@Override
	protected boolean isTopBlock() {
		return true;
	}

    protected int GetGrowthLevel( int meta )
    {
    	return damageToData(meta)[1];
    	
//    	return meta & 7;
    }
    
    protected void SetGrowthLevel( World world, int i, int j, int k, int iLevel )
    {
    	int iMetadata = world.getBlockMetadata( i, j, k );
    	
    	world.setBlockMetadataWithNotify( i, j, k, iMetadata + 4);
    }
    
    protected boolean IsFullyGrown( World world, int i, int j, int k )
    {
    	return IsFullyGrown( world.getBlockMetadata( i, j, k ) );
    }
    
    protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) >= getMaxGrowthStage();
    }
    
    
    public static int dataToDamage(int rotation, int growthLevel)
    {
		return rotation | growthLevel << 2;
    }

	protected static int[] damageToData(int damage)
	{
		int rotation = damage & 3;
		int growthLevel = (damage >> 2) & 3;
		
		return new int[] { rotation, growthLevel };
	}
    
}
