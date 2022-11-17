//Based on: https://github.com/BTW-Community/Deco-Addon-2/blob/master/minecraft/net/minecraft/src/DecoTileEntityFlowerPot.java

package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;


public class SCTileEntityMultiFlowerPot extends TileEntity implements FCITileEntityDataPacketHandler {
	
	private int l_storedBlockID;
	private int c_storedBlockID;
	private int r_storedBlockID;
	private int l_storedBlockMetadata;
	private int c_storedBlockMetadata;
	private int r_storedBlockMetadata;
	private int insertedItem;
	
	/**
	 * @param SCGrowable object
	 */
	public static ArrayList<SCGrowables> validGrowable = new ArrayList<SCGrowables>();
	
	/**
	 * @param Integer SCGrowable.inputItem
	 * @param SCGrowable object
	 */
	public static HashMap<Integer, SCGrowables> storables = new HashMap<Integer, SCGrowables>();
	
	static {
		//Sapling
		SCGrowables sapling = new SCGrowables(
				Block.sapling.blockID,
				new int[] { 0, 1, 2, 3 },
				Block.sapling.blockID,
				Block.sapling.blockID,
				4, 1/32F );
		validGrowable.add(sapling);		
		storables.put(sapling.inputItem, sapling);
		
		//Cut Wild Potato
//		SCGrowables cutWildPotato = new SCGrowables(
//				SCDefs.wildPotatoCut.itemID,
//				new int[] { 0 },
//				SCDefs.wildPotatoCropSapling.blockID,
//				SCDefs.wildPotatoCropSapling.blockID,
//				4, 0.4F );
//		validGrowable.add(cutWildPotato);		
//		storables.put(cutWildPotato.inputItem, cutWildPotato);
//		
//		//Wild Potato
//		SCGrowables wildPotato = new SCGrowables(
//				SCDefs.wildPotato.itemID,
//				new int[] { 0 },
//				SCDefs.wildPotatoCropSapling.blockID,
//				SCDefs.wildPotatoCropSapling.blockID,
//				4, 0.4F );
//		validGrowable.add(wildPotato);		
//		storables.put(wildPotato.inputItem, wildPotato);
//
//		//Wild Carrot Top
//		SCGrowables wildCarrotTop = new SCGrowables(
//				SCDefs.wildCarrotTop.itemID,
//				new int[] { 0 },
//				SCDefs.wildCarrotCropSapling.blockID,
//				SCDefs.wildCarrotCropSapling.blockID,
//				4, 0.4F );
//		validGrowable.add(wildCarrotTop);		
//		storables.put(wildCarrotTop.inputItem, wildCarrotTop);
//		
//		//Wild Carrot
//		SCGrowables wildCarrot = new SCGrowables(
//				SCDefs.wildCarrot.itemID,
//				new int[] { 0 },
//				SCDefs.wildCarrotCropSapling.blockID,
//				SCDefs.wildCarrotCropSapling.blockID,
//				4, 0.4F );
//		validGrowable.add(wildCarrot);		
//		storables.put(wildCarrot.inputItem, wildCarrot);
//		
//		//Reed Roots
//		SCGrowables reedRoots = new SCGrowables(
//				FCBetterThanWolves.fcItemReedRoots.itemID,
//				new int[] { 0 },
//				SCDefs.reedRoots.blockID,
//				SCDefs.reedsRootMature.itemID,
//				8, 0.4F );
//		validGrowable.add(reedRoots);		
//		storables.put(reedRoots.inputItem, reedRoots);
//		
//		if (SCDecoIntegration.isDecoInstalled() )
//		{
//			//Cherry Sapling
//			SCGrowables cherrySapling = new SCGrowables(
//					SCDecoIntegration.cherrySapling.blockID,
//					new int[] { 0, 1, 2, 3 },
//					SCDecoIntegration.cherrySapling.blockID,
//					SCDecoIntegration.cherrySapling.blockID,
//					4, 1/32F );
//			validGrowable.add(cherrySapling);		
//			storables.put(cherrySapling.inputItem, cherrySapling);
//			
//			//Acacia Sapling
//			SCGrowables acaciaSapling = new SCGrowables(
//					SCDecoIntegration.acaciaSapling.blockID,
//					new int[] { 0, 1, 2, 3 },
//					SCDecoIntegration.acaciaSapling.blockID,
//					SCDecoIntegration.acaciaSapling.blockID,
//					4, 1/32F );
//			validGrowable.add(acaciaSapling);		
//			storables.put(acaciaSapling.inputItem, acaciaSapling);
//			
//			//Autumn Sapling
//			SCGrowables autumnSapling = new SCGrowables(
//					SCDecoIntegration.acaciaSapling.blockID,
//					new int[] { 0, 1, 2, 3 },
//					SCDecoIntegration.acaciaSapling.blockID,
//					SCDecoIntegration.acaciaSapling.blockID,
//					4, 1/32F );
//			validGrowable.add(autumnSapling);		
//			storables.put(autumnSapling.inputItem, autumnSapling);
//		}
	}
		
