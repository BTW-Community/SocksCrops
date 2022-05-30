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
	
	private boolean m_bIsCooking = false;
	
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
    	else 
    	{    
    		this.fillLevel = getFillLevel();
    		
    		if ( m_bIsCooking )
    		{
				if ( worldObj.rand.nextInt( 20 ) == 0 )
				{
	                double xPos = xCoord + 0.25F + worldObj.rand.nextFloat() * 0.5F;
	                double yPos = yCoord + 1.0F + worldObj.rand.nextFloat() * 0.25F;
	                double zPos = zCoord + 0.25F + worldObj.rand.nextFloat() * 0.5F;
	                
	                worldObj.spawnParticle( "fcwhitesmoke", xPos, yPos, zPos, 0.0D, 0.0D, 0.0D );
	            }
    		}
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
    	
    	if (getFillLevel() == maxFillLevel)
    	{
    		if ( blockAbove != null && blockAbove.IsGroundCover( ) && !seeSky )
        	{
    			m_bIsCooking = false;
        	}
    		else {
    			m_bIsCooking = true;
    			worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
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
            			worldObj.setBlockMetadata(xCoord, yCoord, zCoord, newMeta );
            			worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
            		}
            		
            		        		
            		if ( m_iCookCounter >= m_iTimeToCook )
            		{
            			m_iCookCounter = 300;
            			this.m_bIsCooking = false;
            			
            			worldObj.setBlockMetadata(xCoord, yCoord, zCoord, 15 );
            			return;
            		}
        		}
        		else {
        			if ( m_iCookCounter >= m_iTimeToCook )
            		{
            			m_iCookCounter = 300;
            			this.m_bIsCooking = false;
            			
            			worldObj.setBlockMetadata(xCoord, yCoord, zCoord, 15 );
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
    
    static
    {
    	//SCStuff
    	compostingTypes.put(SCDefs.bambooItem.itemID, 2);
    	compostingTypes.put(SCDefs.bambooShoot.blockID, 2);
    	compostingTypes.put(SCDefs.sweetberry.itemID, 4);
    	compostingTypes.put(SCDefs.blueberry.itemID, 4);
    	compostingTypes.put(SCDefs.sweetberrySapling.itemID, 2);
    	compostingTypes.put(SCDefs.blueberrySapling.itemID, 2);
    	compostingTypes.put(SCDefs.pumpkinSliceRaw.itemID, 4);
    	compostingTypes.put(SCDefs.melonHoneydewSlice.itemID, 4);
    	compostingTypes.put(SCDefs.melonCantaloupeSlice.itemID, 4);
    	compostingTypes.put(SCDefs.melonCanarySlice.itemID, 4);
    	
    	compostingTypes.put(SCDefs.clover.blockID, 1);
    	compostingTypes.put(SCDefs.shortPlant.blockID, 1);
    	compostingTypes.put(SCDefs.tallPlant.blockID, 1);
    	compostingTypes.put(SCDefs.mossCarpet.blockID, 1);
    	compostingTypes.put(SCDefs.lilyRose.blockID, 1);

    	//Seeds
    	compostingTypes.put(Item.melonSeeds.itemID, 2);
    	compostingTypes.put(Item.pumpkinSeeds.itemID, 2);
    	compostingTypes.put(FCBetterThanWolves.fcItemWheatSeeds.itemID, 2);
    	compostingTypes.put(Item.netherStalkSeeds.itemID, 2);
    	compostingTypes.put(FCBetterThanWolves.fcItemHempSeeds.itemID, 2);
    	compostingTypes.put(FCBetterThanWolves.fcItemCarrotSeeds.itemID, 2);
    	
    	//other
    	compostingTypes.put(Item.spiderEye.itemID, 2);    	
    	compostingTypes.put(Item.fermentedSpiderEye.itemID, 2);    	
    	compostingTypes.put(Item.poisonousPotato.itemID, 2);    	
    	compostingTypes.put(FCBetterThanWolves.fcItemHemp.itemID, 2);    	
    	compostingTypes.put(FCBetterThanWolves.fcItemFoulFood.itemID, 2);    	
    	compostingTypes.put(FCBetterThanWolves.fcItemDogFood.itemID, 2);    	
    	compostingTypes.put(FCBetterThanWolves.fcItemCreeperOysters.itemID, 2);    	
    	compostingTypes.put(Block.cocoaPlant.blockID, 2);   	
    	
    	//Plants    	
    	compostingTypes.put(Item.reed.itemID, 2);
    	compostingTypes.put(FCBetterThanWolves.fcItemReedRoots.itemID, 2);
    	compostingTypes.put(Block.tallGrass.blockID, 1);
    	compostingTypes.put(Block.plantYellow.blockID, 1);
    	compostingTypes.put(Block.plantRed.blockID, 1);
    	compostingTypes.put(Block.leaves.blockID, 1);
    	compostingTypes.put(FCBetterThanWolves.fcBlockBloodLeaves.blockID, 1);    	
    	//Blood sapling here..    	
    	compostingTypes.put(Block.vine.blockID, 1);
    	compostingTypes.put(Block.sapling.blockID, 2);
    	compostingTypes.put(Block.waterlily.blockID, 2);
    	compostingTypes.put(FCBetterThanWolves.fcItemMushroomBrown.itemID, 4);
    	compostingTypes.put(FCBetterThanWolves.fcItemMushroomRed.itemID, 4);
    	
    	compostingTypes.put(FCBetterThanWolves.fcItemBark.itemID, 2);
    	compostingTypes.put(FCBetterThanWolves.fcItemSawDust.itemID, 1);  
    	
    	//Food
    	compostingTypes.put(FCBetterThanWolves.fcItemMushroomBrown.itemID, 4);
    	compostingTypes.put(FCBetterThanWolves.fcItemMushroomRed.itemID, 4);
    	compostingTypes.put(Item.appleRed.itemID, 4);
    	compostingTypes.put(Item.melon.itemID, 4);
    	compostingTypes.put(Item.carrot.itemID, 4);
    	compostingTypes.put(FCBetterThanWolves.fcItemCarrot.itemID, 4);
    	compostingTypes.put(Item.potato.itemID, 4);
    	
    	
    } 

    public boolean isValidItem(int id)
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
    
}
