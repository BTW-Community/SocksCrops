package net.minecraft.src;

import java.awt.Color;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.Sys;

import com.prupe.mcpatcher.cc.ColorizeEntity;

public class SCTileEntityBarrel extends TileEntity implements IInventory, FCITileEntityDataPacketHandler  {

    public int dyedWaterColor;
    
    //Liquid Types
    public static int NOLIQUID = 0;
    public static int WATER = 1;
    public static int MILK = 2;
    public static int CHOCOLATE_MILK = 3;
    public static int COCONUT_MILK = 4;
    public static int APPLE = 5;
    public static int CHERRY = 6;
    public static int GRAPE = 7;
    public static int LEMON = 8;
    
    public static int DYE = 99;
    
    protected ItemStack m_Contents[];
    protected int m_iCookCounter;
    public int m_iScaledCookCounter;
    
    private int m_iTimeToCook = 64;
    
    protected boolean m_bContainsValidIngrediantsForState;
    private boolean m_bForceValidateOnUpdate;
    
    public short m_sStorageSlotsOccupied;
    protected int m_iFilterItemID;
    
    protected int fireUnderType; // 0 = none, 1 = normal, 2 = stoked, -1 = requires validation
	private final int primaryFireFactor = 2; //5
	private final int secondaryFireFactor = 1; //3
	
	protected int m_iStokedCooldownCounter;
	private final int m_iStokedTicksToCooldown = 20;
    
	private static HashMap<ItemStack, ItemStack> liquids = new HashMap<ItemStack, ItemStack>();
		
    public SCTileEntityBarrel() {
    	m_Contents = new ItemStack[getSizeInventory()];
    	
    	dyedWaterColor = 0xFFFFFF;
    	
    	m_iCookCounter = 0;
    	
    	m_bContainsValidIngrediantsForState = false;
    	m_bForceValidateOnUpdate = true;
    	m_iScaledCookCounter = 0;
    	
    	m_sStorageSlotsOccupied = 0;
    	m_iFilterItemID = 0;
    	
    	fireUnderType = 0;


//		addLiquid(
//				SCDefs.liquidBlock, SCBlockLiquid.COCONUT_MILK,
//				SCDefs.bottleWithLiquid, SCItemBottleLiquid.MILK,
//				SCDefs.bucket, SCItemBucket.MILK);
	}
    
    @Override
    public void updateEntity()
    {
//    	if (worldObj.isRemote) System.out.println(" --- CLIENT ---");
//    	else System.out.println(" --- SERVER ---");
    	
    	if (!worldObj.isRemote)
    	{
    		ValidateFireUnderType();
    		ValidateLiquidAbove();
    		

        	int iBlockID = worldObj.getBlockId( xCoord, yCoord, zCoord );
        	int mixerBlockID = worldObj.getBlockId( xCoord, yCoord + 2, zCoord );
    		
        	if (mixerBlockID == SCDefs.mixer.blockID)
        	{
        		SCTileEntityMixer mixerTE = (SCTileEntityMixer) worldObj.getBlockTileEntity(xCoord, yCoord + 2, zCoord);
        		SCBlockMixer mixer = (SCBlockMixer) Block.blocksList[mixerBlockID];

        		if ( mixer != null && mixer.IsInputtingMechanicalPower(worldObj, xCoord, yCoord + 2, zCoord) )
        		{    			
        	    	if ( mixerTE.moveCount >= 100 )
        	    	{
        	        	if ( m_bForceValidateOnUpdate )
        	        	{
        	        		ValidateContentsForState();
        	        		
        	        		m_bForceValidateOnUpdate = false;

        	        	}
        	        	if ( fireUnderType > 0 )
        		    	{        		        	
        		        	if ( fireUnderType == 2 )
        		        	{
        		    			if ( m_iStokedCooldownCounter <= 0 )
        		    			{
        		        			m_iCookCounter = 0;		    			
        		    			}
        		    			
        		    			m_iStokedCooldownCounter = m_iStokedTicksToCooldown;
        		
        		    			PerformMixUpdate( 1 );
        		        	}
        		        	else if ( m_iStokedCooldownCounter > 0 )
        		        	{
        		    			// this prevents the vessel from going back into regular cook mode if the fire beneath it is 
        		    			// momentarily not stoked
        		    			
        		        		m_iStokedCooldownCounter--;
        		    			
        		    			if ( m_iStokedCooldownCounter <= 0 )
        		    			{
        		    				// reset the cook counter so that time spent rendering does not translate into cook time
        		    				
        		        			m_iCookCounter = 0;
        		    			}			
        		        	}
        		        	else
        		        	{
        		        		PerformMixUpdate( 1 );
        		        	}
        		    	}
        	        	else
    		        	{
        	        		PerformMixUpdate( 1 );
    		        	}
        	        	
        	        	if (GetCraftingManager(fireUnderType).GetCraftingResult( this ) == null)
        	        	{
        	        		for(int i=0; i < getSizeInventory(); i++)
                    		{
        	        			ItemStack tempStack = getStackInSlot(i);
        	    	    		
        	    	    		if ( tempStack != null )
        	    	    		{
        	    	    			if (!isValidLiquid(tempStack))
        	    	    			{
        	    	    				if ( FCUtilsInventory.ConsumeItemsInInventory( this, 
                	        					tempStack.getItem().itemID, tempStack.getItemDamage(), 
                	        					tempStack.stackSize, false ) )
            	    					{
        	    	    					 EjectStackOnMixed(tempStack);
            	    					}
        	    	    			}
        	    	    		}
                    		}	
        	        	}
                		
        	    	}
        	    	else
        	    	{
        	    		m_iCookCounter = 0;
        	    	}
            	}
        		
        		
        		
        		m_iScaledCookCounter = ( m_iCookCounter * 1000 ) / m_iTimeToCook; 
        	}
        	
        	markBlockForUpdate();

    	}
    	else
    	{
    		//client
    		showParticles();
    	}  
    }
    
