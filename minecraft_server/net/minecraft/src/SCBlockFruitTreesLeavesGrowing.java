package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockFruitTreesLeavesGrowing extends SCBlockFruitTreesLeavesBase {
	
	protected SCBlockFruitTreesLeavesGrowing(int blockID) {
		super(blockID);
	}
	
	@Override
    public void harvestBlock( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
        if( !world.isRemote && player.getCurrentEquippedItem() != null && 
    		player.getCurrentEquippedItem().getItem() instanceof FCItemShears)
        {
        	
        	if (getGrowthStage(iMetadata) == 0)
        	{
        		dropBlockAsItem_do( world, i, j, k, new ItemStack( SCDefs.fruitLeavesFlowers, 1, getFruitType() ) );
        	}
        	else dropBlockAsItem_do( world, i, j, k, new ItemStack( SCDefs.fruitLeaves, 1, getFruitType() ) );
                        
            player.getCurrentEquippedItem().damageItem( 1, player );
        } 
        else if( !world.isRemote && player.getCurrentEquippedItem() != null && 
    		player.getCurrentEquippedItem().getItem() instanceof FCItemAxe)
        {
        	
        	if (getGrowthStage(iMetadata) == 3)
        	{
        		dropBlockAsItem_do( world, i, j, k, new ItemStack( fruitDropped(), 1, 0 ) );
        	}
                        
            player.getCurrentEquippedItem().damageItem( 1, player );
        } 
        
    }

	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
		ItemStack heldStack = player.getCurrentEquippedItem();
		int meta = world.getBlockMetadata(i, j, k);
    	int growthStage = getGrowthStage(meta);
		
		if (growthStage == 3 && ( heldStack == null || heldStack.itemID == fruitDropped()) )
		{
			if (!world.isRemote)
			{
				FCUtilsItem.EjectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(fruitDropped(), 1, 0), iFacing);
			}
			
			world.setBlockAndMetadata(i, j, k, SCDefs.fruitLeaves.blockID, getFruitType());
			
			return true;
		}
		return false;
    }
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand) {
		super.updateTick(world, i, j, k, rand);
		
		int meta = world.getBlockMetadata(i, j, k);
		
        if ( !world.isRemote )
        {
        	
        	int growthStage = getGrowthStage(meta);
        	
        	if (growthStage < 3 && canGrow(world, i, j, k, meta ))
        	{
        		if (growthStage == 0)
        		{
        			if (rand.nextFloat() <= getFruitGrowChance())
        			{
        				world.setBlockAndMetadata(i, j, k, this.blockID, meta + 1);
        			}
            		else world.setBlockAndMetadata(i, j, k, SCDefs.fruitLeaves.blockID, getFruitType());
        		}
        		else
        		{
            		if (rand.nextFloat() <= getGrowthChance())
        			{
        				world.setBlockAndMetadata(i, j, k, this.blockID, meta + 1);
        			}
        		}
        	}
        	else {
        		if (rand.nextFloat() <= getFruitDropChance() && world.getBlockId(i, j-1, k) == 0)
        		{
        			world.setBlockAndMetadata(i, j, k, SCDefs.fruitLeaves.blockID, getFruitType());
        			
        			int facing = 0; //Down
        			
        			FCUtilsItem.EjectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(fruitDropped(), 1, 0), facing);
        		}        		
        	}
        }
	}
	
	protected abstract float getFruitGrowChance();

	protected abstract float getFruitDropChance();
	
	protected abstract int getFruitType();


	private boolean canGrow(World world, int i, int j, int k, int meta) {

        int timeOfDay = (int)(world.worldInfo.getWorldTime() % 24000L);
        
        boolean night = (timeOfDay > 14000 && timeOfDay < 22000);
		
        if (!night) return true;
        
//		if ( world.getBlockLightValue(j, j + 1, k) >= 9)
//		{
//			return true;
//		}
		
		return false;
	}


	protected abstract float getGrowthChance();


	protected abstract int fruitDropped();
	protected abstract String getFlowerTexture();
	
	@Override
	public int idDropped(int meta, Random random, int fortuneModifier)
	{	
		if (isFruitRipe(meta))
		{
			return fruitDropped();
		}
		else return 0;
	}
	
	protected int getGrowthStage(int meta)
	{
		return meta & 3;
	}
	

	private boolean isFlowering(int meta)
	{
		return (getGrowthStage(meta) == 0);
	}
	
	private boolean isFruitRipe(int meta)
	{
		return (getGrowthStage(meta) == 3);
	}

	@Override
	public int damageDropped(int iMetaData)
	{
		return 0;
	}

	
	
	protected abstract boolean hasAltTexture();

	protected abstract String getFruitTextureName();
	

	protected abstract String getLeavesTexture();

	

	protected abstract int getMaxShift();


	protected abstract boolean getGrowInPairs();

	protected abstract int getNumberOfFruits();

	protected abstract int getFruitSize(int i);

	
	// https://stackoverflow.com/a/37221804
	// cash stands for chaos hash
	private static int cash(int x, int y, int z)
    {   
    	int h = y + x*374761393 + z*668265263; //all constants are prime
    	h = (h^(h >> 13))*1274126177;
       return h^(h >> 16);
	}
	
	private AxisAlignedBB getFruitBounds(float offsetX, float offsetY, float offsetZ, float width) {		
		
		return new AxisAlignedBB(
				0 + offsetX - width/2, 0 + offsetY - width/2, 0 + offsetZ - width/2,
				0 + offsetX + width/2, 0 + offsetY + width/2, 0 + offsetZ + width/2
				);
	}

}
