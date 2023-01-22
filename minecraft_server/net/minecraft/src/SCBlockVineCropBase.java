package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockVineCropBase extends SCBlockCropsDailyGrowth {

	protected SCBlockVineCropBase(int iBlockID, String name) {
		super(iBlockID, name);
		setUnlocalizedName(name);
	}

	@Override
	public int idDropped(int iMetadata, Random random, int iFortuneModifier)
	{
		if (shouldRenderRope())
		{
			return FCBetterThanWolves.fcItemRope.itemID;
		}
		
		return 0;
		
	}
	
    protected boolean UpdateIfBlockStays( World world, int i, int j, int k ) 
    {
        if ( !canBlockStay( world, i, j, k ) )
        {
        	if (shouldRenderRope())
        	{
        		world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.fenceRope.blockID, 2);
        	}
        	else
        	{
        		world.setBlockToAir( i, j, k );
        	}
        	
            //dropBlockAsItem( world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
         
            return false;
        }
        
        return true;
    }
	
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick,  		float fYClick, float fZClick) {
		
    	super.onBlockActivated(world, i, j, k, player, iFacing, fXClick, fYClick, fZClick);
    	
    	ItemStack equippedItem = player.getCurrentEquippedItem();
		
    	if (equippedItem == null || !(equippedItem.getItem() instanceof FCItemShears))
		{
    		if (isFruitRipe(world, i, j, k))
    		{
    			dropFruit(world, i, j, k, iFacing);
    			
    			convert(world, i, j, k);
    			return true;
    		}
    		
			return false;
		}
    	else
    	{
    		//with shears
    		if (canConvertToRope())
    		{
       		 	world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.fenceRope.blockID, 2);
    		 
       		 	world.playSoundAtEntity(player, "mob.sheep.shear", 1.0F, 1.0F);
     		
     			equippedItem.damageItem(1, player);
     			return true;
    		}
    		
    		return false;
    	}
    	
    }
	
	protected boolean canConvertToRope() {
		return false;
	}

	protected abstract Block getConvertedBlock();

	protected abstract Item getFruitDropped();

	
	protected boolean isFruitRipe(World world, int i, int j, int k) {
		return false;
	}

	protected void convert(World world, int i, int j, int k) {
		world.setBlockAndMetadataWithNotify(i, j, k, getConvertedBlock().blockID, 15);
	}

	protected void dropFruit(World world, int i, int j, int k, int iFacing) {
		ItemStack tomato = new ItemStack(getFruitDropped());
		
		int randomNum = 1 + world.rand.nextInt(2);
		
		if (!world.isRemote)
		{  				
			for (int numDropped = 0; numDropped < randomNum; numDropped++)
			{
				FCUtilsItem.EjectStackFromBlockTowardsFacing(world, i, j, k, tomato, iFacing);
			}	
		}
	}

	
    @Override
    protected void AttemptToGrow(World world, int x, int y, int z, Random rand) {
        boolean isNightTime = isNight(world);
        
		if (isNightTime) {
        	// night
        	if (GetHasGrownToday(world, x, y, z)) {
        		SetHasGrownToday(world, x, y, z, false);
        	}
        }
        else {
	    	if (!GetHasGrownToday(world, x, y, z) && GetWeedsGrowthLevel(world, x, y, z) == 0 && canGrowAtCurrentLightLevel(world, x, y, z)) {
		        Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];
		        
		        if (blockBelow != null && blockBelow.IsBlockHydratedForPlantGrowthOn(world, x, y - 1, z)) {
		    		float growthChance = GetBaseGrowthChance(world, x, y, z);
		    		
			    	if (blockBelow.GetIsFertilizedForPlantGrowth(world, x, y - 1, z)) {
			    		growthChance *= 2F;
			    	}
			    	
		            if (rand.nextFloat() <= growthChance) {
		            	IncrementGrowthLevel(world, x, y, z);
		            	UpdateFlagForGrownToday(world, x, y, z);
		            	
		            	if (canGrowVineAbove(world, x, y, z))
		            	{
		            		growVineAbove(world, x, y, z);
		            	}
		            }
		        }
		    }
        }
    }

	protected boolean isNight(World world) {
		int timeOfDay = (int)(world.worldInfo.getWorldTime() % 24000L);
        
        boolean isNightTime = timeOfDay > 14000 && timeOfDay < 22000;
		return isNightTime;
	}

	protected boolean canGrowVineAbove(World world, int x, int y, int z) 
	{
		return GetGrowthLevel(world, x, y, z) == getMaxGrowthStage() && getIsBlockAboveVerticalRope(world, x, y + 1, z);
	}
	
    protected abstract void growVineAbove(World world, int x, int y, int z);

	protected boolean getIsBlockAboveVerticalRope(World world, int x, int y, int z) {
		
    	if (world.getBlockId(x, y, z) == SCDefs.fenceRope.blockID)
    	{
    		  Block rope = Block.blocksList[world.getBlockId(x, y, z)];
    		  
    		  return !((SCBlockFenceRope)rope).GetExtendsAlongAxis(world, x, y, z, 0) && !((SCBlockFenceRope)rope).GetExtendsAlongAxis(world, x, y, z, 2);
    	}
    	
		return false;
	}

    protected boolean shouldRenderCrops() {
		// TODO Auto-generated method stub
		return true;
	}

	protected abstract boolean shouldRenderWeeds();

	protected abstract boolean shouldRenderRope();
	protected abstract boolean shouldRenderLeaves(int meta);

	protected boolean shouldRenderFlower(int blockMetadata)
	{
		return false;
	}
	
    protected boolean shouldRenderFruit(int meta)
    {
    	return false;
    }
    
    protected Icon getFlowerIcon(IBlockAccess blockAccess, int i, int j, int k) {
		
		return null;
	}

	protected double getLeavesWidthOffset(int meta) {
		return 4D / 16D;
	}

}