    private void ValidateLiquidAbove() {
    	
    	int blockIDAbove = worldObj.getBlockId(xCoord, yCoord + 1, zCoord);
    	
    	if (FCUtilsInventory.GetNumOccupiedStacksInRange(this, 9, 11) == 0 && isValidLiquid(new ItemStack(blockIDAbove, 1, 0)) )
    	{
    		setInventorySlotContents( 9, new ItemStack(blockIDAbove, 1, 0 ));
    		setInventorySlotContents( 10, new ItemStack(blockIDAbove, 1, 0 ));
    		setInventorySlotContents( 11, new ItemStack(blockIDAbove, 1, 0 ));
    	}
	}

	public static boolean isValidLiquid(ItemStack stack)
    {
		return SCUtilsLiquids.doListscontain(stack);
    }
	protected void showParticles() {
		
		boolean hasLiquid = false;
		int numOfLiquids = 0;
		for (int i = 9; i < 12; i++) {
			ItemStack stack = m_Contents[i];
			
			if (stack != null)
			{
				hasLiquid = true;
				numOfLiquids++;
			}
		}
		
		if (hasLiquid && fireUnderType > 0 && worldObj.rand.nextFloat() <= 0.25F)
		{
			displayParticles("scbubble", 1/256D, 1/16D + (numOfLiquids*5)/16D);
		}
		
		TileEntity te = worldObj.getBlockTileEntity(xCoord, yCoord + 2, zCoord);
		if (!(te instanceof SCTileEntityMixer))
		{
			return;
		}
		
		SCTileEntityMixer mixerTE = (SCTileEntityMixer) te;
		
		if (mixerTE != null && mixerTE.moveCount >= 100 && GetCraftingManager(fireUnderType).GetCraftingResult( this ) != null)
		{
			for (int i = 0; i < 12; i++) {
				ItemStack stack = m_Contents[i];
				if (stack!=null) {
					if ( stack.getItem() instanceof ItemBlock)
					{
						displayParticles("tilecrack_", stack.itemID, stack.getItemDamage());
					}
					else displayParticles("iconcrack_", stack.itemID, stack.getItemDamage());
				}
			}
			
			if (fireUnderType > 0 && worldObj.rand.nextFloat() <= 0.125F)
			{
				displayParticles("fcwhitesmoke", 1/128D, 1D);
			}
		}
	}

