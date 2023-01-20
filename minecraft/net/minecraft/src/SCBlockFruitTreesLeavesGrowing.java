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

	protected Icon flower;
	private Icon leaves;
	private Icon[] fruit = new Icon[3];
	private Icon[] fruitAlt = new Icon[3];
	
	@Override
	public void registerIcons(IconRegister register)
	{		
		flower = register.registerIcon(getFlowerTexture());
		leaves = register.registerIcon(getLeavesTexture());
		
		for (int i = 0; i < 3; i++)
		{
			fruit[i] = register.registerIcon( "SCBlockFruitTreeLeavesFruit_" + getFruitTextureName() + "_" + i );
			
			if (hasAltTexture())
			{
				fruitAlt[i] = register.registerIcon( "SCBlockFruitTreeLeavesFruit_" + getFruitTextureName() + "_alt_" + i );
			}

		}

	}
	
	protected abstract boolean hasAltTexture();

	protected abstract String getFruitTextureName();
	
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return leaves;
	}

	protected abstract String getLeavesTexture();

	@Override
	public void RenderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean firstPassResult)
	{
		int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
		
		if (isFlowering(meta))
		{
			renderFlowers(renderer, x, y, z);
		}
		else
		{
			renderFruit(renderer, x, y, z);
		}
	}

	private void renderFruit(RenderBlocks renderBlocks, int i, int j, int k) {
		int meta = renderBlocks.blockAccess.getBlockMetadata(i, j, k);
		int growthStage = getGrowthStage(meta) - 1;
		
		float fruitHalfSize = (getFruitSize(growthStage)/16F) / 2;
		fruitHalfSize = fruitHalfSize - 0.001F;
		
		int numberOfFruit = getNumberOfFruits();
		boolean growInPairs = getGrowInPairs();
		
		int cash = cash(i, j, k );

//		if (cash % 2 == 0) //is even
//		{
//			numberOfFruit = 1;
//		}
		for (int num = 0; num < numberOfFruit; num++)
		{
			float centerX = 8/16F;
			float centerY = 8/16F;
			float centerZ = 8/16F;
			
			if (numberOfFruit == 2)
			{
				if (cash % 2 == 0) //is even
				{
					if (num == 0)
					{
						centerX = 4/16F;
						centerY = 4/16F;
						centerZ = 4/16F;
					}
					else {
						centerX = 12/16F;
						centerY = 12/16F;
						centerZ = 12/16F;
					}
				}
				else 
				{
					if (num == 0)
					{
						centerX = 12/16F;
						centerY = 4/16F;
						centerZ = 4/16F;
					}
					else {
						centerX = 12/16F;
						centerY = 12/16F;
						centerZ = 4/16F;
					}
				}
			}
			else
			{
				centerX = 8/16F;
				centerY = 8/16F;
				centerZ = 8/16F;
			}
			
			int maxShift = getMaxShift();
			int doubleMaxShift = maxShift*8;
		
			cash = cash & doubleMaxShift;

			float shiftX = maxShift/16F - cash/64F;
			float shiftY = maxShift/16F - cash/64F;
			float shiftZ = maxShift/16F - cash/64F;
			
			centerX = centerX + shiftX;
			centerY = centerY + shiftY;
			centerZ = centerZ + shiftZ;
	
			
			
			renderFruit(renderBlocks, i, j, k, growthStage, fruitHalfSize, centerX, centerY , centerZ, fruit[growthStage]);
			
			float fruitSize = getFruitSize(growthStage)/16F;
			
			if (growInPairs)
			{
				if (num == 0)
				{
					if (cash % 2 == 0) //is even
					{
						centerZ= centerZ + fruitSize + fruitSize/2;
						centerY = centerY + fruitSize/2;
					}
					else
					{
						centerZ= centerZ - fruitSize - fruitSize/2;
						centerY = centerY - fruitSize/2;
					}
					
					
				}
				else
				{
					if (cash % 2 == 0) //is even
					{
						centerX = centerX - fruitSize - fruitSize/2;
						centerY = centerY - fruitSize/2;
					}
					else 
					{
						centerX = centerX + fruitSize + fruitSize/2;
						centerY = centerY + fruitSize/2;
					}
					
				}

				if (hasAltTexture())
				{
					renderFruit(renderBlocks, i, j, k, growthStage, fruitHalfSize, centerX, centerY , centerZ, fruitAlt[growthStage]);
				}
				else
				{
					renderFruit(renderBlocks, i, j, k, growthStage, fruitHalfSize, centerX, centerY , centerZ, fruit[growthStage]);
				}
				
			}
			
//			if (growInPairs)
//			{
//				if (cash % 2 == 0) //is even
//				{
//					if (num == 0)
//					{
//						centerX = 4/16F + fruitSize;
//						centerY = 4/16F;
//						centerZ = 4/16F;
//					}
//					else {
//						centerX = 12/16F;
//						centerY = 12/16F; 
//						centerZ = 12/16F - fruitSize;
//					}
//				}
//				else 
//				{
//					if (num == 0)
//					{
//						centerX = 12/16F;
//						centerY = 4/16F;
//						centerZ = 4/16F + fruitSize;
//					}
//					else {
//						centerX = 12/16F - fruitSize;
//						centerY = 12/16F;
//						centerZ = 4/16F;
//					}
//				}
//
//				maxShift = getMaxShift();
//				doubleMaxShift = maxShift*2;
//			
//				cash = cash & doubleMaxShift;
//				
//				shiftX = maxShift/16F - cash/16F ;
//				shiftY = maxShift/16F - cash/16F ;
//				shiftZ = maxShift/16F - cash/16F ;
//				
//				centerX = centerX + shiftX;
//				centerY = centerY + shiftY;
//				centerZ = centerZ + shiftZ;
//				
//				renderFruit(renderBlocks, i, j, k, growthStage, fruitHalfSize, centerX, centerY , centerZ);
//			}


		}
		
	}

	protected abstract int getMaxShift();

	private void renderFruit(RenderBlocks renderBlocks, int i, int j, int k, int growthStage, float fruitSize,
			float centerX, float centerY, float centerZ, Icon icon) {
		
		renderBlocks.setOverrideBlockTexture(icon);
		renderBlocks.setRenderBounds( 
				centerX - fruitSize, centerY - fruitSize, centerZ - fruitSize,
				centerX + fruitSize, centerY + fruitSize, centerZ + fruitSize					
				);	
		renderBlocks.renderStandardBlock(this, i, j, k);
		renderBlocks.clearOverrideBlockTexture();
	}
		
		
