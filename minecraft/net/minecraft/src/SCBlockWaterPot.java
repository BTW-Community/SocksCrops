//Based on: https://github.com/BTW-Community/Deco-Addon-2/blob/master/minecraft/net/minecraft/src/DecoBlockFlowerPot.java

package net.minecraft.src;

import java.util.Random;

import org.lwjgl.opengl.GL11;

public class SCBlockWaterPot extends BlockContainer {
	
	public static int empty = 0;
	public static int water = 8;
	
	public SCBlockWaterPot(int id) {
		super(id, Material.circuits);
        this.InitBlockBounds(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);
		this.setHardness(0.0F);
		this.setStepSound(soundPowderFootstep);
		this.setUnlocalizedName("flowerPot");
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
		int insertedItemID = potTile.getInsertedItem();
		int storedMetadata = potTile.getStoredBlockMetadata();
		int meta = world.getBlockMetadata(x, y, z);
		
		if (UpdateIfBlockStays(world, x, y, z))
		{
			if (!hasFillContents(potTile)) {
				return; // Don't do anything unless it has water
			}
			
			float random = rand.nextFloat();
			
			if (world.provider.dimensionId == -1 && potTile.getFillContentsID() == Block.slowSand.blockID)
			{
				if (potTile.hasItem() && random <= getGrowthChance(world, x, y, z) )
				{
					grow(world, x, y, z, potTile, meta, insertedItemID);
				}
			}
			else if (world.provider.dimensionId == 0 && world.GetBlockNaturalLightValue(x, y + 1, z) >= getLightLevelForGrowth() )
			{
				
				if (potTile.hasItem() && random <= getGrowthChance(world, x, y, z) )
				{
					grow(world, x, y, z, potTile, meta, insertedItemID);
				}
			}
		}
	}
	
	private int getLightLevelForGrowth() {
		return 11;
	}

	private void grow(World world, int x, int y, int z, SCTileEntityWaterPot potTile, int meta, int insertedItemID)
	{
		
		int i = 1;
		
		if (insertedItemID == Block.sapling.blockID) // exception for sapling
		{
			i = 4;
		}
		
		if (SCDecoIntegration.isDecoInstalled() )
		{
			// exception for sapling
			if (insertedItemID == SCDecoIntegration.cherrySapling.blockID || 
				insertedItemID == SCDecoIntegration.autumnSapling.blockID ||
				insertedItemID == SCDecoIntegration.acaciaSapling.blockID) 
			{
				i = 4;
			}
		}
		
		int growthStage = getGrowthStage(meta) + 1; //to start at 1 and not 0
				
		if (growthStage < potTile.getGrowableGrowthStages(insertedItemID) )
		{
			System.out.println("growing");
			world.setBlockMetadataWithNotify(x, y, z, meta + 1);
			potTile.setStoredBlockMetadata(potTile.getStoredBlockMetadata() + i);

			potTile.markBlockForRender();
			
			if (growthStage == potTile.getGrowableGrowthStages(insertedItemID) - 1)
			{
				world.setBlockMetadataWithNotify(x, y, z, meta - 8);
				potTile.markBlockForRender();
			}
		}
	}
	
	private float getGrowthChance(World world, int x, int y, int z)
	{
		SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
		int insertedItemID = potTile.getInsertedItem();
		
		if (world.getBlockId(x, y + 1, z) == FCBetterThanWolves.fcLightBulbOn.blockID || 
			world.getBlockId(x, y + 2, z) == FCBetterThanWolves.fcLightBulbOn.blockID )	
		{
			return potTile.getGrowableGrowthChance(insertedItemID) * 2F;
		}
		else return potTile.getGrowableGrowthChance(insertedItemID) * 1.25F;
	}