    private void displayParticles(String particle, double speed, double height) {
    	
		for (int iTempCount = 0; iTempCount < 1; iTempCount ++)
		{
			double posX = xCoord + Math.min(Math.max(worldObj.rand.nextDouble(), 2/16D), 14/16D);
			double posY = yCoord + height;
			double posZ = zCoord + Math.min(Math.max(worldObj.rand.nextDouble(), 2/16D), 14/16D);

			double velX = 0D;
			double velY = worldObj.rand.nextDouble() * speed;
			double velZ = 0D;

			worldObj.spawnParticle(particle, posX, posY, posZ, velX, velY, velZ);
		}
	}
    
	private void displayParticles(String particleName, int itemID, int damage) {
		String particle = particleName + String.valueOf(itemID) + "_" + damage ;
		
		double speed = 0.2D;
		
		for (int iTempCount = 0; iTempCount < 1; iTempCount ++)
		{
			double dChunkX = xCoord + worldObj.rand.nextDouble();
			double dChunkY = yCoord + 1D;
			double dChunkZ = zCoord + worldObj.rand.nextDouble();

			double dChunkVelX = (worldObj.rand.nextDouble() - 0.5D) * 0.02D;
			double dChunkVelY = worldObj.rand.nextDouble() * speed;
			double dChunkVelZ = (worldObj.rand.nextDouble() - 0.5D) * 0.02D;

			worldObj.spawnParticle(particle, dChunkX, dChunkY, dChunkZ, dChunkVelX, dChunkVelY, dChunkVelZ);
		}
	}
	
	public int GetCurrentFireFactor()
    {
    	int iFireFactor = 1;
    	
    	if ( fireUnderType > 0 )
    	{
			iFireFactor = primaryFireFactor;
    		
    		if ( fireUnderType == 1 )
    		{
        		int tempY = yCoord - 1;
        		
        		for ( int tempX = xCoord - 1; tempX <= xCoord + 1; tempX++ )
        		{
        			for ( int tempZ = zCoord - 1; tempZ <= zCoord + 1; tempZ++ )
        			{
        				if ( tempX != xCoord || tempZ != zCoord )
        				{
        					int iTempBlockID = worldObj.getBlockId( tempX, tempY, tempZ );
        					
        		        	if ( iTempBlockID == Block.fire.blockID ||
        		        		iTempBlockID == FCBetterThanWolves.fcBlockCampfireMedium.blockID || 
        		        		iTempBlockID == FCBetterThanWolves.fcBlockCampfireLarge.blockID )
        		        	{
        		        		iFireFactor += secondaryFireFactor;
        		        	}
        				}
        			}
        		}  		
    		}
    		else
    		{
        		int tempY = yCoord - 1;
        		
        		for ( int tempX = xCoord - 1; tempX <= xCoord + 1; tempX++ )
        		{
        			for ( int tempZ = zCoord - 1; tempZ <= zCoord + 1; tempZ++ )
        			{
        				if ( tempX != xCoord || tempZ != zCoord )
        				{
        		        	if ( worldObj.getBlockId( tempX, tempY, tempZ ) == FCBetterThanWolves.fcBlockFireStoked.blockID )
        		        	{
        		        		iFireFactor += secondaryFireFactor;
        		        	}
        				}
        			}
        		}  		
    		}    		
    	}
    	
    	return iFireFactor;
    }
	
	public void ValidateFireUnderType()
	{
		int iNewType = 0;
		
		int iBlockUnderID = worldObj.getBlockId( xCoord, yCoord - 1, zCoord );
		
		if ( iBlockUnderID == Block.fire.blockID ||
			iBlockUnderID == FCBetterThanWolves.fcBlockCampfireMedium.blockID || 
			iBlockUnderID == FCBetterThanWolves.fcBlockCampfireLarge.blockID )
		{
			iNewType = 1;
		}
		else if ( iBlockUnderID == FCBetterThanWolves.fcBlockFireStoked.blockID )
		{
			iNewType = 2;
		}
		
		if ( iNewType != fireUnderType )
		{
			fireUnderType = iNewType;
			
	    	ValidateContentsForState();			
		}
	}

    public void ValidateContentsForState()
    {
		m_bContainsValidIngrediantsForState = false;

		float mixingMultiplier = GetCraftingManager(fireUnderType).GetMixingMultiplier(this);
    	List<ItemStack> inputList = GetCraftingManager(fireUnderType).GetValidCraftingIngrediants(this);
    	
    	
		if ( GetCraftingManager(fireUnderType).GetCraftingResult( this ) != null )
		{
			m_bContainsValidIngrediantsForState = true;
			
	    	int numOfItems = 0;    	
	    	for (ItemStack inputItem : inputList)
	    	{
	    		numOfItems += inputItem.stackSize;
	    	}
	    	
	    	m_iTimeToCook = (int) ((100 * numOfItems) * mixingMultiplier);
		}		

    }
    
