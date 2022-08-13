//Based on: https://github.com/BTW-Community/Deco-Addon-2/blob/master/minecraft/net/minecraft/src/DecoTileEntityFlowerPot.java

package net.minecraft.src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SCTileEntityWaterPot extends TileEntity implements FCITileEntityDataPacketHandler {
	
	private int storedBlockID;
	private int storedBlockMetadata;
	private int insertedID;
	
	/**
	 * Has to be used even if the ItemID is equal to the BlockID
	 * @param Integer The ItemID to insert
	 * @param Integer The BlockID that's actually stored
	 */
	public static HashMap<Integer, Integer> blockToStore = new HashMap<Integer, Integer>();
	
	/**
	 * @param Integer the stored Block
	 * @param Integer the number growth stages of that block
	 */
	public static HashMap<Integer, Integer> growthStages = new HashMap<Integer, Integer>();
	
	/**
	 * @param Integer the stored Block
	 * @param Float the baseGrowthChance of that block
	 */
	public static HashMap<Integer, Float> growthChance = new HashMap<Integer, Float >();

	static {	
		blockToStore.put(SCDefs.wildPotatoCut.itemID, SCDefs.wildPotatoCropSapling.blockID);
		blockToStore.put(SCDefs.wildPotato.itemID, SCDefs.wildPotatoCropSapling.blockID);
		growthStages.put(SCDefs.wildPotatoCropSapling.blockID, 4);
		growthChance.put(SCDefs.wildPotatoCropSapling.blockID, 0.4F);
		
		blockToStore.put(SCDefs.wildCarrotTop.itemID, SCDefs.wildCarrotCropSapling.blockID);
		blockToStore.put(SCDefs.wildCarrot.itemID, SCDefs.wildCarrotCropSapling.blockID);
		growthStages.put(SCDefs.wildCarrotCropSapling.blockID, 4);
		growthChance.put(SCDefs.wildCarrotCropSapling.blockID, 0.4F);
		
		blockToStore.put(Block.sapling.blockID, Block.sapling.blockID);
		growthStages.put(Block.sapling.blockID, 4);
		growthChance.put(Block.sapling.blockID, 1/32F);
		
		if (SCDecoIntegration.isDecoInstalled() )
		{
			blockToStore.put(SCDecoIntegration.cherrySapling.blockID, SCDecoIntegration.cherrySapling.blockID);
			growthStages.put(SCDecoIntegration.cherrySapling.blockID, 4);
			growthChance.put(SCDecoIntegration.cherrySapling.blockID, 1/32F);
		}
	}

	
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("storedBlockID", this.storedBlockID);
        nbtTagCompound.setInteger("storedBlockMetadata", this.storedBlockMetadata);
        nbtTagCompound.setInteger("insertedID", this.insertedID);
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        this.storedBlockID = nbtTagCompound.getInteger("storedBlockID");
        this.storedBlockMetadata = nbtTagCompound.getInteger("storedBlockMetadata");
        this.insertedID = nbtTagCompound.getInteger("insertedID");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("storedBlockID", this.storedBlockID);
        nbtTagCompound.setInteger("storedBlockMetadata", this.storedBlockMetadata);
        nbtTagCompound.setInteger("insertedID", this.insertedID);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
    }
    
    @Override
    public void readNBTFromPacket(NBTTagCompound nbtTagCompound) {
        this.storedBlockID = nbtTagCompound.getInteger("storedBlockID");
        this.storedBlockMetadata = nbtTagCompound.getInteger("storedBlockMetadata");
        this.insertedID = nbtTagCompound.getInteger("insertedID");
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }
    
    public void markBlockForRender() {
    	this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
	}
    
    public int getStoredBlockID() {
    	return storedBlockID;
    }
    
    public int getStoredBlockMetadata() {
    	return storedBlockMetadata;
    }
    
    public int getInsertedID() {
    	return insertedID;
    }
    
    public void setStoredBlockMetadata(int meta) {
    	storedBlockMetadata = meta;
    }

	public boolean hasItem() {
		return insertedID != 0;
	}
	
	public boolean isValidToStore(int id, int meta) {

		return blockToStore.containsKey(id);
	}
	
	public int getBlockToStore(int blockID)
	{
		if (blockToStore.containsKey(blockID))
		{
			return blockToStore.get(blockID);
		}
		
		return 0;
	}

	public int getGrowthStagesForBlock(int blockID)
	{
		if (growthStages.containsKey(blockID))
		{
			return growthStages.get(blockID); 
		}
		
		return 0;

	}
	
	public float getGrowthChanceForBlock(int blockID)
	{
		if (growthChance.containsKey(blockID))
		{
			return growthChance.get(blockID); 
		}
		
		return 0F;

	}

	public void placeItemInPot(int itemID, int metadata) {
		
		storedBlockID = getBlockToStore(itemID);
		storedBlockMetadata = metadata;
		insertedID = itemID;
				
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
        
        this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "step.grass", 1.0F, 1.0F);
	}

	//Removes item and gives it to the player
	public void retrieveItemFromPot(EntityPlayer player) {
		
		int returnItemID = storedBlockID;
		
		if (storedBlockID == 0)
			return;
		
		if(!worldObj.isRemote)
		{
			
			if (storedBlockMetadata < getGrowthStagesForBlock(storedBlockID) - 1)
			{
				returnItemID = insertedID;
			}
			FCUtilsItem.GivePlayerStackOrEjectFavorEmptyHand(player, new ItemStack(Item.itemsList[returnItemID], 1, storedBlockMetadata), this.xCoord, this.yCoord, this.zCoord);
			
		}

		storedBlockID = 0;
		storedBlockMetadata = 0;
		insertedID = 0;
		
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);

        if (!this.worldObj.isRemote)
        	this.worldObj.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, this.xCoord, this.yCoord, this.zCoord, 0);
	}
	
	public void ejectItemFromPot() {
		
		int returnItemID = storedBlockID;
		
		if (storedBlockID == 0)
			return;
		
		if(!worldObj.isRemote)
		{
			
			if (storedBlockMetadata < getGrowthStagesForBlock(storedBlockID) - 1)
			{
				returnItemID = insertedID;
			}

		}
	}
}
