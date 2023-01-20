package net.minecraft.src;

import java.util.ArrayList;

public class SCTileEntitySeedJar extends TileEntity implements IInventory, FCITileEntityDataPacketHandler{

	private ItemStack storageStack;
	private boolean hasLabel;
	private int seedType;
	private boolean hasAttachableBlockAbove;
	
	private static ArrayList<Integer> validSeedItem = new ArrayList<Integer>();
    
    static
    {
    	//CROPS
    	validSeedItem.add( Item.melonSeeds.itemID );
    	validSeedItem.add( Item.pumpkinSeeds.itemID );    	
    	validSeedItem.add( Item.netherStalkSeeds.itemID );
    	
    	validSeedItem.add( FCBetterThanWolves.fcItemHempSeeds.itemID );
    	validSeedItem.add( FCBetterThanWolves.fcItemWheatSeeds.itemID );
    	validSeedItem.add( FCBetterThanWolves.fcItemCarrotSeeds.itemID );
    	
    	validSeedItem.add( FCBetterThanWolves.fcItemCocoaBeans.itemID );    	
    	validSeedItem.add( FCBetterThanWolves.fcItemMelonMashed.itemID );
    	validSeedItem.add( Item.cookie.itemID );
    	validSeedItem.add( FCBetterThanWolves.fcItemChickenFeed.itemID );
    	
    	//SC
    	validSeedItem.add( SCDefs.wildCarrotSeeds.itemID );
    	validSeedItem.add( SCDefs.redGrapeSeeds.itemID );
    	validSeedItem.add( SCDefs.whiteGrapeSeeds.itemID );
    	validSeedItem.add( SCDefs.hopSeeds.itemID );
    	validSeedItem.add( SCDefs.tomatoSeeds.itemID );
    	validSeedItem.add( SCDefs.wildOnionSeeds.itemID );
    	validSeedItem.add( SCDefs.wildLettuceSeeds.itemID );
    	validSeedItem.add( SCDefs.rice.itemID );
    	
    	validSeedItem.add( SCDefs.sunflowerSeeds.itemID );
    	
    	validSeedItem.add( SCDefs.appleSeeds.itemID );
    	validSeedItem.add( SCDefs.cherrySeeds.itemID );
    	validSeedItem.add( SCDefs.lemonSeeds.itemID );
    	validSeedItem.add( SCDefs.oliveSeeds.itemID );
    	
    	validSeedItem.add( SCDefs.grassSeeds.itemID );

    }
    
    public SCTileEntitySeedJar()
    {
    	
    }
    
    @Override
    public void updateEntity()
    {
		FCTileEntityHopper hopper = (FCTileEntityHopper) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
		if (hopper != null)
		{
			markBlockForUpdate();
		}
    }
    
    @Override
    public void onInventoryChanged() {

    	super.onInventoryChanged();
    	
    	if (storageStack != null )
    	{
    		setSeedType( storageStack.itemID );
    		
    		if (storageStack.stackSize == 0) setStorageStack(null);    		
    	}
    	else setSeedType(0);
    	
    	
    	markBlockForUpdate();
    }
    

    public static void addValidSeedToStore(int itemID)
    {
    	validSeedItem.add( itemID );
    }
    
    /**
     * Checks if validItemList contains seedID and return the index
     * @param seedID
     * @return
     */
    public int getSeedTypeForItemRender(int seedID) {
    	
    	for (int i = 0; i < validSeedItem.size(); i++) {
    		if (validSeedItem.contains( seedID ) )
    		{	
    			return validSeedItem.indexOf( seedID );
    		}

		}
    	return 0;
	}
    
	public static ArrayList<Integer> getValidSeedList() {
		return validSeedItem;
	}
	
	public void setStorageStack(ItemStack storageStack) {
		this.storageStack = storageStack;
		//onInventoryChanged();
	}
	
	public ItemStack getStorageStack() {
		return storageStack;
	}
	
	public boolean hasLabel() {
		return hasLabel;
	}
	
	public void setLabel(boolean hasLabel) {
		this.hasLabel = hasLabel;
		//markBlockForUpdate();
	}
	
	public int getSeedType() {
		return seedType;
	}
	
	public void setSeedType(int seedType) {
		this.seedType = seedType;
		//markBlockForUpdate();
	}
	
	public boolean hasAttachableBlockAbove() {
		return hasAttachableBlockAbove;
	}
	
