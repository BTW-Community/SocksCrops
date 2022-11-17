//Based on: https://github.com/BTW-Community/Deco-Addon-2/blob/master/minecraft/net/minecraft/src/DecoBlockFlowerPot.java

package net.minecraft.src;

import java.util.Random;

import org.lwjgl.opengl.GL11;

public class SCBlockMultiFlowerPot extends BlockContainer {
	
	public static int empty = 0;
	public static int water = 8;
	
	public SCBlockMultiFlowerPot(int id) {
		super(id, Material.circuits);
        this.InitBlockBounds(0.3125D - 4/16F, 0.0D, 0.3125D, 0.6875D + 4/16F, 0.375D, 0.6875D);
		this.setHardness(0.0F);
		this.setStepSound(soundPowderFootstep);
		this.setUnlocalizedName("flowerPot");
		//this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
//	@Override
//	public void updateTick(World world, int x, int y, int z, Random rand)
//	{
//		SCTileEntityMultiFlowerPot potTile = (SCTileEntityMultiFlowerPot) world.getBlockTileEntity(x, y, z);
//		int insertedItemID = potTile.getInsertedItem();
//		int storedMetadata = potTile.getCenterStoredBlockMetadata();
//		int meta = world.getBlockMetadata(x, y, z);
//		
//		if (UpdateIfBlockStays(world, x, y, z))
//		{
//			if (!hasWater(meta)) {
//				return; // Don't do anything unless it has water
//			}
//			
//			if (world.GetBlockNaturalLightValue(x, y + 1, z) >= getLightLevelForGrowth() )
//			{
//				float random = rand.nextFloat();
//				System.out.println( random );
//				System.out.println( getGrowthChance(world, x, y, z) );
//				
//				if (potTile.hasItem() && random <= getGrowthChance(world, x, y, z) )
//				{
//					grow(world, x, y, z, potTile, meta, insertedItemID);
//				}
//			}
//		}
//	}
	
	private int getLightLevelForGrowth() {
		return 11;
	}

	private void grow(World world, int x, int y, int z, SCTileEntityMultiFlowerPot potTile, int meta, int insertedItemID)
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
			potTile.setCenterStoredBlockMetadata(potTile.getCenterStoredBlockMetadata() + i);

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
		SCTileEntityMultiFlowerPot potTile = (SCTileEntityMultiFlowerPot) world.getBlockTileEntity(x, y, z);
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
    	SCTileEntityMultiFlowerPot potTile = (SCTileEntityMultiFlowerPot) world.getBlockTileEntity(x, y, z);
    	int potMeta = world.getBlockMetadata(x, y, z);
    	
    	int slot = 1;
    	
    	System.out.println("x: " + fXClick);
    	System.out.println("z: " + fZClick);
    	
    	if (fXClick < 0.3F) slot = 0; 
    	else if (fXClick > 0.7) slot = 2;
    	
    	System.out.println("Slot: " + slot);
    	
    	//Empty hand retrieves item
    	if (handItemStack == null)
    	{
    		System.out.println("hasItemInSlot: " + potTile.hasItemInSlot(slot));
    		
    		if (potTile.hasItemInSlot(slot) )
    		{
        		potTile.retrieveItemFromPot(player, slot);
        		
        		if ( hasWater(potMeta) )
        		{
        			world.setBlockMetadataWithNotify(x, y, z, water);
        		}
        		else world.setBlockMetadataWithNotify(x, y, z, empty);
        		
        		return true;
    		}
    		else return false;
    	}
    	
    	int heldID = handItemStack.getItem().itemID;
    	int heldDamage = handItemStack.getItemDamage();
    	
    	if (heldID == Item.potion.itemID && heldDamage == 0 && !hasWater(potMeta))
		{
			world.setBlockMetadataWithNotify(x, y, z, potMeta + 8);
			
			if (handItemStack.stackSize > 1)
			{
				--handItemStack.stackSize;
			}
			else player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
			
			player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle, 1));
			
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
		}
    	
    	return placeItemInSlot(player, potTile, handItemStack, heldID, heldDamage, slot);
    	
    }

	private boolean placeItemInSlot(EntityPlayer player, SCTileEntityMultiFlowerPot potTile, ItemStack handItemStack, int heldID, int heldDamage, int slot) {
    	
		System.out.println("hasItemInSlot: " + potTile.hasItemInSlot(slot));
		//If pot already has an item do nothing
    	if (potTile.hasItemInSlot(slot)) {
    		return false;
    	}
    	//If item is placeable within the pot, place it
    	else if ( potTile.isValidToStore(heldID, heldDamage) )
    	{
    		potTile.placeItemInPot(heldID, heldDamage, slot);
    		
    		//Decrements stack, unless in creative
    		if (!player.capabilities.isCreativeMode && --handItemStack.stackSize <= 0)
            {
    			player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    		
    		return true;
    	}
    	
		return false;
	}

	private boolean hasWater(int meta) {
		return meta > 7;

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
    	SCTileEntityMultiFlowerPot potTile = (SCTileEntityMultiFlowerPot) world.getBlockTileEntity(x, y, z);
		int blockInPotID = potTile != null ? potTile.getCenterStoredBlockID() : 0;
		
        if ( !canBlockStay( world, x, y, z ) )
        {
            dropBlockAsItem( world, x, y, z, world.getBlockMetadata( x, y, z ), 0 );
            
            if (blockInPotID != 0)
            {
            	int slot = 1;
            	potTile.ejectItemFromPot(world, x, y, z, slot);            	
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
    	SCTileEntityMultiFlowerPot potTile = (SCTileEntityMultiFlowerPot) world.getBlockTileEntity(x, y, z);
		int blockInPotID = potTile != null ? potTile.getCenterStoredBlockID() : 0;
		
        if (!FCUtilsWorld.DoesBlockHaveCenterHardpointToFacing(world, x, y - 1, z, 1))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
           
            if (blockInPotID != 0)
            {
            	int slot = 1;
            	potTile.ejectItemFromPot(world, x, y, z, slot);
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
//    	SCTileEntityMultiFlowerPot potTile = (SCTileEntityMultiFlowerPot) world.getBlockTileEntity(x, y, z);
//    	int blockInPotID = potTile.getCenterStoredBlockID();
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
    	SCTileEntityMultiFlowerPot potTile = (SCTileEntityMultiFlowerPot) world.getBlockTileEntity(x, y, z);
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
    	SCTileEntityMultiFlowerPot potTile = (SCTileEntityMultiFlowerPot) world.getBlockTileEntity(x, y, z);
    	int blockInPotID = potTile != null ? potTile.getCenterStoredBlockID() : 0;
    	int blockInPotMeta = potTile.getCenterStoredBlockMetadata();
    	
        if (blockInPotID != 0)
        {
        	int slot = 1;
        	potTile.ejectItemFromPot(world, x, y, z, slot);
        	
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
        return new SCTileEntityMultiFlowerPot();
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
        Icon var8 =  render.getBlockIcon(FCBetterThanWolves.fcBlockCookedBrick);
        float var9 = (float)(var7 >> 16 & 255) / 255.0F;
        float var10 = (float)(var7 >> 8 & 255) / 255.0F;
        float var11 = (float)(var7 & 255) / 255.0F;
        tess.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
        float var12 = 0.1865F + 4/16F;
        render.renderFaceXPos(this, (double)((float)x - 0.5F + var12), (double)y, (double)z, var8);
        render.renderFaceXNeg(this, (double)((float)x + 0.5F - var12), (double)y, (double)z, var8);
        render.renderFaceZPos(this, (double)x, (double)y, (double)((float)z - 0.5F + var12), var8);
        render.renderFaceZNeg(this, (double)x, (double)y, (double)((float)z + 0.5F - var12), var8);
        
        if ( hasWater(render.blockAccess.getBlockMetadata(x, y, z)) )
        {
        	 render.renderFaceYPos(this, (double)x, (double)((float)y - 0.5F + var12 + 4/16F), (double)z, render.getBlockIcon(Block.dirt));
        }
        else render.renderFaceYPos(this, (double)x, (double)((float)y - 0.5F + var12 + 0/16F), (double)z, render.getBlockIcon(FCBetterThanWolves.fcBlockCookedBrick));
       
        
        super.RenderBlock(render, x, y, z);
        
        //Renders the pot's contents
        SCTileEntityMultiFlowerPot potTile = (SCTileEntityMultiFlowerPot) render.blockAccess.getBlockTileEntity(x, y, z);
		int storedBlockID = potTile.getCenterStoredBlockID();
		int storedBlockMetadata = potTile.getCenterStoredBlockMetadata();
		int storedLeftBlockID = potTile.getLeftStoredBlockID();
		int storedLeftBlockMetadata = potTile.getLeftStoredBlockMetadata();
		int storedRightBlockID = potTile.getRightStoredBlockID();
		int storedRightBlockMetadata = potTile.getRightStoredBlockMetadata();
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		
		
		for (int slot = 0; slot < 3; slot++)
		{
			//Renders other blocks as crossed squares. Double checks validity
			if (potTile.hasItemInSlot(slot))
			{
				Block storedBlock = Block.blocksList[storedBlockID];
				if (slot == 0 ) storedBlock = Block.blocksList[storedLeftBlockID];
				else if (slot == 2) storedBlock = Block.blocksList[storedRightBlockID];
				
				Icon icon = render.getBlockIconFromSideAndMetadata(storedBlock, 0, storedBlockMetadata);
				if (slot == 0 ) icon = render.getBlockIconFromSideAndMetadata(storedBlock, 0, storedLeftBlockMetadata);
				else if (slot == 2) icon = render.getBlockIconFromSideAndMetadata(storedBlock, 0, storedRightBlockMetadata);
				
				
				if (storedBlock == FCBetterThanWolves.fcBlockReedRoots)
				{
					icon = SCBlockReedRoots.getIconForWaterPot(storedBlockMetadata);
					if (slot == 0 ) icon = SCBlockReedRoots.getIconForWaterPot(storedLeftBlockMetadata);
					else if (slot == 2) icon = SCBlockReedRoots.getIconForWaterPot(storedRightBlockMetadata);
				}
				
				if (slot == 0 ) storedBlockMetadata = storedLeftBlockMetadata;
				else if (slot == 2) storedBlockMetadata = storedRightBlockMetadata;
				
				float shift = 0F;
				if (slot == 0 ) shift = -8/16F;
				else if (slot == 2) shift = +8/16F;
				SCUtilsRender.drawCrossedSquaresFlowerPot(render, storedBlock, storedBlockMetadata, potTile.xCoord + shift, potTile.yCoord + .25, potTile.zCoord, 1.0F, 1, icon);
				
			}
			else {
				return false;
			} 
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