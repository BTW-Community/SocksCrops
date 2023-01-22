package net.minecraft.src;

import java.util.ArrayList;

public class SCTileEntityStorageJar extends TileEntity implements IInventory, FCITileEntityDataPacketHandler{

	private ItemStack storageStack;
	private boolean hasLabel;
	private int seedType;
	private int corkType;
	private boolean hasAttachableBlockAbove;
	
	private static ArrayList<Integer> validItemList = new ArrayList<Integer>();
    
    static
    {
    	validItemList.add( Item.dyePowder.itemID );
    	
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
    	
    	validItemList.add( FCBetterThanWolves.fcItemBloodMossSpores.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemBrimstone.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemPotash.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemGlue.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemMetalFragment.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemChunkIronOre.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemChunkGoldOre.itemID );
    	
    	validItemList.add( Item.fireballCharge.itemID );
    	validItemList.add( Item.netherQuartz.itemID );
    	validItemList.add( Item.emerald.itemID );
    	validItemList.add( Item.enderPearl.itemID );
    	validItemList.add( Item.diamond.itemID );
    	
    	validItemList.add( Item.goldNugget.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemNuggetIron.itemID );
    	
    	validItemList.add( FCBetterThanWolves.fcItemHempFibers.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemGear.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemStrap.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemTallow.itemID );
    	
    	validItemList.add( Item.silk.itemID );
    	
    	
    	if( SCDecoIntegration.isDecoInstalled() )
    	{
    		validItemList.add( SCDecoIntegration.fertilizer.itemID );
    		validItemList.add( SCDecoIntegration.amethystShard.itemID );
    		validItemList.add( SCDecoIntegration.prismarineCrystal.itemID );
    		validItemList.add( SCDecoIntegration.prismarineShard.itemID );
    	}
    } 
    
    public SCTileEntityStorageJar()
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
	
	public boolean hasAttachableBlockAbove() {
		return hasAttachableBlockAbove;
	}
	
	public void setHasAttachableBlockAbove(boolean hasAttachableBlockAbove) {
		this.hasAttachableBlockAbove = hasAttachableBlockAbove;
		//markBlockForUpdate();
	}
	
	public int getSeedType() {
		return seedType;
	}
	
	public void setSeedType(int seedType) {
		this.seedType = seedType;
		//markBlockForUpdate();
	}

	public int getCorkType() {
		return corkType;
	}
	
	public void setCorkType(int corkType) {
		this.corkType = corkType;
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
        
        if ( tag.hasKey( "corkType" ) )
        {
        	corkType = tag.getInteger( "corkType" );
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
		
		tag.setInteger( "corkType", corkType ); 
		
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
        
        nbttagcompound1.setInteger( "corkType", corkType ); 
        
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
        
        if ( tag.hasKey( "corkType" ) )
        {
        	corkType = tag.getInteger( "corkType" );
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
			if (isStackStorageItem(stack))
			{
				setStorageStack(stack);
				setSeedType(stack.itemID);
			}
		}	
	}

	private void convertToSeedJar() {
		
		if (worldObj.getBlockId(xCoord, yCoord, zCoord) != SCDefs.seedJar.blockID)
		{
			worldObj.setBlockAndMetadata(xCoord, yCoord, zCoord, SCDefs.seedJar.blockID, worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
			
			SCTileEntitySeedJar seedJar = (SCTileEntitySeedJar) worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
			seedJar.setLabel(this.hasLabel());
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
		if (validItemList.contains( stack.itemID ))
		{
			return true;
		}
		else //If the item can't be stored
		{
			ArrayList<Integer> validSeedList = SCTileEntitySeedJar.getValidSeedList();
			
			SCTileEntityStorageJar storageJar = (SCTileEntityStorageJar) worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
			
			//Convert to a seed jar if the item can be stored in a seedjar and the storage jar is empty
			if (validSeedList.contains(stack.itemID) && storageJar != null && storageJar.getStorageStack() == null)
			{
				convertToSeedJar();
			}
			
			//else don't store items
			return false;
		}
	}
	
	public static boolean isStackStorageItem(ItemStack stack)
	{
		return validItemList.contains( stack.itemID );
	}

}