    /*
     * Returns true if any changes are made, false otherwise
     */
    private boolean ValidateInventoryStateVariables()
    {
    	boolean bStateChanged = false;
    	
    	int currentFilterID = GetFilterIDBasedOnInventory();
    	
    	if ( currentFilterID != m_iFilterItemID )
    	{
    		m_iFilterItemID = currentFilterID;
    		
    		bStateChanged = true;
    	}
    	
    	short numSlotsOccupied = (short)( FCUtilsInventory.GetNumOccupiedStacksInRange( 
    		this, 0, 5 ) ); 
    	
    	if ( numSlotsOccupied != m_sStorageSlotsOccupied )
    	{
    		m_sStorageSlotsOccupied = numSlotsOccupied;
    		
    		bStateChanged = true;
    	}
    	
    	return bStateChanged;
    }
    
    public int GetFilterIDBasedOnInventory()
    {
    	ItemStack filterStack = getStackInSlot( 6 );
    	
    	if ( filterStack != null && filterStack.stackSize > 0 )
    	{
    		return filterStack.itemID;
    	}
    	
    	return 0;
    }
    
    private void PerformMixUpdate( int iFireFactor )
    {
		if ( m_bContainsValidIngrediantsForState )
		{	
        	//System.out.println(m_iCookCounter + " / " + m_iTimeToCook);
			
    		m_iCookCounter += iFireFactor;
    		
    		if ( m_iCookCounter >= m_iTimeToCook )
    		{    			
    			AttemptToMix(fireUnderType);
    			
    	        // reset the cook counter to start the next item
    	        
    	        m_iCookCounter = 0;   	        
    			
    		}
		}
		else
		{
	        m_iCookCounter = 0;
		}
    }
    
    protected SCCraftingManagerMixerBase GetCraftingManager( int iFireType )
    {
    	if ( iFireType == 1 )
    	{
    		return SCCraftingManagerMixerFire.getInstance();
    	}
    	else if ( iFireType == 2 )
    	{
    		return SCCraftingManagerMixerStoked.getInstance();
    	}    	
    	
    	return SCCraftingManagerMixerNormal.getInstance();
    }
    
    protected boolean AttemptToMix(int fireUnder)
    {
    	return AttemptToMixWithManager( GetCraftingManager( fireUnder ) );
    }
    
//    public static final String[] dyeColorNames = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
////    public static final int[] dyeColors = new int[] {			0x000000, 0xFF0000, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 0xFFFF00, 6719955, 12801229, 15435844, 15790320};
//    public static final int[] dyeColors = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

