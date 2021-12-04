// FCMOD

package net.minecraft.src;

import java.util.List;

public class SCTileEntityCuttingBoard extends TileEntity implements FCITileEntityDataPacketHandler
{
	private ItemStack storageStack = null;
	private ItemStack cuttingStack = null;

	private boolean m_bContainsIngrediantsToGrind;
	
    
    public SCTileEntityCuttingBoard()
    {    	
    	super();
    	
    }

	@Override
    public void readFromNBT( NBTTagCompound tag )
    {
        super.readFromNBT( tag );
        
        NBTTagCompound storageTag = tag.getCompoundTag( "scStorageStack" );

        if ( storageTag != null )
        {
            storageStack = ItemStack.loadItemStackFromNBT( storageTag );
        }
        
        NBTTagCompound cuttingTag = tag.getCompoundTag( "scCuttingStack" );

        if ( cuttingTag != null )
        {
        	cuttingStack = ItemStack.loadItemStackFromNBT( cuttingTag );
        }
    }
    
    @Override
    public void writeToNBT( NBTTagCompound tag )
    {
        super.writeToNBT( tag );
        
        if ( storageStack != null)
        {
            NBTTagCompound storageTag = new NBTTagCompound();
            
            storageStack.writeToNBT( storageTag );
            
            tag.setCompoundTag( "scStorageStack", storageTag );
            
        }
        
        if ( cuttingStack != null)
        {
            NBTTagCompound cuttingTag = new NBTTagCompound();
            
            cuttingStack.writeToNBT( cuttingTag );
            
            tag.setCompoundTag( "scCuttingStack", cuttingTag );
            
        }
    } 
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        
        if ( storageStack != null)
        {
            NBTTagCompound storageTag = new NBTTagCompound();
            
            storageStack.writeToNBT( storageTag );
            
            tag.setCompoundTag( "x", storageTag );
        }
        
