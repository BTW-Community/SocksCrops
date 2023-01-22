package net.minecraft.src;

import java.util.HashSet;
import java.util.Set;

public class SCTileEntityLargeFlowerPot extends TileEntity implements FCITileEntityDataPacketHandler {
	
	private boolean[] containsItem = {false, false, false}; 
	
	private boolean hasItem = false;
	private static Set<Integer> validItemList = new HashSet<Integer>();
	
	private int currentBlockID;
	private int currentBlockMetadata;
	
	private int[] currentBlockIDs = {0, 0, 0};
	private int[] currentBlockMetadatas = {0, 0, 0};
	

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setInteger("StoredID", this.currentBlockID);
        tag.setInteger("StoredMetadata", this.currentBlockMetadata);
        
        tag.setIntArray("StoredIDs", currentBlockIDs);
        tag.setIntArray("StoredMetadatas", currentBlockMetadatas);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        this.currentBlockID = tag.getInteger("StoredID");
        this.currentBlockMetadata = tag.getInteger("StoredMetadata");
        
        currentBlockIDs = tag.getIntArray("StoredIDs");
        currentBlockMetadatas = tag.getIntArray("StoredMetadatas");
    }

    /**
     * Overriden in a sign to provide the text.
     */
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("StoredID", this.currentBlockID);
        tag.setInteger("StoredMetadata", this.currentBlockMetadata);
        
        tag.setIntArray("StoredIDs", currentBlockIDs);
        tag.setIntArray("StoredMetadatas", currentBlockMetadatas);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }
    
    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        this.currentBlockID = tag.getInteger("StoredID");
        this.currentBlockMetadata = tag.getInteger("StoredMetadata");
        
        currentBlockIDs = tag.getIntArray("StoredIDs");
        currentBlockMetadatas = tag.getIntArray("StoredMetadatas");
        
        if (currentBlockID != 0) hasItem = true;
        
        for (int i = 0; i < 3; i++) {
			if (currentBlockIDs[i] != 0) containsItem[i] = true;
			
		}
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }
    
    public int getStoredBlockID() {
    	return currentBlockID;
    }
    
    public int getStoredBlockMetadata() {
    	return currentBlockMetadata;
    }

	public boolean hasItem() {
		return hasItem;
	}    
	
	public int getStoredBlockIDInSlot(int slot) {
    	return currentBlockIDs[slot];
    }
    
    public int getStoredBlockMetadataInSlot(int slot) {
    	return currentBlockMetadatas[slot];
    }
    
	public void setStoredBlockIDInSlot(int slot, int id) {
		currentBlockIDs[slot] = id;
    }
    
    public void setStoredBlockMetadataInSlot(int slot, int meta) {
    	currentBlockMetadatas[slot] = meta;
    }

	public boolean hasItemInSlot(int slot) {
		return containsItem[slot];
	}
	
	public void setItemInSlot(int slot, boolean boo) {
		containsItem[slot] = boo;
	}

	public boolean isValidItemForPot(int id, int meta) {
		//Special case for blood wood sapling
		if (id == FCBetterThanWolves.fcAestheticVegetation.blockID)
			return meta == 2;
		
		return validItemList.contains(id);
	}

	public void placeItemInPot(int itemID, int metadata) {
		hasItem = true;
		
		if (itemID == FCBetterThanWolves.fcItemMushroomBrown.itemID)
			currentBlockID = Block.mushroomBrown.blockID;
		else if (itemID == FCBetterThanWolves.fcItemMushroomRed.itemID)
			currentBlockID = Block.mushroomRed.blockID;
		else
			currentBlockID = itemID;
		currentBlockMetadata = metadata;
		
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
        
        this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "step.grass", 1.0F, 1.0F);
	}
	
	public void placeItemInPotInSlot(int itemID, int metadata, int slot) {
		containsItem[slot] = true;
		
		if (itemID == FCBetterThanWolves.fcItemMushroomBrown.itemID)
			currentBlockIDs[slot] = Block.mushroomBrown.blockID;
		else if (itemID == FCBetterThanWolves.fcItemMushroomRed.itemID)
			currentBlockIDs[slot] = Block.mushroomRed.blockID;
		else
			currentBlockIDs[slot] = itemID;
		    currentBlockMetadatas[slot] = metadata;
		
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
        
        this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "step.grass", 1.0F, 1.0F);
	}

	//Removes item and gives it to the player
	public void retrieveItemFromPot(EntityPlayer player) {
		if (currentBlockID == 0)
			return;
		
		if (!player.capabilities.isCreativeMode )
            FCUtilsItem.GivePlayerStackOrEjectFavorEmptyHand(player, new ItemStack(Item.itemsList[currentBlockID], 1, currentBlockMetadata), this.xCoord, this.yCoord, this.zCoord);
		
		hasItem = false;
		currentBlockID = 0;
		
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);

        if (!this.worldObj.isRemote)
        	this.worldObj.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, this.xCoord, this.yCoord, this.zCoord, 0);
	}
	
	public void retrieveItemFromPotFromSlot(EntityPlayer player, int slot) {
		if (currentBlockIDs[slot] == 0)
			return;
		
		if (!this.worldObj.isRemote)
		{
			FCUtilsItem.EjectStackFromBlockTowardsFacing(worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(Item.itemsList[currentBlockIDs[slot]], 1, currentBlockMetadatas[slot]), 1);
			this.worldObj.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, this.xCoord, this.yCoord, this.zCoord, 0);
		}
		
		containsItem[slot] = false;
		currentBlockIDs[slot] = 0;
		currentBlockMetadatas[slot] = 0;
		
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);        	
	}

	static {
		validItemList.add(Block.plantRed.blockID);
		validItemList.add(Block.plantYellow.blockID);
		validItemList.add(Block.mushroomBrown.blockID);
		validItemList.add(Block.mushroomRed.blockID);
		validItemList.add(Block.cactus.blockID);
		validItemList.add(Block.sapling.blockID);
		validItemList.add(Block.tallGrass.blockID);
		validItemList.add(FCBetterThanWolves.fcAestheticVegetation.blockID);
		
		validItemList.add(SCDefs.doubleTallGrass.blockID);
		validItemList.add(SCDefs.fruitTreeSapling.blockID);
		
//		validItemList.add(DecoDefs.flower.blockID);
//		validItemList.add(DecoDefs.flower2.blockID);
//		validItemList.add(DecoDefs.tulip.blockID);
//		validItemList.add(DecoDefs.cherrySapling.blockID);
	}
}
