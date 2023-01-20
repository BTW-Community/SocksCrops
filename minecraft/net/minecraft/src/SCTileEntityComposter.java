package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;

public class SCTileEntityComposter extends TileEntity 
	implements FCITileEntityDataPacketHandler {
	
	private static HashMap<Integer, Integer> compostingTypes = new HashMap<Integer, Integer>();
	
	private int fillLevel = 0;
	public int maxFillLevel = 256;
	
    private final int m_iTimeToCook =  24000; // ( 10 * 60 * 20 );
    
	private int m_iCookCounter = 0;
	
	public boolean m_bIsCooking = false;
	
	public SCTileEntityComposter() {
		
	}
	
    @Override
    public void updateEntity()
    {
    	super.updateEntity();

    	if ( !worldObj.isRemote )
    	{
    		UpdateCooking();
//    		System.out.println(m_iCookCounter);
//    		System.out.println("my meta: " + worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
    	}

    	    	
    }
    
	public void markBlockForUpdate() {
 	
		this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );   
	}
	
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
    	super.readFromNBT(nbttagcompound);
    	
        if ( nbttagcompound.hasKey( "scFillLevel" ) )
        {
        	fillLevel  = nbttagcompound.getInteger( "scFillLevel" );
        }
        
        if( nbttagcompound.hasKey( "fcCookCounter" ) )
        {
        	m_iCookCounter = nbttagcompound.getInteger( "fcCookCounter" );
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
    	super.writeToNBT(nbttagcompound);
    	
        nbttagcompound.setInteger( "scFillLevel", fillLevel );
        
        nbttagcompound.setInteger( "fcCookCounter", m_iCookCounter );
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
    	NBTTagCompound nbttagcompound1 = new NBTTagCompound();

        nbttagcompound1.setInteger( "scFillLevel", fillLevel );
        nbttagcompound1.setBoolean( "fcCookCounter", m_bIsCooking );
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, nbttagcompound1 );
    }
    
    //------------- FCITileEntityDataPacketHandler ------------//
    
    @Override
    public void readNBTFromPacket( NBTTagCompound nbttagcompound )
    {
    	fillLevel = nbttagcompound.getInteger( "scFillLevel" );
        m_bIsCooking = nbttagcompound.getBoolean( "fcCookCounter" );

        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );        
    } 
    
    public int getCookCounter() {
		return m_iCookCounter;
	}
    
    public void setCookCounter(int m_iCookCounter) {
		this.m_iCookCounter = m_iCookCounter;
	}
//------------- Class Specific Methods ------------//
    
    public void UpdateCooking()
    {
    	boolean seeSky = worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord);
    	
    	int iBlockAboveID = worldObj.getBlockId( xCoord, yCoord + 1, zCoord );
    	Block blockAbove = Block.blocksList[iBlockAboveID];
    	
    	if (getFillLevel() >= maxFillLevel)
    	{
    		if ( blockAbove != null && blockAbove.IsGroundCover( ) && !seeSky )
        	{
    			m_bIsCooking = false;
        	}
    		else {
    			m_bIsCooking = true;
    			//worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    		}
        	
        	
        	if ( m_bIsCooking )
        	{
        		if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) < 15)
        		{
            		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            		
            		if ( IsRainingOnBrick( worldObj, xCoord, yCoord, zCoord ) )
                	{
            			m_iCookCounter += 2;
                	}
            		else m_iCookCounter++;
            		
            		int newMeta = ( m_iCookCounter / 1600 );
            		
            		if (newMeta > 0)
            		{
            			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newMeta );
            		}
            		
            		        		
            		if ( m_iCookCounter >= m_iTimeToCook )
            		{
            			m_iCookCounter = 300;
            			this.m_bIsCooking = false;
            			
            			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 15 );
            			return;
            		}
        		}
        		else {
        			if ( m_iCookCounter >= m_iTimeToCook )
            		{
            			m_iCookCounter = 300;
            			this.m_bIsCooking = false;
            			
            			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 15 );
            			return;
            		}
        		}
        		

        	}
