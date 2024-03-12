package net.minecraft.src;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SCTileEntityJuicer extends FCTileEntityMillStone implements FCITileEntityDataPacketHandler {
	
	public ItemStack m_stackMilling = null;
	public ItemStack lastStackMilled = null;

	private final double m_dMaxPlayerInteractionDistSq = 64D;
	
    private final int m_iTimeToGrind = 20; //200
    
    private boolean m_bValidateContentsOnUpdate;
    private boolean m_bContainsIngrediantsToGrind;

    public int m_iGrindCounter; 
    
    private int timesMilled;
    
    /**
     * Contains fruits that can be juiced with the corresponding juice color
     */
    public static HashMap<ItemStack, Integer> validFruits = new HashMap<>();
    
    static {
    	validFruits.put(new ItemStack(Item.appleRed), 0xFFFF00);
    	validFruits.put(new ItemStack(SCDefs.cherry), 0xFF0000);
    }
    
	public SCTileEntityJuicer() {
		m_iGrindCounter = 0;
    	
    	m_bValidateContentsOnUpdate = true;
    	
    	timesMilled = 0;
	}
	
	@Override
    public void updateEntity()
    {
    	//super.updateEntity();
    	
    	if ( !worldObj.isRemote )
    	{
			int iBlockID = worldObj.getBlockId( xCoord, yCoord, zCoord );
			
			SCBlockJuicer juicerBlock = (SCBlockJuicer)Block.blocksList[iBlockID];
			
			if ( m_bValidateContentsOnUpdate )
			{
				ValidateContentsForGrinding( juicerBlock );
			}
			
			if ( m_bContainsIngrediantsToGrind && juicerBlock.GetIsMechanicalOn( worldObj, xCoord, yCoord, zCoord ) )
			{
				m_iGrindCounter++;
				
	    		if ( m_iGrindCounter >= m_iTimeToGrind )
	    		{
	    			GrindContents( juicerBlock );
	    			
	    			m_iGrindCounter = 0;
	    			m_bValidateContentsOnUpdate = true;
	    		}
	    		
	    		//CheckForNauseateNearbyPlayers( millStoneBlock );    		
	    	}
    	}

    }

	
    @Override
    public void readFromNBT( NBTTagCompound tag )
    {
        super.readFromNBT( tag );
        
        if ( tag.hasKey( "timesMilled" ) )
        {
        	timesMilled = tag.getInteger( "timesMilled" );
        }
        
        NBTTagCompound lastStackedMilledTag = tag.getCompoundTag( "lastStackedMilled" );

        if ( lastStackedMilledTag != null )
        {
        	lastStackMilled = ItemStack.loadItemStackFromNBT( lastStackedMilledTag );
        }
    }
    
    @Override
    public void writeToNBT( NBTTagCompound tag )
    {
        super.writeToNBT(tag);
        
        tag.setInteger( "timesMilled", timesMilled );   
        
        if ( lastStackMilled != null)
        {
            NBTTagCompound lastStackedMilledTag = new NBTTagCompound();
            
            lastStackMilled.writeToNBT( lastStackedMilledTag );
            
            tag.setCompoundTag( "lastStackedMilled", lastStackedMilledTag );
        }
    }
    
    @Override
	public Packet getDescriptionPacket()
	{
    	NBTTagCompound tag = new NBTTagCompound();
    	super.writeToNBT(tag);
        
        tag.setInteger( "timesMilled", timesMilled );   
        
        if ( lastStackMilled != null)
        {
            NBTTagCompound lastStackedMilledTag = new NBTTagCompound();
            
            lastStackMilled.writeToNBT( lastStackedMilledTag );
            
            tag.setCompoundTag( "lastStackedMilled", lastStackedMilledTag );
        }
        
    	return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
	}
    
    @Override
	public void readNBTFromPacket(NBTTagCompound tag)
	{
    	super.readFromNBT( tag );
        
        if ( tag.hasKey( "timesMilled" ) )
        {
        	timesMilled = tag.getInteger( "timesMilled" );
        }
        
        NBTTagCompound lastStackedMilledTag = tag.getCompoundTag( "lastStackedMilled" );

        if ( lastStackedMilledTag != null )
        {
        	lastStackMilled = ItemStack.loadItemStackFromNBT( lastStackedMilledTag );
        }
	}
 //------------- IInventory implementation -------------//
    
    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot( int iSlot )
    {
    	if ( iSlot == 0 )
    	{
    		return m_stackMilling;
    	}
    	
        return null;
    }

    @Override
    public ItemStack decrStackSize( int iSlot, int iAmount )
    {
    	return FCUtilsInventory.DecrStackSize( this, iSlot, iAmount );    	
    }

    @Override
    public ItemStack getStackInSlotOnClosing( int iSlot )
    {
    	if ( iSlot == 0 && m_stackMilling != null )
    	{
            ItemStack itemstack = m_stackMilling;
            
            m_stackMilling = null;
            
            return itemstack;
        }
    	
        return null;
    }
    
	@Override
    public void setInventorySlotContents( int iSlot, ItemStack stack )
    {
    	if ( iSlot == 0 )
    	{
    		m_stackMilling = stack;
	    	
	        if( stack != null && stack.stackSize > getInventoryStackLimit() )
	        {
	            stack.stackSize = getInventoryStackLimit();
	        }
	        
	        onInventoryChanged();
    	}
    }
	
	@Override
    public void onInventoryChanged()
    {
    	super.onInventoryChanged();
    	
    	if ( worldObj != null && !worldObj.isRemote )
    	{
	    	if ( ContainsWholeCompanionCube() )
	    	{
	            worldObj.playSoundEffect( (float)xCoord + 0.5F, (float)yCoord + 0.5F, (float)zCoord + 0.5F, 
	        		"mob.wolf.whine", 
	        		0.5F, 2.6F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.8F);
	    	}
	    	
	    	m_bValidateContentsOnUpdate = true;
    	}
    }
	
//------------- Class Specific Methods ------------//
    
    public void EjectStackOnMilled( ItemStack stack )
    {
    	int iFacing = 2 + worldObj.rand.nextInt( 4 ); // random direction to the sides
    	
    	Vec3 ejectPos = Vec3.createVectorHelper( worldObj.rand.nextDouble() * 1.25F - 0.125F, 
    		worldObj.rand.nextFloat() * ( 1F / 16F ) + ( 7F / 16F ), 
    		-0.2F );
    	
    	ejectPos.RotateAsBlockPosAroundJToFacing( iFacing );
    	
        EntityItem entity = (EntityItem) EntityList.createEntityOfType(EntityItem.class, worldObj, xCoord + ejectPos.xCoord, 
        		yCoord + ejectPos.yCoord, zCoord + ejectPos.zCoord, stack );

    	Vec3 ejectVel = Vec3.createVectorHelper( worldObj.rand.nextGaussian() * 0.025D, 
    		worldObj.rand.nextGaussian() * 0.025D + 0.1F, 
    		-0.06D + worldObj.rand.nextGaussian() * 0.04D );
    	
    	ejectVel.RotateAsVectorAroundJToFacing( iFacing );
    	
        entity.motionX = ejectVel.xCoord;
        entity.motionY = ejectVel.yCoord;
        entity.motionZ = ejectVel.zCoord;
        
        entity.delayBeforeCanPickup = 10;
        
        worldObj.spawnEntityInWorld( entity );
    }
    
	private void PlaceLiquidBelow(ItemStack groundStack) {
		int blockIDBelow = worldObj.getBlockId(xCoord, yCoord - 1, zCoord);
		
		if (blockIDBelow == 0)
		{
			int blockToPlace = Block.blocksList[groundStack.itemID].blockID;
			worldObj.setBlock(xCoord, yCoord - 1, zCoord, blockToPlace);
		}
	}
    
    public int getGrindProgressScaled( int iScale )
    {
        return ( m_iGrindCounter * iScale ) / m_iTimeToGrind;
    }    
    
    public boolean IsGrinding()
    {
    	return m_iGrindCounter > 0;
    }
    
    public boolean ContainsWholeCompanionCube()
    {    	
		return m_stackMilling != null && m_stackMilling.itemID == FCBetterThanWolves.fcCompanionCube.blockID &&
			m_stackMilling.getItemDamage() == 0;
    }
	
	private boolean GrindContents( SCBlockJuicer millStoneBlock )
    {
		if ( m_stackMilling != null && SCCraftingManagerJuicer.getInstance().HasRecipeForSingleIngredient( m_stackMilling ) )		
		{
			List<ItemStack> outputList = SCCraftingManagerJuicer.getInstance().GetCraftingResult( m_stackMilling );

			if ( outputList != null )
			{
				if ( m_stackMilling.itemID == FCBetterThanWolves.fcCompanionCube.blockID && m_stackMilling.getItemDamage() == 0 )
				{
	    	        worldObj.playAuxSFX( FCBetterThanWolves.m_iCompanionCubeDeathAuxFXID, xCoord, yCoord, zCoord, 0 );
				}
	            
	            // eject the product stacks				
	            for ( int listIndex = 0; listIndex < outputList.size(); listIndex++ )
	            {
		    		ItemStack groundStack = ((ItemStack)outputList.get( listIndex )).copy();
		    		
		    		if ( groundStack != null )
		    		{
		    			if (lastStackMilled == null || !m_stackMilling.isItemEqual(lastStackMilled))
		    			{
		    				timesMilled = 0;
		    			}
		    			
		    			timesMilled++;
		    			
		    			if (SCCraftingManagerJuicer.getInstance().GetTimesMilled( this ) == timesMilled)
		    			{
		    				
		    				if (Block.blocksList[groundStack.itemID] instanceof SCBlockLiquid)
		    				{
		    					PlaceLiquidBelow( groundStack);
		    				}
		    				else {
		    					EjectStackOnMilled( groundStack );			    				
		    				}
		    				
		    				timesMilled = 0;
		    			}
		    		}
	            }
	            lastStackMilled = m_stackMilling.copy();
	            m_stackMilling = null;
	            
	            return true;
			}
		}        	
    	
    	return false;
    }
	


	private void ValidateContentsForGrinding( SCBlockJuicer juicerBlock )
    {
    	int iOldGrindingType = juicerBlock.GetCurrentGrindingType( worldObj, xCoord, yCoord, zCoord );
    	int iNewGrindingType = SCBlockJuicer.m_iContentsNothing;
    	
    	//MigrateLegacyInventory();
    	
		if ( m_stackMilling != null )		
		{
			if ( SCCraftingManagerJuicer.getInstance().HasRecipeForSingleIngredient( m_stackMilling ) )
			{			
				m_bContainsIngrediantsToGrind = true;
				
				int iItemIndex = m_stackMilling.getItem().itemID;
				
				if ( iItemIndex == FCBetterThanWolves.fcCompanionCube.blockID && m_stackMilling.getItemDamage() == 0 )
				{
					iNewGrindingType = SCBlockJuicer.m_iContentsCompanionCube;
				}
				else if ( iItemIndex == Block.netherrack.blockID )
				{
					iNewGrindingType = SCBlockJuicer.m_iContentsNetherrack;
				}
				else
				{
					iNewGrindingType = SCBlockJuicer.m_iContentsNormalGrinding;
				}
			}
			else
			{
				iNewGrindingType = SCBlockJuicer.m_iContentsJammed;
				
				m_iGrindCounter = 0;
				
				m_bContainsIngrediantsToGrind = false;
			}
		}
		else
		{
			m_iGrindCounter = 0;
			
			m_bContainsIngrediantsToGrind = false;
		}
		
		m_bValidateContentsOnUpdate = false;
		
    	if ( iOldGrindingType != iNewGrindingType )
    	{
    		juicerBlock.SetCurrentGrindingType( worldObj, xCoord, yCoord, zCoord, iNewGrindingType );
    	}
    }
	
	public void EjectContents( int iFacing )
    {
    	if ( iFacing < 2 )
    	{
    		// always eject towards the sides
    		
    		iFacing = worldObj.rand.nextInt( 4 ) + 2;
    	}
        
    	if ( m_stackMilling != null )
    	{
    		FCUtilsItem.EjectStackFromBlockTowardsFacing( worldObj, xCoord, yCoord, zCoord, m_stackMilling, iFacing );
    		
    		m_stackMilling = null;
    		
	        onInventoryChanged();
    	}
    }
    
    public void AttemptToAddSingleItemFromStack( ItemStack stack )
    {
    	if ( m_stackMilling == null )
    	{
    		m_stackMilling = stack.copy();
    		
    		m_stackMilling.stackSize = 1;
    		
    		stack.stackSize--;
    		
	        onInventoryChanged();
    	}
    }
    
    @Override
    public int getInventoryStackLimit()
    {
        return 8; // only single stack items can fit in the millstone
    }
    
    public int getTimesMilled() {
		return timesMilled;
	}
	
}
