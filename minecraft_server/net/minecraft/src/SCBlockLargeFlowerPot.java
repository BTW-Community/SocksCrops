package net.minecraft.src;

import java.util.Random;

public class SCBlockLargeFlowerPot extends SCBlockLargeFlowerPotBase {
	public SCBlockLargeFlowerPot(int id) {
		super(id);
        //this.InitBlockBounds(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);
		this.setStepSound(soundPowderFootstep);
		
		this.setUnlocalizedName("SCBlockLargeFlowerPot");
		
		setCreativeTab(CreativeTabs.tabDecorations);
	}

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return super.canPlaceBlockAt(world, x, y, z) && FCUtilsWorld.DoesBlockHaveCenterHardpointToFacing(world, x, y - 1, z, 1);
    }
    
	
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
    {
        if (!FCUtilsWorld.DoesBlockHaveCenterHardpointToFacing(world, x, y - 1, z, 1))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }


    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World world, int x, int y, int z)
    {
    	return SCDefs.largeFlowerPot.blockID;
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World world, int x, int y, int z)
    {
    	SCTileEntityLargeFlowerPot potTile = (SCTileEntityLargeFlowerPot) world.getBlockTileEntity(x, y, z);
    	int blockInPotMeta = potTile.getStoredBlockMetadata();
        return 0;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World world, int x, int y, int z, int var5, int var6)
    {
    	SCTileEntityLargeFlowerPot potTile = (SCTileEntityLargeFlowerPot) world.getBlockTileEntity(x, y, z);
    	
    	for(int slot = 0; slot < 3; slot++)
    	{
        	int blockInPotID = potTile != null ? potTile.getStoredBlockIDInSlot(slot) : 0;

            if (blockInPotID != 0)
            {
            	int blockInPotMeta = potTile.getStoredBlockMetadataInSlot(slot);
                FCUtilsItem.EjectSingleItemWithRandomOffset(world, x, y, z, blockInPotID, blockInPotMeta);
            }
    	}
    	

        
        super.breakBlock(world, x, y, z, var5, var6);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return SCDefs.largeFlowerPot.blockID;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new SCTileEntityLargeFlowerPot();
    }
 
    //When right clicked by a player
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int iFacing, float xClick, float yClick, float zClick) {
    	ItemStack handItemStack = player.getCurrentEquippedItem();
    	SCTileEntityLargeFlowerPot potTile = (SCTileEntityLargeFlowerPot) world.getBlockTileEntity(x, y, z);
		
    	int meta = world.getBlockMetadata(x,y,z);
    	int slot;
		
		if (xClick < 0.35F) slot = 0;
		else if (xClick >= 0.35F && xClick < 0.65F) slot = 1;
		else slot = 2;
		
		if (meta == 1 || meta == 4 || meta == 5)
		{
			if (zClick < 0.35F) slot = 0;
			else if (zClick >= 0.35F && zClick < 0.65F) slot = 1;
			else slot = 2;
		}
		
    	//Empty hand retrieves item
    	if (handItemStack == null && potTile.hasItemInSlot(slot) ) {
    	
    		int storedID = potTile.getStoredBlockIDInSlot(slot);
    		int storedMeta = potTile.getStoredBlockMetadataInSlot(slot);
    		
    		ItemStack stack = new ItemStack(storedID, 1, storedMeta);
    		
    		if (!world.isRemote)
    		{
    			//FCUtilsItem.EjectStackFromBlockTowardsFacing(world, x, y, z, stack, 1);
    			world.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, x, y, z, 0);
    		}
    		
    		potTile.setStoredBlockIDInSlot(slot, 0);
    		potTile.setStoredBlockMetadataInSlot(slot, 0);
    		
    		potTile.setItemInSlot(slot, false);
    		
	   		world.markBlockForRenderUpdate(x, y, z);
    		
    		return true;
 
    	}


    	if (handItemStack != null && !potTile.hasItemInSlot(slot))
    	{
        	int heldID = handItemStack.getItem().itemID;
        	int heldDamage = handItemStack.getItemDamage();
        	//If pot already has an item do nothing
        	if (potTile.hasItemInSlot(slot)) {
        		return false;
        	}
        	//If item is placeable within the pot, place it
        	else if (potTile.isValidItemForPot(heldID, heldDamage) || heldID == FCBetterThanWolves.fcItemMushroomBrown.itemID || heldID == FCBetterThanWolves.fcItemMushroomRed.itemID) {
//        		potTile.placeItemInPot(heldID, heldDamage);
        		
        		potTile.placeItemInPotInSlot(heldID, heldDamage, slot);
        		
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
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
    	int meta = blockAccess.getBlockMetadata(i, j, k);
    	
    	float height = 6/16F;
    	float halfWidth = 3/16F;
    	float halfLength = 7/16F;
    	
    	float centerX = 8/16F;
    	float centerY = 0/16F;
    	float centerZ = 8/16F;
    	
    	//0 bottom 1 top 2 north 3 south 4 west 5 east

    	switch (meta) {
    		default:
    		case 0:
    		case 2:
    		case 3:
    			halfWidth = 3/16F;
            	halfLength = 7/16F;
    			break;
    		case 1:
    		case 4:
    		case 5:
    			halfWidth = 7/16F;
            	halfLength = 3/16F;
    			break;

		}
    	
    	switch (meta) {
		default:
		case 0:
		case 1:
	    	centerX = 8/16F;
	    	centerY = 0/16F;
	    	centerZ = 8/16F;
			break;
		case 2:
	    	centerX = 8/16F;
	    	centerY = 0/16F;
	    	centerZ = 14/16F;
			break;
		case 3:
	    	centerX = 8/16F;
	    	centerY = 0/16F;
	    	centerZ = 2/16F;
			break;
		case 4:
	    	centerX = 14/16F;
	    	centerY = 0/16F;
	    	centerZ = 8/16F;
			break;
		case 5:
	    	centerX = 2/16F;
	    	centerY = 0/16F;
	    	centerZ = 8/16F;
			break;

	}

    	AxisAlignedBB box = new AxisAlignedBB(
    			centerX - halfLength, centerY, centerZ - halfWidth,
    			centerX + halfLength, centerY + height, centerZ + halfWidth
    	);
    	
    	
    	return box;
    }

}