	public void setHasAttachableBlockAbove(boolean hasAttachableBlockAbove) {
		this.hasAttachableBlockAbove = hasAttachableBlockAbove;
		//markBlockForUpdate();
	}

	
    public void ejectStorageContents( int iFacing )
    {
    	
    	if ( storageStack != null )
    	{
    		FCUtilsItem.EjectStackFromBlockTowardsFacing( worldObj, xCoord, yCoord, zCoord, getStackInSlot(0), iFacing);
    		
    		storageStack = null;
    		
    	}
    	
    	worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );  
    }
	
	public void markBlockForUpdate() {
		    	
		this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );   
	}
	
	//----------- NBT -----------//
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
        NBTTagCompound storageTag = tag.getCompoundTag( "storageStack" );

        if ( storageTag != null )
        {
        	storageStack = ItemStack.loadItemStackFromNBT( storageTag );
        }
        
        if ( tag.hasKey( "hasLabel" ) )
        {
        	hasLabel = tag.getBoolean( "hasLabel" );
        }
        
        if ( tag.hasKey( "seedType" ) )
        {
        	seedType = tag.getInteger( "seedType" );
        }
        
        if ( tag.hasKey( "hasAttachableBlockAbove" ) )
        {
        	hasAttachableBlockAbove = tag.getBoolean( "hasAttachableBlockAbove" );
        }
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
        if ( storageStack != null)
        {
            NBTTagCompound storageTag = new NBTTagCompound();
            
            storageStack.writeToNBT( storageTag );
            
            tag.setCompoundTag( "storageStack", storageTag );
        }
		
		tag.setBoolean( "hasLabel", hasLabel ); 
		
		tag.setInteger( "seedType", seedType ); 
		
		tag.setBoolean( "hasAttachableBlockAbove", hasAttachableBlockAbove ); 
		
		// force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        
    	if ( storageStack != null)
        {
            NBTTagCompound storageTag = new NBTTagCompound();
            
            storageStack.writeToNBT( storageTag );
            
            nbttagcompound1.setCompoundTag( "storageStack", storageTag );
        }
        
        nbttagcompound1.setBoolean( "hasLabel", hasLabel );
        
        nbttagcompound1.setInteger( "seedType", seedType ); 
        
        nbttagcompound1.setBoolean( "hasAttachableBlockAbove", hasAttachableBlockAbove );
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, nbttagcompound1 );
	}
	
	//----------- FCITileEntityDataPacketHandler -----------//
	
	@Override
	public void readNBTFromPacket(NBTTagCompound tag)
	{
        NBTTagCompound storageTag = tag.getCompoundTag( "storageStack" );

        if ( storageTag != null )
        {
        	storageStack = ItemStack.loadItemStackFromNBT( storageTag );
        }
		
        if ( tag.hasKey( "hasLabel" ) )
        {
        	hasLabel = tag.getBoolean( "hasLabel" );
        }
        
        if ( tag.hasKey( "seedType" ) )
        {
        	seedType = tag.getInteger( "seedType" );
        }
        
        if ( tag.hasKey( "hasAttachableBlockAbove" ) )
        {
        	hasAttachableBlockAbove = tag.getBoolean( "hasAttachableBlockAbove" );
        }
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
	}
	
	//----------- IInventory -----------//

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return storageStack;
	}

    @Override
    public ItemStack decrStackSize( int slot, int amount )
    {
    	return FCUtilsInventory.DecrStackSize( this, slot, amount );    	
    }

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		if (stack != null)
		{
			if (isStackSeedItem(stack))
			{
				setStorageStack(stack);
				setSeedType(stack.itemID);
			}
		}	
	}

	private void convertToStorageJar() {
		
		if (worldObj.getBlockId(xCoord, yCoord, zCoord) != SCDefs.storageJar.blockID)
		{
			worldObj.setBlockAndMetadata(xCoord, yCoord, zCoord, SCDefs.storageJar.blockID, worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
			
			SCTileEntityStorageJar storageJar = (SCTileEntityStorageJar) worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
			storageJar.setLabel(this.hasLabel());
		}
	}

	@Override
	public String getInvName() {

		return "Jar";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        if ( worldObj.getBlockTileEntity( xCoord, yCoord, zCoord ) == this )
        {
            return ( entityplayer.getDistanceSq( (double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D ) 
            	<= 64D );
        }
        
        return false;
    }

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}
	
	//Normally not used, but in this case used by FCTileEntityHopper
	@Override
	public boolean isStackValidForSlot(int slot, ItemStack stack)
	{
		//If the item is valid, let it be stored
		if (validSeedItem.contains( stack.itemID ))
		{
			return true;
		}
		else //If the item can't be stored
		{
			ArrayList<Integer> validStorageList = SCTileEntityStorageJar.getValidItemList();
			
			SCTileEntitySeedJar seedJar = (SCTileEntitySeedJar) worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
			
			//Convert to a storage jar if the item can be stored in a storageJar and the seed jar is empty
			if (validStorageList.contains(stack.itemID) && seedJar != null && seedJar.getStorageStack() == null)
			{
				convertToStorageJar();
			}
			
			//else don't store items
			return false;
		}
	}
	
	public static boolean isStackSeedItem(ItemStack stack)
	{
		return  validSeedItem.contains( stack.itemID );
	}

}
