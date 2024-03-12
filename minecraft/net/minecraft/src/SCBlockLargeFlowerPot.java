package net.minecraft.src;

import java.util.Random;

import org.lwjgl.opengl.GL11;

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
            FCUtilsInventory.EjectInventoryContents( world, x, y, z, (IInventory)world.getBlockTileEntity(x, y, z) );

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
    	//SCTileEntityLargeFlowerPot potTile = (SCTileEntityLargeFlowerPot) world.getBlockTileEntity(x, y, z);
    	//int blockInPotMeta = potTile.getStoredBlockMetadata();
        return 0;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World world, int x, int y, int z, int var5, int var6)
    {
    	
    	FCUtilsInventory.EjectInventoryContents( world, x, y, z, (IInventory)world.getBlockTileEntity(x, y, z) );
    	
//    	SCTileEntityLargeFlowerPot potTile = (SCTileEntityLargeFlowerPot) world.getBlockTileEntity(x, y, z);
//    	for(int slot = 0; slot < 3; slot++)
//    	{
//        	int blockInPotID = potTile != null ? potTile.getStoredBlockIDInSlot(slot) : 0;
//
//            if (blockInPotID != 0)
//            {
//            	int blockInPotMeta = potTile.getStoredBlockMetadataInSlot(slot);
//                FCUtilsItem.EjectSingleItemWithRandomOffset(world, x, y, z, blockInPotID, blockInPotMeta);
//            }
//    	}
//    	
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
		//Problems with ejecting the items from the pot
//    	if (handItemStack == null && potTile.hasItemInSlot(slot) ) {
//    	
//    		int storedID = potTile.getStoredBlockIDInSlot(slot);
//    		int storedMeta = potTile.getStoredBlockMetadataInSlot(slot);
//    		
//    		ItemStack stack = new ItemStack(storedID, 1, storedMeta);
//    		
//    		FCUtilsItem.GivePlayerStackOrEjectFromTowardsFacing(player, stack, x, y, z, iFacing);
//    		if (!world.isRemote) world.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, x, y, z, 0);
//
//    		potTile.setStoredBlockIDInSlot(slot, 0);
//    		potTile.setStoredBlockMetadataInSlot(slot, 0);
//    		
//    		potTile.setItemInSlot(slot, false);
//    		
//	   		world.markBlockForRenderUpdate(x, y, z);
//    		
//    		return true;
// 
//    	}
		
    	//If pot already has an item do nothing
    	if (potTile.getStackInSlot(slot) != null) {
    		return false;
    	}

    	if (handItemStack != null)
    	{
        	int heldID = handItemStack.getItem().itemID;
        	int heldDamage = handItemStack.getItemDamage();

        	//If item is placeable within the pot, place it
        	if (potTile.isValidItemForPot(heldID, heldDamage)) {
        		
        		potTile.setInventorySlotContents(slot, handItemStack);
        		
        		//Decrements stack, unless in creative
//        		if (!player.capabilities.isCreativeMode && --handItemStack.stackSize <= 0)
        		if (--handItemStack.stackSize <= 0)
                {
        			player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                }
        		
        		world.markBlockForRenderUpdate(x, y, z);
        		
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
    
    @Override
    public boolean RenderBlock(RenderBlocks render, int x, int y, int z) {
    	int meta = render.blockAccess.getBlockMetadata(x, y, z);
    	
    	super.RenderBlock(render, x,y,z);
        
        Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(render.blockAccess, x, y, z));
        float var6 = 1.0F;
        int var7 = this.colorMultiplier(render.blockAccess, x, y, z);
        Icon var8 = render.getBlockIconFromSide(this, 2);
        float var9 = (float)(var7 >> 16 & 255) / 255.0F;
        float var10 = (float)(var7 >> 8 & 255) / 255.0F;
        float var11 = (float)(var7 & 255) / 255.0F;
        tess.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
        

        //Renders the pot's contents
        SCTileEntityLargeFlowerPot potTile = (SCTileEntityLargeFlowerPot) render.blockAccess.getBlockTileEntity(x, y, z);
		
        for (int slot = 0; slot < 3; slot++)
        {
        	//int storedBlockID = potTile.getStoredBlockIDInSlot(slot);
        	//int storedBlockMetadata = potTile.getStoredBlockMetadataInSlot(slot);
        	tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        	
        	if (potTile.getStackInSlot(slot) != null)
        	{
            	//Cactus renders differently than everything else
            	if (potTile.getStackInSlot(slot).itemID == Block.cactus.blockID) 
            	{
            		renderCactus(render, x, y, z, meta, slot);
            	}
            	//Renders other blocks as crossed squares. Double checks validity
            	else if (potTile.isValidItemForPot(potTile.getStackInSlot(slot).itemID, 0))
            	{
            		renderFlowers(render, meta, potTile, slot, potTile.getStackInSlot(slot).itemID, potTile.getStackInSlot(slot).getItemDamage());
            	}
        	}        	

        }
        
        return true;
    }

	private void renderCactus(RenderBlocks render, int x, int y, int z, int meta, int slot) {
		render.SetRenderAllFaces(true);
		
		float minX = 6/16F + 1/32F;
		float maxX = 10/16F - 1/32F;
		
		float minY = 4/16F;
		float maxY = 16/16F;
		
		float minZ = 6/16F + 1/32F;
		float maxZ = 10/16F - 1/32F;
		
		if (slot == 0)
		{
			maxY = 14/16F;
		}
		else if (slot == 1)
		{
			maxY = 16/16F;
		}
		else if (slot == 2)
		{
			maxY = 15/16F;
		}
		
		double xShift = 0D;
		double zShift = 0D;
		

		if (meta == 0)
		{
			if (slot == 0)
			{
				xShift = -4/16D;
				zShift = 0;
			}
			else if (slot == 1)
			{
		  		xShift = 0;
				zShift = 0;
			}
			else if (slot == 2)
			{
		  		xShift = 4/16D;
				zShift = 0;
			}
		}
		else if (meta == 1)
		{
			if (slot == 0)
			{
				xShift = 0;
				zShift = -4/16D;
			}
			else if (slot == 1)
			{
		  		xShift = 0;
				zShift = 0;
			}
			else if (slot == 2)
			{
		  		xShift = 0;
				zShift = 4/16D;
			}
		}
		else if (meta == 2)
		{
			if (slot == 0)
			{
				xShift = -4/16D;
				zShift = +5/16D;
			}
			else if (slot == 1)
			{
		  		xShift = 0;
				zShift = +5/16D;
			}
			else if (slot == 2)
			{
		  		xShift = 4/16D;
				zShift = 5/16D;
			}
		}
		else if (meta == 3)
		{
			if (slot == 0)
			{
				xShift = -4/16D;
				zShift = -5/16D;
			}
			else if (slot == 1)
			{
		  		xShift = 0;
				zShift = -5/16D;
			}
			else if (slot == 2)
			{
		  		xShift = 4/16D;
				zShift = -5/16D;
			}
		}
		else if (meta == 4)
		{
			if (slot == 0)
			{
				xShift = 5/16D;
				zShift = -4/16D;
			}
			else if (slot == 1)
			{
		  		xShift = 5/15D;
				zShift = 0;
			}
			else if (slot == 2)
			{
		  		xShift = 5/16D;
				zShift = 4/16D;
			}
		}
		else if (meta == 5)
		{
			if (slot == 0)
			{
				xShift = -5/16D;
				zShift = -4/16D;
			}
			else if (slot == 1)
			{
		  		xShift = -5/15D;
				zShift = 0;
			}
			else if (slot == 2)
			{
		  		xShift = -5/16D;
				zShift = 4/16D;
			}
		}
		
		render.setRenderBounds(minX + xShift, minY, minZ + zShift, maxX + xShift, maxY, maxZ + zShift);
		render.renderStandardBlock(Block.cactus, x, y, z);

		render.SetRenderAllFaces(false);
		render.setRenderBounds(0, 0, 0, 1, 1, 1);
	}

	private void renderFlowers(RenderBlocks render, int meta, SCTileEntityLargeFlowerPot potTile, int slot, int storedBlockID, int storedBlockMetadata)
	{
		Block storedBlock;
			
		if (storedBlockID == FCBetterThanWolves.fcItemMushroomRed.itemID)
		{
			storedBlock = Block.mushroomRed;
		}
		else if (storedBlockID == FCBetterThanWolves.fcItemMushroomBrown.itemID)
		{
			storedBlock = Block.mushroomBrown;
		}
		else {
			storedBlock = Block.blocksList[storedBlockID];
		}
		
//		System.out.println("id: " + storedBlockID);
//		System.out.println("storedBlock: " + storedBlock);
				
		double xPos = potTile.xCoord;
		double yPos = potTile.yCoord + 4/16D;
		double zPos = potTile.zCoord;
		
		if (meta == 0)
		{
			if (slot == 0)
			{
				xPos = potTile.xCoord  - 4/16D;
			}
			else if (slot == 2)
			{
				xPos = potTile.xCoord + 4/16D;
			}
		}		
		else if (meta == 1)
		{
			if (slot == 0)
			{
				zPos = potTile.zCoord - 4/16D;
			}
			else if (slot == 2)
			{
				zPos = potTile.zCoord + 4/16D;
			}
		}
		else if (meta == 2)
		{
			if (slot == 0)
			{
				xPos = potTile.xCoord  - 4/16D;
				zPos = potTile.zCoord  + 5/16D;
			}
			else if (slot == 1)
			{
				xPos = potTile.xCoord;
				zPos = potTile.zCoord  + 5/16D;
			}
			else if (slot == 2)
			{
				xPos = potTile.xCoord + 4/16D;
				zPos = potTile.zCoord  + 5/16D;
			}
		}
		else if (meta == 3)
		{
			if (slot == 0)
			{
				xPos = potTile.xCoord  - 4/16D;
				zPos = potTile.zCoord  - 5/16D;
			}
			else if (slot == 1)
			{
				xPos = potTile.xCoord;
				zPos = potTile.zCoord  - 5/16D;
			}
			else if (slot == 2)
			{
				xPos = potTile.xCoord + 4/16D;
				zPos = potTile.zCoord  - 5/16D;
			}
		}
		else if (meta == 4)
		{
			if (slot == 0)
			{
				xPos = potTile.xCoord  + 5/16D;
				zPos = potTile.zCoord  - 4/16D;
			}
			else if (slot == 1)
			{
				xPos = potTile.xCoord  + 5/16D;
				zPos = potTile.zCoord;
			}
			else if (slot == 2)
			{
				xPos = potTile.xCoord + 5/16D;
				zPos = potTile.zCoord  + 4/16D;
			}
		}
		else if (meta == 5)
		{
			if (slot == 0)
			{
				xPos = potTile.xCoord  - 5/16D;
				zPos = potTile.zCoord  - 4/16D;
			}
			else if (slot == 1)
			{
				xPos = potTile.xCoord  - 5/16D;
				zPos = potTile.zCoord;
			}
			else if (slot == 2)
			{
				xPos = potTile.xCoord - 5/16D;
				zPos = potTile.zCoord  + 4/16D;
			}
		}
		            		
		SCUtilsRender.renderCrossedSquaresFlowers(render, storedBlock, storedBlockMetadata, xPos, yPos, zPos, 14/16F, 1, slot);
	}

//    /**
//     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
//     * coordinates.  Args: blockAccess, x, y, z, side
//     */
//    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5)
//    {
//        return var5 == 0 ? !var1.isBlockOpaqueCube(var2, var3, var4) : true;
//    }
}