	public boolean isValidToStore(int heldID, int metadata)
	{
		boolean valid = false;

		if (storables.containsKey(heldID) )
		{
			SCGrowables growable = storables.get(heldID);

			for (int meta : growable.validMetadata) {
				if (meta == metadata) valid = true;
				break;		       
			}
		}

		return valid;
	}

	
	public int getGrowableInput(int insertedItemID)
	{
		if (storables.containsKey(insertedItemID) ) return storables.get(insertedItemID).inputItem;
		else return 0;
	}
	
	public int getGrowableToStore(int insertedItemID)
	{
		if (storables.containsKey ( insertedItemID ) ) return storables.get(insertedItemID).blockToStore;
		else return 0;
	}
	
	public int getGrowableOutput(int insertedItemID)
	{
		if (storables.containsKey(insertedItemID) ) return storables.get(insertedItemID).outputItem;
		else return 0;
	}
	
	public int getGrowableGrowthStages(int insertedItemID)
	{		
		if (storables.containsKey(insertedItemID) ) return storables.get(insertedItemID).growthStages;
		else return 0;
	}
	
	public float getGrowableGrowthChance(int insertedItemID)
	{		
		if (storables.containsKey(insertedItemID) ) return storables.get(insertedItemID).growthChance;
		else return 0F;
	}
	

	/**
	 * Has to be used even if the ItemID is equal to the BlockID
	 * @param Integer The ItemID to insert
	 * @param Integer The BlockID that's actually stored
	 */
	//public static HashMap<Integer, Integer> blockToStore = new HashMap<Integer, Integer>();
	
	/**
	 * @param Integer the stored Block
	 * @param Integer the number growth stages of that block
	 */
	//public static HashMap<Integer, Integer> growthStages = new HashMap<Integer, Integer>();
	
