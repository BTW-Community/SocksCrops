package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class SCTileEntityChoppingBoard extends TileEntity implements FCITileEntityDataPacketHandler, IInventory {
	
	private ItemStack knifeStack;
	private ItemStack cuttingStack;
	
    @Override
    public void updateEntity()
    {
    	super.updateEntity();
    }
    	
//    	if ( !worldObj.isRemote )
//    	{
//			int iBlockID = worldObj.getBlockId( xCoord, yCoord, zCoord );
//			
//			SCBlockChoppingBoard choppingBoard = (SCBlockChoppingBoard)Block.blocksList[iBlockID];
//			
//			if ( choppingBoard != null && cuttingStack != null )
//			{
//    			//cutContents( choppingBoard );
//	    	}
//    	}
//    }
	
	//------------- Class specific ------------//
	
    
//    public boolean cutContents( SCBlockChoppingBoard cuttingBoard )
//    {
//		Item filterItem = knifeStack.getItem();
//		
//		SCCraftingManagerChoppingBoardFilterRecipe recipe = filterItem == null ?
//				null : SCCraftingManagerChoppingBoardFilter.instance.getRecipe(null, new ItemStack(filterItem));
//		
//		if (recipe != null) {
//			
//    		ItemStack[] output = recipe.getBoardOutput();
//    		
//			assert( output != null && output.length > 0 );
//			
//            for ( int listIndex = 0; listIndex < output.length; listIndex++ )
//            {
//	    		ItemStack cutStack = output[listIndex].copy();
//	    		
//	    		if ( cutStack != null )
//	    		{
//	    			int iFacing = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) + 2;
//	    			FCUtilsItem.EjectStackFromBlockTowardsFacing( worldObj, xCoord, yCoord, zCoord, cutStack, iFacing );
//
//
//	    		}
//            }
//            
//            setCuttingStack(null);
//            
//            int oldDamage = knifeStack.getItemDamage();
//            knifeStack.setItemDamage(oldDamage + 1);
//            
//            return true;
//			
//		}
    	
    	
//		SCCraftingManagerCuttingBoard manager = SCCraftingManagerCuttingBoard.getInstance();
//		
//    	if ( manager != null )
//    	{
//        	if ( manager.GetCraftingResult( this ) != null )
//        	{        		
//        		List<ItemStack> outputList = manager.ConsumeIngrediantsAndReturnResult( this );
//        		
//    			assert( outputList != null && outputList.size() > 0 );
//    			
//                for ( int listIndex = 0; listIndex < outputList.size(); listIndex++ )
//                {
//    	    		ItemStack cutStack = ((ItemStack)outputList.get( listIndex )).copy();
//    	    		
//    	    		if ( cutStack != null )
//    	    		{
//    	    			int iFacing = worldObj.rand.nextInt( 4 ) + 2;
//    	    			FCUtilsItem.EjectStackFromBlockTowardsFacing( worldObj, xCoord, yCoord, zCoord, cutStack, iFacing );
//
//    	    		}
//                }
//                
//                return true;
//    		}        	
//    	}
//    	
//    	return false;
//	}

	public ItemStack getKnifeStack() {
		return knifeStack;
	}
	
	public void setKnifeStack(ItemStack knifeStack) {
		this.knifeStack = knifeStack;
		markBlockForUpdate();
	}
	
	public ItemStack getCuttingStack() {
		return cuttingStack;
	}
	
	public void setCuttingStack(ItemStack cuttingStack) {
		markBlockForUpdate();
		this.cuttingStack = cuttingStack;
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
        
        NBTTagCompound cuttingTag = tag.getCompoundTag( "cuttingStack" );

        if ( cuttingTag != null )
        {
        	cuttingStack = ItemStack.loadItemStackFromNBT( cuttingTag );
        }

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
        
        if ( cuttingStack != null)
        {
            NBTTagCompound cuttingTag = new NBTTagCompound();
            
            cuttingStack.writeToNBT( cuttingTag );
            
            tag.setCompoundTag( "cuttingStack", cuttingTag );
        }
        
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 

    } 
    
    @Override
    public Packet getDescriptionPacket()
    {
    	NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        
    	if ( knifeStack != null)
        {
            NBTTagCompound knifeTag = new NBTTagCompound();
            
            knifeStack.writeToNBT( knifeTag );
            
            nbttagcompound1.setCompoundTag( "knifeStack", knifeTag );
        }
        
        if ( cuttingStack != null)
        {
            NBTTagCompound cuttingTag = new NBTTagCompound();
            
            cuttingStack.writeToNBT( cuttingTag );
            
            nbttagcompound1.setCompoundTag( "cuttingStack", cuttingTag );
        }
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, nbttagcompound1 );
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
        
        NBTTagCompound cuttingTag = tag.getCompoundTag( "cuttingStack" );

        if ( cuttingTag != null )
        {
        	cuttingStack = ItemStack.loadItemStackFromNBT( cuttingTag );
        }   
        
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
    }
    
    //------------- IInventory ------------//

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
    	if ( slot == 0 )
    	{
    		return knifeStack;
    	}
    	
    	else if ( slot == 1 )
    	{
    		return cuttingStack;
    	}
    	
        return null;
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
		if (slot == 0 ) setKnifeStack(stack);
		if (slot == 1 ) setCuttingStack(stack);
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

  
}
