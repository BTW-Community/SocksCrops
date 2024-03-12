package net.minecraft.src;

public class SCTileEntityPan extends TileEntity implements FCITileEntityDataPacketHandler, IInventory
{

    /** The pan's rotation. */
    private int panRotation;
    
	private ItemStack cookStack;
	private ItemStack inputStack;
	
	
	private int cookCounter = 0;
	
	private final int panBurnTimeMultiplier = 4; //campfireBurnTimeMultiplier = 8
	
    private final int timeToCook = ( TileEntityFurnace.m_iDefaultCookTime * 
    		panBurnTimeMultiplier * 
        	3 / 2 ); // this line represents efficiency relative to furnace cooking
    
    private final int timeToBurnFood = ( timeToCook / 2 );
    
    private int cookBurningCounter = 0;
    
    
	//------------- Cooking ------------//
	
    @Override
    public void updateEntity()
    {
    	super.updateEntity();
    	
    	if ( !worldObj.isRemote )
    	{
    		int fireLevel = getFireLevel();
    		
    		
          	if ( fireLevel > 0 )
        	{
	    		updateCookState();
        	}
    	}

    }

    
    private void updateCookState()
	{
		if ( cookStack != null )
		{
			this.inputStack = cookStack;
			
			int fireLevel = getFireLevel();
			
			if ( fireLevel >= 2 )
			{				
				SCCraftingManagerPanCookingRecipe recipe = SCCraftingManagerPanCooking.instance.getRecipe( cookStack );
				
				if ( recipe != null )
				{
					cookCounter++;
					
					if ( cookCounter >= timeToCook )
					{
						setCookStack( recipe.getOutput() );
						
						cookCounter = 0;
						
						// don't reset burn counter here, as the food can still burn after cooking
					}
				}
				
				if ( fireLevel >= 3 && cookStack.itemID != FCBetterThanWolves.fcItemMeatBurned.itemID )
				{
					cookBurningCounter++;
					
					if ( cookBurningCounter >= timeToBurnFood )
					{
						setCookStack( recipe.getBurnedOutput() );
						
						cookCounter = 0;
						cookBurningCounter = 0;
					}
				}
			}
		}
	}


	private int getFireLevel() {
		
		int blockBelow = worldObj.getBlockId(xCoord, yCoord - 1 , zCoord);
		
		if (blockBelow == FCBetterThanWolves.fcBlockCampfireSmall.blockID) return 1;
		else if (blockBelow == FCBetterThanWolves.fcBlockCampfireMedium.blockID) return 2;
		else if (blockBelow == FCBetterThanWolves.fcBlockCampfireLarge.blockID) return 3;
		
		else if (blockBelow == FCBetterThanWolves.fcBlockFurnaceBrickBurning.blockID) return 2;
		
		else return 0;
	}

	public boolean isFoodCooking()
	{
		if ( cookStack != null && getFireLevel() >= 2 )
		{			
			if ( SCCraftingManagerPanCooking.instance.getRecipe( cookStack ) != null )
			{
				System.out.println("cooking");
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isFoodBurning()
	{
		
		if ( cookStack != null && getFireLevel() >= 3)
		{
			SCCraftingManagerPanCookingRecipe recipe = SCCraftingManagerPanCooking.instance.getRecipe( cookStack ) ;

			if (recipe != null && cookStack.itemID != recipe.getBurnedOutput().itemID )
			{
				System.out.println("burning");
				return true;
			}

		}
		
		return false;
	}

	public void markBlockForUpdate() {
		
		this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );   
	}
    
	public ItemStack getCookStack() {
		return cookStack;
	}
	
	public void setCookStack(ItemStack knifeStack) {
		this.cookStack = knifeStack;
		markBlockForUpdate();
	}
	
    //------------- IInventory ------------//

	@Override
	public int getSizeInventory() {
		return 1;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
    	return cookStack;
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
		if (slot == 0 ) setCookStack(stack);
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
	public boolean isStackValidForSlot(int slot, ItemStack stack) {
		
		if (isValidCookItem(stack))
		{
			return true;
		}
		return false;
	}
	
	public boolean isValidCookItem( ItemStack stack )
	{
		if ( SCCraftingManagerPanCooking.instance.getRecipe( stack ) != null )
		{
			return true;
		}
		
		return false;
	}
	
	//------------- NBT ------------//
	
    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setByte("panRotation", (byte)(this.panRotation & 255));
        
        if ( cookStack != null)
        {
            NBTTagCompound cookTag = new NBTTagCompound();
            
            cookStack.writeToNBT( cookTag );
            
            tag.setCompoundTag( "cookStack", cookTag );
        }
        
        tag.setInteger( "cookCounter", cookCounter );
        tag.setInteger( "cookBurning", cookBurningCounter );
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        this.panRotation = tag.getByte("panRotation");
        
        NBTTagCompound cookTag = tag.getCompoundTag( "cookStack" );

        if ( cookTag != null )
        {
        	cookStack = ItemStack.loadItemStackFromNBT( cookTag );
        }
        
        if ( tag.hasKey( "cookCounter" ) )
        {
        	cookCounter = tag.getInteger( "cookCounter" );
        }
        
        if ( tag.hasKey( "cookBurning" ) )
        {
        	cookBurningCounter = tag.getInteger( "cookBurning" );
        }    
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }
    

	@Override
	public void readNBTFromPacket(NBTTagCompound nbttagcompound) {
		this.readFromNBT(nbttagcompound);
	}


    public int getSkullRotation()
    {
        return this.panRotation;
    }

    /**
     * Set the skull's rotation
     */
    public void setSkullRotation(int par1)
    {
        this.panRotation = par1;
    }

    
    // FCMOD: Code added
    public int GetSkullRotationServerSafe()
    {
    	return this.panRotation;
}
    // END FCMOD


}
