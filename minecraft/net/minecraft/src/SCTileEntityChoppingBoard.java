package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class SCTileEntityChoppingBoard extends TileEntity implements FCITileEntityDataPacketHandler, IInventory {
	
	private ItemStack knifeStack;
	
	private int itemRotation = 0;

	public int ticks = 0;
	
	private int woodType;
	
    @Override
    public void updateEntity()
    {   	
    	super.updateEntity();
    	
    	EntityPlayer player = this.worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 12);
    	
    	if (player != null && knifeStack != null && knifeStack.itemID == FCBetterThanWolves.fcItemDung.itemID)
    	{
    		if (ticks > 30)
    		{
    			ticks = 0;
    		}
    		
    		ticks++;
    	}
    	
    	
    	//System.out.println(ticks);
    }
    	
	public ItemStack getKnifeStack() {
		return knifeStack;
	}
	
	public void setKnifeStack(ItemStack knifeStack) {
		this.knifeStack = knifeStack;
		markBlockForUpdate();
	}
	
	public void markBlockForUpdate() {
		
		this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );   
	}
	
	//------------- NBT ------------//
	
    @Override
    public void readFromNBT( NBTTagCompound tag )
    {
        super.readFromNBT( tag );
        
        
        NBTTagCompound knifeTag = tag.getCompoundTag( "knifeStack" );

        if ( knifeTag != null )
        {
        	knifeStack = ItemStack.loadItemStackFromNBT( knifeTag );
        }
        
        this.itemRotation = tag.getByte("Rot");
        
        this.ticks = tag.getInteger("Ticks");
        
        this.woodType = tag.getInteger("woodType");
        

    }
	
    @Override
    public void writeToNBT( NBTTagCompound tag )
    {
        super.writeToNBT( tag );
        
        if ( knifeStack != null)
        {
            NBTTagCompound knifeTag = new NBTTagCompound();
            
            knifeStack.writeToNBT( knifeTag );
            
            tag.setCompoundTag( "knifeStack", knifeTag );
        }
        
        
        tag.setByte("Rot", (byte)(this.itemRotation & 255));
        
        tag.setInteger("Ticks", this.ticks);

        tag.setInteger("woodType", this.woodType);
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 

    } 
    
    @Override
    public Packet getDescriptionPacket()
    {
    	NBTTagCompound tag = new NBTTagCompound();
        
    	if ( knifeStack != null)
        {
            NBTTagCompound knifeTag = new NBTTagCompound();
            
            knifeStack.writeToNBT( knifeTag );
            
            tag.setCompoundTag( "knifeStack", knifeTag );
        }
        
        
        tag.setByte("Rot", (byte)(this.itemRotation & 255));
        
        tag.setInteger("Ticks", this.ticks);
        
        tag.setInteger("woodType", this.woodType);
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
    }
    
    
    //------------- FCITileEntityDataPacketHandler ------------//
    
    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {
        NBTTagCompound knifeTag = tag.getCompoundTag( "knifeStack" );

        if ( knifeTag != null )
        {
        	knifeStack = ItemStack.loadItemStackFromNBT( knifeTag );
        }
        
        this.itemRotation = tag.getByte("Rot");
        
        this.ticks = tag.getInteger("Ticks");
        
        this.woodType = tag.getInteger("woodType");
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
    }
    
    //------------- Inventory ------------//

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return knifeStack;
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
		setKnifeStack(stack);
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
		if (slot == 0) return true;
		else return false;
	}
	
	protected boolean specialRenderer = false;


	public boolean hasItemSpecialRenderer() {
		return specialRenderer;
	}

	public void setItemHasSpecialRenderer(boolean boo) {
		this.specialRenderer = boo;
	}

	public void setItemRotation(int rotation) {
		this.itemRotation = rotation;
	}
	
	public int getItemRotation()
	{
		return this.itemRotation;
	}

	public void setWoodType(int type) {
		this.woodType = type;
	}
	
	public int getWoodType()
	{
		return this.woodType;
	}
  
}
