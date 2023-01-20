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
    
	protected Icon[] cropIcons = new Icon[8];
	protected Icon[] leavesIcons = new Icon[4];
	protected Icon flowerIcon;
	
	@Override
	public void registerIcons(IconRegister register) {
		
		for(int i = 0; i < cropIcons.length; i++)
		{
			blockIcon = cropIcons[i] = register.registerIcon(name + "_" + i);
		}
		
		for(int i = 0; i < leavesIcons.length; i++)
		{
			leavesIcons[i] = register.registerIcon(name + "_leaves_" + i);
		}
		
		flowerIcon = register.registerIcon("SCBlockTomato_flower");
	}
	
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	if (shouldRenderCrops())
    	{
    		RenderCrops( renderer, i, j, k );
    	}
    	
    	if (shouldRenderRope())
    	{
    		RenderRope( renderer, i, j, k);
    	}
    	
    	if (shouldRenderLeaves(renderer.blockAccess.getBlockMetadata(i, j, k)))
    	{
    		RenderLeaves(renderer, i, j, k);
    	}
    	
    	if (shouldRenderWeeds())
    	{
        	FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, i, j, k );
    	}
    	
    	return true;
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
    
    @Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult)
    {
    	if (shouldRenderFlower(renderer.blockAccess.getBlockMetadata(i, j, k)))
    	{
    		RenderFlower(renderer, i, j, k);
    	}
    	
    	if (shouldRenderFruit(renderer.blockAccess.getBlockMetadata(i, j, k)))
    	{
    		RenderFruit(renderer, i, j, k);
    	}    	
    }

    protected void RenderFlower(RenderBlocks renderer, int i, int j, int k)
	{
        Tessellator tessellator = Tessellator.instance;
        
        tessellator.setBrightness( getMixedBrightnessForBlock( renderer.blockAccess, i, j, k ) );
        tessellator.setColorOpaque_F( 1F, 1F, 1F );
        
        double dVerticalOffset = 0D;
        Block blockBelow = Block.blocksList[renderer.blockAccess.getBlockId( i, j - 1, k )];
        
//        if ( blockBelow != null )
//        {
//        	dVerticalOffset = blockBelow.GroundCoverRestingOnVisualOffset( 
//        		renderer.blockAccess, i, j - 1, k );
//        }
        
        RenderCrossHatch( renderer, i, j, k, getFlowerIcon(renderer.blockAccess, i, j, k),
            	getLeavesWidthOffset(renderer.blockAccess.getBlockMetadata(i, j, k)), dVerticalOffset );
        
	}

    protected Icon getFlowerIcon(IBlockAccess blockAccess, int i, int j, int k) {
		
		return null;
	}

	protected void RenderLeaves( RenderBlocks renderer, int i, int j, int k )
    {
        Tessellator tessellator = Tessellator.instance;
        
        tessellator.setBrightness( getMixedBrightnessForBlock( renderer.blockAccess, i, j, k ) );
        tessellator.setColorOpaque_F( 1F, 1F, 1F );
        
        double dVerticalOffset = 0D;
        Block blockBelow = Block.blocksList[renderer.blockAccess.getBlockId( i, j - 1, k )];
        
//        if ( blockBelow != null )
//        {
//        	dVerticalOffset = blockBelow.GroundCoverRestingOnVisualOffset( 
//        		renderer.blockAccess, i, j - 1, k );
//        }
        
        RenderCrossHatch( renderer, i, j, k, getLeavesIcon(renderer.blockAccess, i, j, k),
        	getLeavesWidthOffset(renderer.blockAccess.getBlockMetadata(i, j, k)), dVerticalOffset );
    }

	protected double getLeavesWidthOffset(int meta) {
		return 4D / 16D;
	}

	protected Icon getLeavesIcon(IBlockAccess blockAccess, int i, int j, int k)
	{
		return leavesIcons[GetGrowthLevel(blockAccess, i,j,k) & 3];
	}
    
    private void RenderRope(RenderBlocks renderer, int i, int j, int k)
    {
    	float halfHeight = 1/16F;
    	
    	renderer.setRenderBounds(
    			0.5F - halfHeight, 0F, 0.5F - halfHeight,
    			0.5F + halfHeight, 1F, 0.5F + halfHeight);
    	
		FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, SCDefs.fenceRope.blockIcon );
	}

	protected void RenderCrops( RenderBlocks renderer, int i, int j, int k )
    {
        Tessellator tessellator = Tessellator.instance;
        
        tessellator.setBrightness( getMixedBrightnessForBlock( renderer.blockAccess, i, j, k ) );
        tessellator.setColorOpaque_F( 1F, 1F, 1F );
        
        double dVerticalOffset = 0D;
        Block blockBelow = Block.blocksList[renderer.blockAccess.getBlockId( i, j - 1, k )];
        
//        if ( blockBelow != null )
//        {
//        	dVerticalOffset = blockBelow.GroundCoverRestingOnVisualOffset( 
//        		renderer.blockAccess, i, j - 1, k );
//        }
        
        int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
        SCUtilsRender.renderCrossedSquaresWithVerticalOffset(renderer, this, i, j, k, getCropIcons(renderer.blockAccess, i, j, k), dVerticalOffset);
    }

	protected Icon getCropIcons(IBlockAccess blockAccess, int i, int j, int k) {
		return cropIcons[GetGrowthLevel(blockAccess, i, j, k)];
	}
	
	protected void RenderFruit( RenderBlocks renderer, int i, int j, int k )
    {
		Tessellator tessellator = Tessellator.instance;
        
        tessellator.setBrightness( getMixedBrightnessForBlock( renderer.blockAccess, i, j, k ) );
        tessellator.setColorOpaque_F( 1F, 1F, 1F );
        
        double dVerticalOffset = 0D;
        Block blockBelow = Block.blocksList[renderer.blockAccess.getBlockId( i, j - 1, k )];
        
//        if ( blockBelow != null )
//        {
//        	dVerticalOffset = blockBelow.GroundCoverRestingOnVisualOffset( 
//        		renderer.blockAccess, i, j - 1, k );
//        }
        
        int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
        SCUtilsRender.renderCrossedSquaresWithVerticalOffset(renderer, this, i, j, k, getFruitIcons(renderer.blockAccess, i, j, k), dVerticalOffset);
    }

	protected Icon getFruitIcons(IBlockAccess blockAccess, int i, int j, int k)
	{
		return null;
	};

}