    private boolean AttemptToMixWithManager( SCCraftingManagerMixerBase manager )
    {
    	if ( manager != null )
    	{    		
        	if ( manager.GetCraftingResult( this ) != null )
        	{	        	
        		List<ItemStack> outputList = manager.ConsumeIngrediantsAndReturnResult( this );
        		//List<ItemStack> inputList = manager.GetValidCraftingIngrediants(this); 
        		//boolean isEmpty = FCUtilsInventory.GetNumOccupiedStacksInRange(this, 9, 11) == 0;

        		//assert( inputList != null && inputList.size() > 0 );
        		assert( outputList != null && outputList.size() > 0 );
        		
        		//if the recipe doesn't require a liquid, but the liquid slots are occupied, don't craft
//        		boolean shouldMix = true;
//        		
//        		for ( int listIndex = 0; listIndex < inputList.size(); listIndex++ )
//                {
//        			
//        			if (!isEmpty && !isValidLiquid(new ItemStack(inputList.get(listIndex).itemID, 1, 0)))
//        			{
//        				shouldMix = false;
//        			}
//                }
//        		if (!shouldMix) return false;
        		
                for ( int listIndex = 0; listIndex < outputList.size(); listIndex++ )
                {
    	    		ItemStack mixedStack = ((ItemStack)outputList.get( listIndex )).copy();
    	    		
    	    		if ( mixedStack != null )
    	    		{    	    		
    	    			if (SCUtilsLiquids.isValidLiquidBlock(mixedStack))
    	    			{
    	    				for (int slot = 9; slot < 9 + mixedStack.stackSize; slot++)
    	    				{
    	    					setInventorySlotContents(slot, new ItemStack(mixedStack.getItem(), 1));
    	    					break;
    	    				}
    	    			}
    	    			else {
    	    				EjectStackOnMixed( mixedStack );   
    	    				break;
    	    			}
    	    		}
	        		// DYE //	    			
	    			//dyeItem(cookedStack);
	    		}
                
                return true;
    		}
    	}
    	
    	return false;
    }


//	protected boolean dyeItem(ItemStack cookedStack) {
//		if (cookedStack.getItem().itemID == Item.dyePowder.itemID)
//		{
//			int dyeType = cookedStack.getItemDamage();
//			
//			if (getDyedWaterColor() == 0xFFFFFF)
//			{
//				setDyedWaterColor(new Color(dyeColors[dyeType]).getRGB());
//				liquidType = DYE;
//				return true;
//			}
//			
//			liquidType = DYE;
//			//System.out.println("OLD COLOR: " + getDyedWaterColor());
//			    	    				
//			
//			
//		    Color color1 = new Color(getDyedWaterColor());
//		    Color color2 = new Color(dyeColors[dyeType]);
//
////	    		        // Set the strength of the multiply effect (between 0 and 1)
////	    		        double strength = 0.5;
////
////	    		        // Apply multiply blend mode with the specified strength
////	    		        Color resultColor = multiplyBlend(color1, color2, strength);
//		    
//		    double weight1 = 1D; // Adjust this to control the amount of color1 in the mix
//		    double weight2 = 1/3D; // Adjust this to control the amount of color2 in the mix
//
//		    Color mixedColor = mixColors(color1, color2, weight1, weight2);
//		    
//		
//			setDyedWaterColor(mixedColor.getRGB());
//			
//			//System.out.println("NEW COLOR: " + getDyedWaterColor());
//		}    
//		else if (cookedStack.getItem() instanceof ItemArmor)
//		{
//			ItemArmor itemArmor = (ItemArmor)cookedStack.getItem();
//			NBTTagCompound var3 = cookedStack.getTagCompound();
//
//		    if (!itemArmor.hasColor(cookedStack))
//		    {
//		        var3 = new NBTTagCompound();
//		        cookedStack.setTagCompound(var3);
//		    }
//
//		    NBTTagCompound var4 = var3.getCompoundTag("display");
//
//		    if (!var3.hasKey("display"))
//		    {
//		        var3.setCompoundTag("display", var4);
//		    }
//
//		    var4.setInteger("color",  getDyedWaterColor());
//		    
//			EjectStackOnMixed( cookedStack );
//
//		}
//		else {
//			EjectStackOnMixed( cookedStack );
//			return true;
//		}
//		return false;
//	}
    
//    private static final int sizeOfIntInHalfBytes = 8;
//    private static final int numberOfBitsInAHalfByte = 4;
//    private static final int halfByte = 0x0F;
//    private static final char[] hexDigits = { 
//      '0', '1', '2', '3', '4', '5', '6', '7', 
//      '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
//    };
//    
//    public static String decToHex(int dec) {
//        StringBuilder hexBuilder = new StringBuilder(sizeOfIntInHalfBytes);
//        hexBuilder.setLength(sizeOfIntInHalfBytes);
//        for (int i = sizeOfIntInHalfBytes - 1; i >= 0; --i)
//        {
//          int j = dec & halfByte;
//          hexBuilder.setCharAt(i, hexDigits[j]);
//          dec >>= numberOfBitsInAHalfByte;
//        }
//        return hexBuilder.toString(); 
//      }
//        
//    
//    public static Color mixColors(Color color1, Color color2, double weight1, double weight2) {
//        // Validate weights
//        if (weight1 < 0 || weight2 < 0 || (weight1 + weight2) == 0) {
//            throw new IllegalArgumentException("Weights must be non-negative and at least one weight should be positive");
//        }
//
//        // Calculate mixed RGB components
//        int r = (int) ((color1.getRed() * weight1 + color2.getRed() * weight2) / (weight1 + weight2));
//        int g = (int) ((color1.getGreen() * weight1 + color2.getGreen() * weight2) / (weight1 + weight2));
//        int b = (int) ((color1.getBlue() * weight1 + color2.getBlue() * weight2) / (weight1 + weight2));
//
//        // Ensure RGB components are within the valid range (0-255)
//        r = Math.min(255, Math.max(0, r));
//        g = Math.min(255, Math.max(0, g));
//        b = Math.min(255, Math.max(0, b));
//
//        // Create and return the mixed color
//        return new Color(r, g, b);
//    }
//    
//    public static Color multiplyBlend(Color color1, Color color2, double strength) {
//        int red = (int) ((color1.getRed() * color2.getRed() / 255.0) * strength + color1.getRed() * (1 - strength));
//        int green = (int) ((color1.getGreen() * color2.getGreen() / 255.0) * strength + color1.getGreen() * (1 - strength));
//        int blue = (int) ((color1.getBlue() * color2.getBlue() / 255.0) * strength + color1.getBlue() * (1 - strength));
//
//        // Ensure the values are in the valid color range [0, 255]
//        red = Math.min(Math.max(red, 0), 255);
//        green = Math.min(Math.max(green, 0), 255);
//        blue = Math.min(Math.max(blue, 0), 255);
//
//        return new Color(red, green, blue);
//    }

