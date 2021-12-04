//Copied and Modified from FCMOD/FCTileEntityVase

package net.minecraft.src;

public class SCTileEntitySeedJar extends TileEntity
    implements IInventory, FCITileEntityDataPacketHandler
{	
	private final int m_iVaseInventorySize = 1;
	private final int m_iVaseStackSizeLimit = 64;
	private final double m_dVaseMaxPlayerInteractionDist = 64D;
	
    private ItemStack m_VaseContents[];
	private int seedType;
	private boolean hasLabel;
    
    public SCTileEntitySeedJar()
    {
    	m_VaseContents = new ItemStack[m_iVaseInventorySize];
    	
    }
    
    @Override
    public void updateEntity()
    {
    	super.updateEntity();
    	seedType = GetSeedType();
    	hasLabel = HasLabel();
    	
    }
        
	@Override
    public int getSizeInventory()
    {
        return m_iVaseInventorySize;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return m_iVaseStackSizeLimit;
    }
    
    @Override
    public ItemStack getStackInSlot( int iSlot )
    {
        return getM_VaseContents()[iSlot];
    }

    @Override
    public ItemStack decrStackSize( int iSlot, int iAmount )
    {
    	return FCUtilsInventory.DecrStackSize( this, iSlot, iAmount );    	
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (getM_VaseContents()[par1] != null)
        {
            ItemStack itemstack = getM_VaseContents()[par1];
            getM_VaseContents()[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public void setInventorySlotContents( int iSlot, ItemStack itemstack )
    {
    	getM_VaseContents()[iSlot] = itemstack;
    	
        if( itemstack != null && itemstack.stackSize > getInventoryStackLimit() )
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
        
        onInventoryChanged();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

    }


    @Override
    public String getInvName()
    {
        return "Vase";
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        if( worldObj.getBlockTileEntity( xCoord, yCoord, zCoord ) != this )
        {
            return false;
        }
        
        return ( entityplayer.getDistanceSq( (
    		double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D ) 
        	<= m_dVaseMaxPlayerInteractionDist );
    }
    
    @Override
    public void openChest()
    {
    }

    @Override
    public void closeChest()
    {
    }

    public static int[] validIDs = {
    		Item.pumpkinSeeds.itemID,
    		Item.melonSeeds.itemID,
    		Item.netherStalkSeeds.itemID,
    		FCBetterThanWolves.fcItemHempSeeds.itemID,
    		FCBetterThanWolves.fcItemWheatSeeds.itemID,
    		FCBetterThanWolves.fcItemCarrotSeeds.itemID
    };
    
    @Override
    public void readFromNBT( NBTTagCompound nbttagcompound )
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        
        setM_VaseContents(new ItemStack[getSizeInventory()]);
        
        for ( int i = 0; i < nbttaglist.tagCount(); i++ )
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt( i );
            
            int j = nbttagcompound1.getByte( "Slot" ) & 0xff;
            
            if ( j >= 0 && j < getM_VaseContents().length )
            {
            	getM_VaseContents()[j] = ItemStack.loadItemStackFromNBT( nbttagcompound1 );
            }
        }
        
        if ( nbttagcompound.hasKey( "scSeedType" ) )
        {
        	seedType  = nbttagcompound.getInteger( "scSeedType" );
        }
        
        if ( nbttagcompound.hasKey( "scHasLabel" ) )
        {
        	hasLabel  = nbttagcompound.getBoolean( "scHasLabel" );
        }

    }
    
    @Override
    public void writeToNBT( NBTTagCompound nbttagcompound )
    {
        super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();
        
        for ( int i = 0; i < getM_VaseContents().length; i++ )
        {
            if ( getM_VaseContents()[i] != null )
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte( "Slot", (byte)i );
                
                getM_VaseContents()[i].writeToNBT( nbttagcompound1 );
                
                nbttaglist.appendTag( nbttagcompound1 );
            }
        }
        
        nbttagcompound.setTag( "Items", nbttaglist );
        
        nbttagcompound.setInteger( "scSeedType", seedType );
        nbttagcompound.setBoolean( "scHasLabel", hasLabel);
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
    	NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        NBTTagList nbttaglist = new NBTTagList();
        
        for ( int i = 0; i < getM_VaseContents().length; i++ )
        {
            if ( getM_VaseContents()[i] != null )
            {
                
                nbttagcompound1.setByte( "Slot", (byte)i );
                
                getM_VaseContents()[i].writeToNBT( nbttagcompound1 );
                
                nbttaglist.appendTag( nbttagcompound1 );
            }
        }
        
        nbttagcompound1.setTag( "Items", nbttaglist );
        nbttagcompound1.setInteger( "scSeedType", seedType );
        nbttagcompound1.setBoolean( "scHasLabel", hasLabel );
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, nbttagcompound1 );
    }
    
    
    //------------- FCITileEntityDataPacketHandler ------------//
    
    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {
        NBTTagList nbttaglist = tag.getTagList("Items");
        
        setM_VaseContents(new ItemStack[getSizeInventory()]);
        
        for ( int i = 0; i < nbttaglist.tagCount(); i++ )
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt( i );
            
            int j = nbttagcompound1.getByte( "Slot" ) & 0xff;
            
            if ( j >= 0 && j < getM_VaseContents().length )
            {
            	getM_VaseContents()[j] = ItemStack.loadItemStackFromNBT( nbttagcompound1 );
            }
        }
        
        seedType = tag.getInteger( "scSeedType" );
        hasLabel  = tag.getBoolean( "scHasLabel" );
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
    }   
    
    @Override
    public boolean isStackValidForSlot( int iSlot, ItemStack stack )
    {
        return true;
    }
    
    @Override
    public boolean isInvNameLocalized()
    {
    	return true;
    }
    
    public ItemStack GetStorageStack()
    {
    	return this.getM_VaseContents()[0];
    }

	public void AttemptToAddToStorageFromStack(ItemStack heldStack) {
		
//		ItemStack storage = m_VaseContents[0];
//		
//		if ( storage == null)
//    	{
//			storage = heldStack.copy();
//  		
//    	}

		    	
		this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );   
	}
	
	
    public int GetSeedType()
    {
    	return seedType;
    }
    
    public void SetSeedType( int seedType ) 
    {
    	this.seedType = seedType;
    	
    }
    
    public boolean HasLabel()
	{
		return hasLabel;
	}
	
    public void SetHasLabel( boolean hasLabel )
    {
    	this.hasLabel = hasLabel;
    	
    }

	public void applyLabel()
	{
		SetHasLabel(true);
		this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
	}

	public ItemStack[] getM_VaseContents() {
		return m_VaseContents;
	}

	public void setM_VaseContents(ItemStack m_VaseContents[]) {
		this.m_VaseContents = m_VaseContents;
	}

	

    public void EjectStorageContents( int iFacing )
    {
    	
    	if (this.m_VaseContents[0] != null )
    	{
    		FCUtilsItem.EjectStackFromBlockTowardsFacing( worldObj, xCoord, yCoord, zCoord, getStackInSlot(0), iFacing);
    		
    		this.m_VaseContents[0] = null;
    		
    	}
    	
    	worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );  
    }
}