	/**
	 * @param Integer the stored Block
	 * @param Float the baseGrowthChance of that block
	 */
	//public static HashMap<Integer, Float> growthChance = new HashMap<Integer, Float >();
	
//	static {	
//		
//		blockToStore.put(Block.sapling.blockID, Block.sapling.blockID);
//		growthStages.put(Block.sapling.blockID, 4);
//		growthChance.put(Block.sapling.blockID, 1/32F);
//		
//		blockToStore.put(SCDefs.wildPotatoCut.itemID, SCDefs.wildPotatoCropSapling.blockID);
//		blockToStore.put(SCDefs.wildPotato.itemID, SCDefs.wildPotatoCropSapling.blockID);
//		growthStages.put(SCDefs.wildPotatoCropSapling.blockID, 4);
//		growthChance.put(SCDefs.wildPotatoCropSapling.blockID, 0.4F);
//		
//		blockToStore.put(SCDefs.wildCarrotTop.itemID, SCDefs.wildCarrotCropSapling.blockID);
//		blockToStore.put(SCDefs.wildCarrot.itemID, SCDefs.wildCarrotCropSapling.blockID);
//		growthStages.put(SCDefs.wildCarrotCropSapling.blockID, 4);
//		growthChance.put(SCDefs.wildCarrotCropSapling.blockID, 0.4F);
//		
//		blockToStore.put(FCBetterThanWolves.fcItemReedRoots.itemID, SCDefs.reedRoots.blockID);
//		growthStages.put(SCDefs.reedRoots.blockID, 8);
//		growthChance.put(SCDefs.reedRoots.blockID, 1F);
//		
//		if (SCDecoIntegration.isDecoInstalled() )
//		{
//			blockToStore.put(SCDecoIntegration.cherrySapling.blockID, SCDecoIntegration.cherrySapling.blockID);
//			growthStages.put(SCDecoIntegration.cherrySapling.blockID, 4);
//			growthChance.put(SCDecoIntegration.cherrySapling.blockID, 1/32F);
//			
//			blockToStore.put(SCDecoIntegration.acaciaSapling.blockID, SCDecoIntegration.acaciaSapling.blockID);
//			growthStages.put(SCDecoIntegration.acaciaSapling.blockID, 4);
//			growthChance.put(SCDecoIntegration.acaciaSapling.blockID, 1/32F);
//			
//			blockToStore.put(SCDecoIntegration.autumnSapling.blockID, SCDecoIntegration.autumnSapling.blockID);
//			growthStages.put(SCDecoIntegration.autumnSapling.blockID, 4);
//			growthChance.put(SCDecoIntegration.autumnSapling.blockID, 1/32F);
//		}
//	}

	
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("l_storedBlockID", this.l_storedBlockID);
        nbtTagCompound.setInteger("c_storedBlockID", this.c_storedBlockID);
        nbtTagCompound.setInteger("r_storedBlockID", this.r_storedBlockID);
        nbtTagCompound.setInteger("l_storedBlockMetadata", this.l_storedBlockMetadata);
        nbtTagCompound.setInteger("c_storedBlockMetadata", this.c_storedBlockMetadata);
        nbtTagCompound.setInteger("r_storedBlockMetadata", this.r_storedBlockMetadata);
        nbtTagCompound.setInteger("insertedItem", this.insertedItem);

    }

    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        this.l_storedBlockID = nbtTagCompound.getInteger("l_storedBlockID");
        this.c_storedBlockID = nbtTagCompound.getInteger("c_storedBlockID");
        this.r_storedBlockID = nbtTagCompound.getInteger("r_storedBlockID");
        this.l_storedBlockMetadata = nbtTagCompound.getInteger("l_storedBlockMetadata");
        this.c_storedBlockMetadata = nbtTagCompound.getInteger("c_storedBlockMetadata");
        this.r_storedBlockMetadata = nbtTagCompound.getInteger("r_storedBlockMetadata");
        this.insertedItem = nbtTagCompound.getInteger("insertedItem");

    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("l_storedBlockID", this.l_storedBlockID);
        nbtTagCompound.setInteger("c_storedBlockID", this.c_storedBlockID);
        nbtTagCompound.setInteger("r_storedBlockID", this.r_storedBlockID);
        nbtTagCompound.setInteger("l_storedBlockMetadata", this.l_storedBlockMetadata);
        nbtTagCompound.setInteger("c_storedBlockMetadata", this.c_storedBlockMetadata);
        nbtTagCompound.setInteger("r_storedBlockMetadata", this.r_storedBlockMetadata);
        nbtTagCompound.setInteger("insertedItem", this.insertedItem);

        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
    }
    
    @Override
    public void readNBTFromPacket(NBTTagCompound nbtTagCompound) {
        this.l_storedBlockID = nbtTagCompound.getInteger("l_storedBlockID");
        this.c_storedBlockID = nbtTagCompound.getInteger("c_storedBlockID");
        this.r_storedBlockID = nbtTagCompound.getInteger("r_storedBlockID");
        this.l_storedBlockMetadata = nbtTagCompound.getInteger("l_storedBlockMetadata");
        this.c_storedBlockMetadata = nbtTagCompound.getInteger("c_storedBlockMetadata");
        this.r_storedBlockMetadata = nbtTagCompound.getInteger("r_storedBlockMetadata");
        this.insertedItem = nbtTagCompound.getInteger("insertedItem");
        
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }
    
    public void markBlockForRender() {
    	this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
	}
    
    public int getCenterStoredBlockID() {
    	return c_storedBlockID;
    }
    
    public int setCenterStoredBlockID(int blockID) {
    	return c_storedBlockID = blockID;
    }
    
    public int getCenterStoredBlockMetadata() {
    	return c_storedBlockMetadata;
    }
    
    public void setCenterStoredBlockMetadata(int meta) {
    	c_storedBlockMetadata = meta;
    }
    
	public int getLeftStoredBlockID() {
		return l_storedBlockID;
	}


	public void setLeftStoredBlockID(int l_storedBlockID) {
		this.l_storedBlockID = l_storedBlockID;
	}


	public int getLeftStoredBlockMetadata() {
		return l_storedBlockMetadata;
	}


	public void setLeftStoredBlockMetadata(int l_storedBlockMetadata) {
		this.l_storedBlockMetadata = l_storedBlockMetadata;
	}

	public int getRightStoredBlockID() {
		return r_storedBlockID;
	}


	public void setRightStoredBlockID(int r_storedBlockID) {
		this.r_storedBlockID = r_storedBlockID;
	}


	public int getRightStoredBlockMetadata() {
		return r_storedBlockMetadata;
	}


	public void setRightStoredBlockMetadata(int r_storedBlockMetadata) {
		this.r_storedBlockMetadata = r_storedBlockMetadata;
	}
    
    public int getInsertedItem() {
    	return insertedItem;
    }
    
    public void setInsertedItem(int insertedItemID) {
    	insertedItem = insertedItemID;
    }

	public boolean hasItem() {
		return insertedItem != 0;
	}
	
	public boolean hasItemInSlot(int slot) {
		if (slot == 0 && l_storedBlockID > 0) return true;
		if (slot == 1 && c_storedBlockID > 0) return true;
		if (slot == 2 && r_storedBlockID > 0) return true;
		
		return false;
	}
	
