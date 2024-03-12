package net.minecraft.src;

public class SCTileEntityWickerBasket extends FCTileEntityBasketWicker {
    @Override
    public void updateEntity()
    {
    	super.updateEntity();
    	
    	if ( (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.cauldron.blockID || worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == SCDefs.barrel.blockID) && worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord) != 3)
    	{
    		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			worldObj.setBlockAndMetadata(xCoord, yCoord, zCoord, FCBetterThanWolves.fcBlockBasketWicker.blockID, meta);
//			worldObj.setBlockTileEntity(xCoord, yCoord, zCoord, new FCTileEntityBasketWicker());		

    	}
    	
		UpdateVisualContentsState();
    }

    @Override
    public void SetStorageStack( ItemStack stack )
    {
    	if ( stack != null )
    	{
	    	m_storageStack = stack.copy();
	    	//SC added
	    	m_storageStack.stackSize = 1;
    	}
    	else
    	{
    		m_storageStack = null;
    	}
    	
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    }
    
}
