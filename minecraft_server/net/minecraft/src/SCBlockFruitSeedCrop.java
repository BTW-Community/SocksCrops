package net.minecraft.src;

import java.util.Random;

public class SCBlockFruitSeedCrop extends FCBlockCrops {

	public static int apple = 0;
	public static int cherry = 4;
	public static int lemon = 8;
	public static int olive = 12;
	
	protected SCBlockFruitSeedCrop(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockFruitTreeSeedling");
	}
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	
	        if ( world.provider.dimensionId != 1 && !IsFullyGrown( world, i, j, k ) )
	        {
	        	AttemptToGrow( world, i, j, k, rand );
	        }
	        
        }
    }
    
    protected void AttemptToGrow(World world, int x, int y, int z, Random rand) {
    	if (GetWeedsGrowthLevel(world, x, y, z) == 0 && canGrowAtCurrentLightLevel(world, x, y, z)) {
	        Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];
	        
	        if (blockBelow != null && blockBelow.IsBlockHydratedForPlantGrowthOn(world, x, y - 1, z)) {
	    		float fGrowthChance = GetBaseGrowthChance(world, x, y, z) *
	    			blockBelow.GetPlantGrowthOnMultiplier(world, x, y - 1, z, this);
	    		
	            if (rand.nextFloat() <= fGrowthChance) {
	            	IncrementGrowthLevel(world, x, y, z);
	            }
	        }
	    }
    }
	
    public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.05F;
    }
    
    private int getType(int meta) {
    	if (meta < 4) return 0;
    	else if (meta < 8 ) return 1;
    	else if (meta < 12) return 2;
    	else return 3;
	}
    

	@Override
	protected int GetCropItemID() {
		return SCDefs.fruitTreeSapling.blockID;
	}
	
	@Override
	public int damageDropped(int meta) {
		return getType(meta);
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}
	
    protected int GetGrowthLevel( int iMetadata )
    {
    	return iMetadata & 3;
    }
        
    protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) == 3;
    }
    
    protected boolean IsFullyGrown( World world, int i, int j, int k )
    {
    	return IsFullyGrown( world.getBlockMetadata( i, j, k ) );
    }
    
    protected void IncrementGrowthLevel( World world, int i, int j, int k )
    {
    	int iMetadata = world.getBlockMetadata( i, j, k );	
    	
    	world.setBlockMetadataWithNotify( i, j, k, iMetadata + 1);
        
        if ( IsFullyGrown( world, i, j, k ) )
        {
        	Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
        	
        	if ( blockBelow != null )
        	{
        		blockBelow.NotifyOfFullStagePlantGrowthOn( world, i, j - 1, k, this );
        	}
        }
    }

    @Override
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		
    	int growthStage = GetGrowthLevel(blockAccess.getBlockMetadata(i, j, k));
    	
    	if (growthStage == 0)
    	{
			return new AxisAlignedBB(
					4/16D, 0, 4/16D,
					12/16D, 4/16D, 12/16D);
    	}
    	else if (growthStage == 1)
    	{
			return new AxisAlignedBB(
					5/16D, 0, 5/16D,
					11/16D, 6/16D, 11/16D);
    	}
    	else if (growthStage == 2)
    	{
			return new AxisAlignedBB(
					4/16D, 0, 4/16D,
					12/16D, 8/16D, 12/16D);
    	}
    	else
		{
			return new AxisAlignedBB(
					3/16D, 0, 3/16D,
					13/16D, 10/16D, 13/16D);
		}
    }
    
    boolean secondPass;

    public String[] types = {"apple","cherry","lemon","olive"};
    
   

}
