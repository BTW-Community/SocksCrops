package net.minecraft.src;

import java.util.ArrayList;

public class SCTileEntityJar extends TileEntity implements IInventory, FCITileEntityDataPacketHandler
{
	private static ArrayList<Integer> validItemList = SCBlockJar.getValidItemList();
	
	private ItemStack[] storageStacks = new ItemStack[4];
	private boolean hasLabel;
	private int contentType;
	
    @Override
    public void updateEntity()
    {    	
		TileEntity te = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
		if (te instanceof FCTileEntityHopper)
		{
			markBlockForUpdate();
		}
    }
    
    @Override
    public void onInventoryChanged() {
    	super.onInventoryChanged();
    	
    	if (storageStacks[0] != null )
    	{
    		setContentType( storageStacks[0].itemID );
    	}
    	else setContentType(0);
    	
    	markBlockForUpdate();
    }
	
	public void markBlockForUpdate() {
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );   
	}
	
	// --- Storage Stacks --- //
	private ItemStack[] getStorageStacks() {
		return storageStacks;
	}

	private void setStackInSlot(int slot, ItemStack stack) {
		this.storageStacks[slot] = stack;
	}
	
	// --- Label --- //
	public boolean hasLabel() {
		return hasLabel;
	}

	public void setHasLabel(boolean hasLabel) {
		this.hasLabel = hasLabel;
	}

	// --- ContentType --- //
	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	// --- NBT --- //	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
        if ( storageStacks[0] != null)
        {
            NBTTagCompound storageTag0 = new NBTTagCompound();
            
            storageStacks[0].writeToNBT( storageTag0 );
            
            tag.setCompoundTag( "storageStack0", storageTag0 );
        }
        
        if ( storageStacks[1] != null)
        {
            NBTTagCompound storageTag1 = new NBTTagCompound();
            
            storageStacks[1].writeToNBT( storageTag1 );
            
            tag.setCompoundTag( "storageStack1", storageTag1 );
        }
        
        if ( storageStacks[2] != null)
        {
            NBTTagCompound storageTag2 = new NBTTagCompound();
            
            storageStacks[2].writeToNBT( storageTag2 );
            
            tag.setCompoundTag( "storageStack2", storageTag2 );
        }
        
        if ( storageStacks[3] != null)
        {
            NBTTagCompound storageTag3 = new NBTTagCompound();
            
            storageStacks[3].writeToNBT( storageTag3 );
            
            tag.setCompoundTag( "storageStack3", storageTag3 );
        }
		
		tag.setBoolean( "hasLabel", hasLabel ); 
		
		tag.setInteger( "contentType", contentType ); 
		
		// force a visual update for the block since the above variables affect it        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
        NBTTagCompound storageTag0 = tag.getCompoundTag( "storageStack0" );

        if ( storageTag0 != null )
        {
        	storageStacks[0] = ItemStack.loadItemStackFromNBT( storageTag0 );
        }
        
        NBTTagCompound storageTag1 = tag.getCompoundTag( "storageStack1" );

        if ( storageTag1 != null )
        {
        	storageStacks[1] = ItemStack.loadItemStackFromNBT( storageTag1 );
        }
        
        NBTTagCompound storageTag2 = tag.getCompoundTag( "storageStack2" );

        if ( storageTag2 != null )
        {
        	storageStacks[2] = ItemStack.loadItemStackFromNBT( storageTag2 );
        }
        
        NBTTagCompound storageTag3 = tag.getCompoundTag( "storageStack3" );

        if ( storageTag3 != null )
        {
        	storageStacks[3] = ItemStack.loadItemStackFromNBT( storageTag3 );
        }
        
        if ( tag.hasKey( "hasLabel" ) )
        {
        	hasLabel = tag.getBoolean( "hasLabel" );
        }
        
        if ( tag.hasKey( "contentType" ) )
        {
        	contentType = tag.getInteger( "contentType" );
        }
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
        
		writeToNBT(tag);
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
	}
	
	// --- FCITileEntityDataPacketHandler --- //
	@Override
	public void readNBTFromPacket(NBTTagCompound tag)
	{		
		readFromNBT(tag);
	}
	
	// --- IInventory --- //
	@Override
	public int getSizeInventory()
	{		
		return 4;
	}
	
	@Override
	public int getInventoryStackLimit()
	{		
		return 16;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{		
		return storageStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		return FCUtilsInventory.DecrStackSize(this, slot, amount);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{		
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{	
		setStackInSlot(slot, stack);

		onInventoryChanged();

	}

	@Override
	public String getInvName()
	{		
		return "Jar";
	}

	@Override
	public boolean isInvNameLocalized()
	{		
		return false;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{		
        if ( worldObj.getBlockTileEntity( xCoord, yCoord, zCoord ) == this )
        {
            return ( player.getDistanceSq( (double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D ) 
            	<= 64D );
        }
        
        return false;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack stack)
	{
		System.out.println("ContentType: " + getContentType());
		
		int jarMeta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		boolean isSideways = SCBlockJar.isSideways(jarMeta);
		
		return !isSideways && validItemList.contains( stack.itemID )
				&& (getContentType() == stack.itemID || getContentType() == 0);
	}
	
	
	
	// --- Custom --- //
	
    public void ejectContentsInSlotTowardsFacing( int slot, int iFacing )
    {    		   
    	
    	if ( storageStacks[slot] != null )
    	{
    		if (!worldObj.isRemote)FCUtilsItem.EjectStackFromBlockTowardsFacing( worldObj, xCoord, yCoord, zCoord, getStackInSlot(slot), iFacing);
    		
    		storageStacks[slot] = null;
    	}
    	
		markBlockForUpdate();
		onInventoryChanged();
    }

}
