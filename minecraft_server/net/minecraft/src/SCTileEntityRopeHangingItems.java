package net.minecraft.src;

public class SCTileEntityRopeHangingItems extends TileEntity implements FCITileEntityDataPacketHandler, IInventory {

	private ItemStack storageStack;
	
	private int[] itemRotation = { 0 };
		
	public ItemStack getStorageStack() {
		return storageStack;
	}
	
	public void setStorageStack(ItemStack stackToStore) {
		this.storageStack = stackToStore;
		markBlockForUpdate();
	}
  	
  	public void setItemRotation(int slot, int rotation) {
  		this.itemRotation[slot] = rotation;
  	}
  	
  	public int[] getItemRotation()
  	{
  		return this.itemRotation;
  	}
	
	public void markBlockForUpdate()
	{		
		this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );   
	}	
	
	//------------- NBT ------------//
	
    @Override
    public void readFromNBT( NBTTagCompound tag )
    {
        super.readFromNBT( tag );
        
        
        NBTTagCompound storageTag = tag.getCompoundTag( "storageStack" );

        if ( storageTag != null )
        {
        	storageStack = ItemStack.loadItemStackFromNBT( storageTag );
        }
        
        this.itemRotation = tag.getIntArray("Rot");       

    }
	
    @Override
    public void writeToNBT( NBTTagCompound tag )
    {
        super.writeToNBT( tag );
        
        if ( storageStack != null)
        {
            NBTTagCompound storageTag = new NBTTagCompound();
            
            storageStack.writeToNBT( storageTag );
            
            tag.setCompoundTag( "storageStack", storageTag );
        }
        
        
        tag.setIntArray("Rot", this.itemRotation);
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 

    } 
    
    @Override
    public Packet getDescriptionPacket()
    {
    	NBTTagCompound tag = new NBTTagCompound();
        
    	if ( storageStack != null)
        {
            NBTTagCompound storageTag = new NBTTagCompound();
            
            storageStack.writeToNBT( storageTag );
            
            tag.setCompoundTag( "storageStack", storageTag );
        }
        
        
    	tag.setIntArray("Rot", this.itemRotation);
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
    }
    
    
    //------------- FCITileEntityDataPacketHandler ------------//
    
    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {
        NBTTagCompound storageTag = tag.getCompoundTag( "storageStack" );

        if ( storageTag != null )
        {
        	storageStack = ItemStack.loadItemStackFromNBT( storageTag );
        }
        
        this.itemRotation = tag.getIntArray("Rot");
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
    }
    
    //------------- Inventory ------------//

  	@Override
  	public int getSizeInventory() {
  		return 1;
  	}

  	@Override
  	public ItemStack getStackInSlot(int slot) {
  		return storageStack;
  	}

      @Override
      public ItemStack decrStackSize( int iSlot, int iAmount )
      {
      	return FCUtilsInventory.DecrStackSize( this, iSlot, iAmount );    	
      }

  	@Override
  	public ItemStack getStackInSlotOnClosing(int var1) {
  		return null;
  	}

  	@Override
  	public void setInventorySlotContents(int slot, ItemStack stack)
  	{
  		setStorageStack(stack);
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
  	public void openChest() {
  		
  	}

  	@Override
  	public void closeChest() {
  		
  	}

  	@Override
  	public boolean isStackValidForSlot(int slot, ItemStack stack)
  	{		
  		return true;
  	}

}