//		//get the random number from coordinates
//        int cash = cash( i, j, k );
//        
//        //limit the cash to 0 - 3
//        cash = cash & 7;
//        
//        float width;
//		switch (growthStage) {
//		case 1:
//			width = 1/16F;
//			break;
//			
//		case 2:
//			width = 2/16F;
//			break;
//			
//		case 3:
//			width = 4/16F;
//			break;
//			
//		default:
//			width = 4/16F;
//			break;
//		}
//        
//        shiftX = 3/16F;
//        shiftY = 3/16F;
//        shiftZ = 3/16F;
//        
//        renderBlocks.setOverrideBlockTexture(fruit[growthStage - 1]);
//		renderBlocks.setRenderBounds( getFruitBounds(cash/16F + shiftX, cash/16F + shiftY, cash/16F + shiftZ, width) );	
//		renderBlocks.renderStandardBlock(this, i, j, k);
//		renderBlocks.clearOverrideBlockTexture();
//		
//		if (hasAltTexture())
//		{
//	        renderBlocks.setOverrideBlockTexture(fruitAlt[growthStage - 1]);
//		}
//		else renderBlocks.setOverrideBlockTexture(fruit[growthStage - 1]);
//		
//		renderBlocks.setRenderBounds( getFruitBounds(cash/16F + shiftX + 1/16F, cash/16F + shiftY, cash/16F + shiftZ + 5/16F, width) );
//		renderBlocks.renderStandardBlock(this, i, j, k);
//		renderBlocks.clearOverrideBlockTexture();
//		
//        shiftX = 10/16F;
//        shiftY = 10/16F;
//        shiftZ = 10/16F;
//		
//        renderBlocks.setOverrideBlockTexture(fruit[growthStage - 1]);
//		renderBlocks.setRenderBounds( getFruitBounds(cash/16F + shiftX, cash/16F + shiftY, cash/16F + shiftZ, width) );	
//		renderBlocks.renderStandardBlock(this, i, j, k);
//		renderBlocks.clearOverrideBlockTexture();
//		
//		if (hasAltTexture())
//		{
//	        renderBlocks.setOverrideBlockTexture(fruitAlt[growthStage - 1]);
//		}
//		else renderBlocks.setOverrideBlockTexture(fruit[growthStage - 1]);
//		renderBlocks.setRenderBounds( getFruitBounds(cash/16F + shiftX - 5/16F, cash/16F + shiftY, cash/16F + shiftZ - 1/16F, width) );	
//		renderBlocks.renderStandardBlock(this, i, j, k);
//		renderBlocks.clearOverrideBlockTexture();
//		
//	}

	protected abstract boolean getGrowInPairs();

	protected abstract int getNumberOfFruits();

	protected abstract int getFruitSize(int i);

	private void renderFlowers(RenderBlocks renderer, int x, int y, int z)
	{
		renderer.setOverrideBlockTexture(flower);
		renderer.setRenderBounds(0,0,0,1,1,1);
		renderer.renderStandardBlock(this, x, y, z);
		renderer.clearOverrideBlockTexture();
	}
	
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