//	public boolean isValidToStore(int id, int meta) {
//
//		return blockToStore.containsKey(id);
//	}
	
//	public int getBlockToStore(int blockID)
//	{
//		if (blockToStore.containsKey(blockID))
//		{
//			return blockToStore.get(blockID);
//		}
//		
//		return 0;
//	}

//	public int getGrowthStagesForBlock(int blockID)
//	{
//		if (growthStages.containsKey(blockID))
//		{
//			return growthStages.get(blockID); 
//		}
//		
//		return 0;
//
//	}
	
//	public float getGrowthChanceForBlock(int blockID)
//	{
//		if (growthChance.containsKey(blockID))
//		{
//			return growthChance.get(blockID); 
//		}
//		
//		return 0F;
//
//	}

	public void placeItemInPot(int heldID, int metadata, int slot) {
		if (slot == 0)
		{
			setLeftStoredBlockID(getGrowableToStore(heldID));
			setLeftStoredBlockMetadata(metadata);
		}
		else if (slot == 1)
		{
			setCenterStoredBlockID(getGrowableToStore(heldID));
			setCenterStoredBlockMetadata(metadata);
		}
		else
		{
			setRightStoredBlockID(getGrowableToStore(heldID));
			setRightStoredBlockMetadata(metadata);
		}

		setInsertedItem(heldID);
				
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
        
        this.worldObj.playSound(this.xCoord, this.yCoord, this.zCoord, "step.grass", 1.0F, 1.0F);
	}

	//Removes item and gives it to the player
	public void retrieveItemFromPot(EntityPlayer player, int slot) {
		
		int returnItemID = insertedItem;
		
		if (slot == 0 && l_storedBlockID == 0)
			return;
		
		if (slot == 1 && c_storedBlockID == 0)
			return;
		
		if (slot == 2 && r_storedBlockID == 0)
			return;
		
		if(!worldObj.isRemote)
		{
			FCUtilsItem.GivePlayerStackOrEjectFavorEmptyHand(player, new ItemStack(Item.itemsList[returnItemID], 1, 0), this.xCoord, this.yCoord, this.zCoord);
		}
		
		if (slot == 0)
		{
			setLeftStoredBlockID(getGrowableToStore(0));
			setLeftStoredBlockMetadata(0);
		}
		else if (slot == 1)
		{
			setCenterStoredBlockID(getGrowableToStore(0));
			setCenterStoredBlockMetadata(0);
		}
		else
		{
			setRightStoredBlockID(getGrowableToStore(0));
			setRightStoredBlockMetadata(0);
		}

		setInsertedItem( 0 );
		
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);

        if (!this.worldObj.isRemote)
        	this.worldObj.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, this.xCoord, this.yCoord, this.zCoord, 0);
	}
	
	public void ejectItemFromPot(World world, int x, int y, int z, int slot) {
		
		int returnItemID = insertedItem;
		
		if (slot == 0 && l_storedBlockID == 0)
			return;
		
		if (slot == 1 && c_storedBlockID == 0)
			return;
		
		if (slot == 2 && r_storedBlockID == 0)
			return;
		
		
		if (!world.isRemote)
		{			
			FCUtilsItem.DropStackAsIfBlockHarvested( world, x, y, z, new ItemStack( returnItemID, 1 , 0) );
		}
		
		if (slot == 0)
		{
			setLeftStoredBlockID(getGrowableToStore(0));
			setLeftStoredBlockMetadata(0);
		}
		else if (slot == 1)
		{
			setCenterStoredBlockID(getGrowableToStore(0));
			setCenterStoredBlockMetadata(0);
		}
		else
		{
			setRightStoredBlockID(getGrowableToStore(0));
			setRightStoredBlockMetadata(0);
		}
		setInsertedItem( 0 );
	}
}
