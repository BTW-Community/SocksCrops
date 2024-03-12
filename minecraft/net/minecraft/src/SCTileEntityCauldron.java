//package net.minecraft.src;
//
//import java.util.List;
//
//public class SCTileEntityCauldron extends FCTileEntityCookingVessel {
//
//	private ItemStack[] liquidStack = new ItemStack[1];
//	
//    public int liquidFillLevel;
//    public int liquidType;
//    
//    //Liquid Types
//    public static int NO_LIQUID = 0;
//    public static int WATER = 1;
//    public static int MILK = 2;
//    public static int CHOCOLATEMILK = 3;
//    public static int APPLE = 4;
//    public static int BLUEBERRY = 5;
//    
//    public static boolean hasItems;
//    
//    //copy from super
//    private boolean m_bForceValidateOnUpdate;
//	private final int m_iPrimaryFireFactor = 5;
//	private final int m_iSecondaryFireFactor = 3;
//	
//	// the first number in this equation represents the minimum number of ticks to cook something (with max fire)
//    private final int m_iTimeToCook = 150; // 150 * ( m_iPrimaryFireFactor + ( m_iSecondaryFireFactor * 8 ) ); TODO: FIX
//    
//	
//	public SCTileEntityCauldron() {
//		m_bForceValidateOnUpdate = true;
//		hasItems = false;
//	}
//	
//	@Override
//    public void updateEntity()
//    {
////    	if (worldObj.isRemote) System.out.println(" --- CLIENT ---");
////    	else System.out.println(" --- SERVER ---");
//    	
//    	if (!worldObj.isRemote)
//    	{
//        	int blockAbove = worldObj.getBlockId(xCoord, yCoord + 1, zCoord);
//        	
//        	if (liquidFillLevel == 0)
//        	{        	
//            	if (blockAbove == Block.waterStill.blockID || blockAbove == Block.waterMoving.blockID )
//            	{
//            		setLiquidFillLevel(15);
//            		setLiquidType(WATER);
//            	}
//            	
//            	if (blockAbove == FCBetterThanWolves.fcBlockMilk.blockID)
//            	{
//            		setLiquidFillLevel(15);
//            		setLiquidType(MILK);
//            		setInventorySlotContents( 6, new ItemStack(Item.bucketMilk ));
//            	}
//
//        	} 
//    		
//        	int iBlockID = worldObj.getBlockId( xCoord, yCoord, zCoord );
//        	int mixerBlockID = worldObj.getBlockId( xCoord, yCoord + 2, zCoord );
//    		
//        	if (mixerBlockID == SCDefs.mixer.blockID)
//        	{
//        		SCTileEntityMixer mixerTE = (SCTileEntityMixer) worldObj.getBlockTileEntity(xCoord, yCoord + 2, zCoord);
//        		SCBlockMixer mixer = (SCBlockMixer) Block.blocksList[mixerBlockID];
//            	//SCTileEntityCauldron barrel = (SCTileEntityCauldron) worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
//            	
//        		if ( mixer != null && mixer.IsInputtingMechanicalPower(worldObj, xCoord, yCoord + 2, zCoord) )
//        		{    			
//        	    	if ( mixerTE.moveCount >= 100 )
//        	    	{
//        	    		System.out.println("LIQUID: " + this.liquidType);
//        	    		
//        	        	if ( m_bForceValidateOnUpdate )
//        	        	{
//        	        		ValidateContentsForState();
//        	        		
//        	        		m_bForceValidateOnUpdate = false;
//
//        	        	}
//        	        	System.out.println("validIngreditents: " + m_bContainsValidIngrediantsForState);
//        	        	
//        	        	PerformNormalFireUpdate( 1 );
//        	    	}
//        	    	else
//        	    	{
//        	    		m_iCookCounter = 0;
//        	    	}
//            	}
//        		
//        		
//        		
//        		m_iScaledCookCounter = ( m_iCookCounter * 1000 ) / m_iTimeToCook; 
//        	}
//        	
//        	
////        	System.out.println("CookCounter" + m_iCookCounter);
//    	}
//        	
//    	if (worldObj.isRemote)
//    	{
//    		SCTileEntityMixer mixerTE = (SCTileEntityMixer) worldObj.getBlockTileEntity(xCoord, yCoord + 2, zCoord);
//    		int meta = worldObj.getBlockMetadata(xCoord, yCoord + 2, zCoord);
//    		
//    		if (mixerTE != null )
//    		{
//    			if (m_iCookCounter != 0)
//    			{   				    				
//    				for(ItemStack item : m_Contents)
//    				{
//    					if (item!=null) displayParticles(item.itemID, item.getItemDamage());
//    				} 				
//    			}		
//    		}
//    	}	
//    }
//
//	public void setLiquidFillLevel(int fillLevel) {
//		this.liquidFillLevel = fillLevel;
//	}
//	
//	public void setLiquidType(int type) {
//		this.liquidType = type;
//	}
//
//	private void PerformNormalFireUpdate( int iFireFactor )
//    {
//		if ( m_bContainsValidIngrediantsForState )
//		{
//    		m_iCookCounter += iFireFactor;
//    		
//    		if ( m_iCookCounter >= m_iTimeToCook )
//    		{
//    			AttemptToCookNormal();
//    			
//    	        // reset the cook counter to start the next item
//    	        
//    	        m_iCookCounter = 0;
//    		}
//		}
//		else
//		{
//	        m_iCookCounter = 0;
//		}
//    }
//	
//	private void PerformStokedFireUpdate( int iFireFactor )
//    {
//		if ( m_bContainsValidIngrediantsForState )
//		{
//			m_iCookCounter += iFireFactor;
//			
//			if ( m_iCookCounter >= m_iTimeToCook )
//			{
//	    		if ( DoesContainExplosives() )
//				{
//	    			//BlowUp();
//				}
//				else 
//				{
//					AttemptToCookStoked();
//	    		}
//				
//				m_iCookCounter = 0;
//			}
//		}
//		else
//		{
//			m_iCookCounter = 0;
//		}
//    }
//	
//	protected boolean AttemptToCookNormal()
//    {
//    	return AttemptToCookWithManager( GetCraftingManager( 1 ) );
//    }
//    
//    @Override
//    protected boolean AttemptToCookStoked()
//    {
//    	return AttemptToCookWithManager( GetCraftingManager( 2 ) );
////		int iBurnableSlot = GetFirstStackThatContainsItemsDestroyedByStokedFire();
////		
////		if ( iBurnableSlot >= 0 )
////		{
////			decrStackSize( iBurnableSlot, 1 );
////			
////			return true;
////		}
////		
////		return super.AttemptToCookStoked();
//    }
//    
//    private boolean AttemptToCookWithManager( SCCraftingManagerMixer manager )
//    {
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
//    	    		ItemStack cookedStack = ((ItemStack)outputList.get( listIndex )).copy();
//    	    		
//    	    		if ( cookedStack != null )
//    	    		{
//    	    	        if ( !FCUtilsInventory.AddItemStackToInventory( this, cookedStack ) )
//    	    	        {    	        	
//    	    	        	FCUtilsItem.EjectStackWithRandomOffset( worldObj, xCoord, yCoord + 1, zCoord, cookedStack );			    	        	
//    	    	        }
//    	    		}
//                }
//                
//                return true;
//    		}        	
//    	}
//    	
//    	return false;
//    }
//
//	private void displayParticles(int itemID, int damage) {
//		System.out.println("Particles");
//		String particle = "iconbreak_" + String.valueOf(itemID) + "_" + String.valueOf(damage) ;	
//		//particle = "iconcrack_" + itemID;
//		
//		for (int iTempCount = 0; iTempCount < 1; iTempCount ++)
//		{
//			double dChunkX = xCoord + worldObj.rand.nextDouble();
//			double dChunkY = yCoord + 16/16D;
//			double dChunkZ = zCoord + worldObj.rand.nextDouble();
//
//			double dChunkVelX = (worldObj.rand.nextDouble() - 0.5D) * 0.02D;
//			double dChunkVelY = worldObj.rand.nextDouble() * 0.2D;
//			double dChunkVelZ = (worldObj.rand.nextDouble() - 0.5D) * 0.02D;
//
//			worldObj.spawnParticle(particle, dChunkX, dChunkY, dChunkZ, 
//					dChunkVelX, dChunkVelY, dChunkVelZ);
//		}
//	}
//	
//	@Override
//    public void readFromNBT( NBTTagCompound tag )
//    {
//        super.readFromNBT(tag);
//        
//        if ( tag.hasKey( "liquidFillLevel" ) )
//        {
//        	liquidFillLevel = tag.getInteger( "liquidFillLevel" );
//        }
//        
//        if ( tag.hasKey( "liquidType" ) )
//        {
//        	liquidType = tag.getInteger( "liquidType" );
//        }
//        
//        NBTTagCompound liquidTag = tag.getCompoundTag( "liquidStack" );
//
//        if ( liquidTag != null )
//        {
//        	liquidStack[0] = ItemStack.loadItemStackFromNBT( liquidTag );
//        }
//        
//    	m_iCookCounter = tag.getInteger( "m_iCrucibleCookCounter" );
//    	
//        if ( tag.hasKey( "m_iStokedCooldownCounter" ) )
//        {
//        	m_iStokedCooldownCounter = tag.getInteger( "m_iStokedCooldownCounter" );
//        }
//        
//        if ( tag.hasKey( "m_bContainsValidIngrediantsForState" ) )
//        {
//        	m_bContainsValidIngrediantsForState = tag.getBoolean( "m_bContainsValidIngrediantsForState" );
//        }        
//    }
//
//    @Override
//    public void writeToNBT( NBTTagCompound tag )
//    {
//        super.writeToNBT(tag);
//        
//        tag.setInteger( "liquidFillLevel", liquidFillLevel );   
//        
//        tag.setInteger( "liquidType", liquidType );  
//        
//        if ( liquidStack[0] != null)
//        {
//            NBTTagCompound liquidTag = new NBTTagCompound();
//            
//            liquidStack[0].writeToNBT( liquidTag );
//            
//            tag.setCompoundTag( "liquidStack", liquidTag );
//        }
//
//    	tag.setInteger( "m_iCrucibleCookCounter", m_iCookCounter );
//    	tag.setInteger( "m_iStokedCooldownCounter", m_iStokedCooldownCounter );    	
//    	
//    	worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );
//    }
//    
//    @Override
//    public void readNBTFromPacket(NBTTagCompound tag) {
//    	// TODO Auto-generated method stub
//    	super.readNBTFromPacket(tag);
//    	
//    	if ( tag.hasKey( "liquidFillLevel" ) )
//        {
//        	liquidFillLevel = tag.getInteger( "liquidFillLevel" );
//        }
//        
//        if ( tag.hasKey( "liquidType" ) )
//        {
//        	liquidType = tag.getInteger( "liquidType" );
//        }
//        
//        NBTTagCompound liquidTag = tag.getCompoundTag( "liquidStack" );
//
//        if ( liquidTag != null )
//        {
//        	liquidStack[0] = ItemStack.loadItemStackFromNBT( liquidTag );
//        }
//        
//        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );
//    }
//    
//    @Override
//    public Packet getDescriptionPacket()
//    {
//        NBTTagCompound tag = new NBTTagCompound();
//        
//        tag.setShort( "s", m_sStorageSlotsOccupied );
//        
//        tag.setInteger( "liquidFillLevel", liquidFillLevel );
//        
//        tag.setInteger( "liquidType", liquidType );  
//        
//        if ( liquidStack[0] != null)
//        {
//            NBTTagCompound liquidTag = new NBTTagCompound();
//            
//            liquidStack[0].writeToNBT( liquidTag );
//            
//            tag.setCompoundTag( "liquidStack", liquidTag );
//        }
//        
//        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
//    }
//    
//    //************* Liquid Inventory ************//
//
//	
//	private ItemStack getLiquidStack() {
//		return liquidStack[0];
//	}
//	   
//    
//    //************* IInventory ************//
//    
//    @Override
//    public String getInvName()
//    {
//        return "Crucible";
//    }
//    
//    @Override
//    public boolean isStackValidForSlot( int iSlot, ItemStack stack )
//    {
//        return true;
//    }
//    
//    @Override
//    public boolean isInvNameLocalized()
//    {
//    	return true;
//    }
//    
//    @Override
//    public void onInventoryChanged() {    	
//    	super.onInventoryChanged();
//    	
//    	short currentSlotsOccupied = (short)( FCUtilsInventory.GetNumOccupiedStacks( this ) ); 
//    	
//    	if ( currentSlotsOccupied > 0 )
//    	{
//    		this.hasItems = true;
//    	}
//    	else this.hasItems = false;
//    	
//    }
//    
//    //************* FCTileEntityCookingVessel ************//
//    
//    @Override
//    public void ValidateContentsForState()
//    {
//		m_bContainsValidIngrediantsForState = false;
//		
//		if ( m_iFireUnderType == 0 )
//    	{
//    		// no fire
//
//    		if ( SCCraftingManagerMixer.getInstance().GetCraftingResult( this ) != null )
//    		{
//    			m_bContainsValidIngrediantsForState = true;
//    		}
//    	}
//    	else if ( m_iFireUnderType == 1 )
//    	{
//    		// regular fire
//
//    		if ( SCCraftingManagerMixer.getInstance().GetCraftingResult( this ) != null )
//    		{
//    			m_bContainsValidIngrediantsForState = true;
//    		}
//    	}
//    	else if ( m_iFireUnderType == 2 )
//    	{
//    		// stoked fire
//    		
//    		if ( DoesContainExplosives() )
//    		{
//				m_bContainsValidIngrediantsForState = true;
//    		}
//    		else if ( FCCraftingManagerCrucibleStoked.getInstance().GetCraftingResult( this ) != null ) 
//			{
//	    		m_bContainsValidIngrediantsForState = true;
//			}
//    		else if ( GetFirstStackThatContainsItemsDestroyedByStokedFire() >= 0 )
//    		{    			
//	    		m_bContainsValidIngrediantsForState = true;
//    		}
//    	}
//    }
//    
//    @Override
//    protected SCCraftingManagerMixer GetCraftingManager( int iFireType )
//    {
//    	if ( iFireType == 0 )
//    	{
//    		return SCCraftingManagerMixer.getInstance();
//    	}
//    	else if ( iFireType == 1 )
//    	{
//    		return SCCraftingManagerMixer.getInstance();
//    	}
//    	else if ( iFireType == 2 )
//    	{
//    		return SCCraftingManagerMixer.getInstance();
//    	}    	
//    	
//    	return null;
//    }
//    
//
//    
//    //------------- Class Specific Methods ------------//
//    
//    private int GetFirstStackThatContainsItemsDestroyedByStokedFire()
//    {
//        for (int i = 0; i < getSizeInventory(); i++)
//        {
//            if ( getStackInSlot( i ) != null )
//            {
//            	if ( getStackInSlot( i ).getItem().IsIncineratedInCrucible() )
//            	{
//            		return i;
//            	}
//            }
//        }
//        
//    	return -1;
//    }
//
//}
