package net.minecraft.src;

import java.util.ArrayList;

public class SCTileEntityStorageJar extends TileEntity implements IInventory, FCITileEntityDataPacketHandler{

	private ItemStack storageStack;
	private boolean hasLabel;
	private int seedType;
	
	private static ArrayList<Integer> validItemList = new ArrayList<Integer>();
    
    static
    {
    	validItemList.add( Item.dyePowder.itemID );
    	
    	//CROPS
    	validItemList.add( Item.melonSeeds.itemID );
    	validItemList.add( Item.pumpkinSeeds.itemID );    	
    	validItemList.add( Item.netherStalkSeeds.itemID );
    	
    	validItemList.add( FCBetterThanWolves.fcItemHempSeeds.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemWheatSeeds.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemCarrotSeeds.itemID );
    	
    	//BREWING
    	validItemList.add( Item.spiderEye.itemID );
    	validItemList.add( Item.fermentedSpiderEye.itemID );
    	validItemList.add( Item.magmaCream.itemID );
    	validItemList.add( Item.ghastTear.itemID );
    	validItemList.add( Item.blazePowder.itemID );
    	validItemList.add( Item.gunpowder.itemID );
    	
    	validItemList.add( FCBetterThanWolves.fcItemMushroomRed.itemID );    	
    	validItemList.add( FCBetterThanWolves.fcItemMysteriousGland.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemWitchWart.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemSoulFlux.itemID );
    	
    	//BAIT
    	validItemList.add(Item.rottenFlesh.itemID);
    	validItemList.add(FCBetterThanWolves.fcItemBatWing.itemID);
    	validItemList.add(FCBetterThanWolves.fcItemCreeperOysters.itemID);
    	
    	//OTHER
    	validItemList.add( FCBetterThanWolves.fcItemNitre.itemID);
    	validItemList.add( FCBetterThanWolves.fcItemMushroomBrown.itemID );  
    	validItemList.add( Item.slimeBall.itemID );    

    	//OTHER DUSTS/POWDERS
    	validItemList.add( Item.redstone.itemID );
    	validItemList.add( Item.lightStoneDust.itemID );
    	validItemList.add( Item.sugar.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemFlour.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemCocoaBeans.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemChickenFeed.itemID );
    	
    	//SC
    	validItemList.add( SCDefs.wildCarrotSeeds.itemID);
    	validItemList.add( SCDefs.wildCarrotCropSapling.blockID);
    	
    	if( SCDecoIntegration.isDecoInstalled() )
    	{
    		validItemList.add( SCDecoIntegration.fertilizer.itemID );
    		validItemList.add( SCDecoIntegration.amethystShard.itemID );
    	}
    } 
    
    public SCTileEntityStorageJar()
    {
    	
    }
    
    @Override
    public void updateEntity()
    {
    	if ( worldObj.isRemote )
    	{
    		return;
    	}
    }
    
    @Override
    public void onInventoryChanged() {

    	super.onInventoryChanged();
    	
    	if (storageStack != null )
    	{
    		setSeedType( storageStack.itemID );
    	}
    	else setSeedType(0);
    }
    

    public static void addValidItemToStore(int itemID)
    {
    	validItemList.add( itemID );
    }
    
    /**
     * Checks if validItemList contains seedID and return the index
     * @param seedID
     * @return
     */
    public int getSeedTypeForItemRender(int seedID) {
    	
    	for (int i = 0; i < validItemList.size(); i++) {
    		if (validItemList.contains( seedID ) )
    		{	
    			return validItemList.indexOf( seedID );
    		}

		}
    	return 0;
	}
    
	public static ArrayList<Integer> getValidItemList() {
		return validItemList;
	}
	
	public void setStorageStack(ItemStack storageStack) {
		this.storageStack = storageStack;
		markBlockForUpdate();
		onInventoryChanged();
	}
	
	public ItemStack getStorageStack() {
		return storageStack;
	}
	
	public boolean hasLabel() {
		return hasLabel;
	}
	
	public void setLabel(boolean hasLabel) {
		this.hasLabel = hasLabel;
		markBlockForUpdate();
	}
	
	public int getSeedType() {
		return seedType;
	}
	
	public void setSeedType(int seedType) {
		this.seedType = seedType;
		markBlockForUpdate();
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

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		if (isStackValidForSlot(slot, stack))
		{
			setStorageStack(stack);
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

	@Override
	public boolean isStackValidForSlot(int var1, ItemStack stack) {
		return validItemList.contains( stack.itemID );
	}



}