	//When right clicked by a player
    @Override
    public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick ) {
     	ItemStack handItemStack = player.getCurrentEquippedItem();
    	SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
    	int potMeta = world.getBlockMetadata(x, y, z);
    	
    	//Empty hand retrieves item
    	if (handItemStack == null)
    	{
    		if (potTile.hasItem() )
    		{
        		potTile.retrieveItemFromPot(player);
        		
//        		if ( hasFillContents(potTile) )
//        		{
//        			world.setBlockMetadataWithNotify(x, y, z, water);
//        		}
//        		else world.setBlockMetadataWithNotify(x, y, z, empty);

        		return true;
    		}
    		else return false;
    	}
    	
    	int heldID = handItemStack.getItem().itemID;
    	int heldDamage = handItemStack.getItemDamage();
    	
//    	if ( potTile.getFillContentsID() > 0 )
//    	{
//    		potTile.setFillType(handItemStack);
//			
//			if (handItemStack.stackSize > 1)
//			{
//				--handItemStack.stackSize;
//			}
//			else player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
//			
//	    	if (heldID == Item.potion.itemID && heldDamage == 0 )
//			{
//	    		player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle, 1));
//			}
//    		return true;
//    	}
    	
    	if (potTile.getFillContentsID() == 0)
    	{
        	if (heldID == Item.potion.itemID && heldDamage == 0)
    		{
    			world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, water);
    			
    			potTile.setFillType(handItemStack);
    			
    			if (handItemStack.stackSize > 1)
    			{
    				--handItemStack.stackSize;
    			}
    			else player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
    			
    			player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle, 1));
    			
    			return true;
    		}
        	else if (heldID == Block.slowSand.blockID)
    		{
    			world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, water);
    			
    			potTile.setFillType(handItemStack);
    			
    			if (handItemStack.stackSize > 1)
    			{
    				--handItemStack.stackSize;
    			}
    			else player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
    			
    			return true;
    		}
        	else if (heldID == FCBetterThanWolves.fcItemPileDirt.itemID)
    		{
    			world.setBlockWithNotify(x, y, z, SCDefs.flowerPot.blockID);
    			
    			if (handItemStack.stackSize > 1)
    			{
    				--handItemStack.stackSize;
    			}
    			else player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
    			
    			return true;
    		}
    	}

    	//If pot already has an item do nothing
    	if (potTile.hasItem()) {
    		return false;
    	}
    	//If item is placeable within the pot, place it
    	else 
    	{
    		if ( potTile.isValidToStore(heldID, heldDamage) && potTile.getFillContentsID() == Block.waterStill.blockID)
    		{
        		potTile.placeItemInPot(heldID, heldDamage);
        		
        		//Decrements stack, unless in creative
        		if (!player.capabilities.isCreativeMode && --handItemStack.stackSize <= 0)
                {
        			player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                }
        		
        		return true;
    		}
    		
    		if (potTile.getFillContentsID() == Block.slowSand.blockID && potTile.isValidToStoreNether(heldID, heldDamage) )
    		{
        		potTile.placeItemInPot(heldID, heldDamage);
    		
        		//Decrements stack, unless in creative
        		if (!player.capabilities.isCreativeMode && --handItemStack.stackSize <= 0)
                {
        			player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                }
        		
        		return true;
    		}

    	} 	
    	
		
    	return false;
    }

	private boolean hasFillContents(SCTileEntityWaterPot potTile) {
		return potTile.getFillContentsID() > 0;

	}

    private int getGrowthStage(int meta) {
		    	
		return meta & 7;
	}


	/**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return super.canPlaceBlockAt(world, x, y, z) && FCUtilsWorld.DoesBlockHaveCenterHardpointToFacing(world, x, y - 1, z, 1);
    }
    
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        if (!FCUtilsWorld.DoesBlockHaveCenterHardpointToFacing(world, x, y - 1, z, 1))
        {
            return false;
        }
        return true;
    }
    
    protected boolean UpdateIfBlockStays( World world, int x, int y, int z ) 
    {    
    	SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
		int blockInPotID = potTile != null ? potTile.getStoredBlockID() : 0;
		
        if ( !canBlockStay( world, x, y, z ) )
        {
            dropBlockAsItem( world, x, y, z, world.getBlockMetadata( x, y, z ), 0 );
            
            if (blockInPotID != 0)
            {
            	potTile.ejectItemFromPot(world, x, y, z);            	
            }
            
            world.setBlockToAir( x, y, z );
            
            return false;
        }
        
        return true;
    }
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
    {    	
    	SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
		int blockInPotID = potTile != null ? potTile.getStoredBlockID() : 0;
		
        if (!FCUtilsWorld.DoesBlockHaveCenterHardpointToFacing(world, x, y - 1, z, 1))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
           
            if (blockInPotID != 0)
            {
            	potTile.ejectItemFromPot(world, x, y, z);
            }
            
            world.setBlockToAir(x, y, z);
        }
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World world, int x, int y, int z)
    {
//    	SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
//    	int blockInPotID = potTile.getStoredBlockID();
//    	
//    	if (blockInPotID == Block.mushroomBrown.blockID)
//    		return FCBetterThanWolves.fcItemMushroomBrown.itemID;
//    	else if (blockInPotID == Block.mushroomRed.blockID)
//    		return FCBetterThanWolves.fcItemMushroomRed.itemID;
//    	else
//    		return blockInPotID == 0 ? Item.flowerPot.itemID : blockInPotID;
    	
    	return SCDefs.waterPot.blockID;
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World world, int x, int y, int z)
    {
    	SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
    	//int blockInPotMeta = potTile.getStoredBlockMetadata();
        return 0;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World world, int x, int y, int z, int var5, int var6)
    {
        super.breakBlock(world, x, y, z, var5, var6);
    }
    
    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int par5, EntityPlayer par6EntityPlayer)
    {
    	SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
    	int blockInPotID = potTile != null ? potTile.getStoredBlockID() : 0;
    	int blockInPotMeta = potTile.getStoredBlockMetadata();
    	
        if (blockInPotID != 0)
        {
        	potTile.ejectItemFromPot(world, x, y, z);
        	
        }
        
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return SCDefs.waterPotEmpty.itemID;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new SCTileEntityWaterPot();
    }
    

    public boolean CanGroundCoverRestOnBlock(World var1, int var2, int var3, int var4)
    {
        return var1.doesBlockHaveSolidTopSurface(var2, var3 - 1, var4);
    }

    public float GroundCoverRestingOnVisualOffset(IBlockAccess var1, int var2, int var3, int var4)
    {
        return -1.0F;
    }
    
    //CLIENT ONLY
    
    @Override
    public boolean RenderBlock(RenderBlocks render, int x, int y, int z) {
        render.setRenderBounds(this.GetBlockBoundsFromPoolBasedOnState(render.blockAccess, x, y, z));
    	
        Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(render.blockAccess, x, y, z));
        float var6 = 1.0F;
        int var7 = this.colorMultiplier(render.blockAccess, x, y, z);
        Icon var8 = render.getBlockIconFromSide(this, 0);
        float var9 = (float)(var7 >> 16 & 255) / 255.0F;
        float var10 = (float)(var7 >> 8 & 255) / 255.0F;
        float var11 = (float)(var7 & 255) / 255.0F;
        tess.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
        float var12 = 0.1865F;
        render.renderFaceXPos(this, (double)((float)x - 0.5F + var12), (double)y, (double)z, var8);
        render.renderFaceXNeg(this, (double)((float)x + 0.5F - var12), (double)y, (double)z, var8);
        render.renderFaceZPos(this, (double)x, (double)y, (double)((float)z - 0.5F + var12), var8);
        render.renderFaceZNeg(this, (double)x, (double)y, (double)((float)z + 0.5F - var12), var8);
        
        //Renders the pot's contents
        SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) render.blockAccess.getBlockTileEntity(x, y, z);
       
        if ( hasFillContents(potTile) )
        {
        	Icon contentsIcon = Block.blocksList[potTile.getFillContentsID()].getBlockTextureFromSide(0);
        	render.renderFaceYPos(this, (double)x, (double)((float)y - 0.5F + var12 + 4/16F), (double)z, contentsIcon );
        }
        else render.renderFaceYPos(this, (double)x, (double)((float)y - 0.5F + var12 + 0/16F), (double)z, render.getBlockIcon(FCBetterThanWolves.fcBlockCookedBrick));
       
        
        super.RenderBlock(render, x, y, z);
        
        
		int storedBlockID = potTile.getStoredBlockID();
		int storedBlockMetadata = potTile.getStoredBlockMetadata();
        tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);

    	storedBlockID = potTile.getStoredBlockID();
		storedBlockMetadata = potTile.getStoredBlockMetadata();
	
    
		//Renders other blocks as crossed squares. Double checks validity
		if (potTile.hasItem())
		{
			Block storedBlock = Block.blocksList[storedBlockID];
			Icon icon = render.getBlockIconFromSideAndMetadata(storedBlock, 0, storedBlockMetadata);

			if (storedBlock == FCBetterThanWolves.fcBlockReedRoots)
			{
				icon = SCBlockReedRoots.getIconForWaterPot(storedBlockMetadata);
			}
			
			SCUtilsRender.drawCrossedSquaresFlowerPot(render, storedBlock, storedBlockMetadata, potTile.xCoord, potTile.yCoord + .25, potTile.zCoord, 1.0F, 1, icon);
		}
		else {
			return false;
		}       
        return true;
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return var5 == 0 ? !var1.isBlockOpaqueCube(var2, var3, var4) : true;
    }
}