    public void EjectStackOnMixed( ItemStack stack )
    {
    	int iFacing = 2 + worldObj.rand.nextInt( 4 ); // random direction to the sides
    	
    	Vec3 ejectPos = Vec3.createVectorHelper( worldObj.rand.nextDouble() * 1.25F - 0.125F, 
    		worldObj.rand.nextFloat() * ( 1F / 16F ) + ( 7F / 16F ) + 0.5F, 
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
    
    public void setDyedWaterColor(int dyedWaterColor) {
		this.dyedWaterColor = dyedWaterColor;
	}
    
    public int getDyedWaterColor() {
		return dyedWaterColor;
	}
    
	@Override
    public void readFromNBT( NBTTagCompound tag )
    {
        super.readFromNBT( tag );
                
        if ( tag.hasKey( "dyedWaterColor" ) )
        {
        	dyedWaterColor = tag.getInteger( "dyedWaterColor" );
        }
        
        if ( tag.hasKey( "fireUnderType" ) )
        {
        	fireUnderType = tag.getInteger( "fireUnderType" );
        }
        
        NBTTagList nbttaglist = tag.getTagList("Items");
        
        m_Contents = new ItemStack[getSizeInventory()];
        
        for ( int i = 0; i < nbttaglist.tagCount(); i++ )
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt( i );
            
            int j = nbttagcompound1.getByte( "Slot" ) & 0xff;
            
            if ( j >= 0 && j < m_Contents.length )
            {
            	m_Contents[j] = ItemStack.loadItemStackFromNBT( nbttagcompound1 );
            }
        }
        
    	m_iCookCounter = tag.getInteger( "m_iCrucibleCookCounter" );
    	
        if ( tag.hasKey( "m_bContainsValidIngrediantsForState" ) )
        {
        	m_bContainsValidIngrediantsForState = tag.getBoolean( "m_bContainsValidIngrediantsForState" );
        } 
        
        ValidateInventoryStateVariables();
    }
    
    @Override
    public void writeToNBT( NBTTagCompound tag )
    {
        super.writeToNBT(tag);
        
        tag.setInteger( "dyedWaterColor", dyedWaterColor );  
        
        tag.setInteger( "fireUnderType", fireUnderType );  
        
        NBTTagList nbttaglist = new NBTTagList();
        
        for ( int i = 0; i < m_Contents.length; i++ )
        {
            if ( m_Contents[i] != null )
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte( "Slot", (byte)i );
                
                m_Contents[i].writeToNBT( nbttagcompound1 );
                
                nbttaglist.appendTag( nbttagcompound1 );
            }
        }     

        tag.setTag( "Items", nbttaglist );
        
    	tag.setInteger( "m_iCrucibleCookCounter", m_iCookCounter );

    	tag.setBoolean( "m_bContainsValidIngrediantsForState", m_bContainsValidIngrediantsForState );
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
    }
    
    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {        
        if ( tag.hasKey( "dyedWaterColor" ) )
        {
        	dyedWaterColor = tag.getInteger( "dyedWaterColor" );
        }
        
        if ( tag.hasKey( "fireUnderType" ) )
        {
        	fireUnderType = tag.getInteger( "fireUnderType" );
        }
        
        if ( tag.hasKey( "s" ) )
        {
        	m_sStorageSlotsOccupied = tag.getShort( "s" ); 
        }
        if ( tag.hasKey( "f" ) )
        {
        	m_iFilterItemID = tag.getInteger( "f" ); 
        }
        
        m_iCookCounter = tag.getInteger( "m_iCrucibleCookCounter" );
        
        if ( tag.hasKey( "m_bContainsValidIngrediantsForState" ) )
        {
        	m_bContainsValidIngrediantsForState = tag.getBoolean( "m_bContainsValidIngrediantsForState" );
        }
        
        NBTTagList nbttaglist = tag.getTagList("Items");
        
        m_Contents = new ItemStack[getSizeInventory()];
        
        for ( int i = 0; i < nbttaglist.tagCount(); i++ )
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt( i );
            
            int j = nbttagcompound1.getByte( "Slot" ) & 0xff;
            
            if ( j >= 0 && j < m_Contents.length )
            {
            	m_Contents[j] = ItemStack.loadItemStackFromNBT( nbttagcompound1 );
            }
        }
         
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
    	NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        
        nbttagcompound1.setInteger( "dyedWaterColor", dyedWaterColor );  
        
