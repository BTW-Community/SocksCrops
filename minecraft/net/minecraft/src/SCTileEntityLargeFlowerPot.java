package net.minecraft.src;

import java.util.ArrayList;

public class SCTileEntityLargeFlowerPot extends TileEntity implements IInventory, FCITileEntityDataPacketHandler{
	
	private static ArrayList<Integer> validItemStacks = new ArrayList<Integer>();
		
	protected ItemStack contents[];
	
	public SCTileEntityLargeFlowerPot() {
		contents = new ItemStack[getSizeInventory()];
	}

	static {
		validItemStacks.add(Block.plantRed.blockID);
		validItemStacks.add(Block.plantYellow.blockID);
		validItemStacks.add(FCBetterThanWolves.fcItemMushroomBrown.itemID);
		validItemStacks.add(FCBetterThanWolves.fcItemMushroomRed.itemID);
		validItemStacks.add(Block.cactus.blockID);
		validItemStacks.add(Block.sapling.blockID);
		validItemStacks.add(Block.tallGrass.blockID);
		validItemStacks.add(FCBetterThanWolves.fcAestheticVegetation.blockID);
		
		validItemStacks.add(SCDefs.doubleTallGrass.blockID);
		validItemStacks.add(SCDefs.fruitTreeSapling.blockID);
		
//		validItemList.add(DecoDefs.flower.blockID);
//		validItemList.add(DecoDefs.flower2.blockID);
//		validItemList.add(DecoDefs.tulip.blockID);
//		validItemList.add(DecoDefs.cherrySapling.blockID);
	}
	
    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound tag)
    {    	
        super.writeToNBT(tag);
        
        NBTTagList nbttaglist = new NBTTagList();
        
        for ( int i = 0; i < contents.length; i++ )
        {
            if ( contents[i] != null )
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte( "Slot", (byte)i );
                
                contents[i].writeToNBT( nbttagcompound1 );
                
                nbttaglist.appendTag( nbttagcompound1 );
            }
        }     

        tag.setTag( "Items", nbttaglist );

    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        
        NBTTagList nbttaglist = tag.getTagList("Items");
        
        contents = new ItemStack[getSizeInventory()];
        
        for ( int i = 0; i < nbttaglist.tagCount(); i++ )
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt( i );
            
            int j = nbttagcompound1.getByte( "Slot" ) & 0xff;
            
            if ( j >= 0 && j < contents.length )
            {
            	contents[j] = ItemStack.loadItemStackFromNBT( nbttagcompound1 );
            }
        }
        // force a visual update for the block since the above variables affect it
        
        //worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
    }

    /**
     * Overriden in a sign to provide the text.
     */
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList nbttaglist = new NBTTagList();
        
        for ( int i = 0; i < contents.length; i++ )
        {
            if ( contents[i] != null )
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte( "Slot", (byte)i );
                
                contents[i].writeToNBT( nbttagcompound1 );
                
                nbttaglist.appendTag( nbttagcompound1 );
            }
        }     

        tag.setTag( "Items", nbttaglist );
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }
    

	@Override
	public void readNBTFromPacket(NBTTagCompound tag) {
		NBTTagList nbttaglist = tag.getTagList("Items");
        
        contents = new ItemStack[getSizeInventory()];
        
        for ( int i = 0; i < nbttaglist.tagCount(); i++ )
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt( i );
            
            int j = nbttagcompound1.getByte( "Slot" ) & 0xff;
            
            if ( j >= 0 && j < contents.length )
            {
            	contents[j] = ItemStack.loadItemStackFromNBT( nbttagcompound1 );
            }
        }
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );
	}
        
	public boolean isValidItemForPot(int id, int meta) {
		//Special case for blood wood sapling
		if (id == FCBetterThanWolves.fcAestheticVegetation.blockID)
			return meta == 2;
		
		return validItemStacks.contains(id);
	}

//	public void placeItemInPot(int itemID, int metadata) {
//		hasItem = true;
//		
//		if (itemID == FCBetterThanWolves.fcItemMushroomBrown.itemID)
//			currentBlockID = Block.mushroomBrown.blockID;
//		else if (itemID == FCBetterThanWolves.fcItemMushroomRed.itemID)
//			currentBlockID = Block.mushroomRed.blockID;
//		else
//			currentBlockID = itemID;
//		currentBlockMetadata = metadata;
//		
//        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
//        
//        this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "step.grass", 1.0F, 1.0F);
//	}
	
//	public void placeItemInPotInSlot(int itemID, int metadata, int slot) {
//		containsItem[slot] = true;
//		
//		if (itemID == FCBetterThanWolves.fcItemMushroomBrown.itemID)
//			currentBlockIDs[slot] = Block.mushroomBrown.blockID;
//		else if (itemID == FCBetterThanWolves.fcItemMushroomRed.itemID)
//			currentBlockIDs[slot] = Block.mushroomRed.blockID;
//		else
//			currentBlockIDs[slot] = itemID;
//		    currentBlockMetadatas[slot] = metadata;
//		
//        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
//        
//        this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "step.grass", 1.0F, 1.0F);
//	}

	//Removes item and gives it to the player
//	public void retrieveItemFromPot(EntityPlayer player) {
//		if (currentBlockID == 0)
//			return;
//		
//		if (!player.capabilities.isCreativeMode )
//            FCUtilsItem.GivePlayerStackOrEjectFavorEmptyHand(player, new ItemStack(Item.itemsList[currentBlockID], 1, currentBlockMetadata), this.xCoord, this.yCoord, this.zCoord);
//		
//		hasItem = false;
//		currentBlockID = 0;
//		
//        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
//
//        if (!this.worldObj.isRemote)
//        	this.worldObj.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, this.xCoord, this.yCoord, this.zCoord, 0);
//	}
	
//	public void retrieveItemFromPotFromSlot(EntityPlayer player, int slot) {
//		if (currentBlockIDs[slot] == 0)
//			return;
//		
//		if (!this.worldObj.isRemote)
//		{
//			FCUtilsItem.EjectStackFromBlockTowardsFacing(worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(Item.itemsList[currentBlockIDs[slot]], 1, currentBlockMetadatas[slot]), 1);
//			this.worldObj.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, this.xCoord, this.yCoord, this.zCoord, 0);
//		}
//		
//		containsItem[slot] = false;
//		currentBlockIDs[slot] = 0;
//		currentBlockMetadatas[slot] = 0;
//		
//        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);        	
//	}

	// --- IInventory --- //
	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return contents[slot];		
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {		
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		contents[var1] = new ItemStack(var2.itemID, 1, var2.getItemDamage());
	}

	@Override
	public String getInvName() {
		return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return false;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack var2) {
		return contents[slot] == null && validItemStacks.contains(var2.itemID);
	}

}