        if ( cuttingStack != null)
        {
            NBTTagCompound cuttingTag = new NBTTagCompound();
            
            cuttingStack.writeToNBT( cuttingTag );
            
            tag.setCompoundTag( "y", cuttingTag );
        }
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
    }
    
    
    //------------- FCITileEntityDataPacketHandler ------------//
    
    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {
        NBTTagCompound storageTag = tag.getCompoundTag( "x" );

        if ( storageTag != null )
        {
        	storageStack = ItemStack.loadItemStackFromNBT( storageTag );
        }        
        
        NBTTagCompound cuttingTag = tag.getCompoundTag( "y" );

        if ( cuttingTag != null )
        {
        	cuttingStack = ItemStack.loadItemStackFromNBT( cuttingTag );
        }        
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );        
    }    
    
    //------------- Class Specific Methods ------------//
    
    public void SetStorageStack( ItemStack stack ) 
    {
    	if ( stack != null )
    	{
	    	storageStack = stack.copy();
	    	
	    	storageStack.stackSize = 1;
    	}
    	else
    	{
    		storageStack = null;
    	}
    	
        worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    }
    
    public ItemStack GetStorageStack()
    {
    	return storageStack;
    }
    
    public void SetCutStack( ItemStack stack )
    {
    	if ( stack != null )
    	{
	    	cuttingStack = stack.copy();
	    	
	    	cuttingStack.stackSize = 1;
    	}
    	else
    	{
    		cuttingStack = null;
    	}
		
        worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    }
    
    public ItemStack GetCutStack()
    {
    	return cuttingStack;
    }
    
    public void EjectContents()
    {
    	if ( storageStack != null )
    	{
    		FCUtilsItem.EjectStackWithRandomOffset( worldObj, this.xCoord, yCoord, zCoord, storageStack );
    		
    		storageStack = null;
    	}
    	
    	if ( cuttingStack != null )
    	{
    		FCUtilsItem.EjectStackWithRandomOffset( worldObj, this.xCoord, yCoord, zCoord, cuttingStack );
    		
    		cuttingStack = null;
    	}
    }    
    
    //------------- Class specific methods ------------//
    
    @Override
    public void updateEntity()
    {
    	super.updateEntity();
    	

    	
    	if ( !worldObj.isRemote )
    	{
			int iBlockID = worldObj.getBlockId( xCoord, yCoord, zCoord );
			
			SCBlockCuttingBoard cuttingBoard = (SCBlockCuttingBoard)Block.blocksList[iBlockID];
			
			if (cuttingStack != null)
			{
				ValidateContentsForGrinding( cuttingBoard );
			}
			
			if ( m_bContainsIngrediantsToGrind )
			{
	    		GrindContents( cuttingBoard );
	    		worldObj.spawnParticle("fcwhitesmoke", xCoord, yCoord + 2/16D, zCoord, 0.0, 0.1, 0.0);
	    			
	    	}
    	}		
    }

    private boolean GrindContents( SCBlockCuttingBoard cuttingBoard )
    {
    	
    	
		if ( cuttingStack != null && SCCraftingManagerCuttingBoard.getInstance().HasRecipeForSingleIngredient( cuttingStack ) )		
		{
			List<ItemStack> outputList = SCCraftingManagerCuttingBoard.getInstance().GetCraftingResult( cuttingStack );

			if ( outputList != null )
			{
				
				if ( cuttingStack.itemID == FCBetterThanWolves.fcCompanionCube.blockID && cuttingStack.getItemDamage() == 0 )
				{
	    	        worldObj.playAuxSFX( FCBetterThanWolves.m_iCompanionCubeDeathAuxFXID, xCoord, yCoord, zCoord, 0 );
				}
	            
	            // eject the product stacks
	            
	            for ( int listIndex = 0; listIndex < outputList.size(); listIndex++ )
	            {
		    		ItemStack groundStack = ((ItemStack)outputList.get( listIndex )).copy();
		    		
		    		if ( groundStack != null )
		    		{
		    			
		    			EjectStackOnMilled( groundStack );
		    			
		    		}
	            }
	            
	            cuttingStack = null;
	            
	            return true;
			}
		}        	
    	
    	return false;
    }

    public void EjectStackOnMilled( ItemStack stack )
    {
    
    	FCUtilsItem.EjectStackFromBlockTowardsFacing( worldObj, xCoord, yCoord, zCoord, stack, SCBlockCuttingBoard.GetFacingPLS(blockMetadata) );
    	
    }

	private void ValidateContentsForGrinding( SCBlockCuttingBoard cuttingBoard )
    {    	
		
		//System.out.println("valid?");
		if ( cuttingStack != null )
		{
			if ( SCCraftingManagerCuttingBoard.getInstance().HasRecipeForSingleIngredient( cuttingStack ) )
			{			
				m_bContainsIngrediantsToGrind = true;
				
				int iItemIndex = cuttingStack.getItem().itemID;
				
				//System.out.println("yes");
			}

		}
		else
		{
			m_bContainsIngrediantsToGrind = false;

		}
		
		
    }


    public void EjectStorageContents( int iFacing )
    {
    	
    	if ( storageStack != null )
    	{
    		FCUtilsItem.EjectStackFromBlockTowardsFacing( worldObj, xCoord, yCoord, zCoord, storageStack, iFacing);
    		
    		storageStack = null;
    		
    	}
    	
    	worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    }

    public void AttemptToAddSingleItemToStorageFromStack( ItemStack stack )
    {
    	if ( storageStack == null )
    	{
    		storageStack = stack.copy();
    		
    		storageStack.stackSize = 1;
    		
    		stack.stackSize--;
    		
    	}
    	
    	worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    }
    
    public void AttemptToAddSingleItemToCuttingFromStack( ItemStack heldStack )
    {
    	
    	if ( storageStack != null
    			&& SCCraftingManagerCuttingBoard.getInstance().HasRecipeForSingleIngredient(heldStack) 
    			&& storageStack.itemID == Item.axeDiamond.itemID )
    	{
    		cuttingStack = heldStack.copy();
    		
    		cuttingStack.stackSize = 1;
    		
    		heldStack.stackSize--;
    		
    	}
    }

}