//        	else
//        	{
//        		if ( IsRainingOnBrick( worldObj, xCoord, yCoord, zCoord ) )
//        		{
//        			m_iCookCounter = m_iCookCounter + 2;
//        			
//               		if ( m_iCookCounter >= m_iTimeToCook )
//            		{
//               			m_iCookCounter = 300;
//               			m_bIsCooking = false;
//               			
//               			worldObj.setBlockMetadata(xCoord, yCoord, zCoord, 15 );
//               			
//               			
//            			return;
//            		}
//        		}    		
//        	}
    	}
    	
    	
    }
    
    public boolean IsRainingOnBrick( World world, int i, int j, int k )
    {
    	return world.isRaining() && world.IsRainingAtPos( i, j, k );
    }
    
    public int getFillLevel() {
		return fillLevel;
	}
    
    public void setFillLevel(int fillLevel) {
		this.fillLevel = fillLevel;
	}
    
    
    public static void addValidItemToCompost(int id, int fillValue)
    {
    	compostingTypes.put(id, fillValue);
    }
    
    public static boolean isValidItem(int id)
    {
    	return compostingTypes.containsKey(id);
    }
    
    public int getFillValue(int id)
    {
    	return compostingTypes.get(id);
    }


    /**
     * Credit to ExpHP
     */
	public void addStackToComposter(ItemStack heldStack)
	{
		int sizeOfOne = getFillValue(heldStack.itemID);
		int howMuchSpace = maxFillLevel - getFillLevel();
		int howManyFit = howMuchSpace / sizeOfOne;
		int howManyToInsert = Math.min(howManyFit, heldStack.stackSize); // can't insert more than we hold

		setFillLevel(getFillLevel() + sizeOfOne * howManyToInsert);
		heldStack.stackSize =  heldStack.stackSize - howManyToInsert;
		
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
	}
    
	//the higher the value, the less of that item is needed to fill a composter
	public static final int plantValue = 1;
	public static final int cropValue = 2;
	public static final int foodValue = 4;
	
	static
    {
    	//SCStuff
		compostingTypes.put(SCDefs.redGrapes.itemID, foodValue);
		compostingTypes.put(SCDefs.whiteGrapes.itemID, foodValue);
		
    	compostingTypes.put(SCDefs.bambooItem.itemID, cropValue);
    	compostingTypes.put(SCDefs.bambooShoot.blockID, cropValue);
    	
    	compostingTypes.put(SCDefs.sweetberry.itemID, foodValue);
    	compostingTypes.put(SCDefs.blueberry.itemID, foodValue);
    	
    	compostingTypes.put(SCDefs.sweetberrySapling.itemID, cropValue);
    	compostingTypes.put(SCDefs.blueberrySapling.itemID, cropValue);
    	
    	compostingTypes.put(SCDefs.pumpkinSliceRaw.itemID, foodValue);
    	compostingTypes.put(SCDefs.melonHoneydewSlice.itemID, foodValue);
    	compostingTypes.put(SCDefs.melonCantaloupeSlice.itemID, foodValue);
    	compostingTypes.put(SCDefs.melonCanarySlice.itemID, foodValue);
    	
    	compostingTypes.put(SCDefs.clover.blockID, plantValue);
    	compostingTypes.put(SCDefs.groundFlowers.blockID, plantValue);
    	compostingTypes.put(SCDefs.doubleTallGrass.blockID, plantValue);
    	compostingTypes.put(SCDefs.lilyRose.blockID, plantValue);
    	compostingTypes.put(SCDefs.mossBall.itemID, cropValue);

    	//Seeds
    	compostingTypes.put(Item.melonSeeds.itemID, cropValue);
    	compostingTypes.put(Item.pumpkinSeeds.itemID, cropValue);
    	compostingTypes.put(FCBetterThanWolves.fcItemWheatSeeds.itemID, cropValue);
    	compostingTypes.put(Item.netherStalkSeeds.itemID, cropValue);
    	compostingTypes.put(FCBetterThanWolves.fcItemHempSeeds.itemID, cropValue);
    	compostingTypes.put(FCBetterThanWolves.fcItemCarrotSeeds.itemID, cropValue);
    	
    	//other
    	compostingTypes.put(Item.spiderEye.itemID, cropValue);    	
    	compostingTypes.put(Item.fermentedSpiderEye.itemID, cropValue);    	
    	compostingTypes.put(Item.poisonousPotato.itemID, cropValue);    	
    	compostingTypes.put(FCBetterThanWolves.fcItemHemp.itemID, cropValue);    	
    	compostingTypes.put(FCBetterThanWolves.fcItemFoulFood.itemID, cropValue);    	
    	compostingTypes.put(FCBetterThanWolves.fcItemDogFood.itemID, cropValue);    	
    	compostingTypes.put(FCBetterThanWolves.fcItemCreeperOysters.itemID, cropValue);    	
    	compostingTypes.put(Block.cocoaPlant.blockID, cropValue);   	
    	
    	//Plants    	
    	compostingTypes.put(Item.reed.itemID, cropValue);
    	compostingTypes.put(FCBetterThanWolves.fcItemReedRoots.itemID, cropValue);
    	compostingTypes.put(Block.tallGrass.blockID, plantValue);
    	compostingTypes.put(Block.plantYellow.blockID, plantValue);
    	compostingTypes.put(Block.plantRed.blockID, plantValue);
    	compostingTypes.put(Block.leaves.blockID, plantValue);
    	compostingTypes.put(FCBetterThanWolves.fcBlockBloodLeaves.blockID, plantValue);    	
    	
    	//Blood sapling here..    	
    	compostingTypes.put(Block.vine.blockID, plantValue);
    	compostingTypes.put(Block.sapling.blockID, cropValue);
    	compostingTypes.put(Block.waterlily.blockID, cropValue);
    	compostingTypes.put(FCBetterThanWolves.fcItemMushroomBrown.itemID, foodValue);
    	compostingTypes.put(FCBetterThanWolves.fcItemMushroomRed.itemID, foodValue);
    	
    	compostingTypes.put(FCBetterThanWolves.fcItemBark.itemID, plantValue);
    	compostingTypes.put(FCBetterThanWolves.fcItemSawDust.itemID, plantValue);  
    	
    	//Food
    	compostingTypes.put(FCBetterThanWolves.fcItemMushroomBrown.itemID, foodValue);
    	compostingTypes.put(FCBetterThanWolves.fcItemMushroomRed.itemID, foodValue);
    	compostingTypes.put(Item.appleRed.itemID, foodValue);
    	compostingTypes.put(Item.melon.itemID, foodValue);
    	compostingTypes.put(Item.carrot.itemID, foodValue);
    	compostingTypes.put(FCBetterThanWolves.fcItemCarrot.itemID, foodValue);
    	compostingTypes.put(Item.potato.itemID, foodValue);
    	
		if (SCDecoIntegration.isDecoInstalled() )
		{
			compostingTypes.put(SCDecoIntegration.cherrySapling.blockID, cropValue);
			compostingTypes.put(SCDecoIntegration.cherryLeaves.blockID, plantValue);
			compostingTypes.put(SCDecoIntegration.autumnSapling.blockID, cropValue);
			compostingTypes.put(SCDecoIntegration.autumnLeaves.blockID, plantValue);
			compostingTypes.put(SCDecoIntegration.flower.blockID, plantValue);
			compostingTypes.put(SCDecoIntegration.flower2.blockID, plantValue);
			compostingTypes.put(SCDecoIntegration.tulip.blockID, plantValue);
		}
    } 
}
