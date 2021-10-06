// FCMOD

package net.minecraft.src;

import java.util.List;

public class SCTileEntityCuttingBoard extends TileEntity
	implements FCITileEntityDataPacketHandler
{
	private ItemStack m_storageStack = null;
	private boolean m_bContainsIngrediantsToCut;
	private boolean m_bValidateContentsOnUpdate;
	
    public SCTileEntityCuttingBoard()
    {
    	m_bValidateContentsOnUpdate = true;
    }
    
    @Override
    public void updateEntity()
    {
    	
    	//System.out.println("updateEntity");
    	
    	if (m_storageStack != null) {
	    	
	    	if ( !worldObj.isRemote )
	    	{
				int iBlockID = worldObj.getBlockId( xCoord, yCoord, zCoord );
				
				SCBlockCuttingBoard cuttingBoard = (SCBlockCuttingBoard)Block.blocksList[iBlockID];
				
				if ( m_bValidateContentsOnUpdate )
				{
					ValidateContentsForCutting( cuttingBoard );
				}
				
				if ( m_bContainsIngrediantsToCut )
				{
	
		    		CutContents( cuttingBoard );
		    			
		    		m_bValidateContentsOnUpdate = true;
		    				
		    	}
	    	}
    	}
    }	
	

    private boolean CutContents(SCBlockCuttingBoard cuttingBoard) {
    	System.out.println("CutContents");
    	if ( m_storageStack != null  && FCCraftingManagerMillStone.getInstance().HasRecipeForSingleIngredient( m_storageStack ))		
		{
			List<ItemStack> outputList = SCCraftingManagerCuttingBoard.instance.GetCraftingResult( m_storageStack );

			if ( outputList != null )
			{
				if ( m_storageStack.itemID == FCBetterThanWolves.fcCompanionCube.blockID && m_storageStack.getItemDamage() == 0 )
				{
	    	        worldObj.playAuxSFX( FCBetterThanWolves.m_iCompanionCubeDeathAuxFXID, xCoord, yCoord, zCoord, 0 );
				}
	            
	            // eject the product stacks
	            
	            for ( int listIndex = 0; listIndex < outputList.size(); listIndex++ )
	            {
		    		ItemStack groundStack = ((ItemStack)outputList.get( listIndex )).copy();
		    		
		    		if ( groundStack != null )
		    		{
		    			EjectStackOnCut( groundStack );
		    		}
	            }
	            
	            m_storageStack = null;
	            
	            return true;
			}
		}        	
    	
    	return false;
		
	}

	
    
    
    
    private void EjectStackOnCut(ItemStack groundStack) {
    	if ( groundStack != null )
    	{
    		FCUtilsItem.EjectStackWithRandomOffset( worldObj, this.xCoord, yCoord, zCoord, groundStack );
    		
    		groundStack = null;
    	}
		
	}

	private void ValidateContentsForCutting(SCBlockCuttingBoard cuttingBoard) {
	
    	int iNewGrindingType = SCBlockCuttingBoard.m_iContentsNothing;
    	
    	if ( m_storageStack != null )		
		{
			if ( SCCraftingManagerCuttingBoard.getInstance().HasRecipeForSingleIngredient( m_storageStack ) )
			{			
				m_bContainsIngrediantsToCut = true;
				//System.out.println("m_bContainsIngrediantsToCut = true");
				
				int iItemIndex = m_storageStack.getItem().itemID;
				
				iNewGrindingType = SCBlockCuttingBoard.m_iContentsNormalCutting;

			}
			else
			{
				iNewGrindingType = SCBlockCuttingBoard.m_iContentsJammed;
				//System.out.println("m_bContainsIngrediantsToCut = false");
				
				m_bContainsIngrediantsToCut = false;
			}
		}
		else
		{
			m_bContainsIngrediantsToCut = false;
		}
	}

	@Override
    public void readFromNBT( NBTTagCompound tag )
    {
        super.readFromNBT( tag );
        
        NBTTagCompound storageTag = tag.getCompoundTag( "fcStorageStack" );

        if ( storageTag != null )
        {
            m_storageStack = ItemStack.loadItemStackFromNBT( storageTag );
        }
    }
    
    @Override
    public void writeToNBT( NBTTagCompound tag )
    {
        super.writeToNBT( tag );
        
        if ( m_storageStack != null)
        {
            NBTTagCompound storageTag = new NBTTagCompound();
            
            m_storageStack.writeToNBT( storageTag );
            
            tag.setCompoundTag( "fcStorageStack", storageTag );
            
        }
    }

    public void EjectContents()
    {
    	if ( m_storageStack != null )
    	{
    		FCUtilsItem.EjectStackWithRandomOffset( worldObj, this.xCoord, yCoord, zCoord, m_storageStack );
    		  
    		
    		m_storageStack = null;
    	}
    } 
    
    public void EjectCraftingResult(ItemStack stack)
    {
    	if ( m_storageStack != null )
    	{
    		FCUtilsItem.EjectStackWithRandomOffset( worldObj, this.xCoord, yCoord, zCoord, stack );
    		
    		m_storageStack = null;
    		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    	}
    }   
    
    //------------- FCITileEntityDataPacketHandler ------------//
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        
        if ( m_storageStack != null)
        {
            NBTTagCompound storageTag = new NBTTagCompound();
            
            m_storageStack.writeToNBT( storageTag );
            
            tag.setCompoundTag( "x", storageTag );
        }
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
    }

    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {
        NBTTagCompound storageTag = tag.getCompoundTag( "x" );

        if ( storageTag != null )
        {
        	m_storageStack = ItemStack.loadItemStackFromNBT( storageTag );
        }        
        // force a visual update for the block since the above variables affect it
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );   
       	
    }    
    
//    @Override
//    public boolean ShouldStartClosingServerSide()
//    {
//    	return !worldObj.isRemote && worldObj.getClosestPlayer( xCoord, yCoord, zCoord, m_fMaxKeepOpenRange ) == null;
//    }
    
    //------------- Class Specific Methods ------------//

    
    public void SetStorageStack( ItemStack stack )
    {
    	if ( stack != null )
    	{
	    	m_storageStack = stack.copy();
	    	m_storageStack.stackSize = 1;
    	}
    	else
    	{
    		m_storageStack = null;
    	}
    	
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    }
    
    public ItemStack GetStorageStack()
    {
    	return m_storageStack;
    }
    
//    private void UpdateVisualContentsState()
//    {
//    	if ( !worldObj.isRemote )
//    	{
//			// validate the block's indication of contents in metadata, since it wasn't included in the initial releases
//			
//			boolean bHasContents = SCDefs.cuttingBoard.GetHasContents( worldObj, xCoord, yCoord, zCoord );
//			
//			if ( bHasContents != ( m_storageStack != null ) )
//			{
//				FCBetterThanWolves.fcBlockBasketWicker.SetHasContents( worldObj, xCoord, yCoord, zCoord, m_storageStack != null );
//			}
//    	}
//    }
    
	//----------- Client Side Functionality -----------//
}