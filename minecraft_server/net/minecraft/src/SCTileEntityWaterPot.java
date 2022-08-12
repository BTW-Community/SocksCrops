//Based on: https://github.com/BTW-Community/Deco-Addon-2/blob/master/minecraft/net/minecraft/src/DecoTileEntityFlowerPot.java

package net.minecraft.src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SCTileEntityWaterPot extends TileEntity implements FCITileEntityDataPacketHandler {
	private boolean hasItem = false;
	//private static Set<Integer> validBlockList = new HashSet<Integer>();
	private static Set<Integer> validItemList = new HashSet<Integer>();
	private static HashMap<Integer, Integer> validBlockList = new HashMap<Integer, Integer>();
	
	private int currentBlockID;
	private int currentBlockMetadata;

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("StoredID", this.currentBlockID);
        nbtTagCompound.setInteger("StoredMetadata", this.currentBlockMetadata);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        this.currentBlockID = nbtTagCompound.getInteger("StoredID");
        this.currentBlockMetadata = nbtTagCompound.getInteger("StoredMetadata");
    }

    /**
     * Overriden in a sign to provide the text.
     */
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("StoredID", this.currentBlockID);
        nbtTagCompound.setInteger("StoredMetadata", this.currentBlockMetadata);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
    }
    
    @Override
    public void readNBTFromPacket(NBTTagCompound nbtTagCompound) {
        this.currentBlockID = nbtTagCompound.getInteger("StoredID");
        this.currentBlockMetadata = nbtTagCompound.getInteger("StoredMetadata");
        if (currentBlockID != 0) hasItem = true;
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }
    
    public void markBlockForRender() {
    	this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
	}
    
    public int getStoredBlockID() {
    	return currentBlockID;
    }
    
    public int getStoredBlockMetadata() {
    	return currentBlockMetadata;
    }
    
    public void setStoredBlockMetadata(int meta) {
    	currentBlockMetadata = meta;
    }

	public boolean hasItem() {
		return hasItem;
	}

	public boolean isValidBlockForPot(int id, int meta) {
		//Special case for blood wood sapling
		if (id == FCBetterThanWolves.fcAestheticVegetation.blockID)
			return meta == 2;
		
		return validBlockList.containsKey(id);
	}

	public boolean isValidItemForPot(int id, int meta) {

		return validItemList.contains(id);
	}

	public void placeItemInPot(int itemID, int metadata) {
		hasItem = true;
		
		if (itemID == SCDefs.bambooItem.itemID)
			currentBlockID = SCDefs.bambooShoot.blockID;
		else if (itemID == SCDefs.wildPotatoCut.itemID)
			currentBlockID = SCDefs.wildPotatoCropSapling.blockID;
		else if (itemID == SCDefs.wildCarrotTop.itemID)
			currentBlockID = SCDefs.wildCarrotCropSapling.blockID;
		else
			currentBlockID = itemID;
		currentBlockMetadata = metadata;
		
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
        
        this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "step.grass", 1.0F, 1.0F);
	}

	//Removes item and gives it to the player
	public void retrieveItemFromPot(EntityPlayer player) {
		
		if (currentBlockID == 0)
			return;
		
		if (currentBlockID == SCDefs.bambooShoot.blockID)
		{
			if(!worldObj.isRemote)
				FCUtilsItem.DropStackAsIfBlockHarvested(worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(SCDefs.bambooItem, 1, 0));
		}
		else if (currentBlockID == SCDefs.wildPotatoCropSapling.blockID)
		{
			if(!worldObj.isRemote)
				FCUtilsItem.DropStackAsIfBlockHarvested(worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(SCDefs.wildPotatoCropSapling, 1, 0));
		}
		else if (currentBlockID == SCDefs.wildCarrotCropSapling.blockID)
		{
			if(!worldObj.isRemote)
				FCUtilsItem.DropStackAsIfBlockHarvested(worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(SCDefs.wildCarrotCropSapling, 1, 0));
		}
		else 
		{
			if(!worldObj.isRemote)
				FCUtilsItem.DropStackAsIfBlockHarvested(worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(Item.itemsList[currentBlockID], 1, currentBlockMetadata));
		}
		
		hasItem = false;
		currentBlockID = 0;
		currentBlockMetadata = 0;
		
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);

        if (!this.worldObj.isRemote)
        	this.worldObj.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, this.xCoord, this.yCoord, this.zCoord, 0);
	}
	
	public int getGrowthStagesForBlock(int blockID)
	{
		if (validBlockList.containsKey(blockID))
		{
			return validBlockList.get(blockID); 
		}
		
		return 0;

	}

	static {
		validItemList.add(SCDefs.bambooItem.itemID);
		validItemList.add(SCDefs.wildPotatoCut.itemID);
		validItemList.add(SCDefs.wildCarrotTop.itemID);
		
		validBlockList.put(SCDefs.bambooShoot.blockID, 3);
		validBlockList.put(SCDefs.wildPotatoCropSapling.blockID, 3);
		validBlockList.put(SCDefs.wildCarrotCropSapling.blockID, 3);
	}
}