        nbttagcompound1.setInteger( "fireUnderType", fireUnderType );  
        
        nbttagcompound1.setShort( "s", m_sStorageSlotsOccupied );
        
        nbttagcompound1.setInteger( "f", m_iFilterItemID );
        
        nbttagcompound1.setInteger( "m_iCrucibleCookCounter", m_iCookCounter );
        
    	nbttagcompound1.setBoolean( "m_bContainsValidIngrediantsForState", m_bContainsValidIngrediantsForState );
    	
        NBTTagList nbttaglist = new NBTTagList();
        
        for ( int i = 0; i < m_Contents.length; i++ )
        {
            if ( m_Contents[i] != null )
            {
                NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setByte( "Slot", (byte)i );
                
                m_Contents[i].writeToNBT( nbttagcompound2 );
                
                nbttaglist.appendTag( nbttagcompound2 );
            }
        }     

        nbttagcompound1.setTag( "Items", nbttaglist );
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, nbttagcompound1 );
    }    

	public void markBlockForUpdate() {
	 	
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord ); 
		worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
	}
	
    public Item GetCurrentFilterItem()
    {
		return Item.itemsList[m_iFilterItemID];
    }
	

	@Override
	public int getSizeInventory()
	{
		return 12;
	}

    @Override
    public ItemStack getStackInSlot( int iSlot )
    {
        return m_Contents[iSlot];
    }

    @Override
    public ItemStack decrStackSize( int iSlot, int iAmount )
    {
    	return FCUtilsInventory.DecrStackSize( this, iSlot, iAmount );    	
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if ( m_Contents[par1] != null )
        {
            ItemStack itemstack = m_Contents[par1];
            m_Contents[par1] = null;
            
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
    	m_Contents[iSlot] = itemstack;
    	
        if( itemstack != null && itemstack.stackSize > getInventoryStackLimit() )
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
        
        onInventoryChanged();
    }
    
    @Override
    public void onInventoryChanged()
    {
    	super.onInventoryChanged();
    	
		m_bForceValidateOnUpdate = true;
		
    	if ( worldObj != null )
    	{
	        if ( ValidateInventoryStateVariables() )
	        {
		        worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
	        }
	        
	        ( (SCBlockBarrel)(SCDefs.barrel) ).SetHasFilter( worldObj, 
		        	xCoord, yCoord, zCoord, m_iFilterItemID > 0 );
    	}
    }

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return "Mixer";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 1;
	}

    @Override
    public boolean isUseableByPlayer( EntityPlayer entityPlayer )
    {
        if( worldObj.getBlockTileEntity( xCoord, yCoord, zCoord ) != this )
        {
            return false;
        }
        
        return ( entityPlayer.getDistanceSq( 
    		(double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D ) 
        	<= 64 );
    }

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public boolean isStackValidForSlot( int slot, ItemStack stack )
    {
        return true;
    }
}
