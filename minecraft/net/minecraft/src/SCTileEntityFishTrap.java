package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class SCTileEntityFishTrap extends TileEntity implements FCITileEntityDataPacketHandler {

	private boolean isBaited;
	private boolean hasFish;
	private int waterSpots;
	
	private ItemStack fishStack;
	
	private static ArrayList<Integer> validBait = new ArrayList<Integer>();

	private static ArrayList<FCUtilsRandomItemStack> normalLoot = new ArrayList();
	private static ArrayList<FCUtilsRandomItemStack> exoticLoot = new ArrayList();
	private static ArrayList<FCUtilsRandomItemStack> riverLoot = new ArrayList();
	private static ArrayList<FCUtilsRandomItemStack> oceanLoot = new ArrayList();
	
	public static void addValidBait(int itemID) {
		validBait.add(itemID);
	}
	
	public static void addNormalLoot(FCUtilsRandomItemStack randomItemStack) {
		normalLoot.add(randomItemStack);
	}
	
	public static void addExoticLoot(FCUtilsRandomItemStack randomItemStack) {
		exoticLoot.add(randomItemStack);
	}
	
	public static void addRiverLoot(FCUtilsRandomItemStack randomItemStack) {
		riverLoot.add(randomItemStack);
	}
	
	public static void addOceanLoot(FCUtilsRandomItemStack randomItemStack) {
		oceanLoot.add(randomItemStack);
	}
	
	static {    	
		addNormalLoot( new FCUtilsRandomItemStack(Item.fishRaw.itemID, 0, 1, 1, 100)); //itemID, damage, minNum, maxNum, weight
		addNormalLoot( new FCUtilsRandomItemStack(Item.bootsLeather.itemID, 0, 1, 1, 1));
		
		addExoticLoot( new FCUtilsRandomItemStack(Item.fishRaw.itemID, 0, 1, 1, 100)); //itemID, damage, minNum, maxNum, weight
		addExoticLoot( new FCUtilsRandomItemStack(SCDefs.tropicalRaw.itemID, 0, 1, 1, 25));
		addExoticLoot( new FCUtilsRandomItemStack(Item.bootsLeather.itemID, 0, 1, 1, 1));
		
		addRiverLoot( new FCUtilsRandomItemStack(Item.fishRaw.itemID, 0, 1, 1, 100)); //itemID, damage, minNum, maxNum, weight
		addRiverLoot( new FCUtilsRandomItemStack(SCDefs.salmonRaw.itemID, 0, 1, 1, 25));
		addRiverLoot( new FCUtilsRandomItemStack(Item.bootsLeather.itemID, 0, 1, 1, 1));
		
		addOceanLoot( new FCUtilsRandomItemStack(Item.fishRaw.itemID, 0, 1, 1, 100)); //itemID, damage, minNum, maxNum, weight
		addOceanLoot( new FCUtilsRandomItemStack(SCDefs.codRaw.itemID, 0, 1, 1, 25));
		addOceanLoot( new FCUtilsRandomItemStack(Item.bootsLeather.itemID, 0, 1, 1, 1));
	}

	static {  
		validBait.add(FCBetterThanWolves.fcItemCreeperOysters.itemID);
		validBait.add(FCBetterThanWolves.fcItemBatWing.itemID);
		validBait.add(FCBetterThanWolves.fcItemWitchWart.itemID);
		validBait.add(Item.spiderEye.itemID);
		validBait.add(Item.rottenFlesh.itemID);
	}
	
	public SCTileEntityFishTrap() {
		this.isBaited = false;
		this.hasFish = false;
	}
	
	public void setBaited(boolean isBaited) {
		this.isBaited = isBaited;
	}
	
	public boolean isBaited() {
		return isBaited;
	}
	
	
	public boolean hasFish() {
		return hasFish;
	}

	public void setHasFish(boolean hasFish) {
		this.hasFish = hasFish;
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			
			System.out.println(worldObj.getBiomeGenForCoords(xCoord, zCoord));
			System.out.println(worldObj.getBiomeGenForCoords(xCoord, zCoord).biomeName);
			
			if (isTouchingWater() && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1) {
				// System.out.println("I have water");
				
				if (isBodyOfWaterLargeEnoughForFishing() && worldObj.rand.nextInt(64) == 0) {
					if (checkForBite()) {
						System.out.println("fish");

						catchFish();
					}
				}
			}
		}
	}
	
	private void catchFish()
	{
		setHasFish(true);
		setBaited(false);
		
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(xCoord, zCoord);
		
		if (biome == BiomeGenBase.ocean || biome == BiomeGenBase.frozenOcean || biome instanceof BiomeGenBeach)
		{
			FCUtilsRandomItemStack[] arr = new FCUtilsRandomItemStack[oceanLoot.size()];
			setFishStack( FCUtilsRandomItemStack.GetRandomStack( worldObj.rand, oceanLoot.toArray(arr)) );
		}
		else if (biome instanceof SCBiomeGenRiverJungle || biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleHills)
		{
			FCUtilsRandomItemStack[] arr = new FCUtilsRandomItemStack[exoticLoot.size()];
			setFishStack( FCUtilsRandomItemStack.GetRandomStack( worldObj.rand, exoticLoot.toArray(arr)) );
		}
		else if (biome instanceof BiomeGenRiver && !(biome instanceof SCBiomeGenRiverJungle) )
		{
			FCUtilsRandomItemStack[] arr = new FCUtilsRandomItemStack[riverLoot.size()];
			setFishStack( FCUtilsRandomItemStack.GetRandomStack( worldObj.rand, riverLoot.toArray(arr)) );
		}
		else {
			FCUtilsRandomItemStack[] arr = new FCUtilsRandomItemStack[normalLoot.size()];
			setFishStack( FCUtilsRandomItemStack.GetRandomStack( worldObj.rand, normalLoot.toArray(arr)) );
		}

		worldObj.setBlockMetadata(xCoord, yCoord, zCoord, 0);
		this.markBlockForUpdate();
	}

		
//        int i = MathHelper.floor_double( xCoord );
//        int j = MathHelper.floor_double( yCoord );
//        int k = MathHelper.floor_double( zCoord );
//		
//        for ( int iTempI = i - 2; iTempI <= i + 2; iTempI++ )
//        {
//            for ( int iTempJ = j - 2; iTempJ <= j + 2; iTempJ++ )
//            {
//                for ( int iTempK = k - 2; iTempK <= k + 2; iTempK++ )
//                {
//                    if(worldObj.rand.nextInt(16)==0)
//                    {
//                        worldObj.spawnParticle("reddust", iTempI+0.5D, iTempJ+0.5D, iTempK+0.5D, 0, 0, 0);
//                    }      	
//                }
//            }
//        }
	
	public ItemStack getFishStack() {
		return fishStack;
	}
	
	public void setFishStack(ItemStack fishStack) {
		this.fishStack = fishStack;
		markBlockForUpdate();
	}
	
    @Override
    public void readFromNBT(NBTTagCompound tag) {
    	super.readFromNBT(tag);
    	
        if ( tag.hasKey( "scIsBaited" ) )
        {
        	isBaited  = tag.getBoolean( "scIsBaited" );
        }
        
        if( tag.hasKey( "scHasFish" ) )
        {
        	hasFish = tag.getBoolean( "scHasFish" );
        }
        
        NBTTagCompound fishTag = tag.getCompoundTag( "fishStack" );

        if ( fishTag != null )
        {
        	fishStack = ItemStack.loadItemStackFromNBT( fishTag );
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag) {
    	super.writeToNBT(tag);
    	
        tag.setBoolean( "scIsBaited", isBaited );
        
        tag.setBoolean( "scHasFish", hasFish );
        
        if ( fishStack != null)
        {
            NBTTagCompound fishTag = new NBTTagCompound();
            
            fishStack.writeToNBT( fishTag );
            
            tag.setCompoundTag( "fishStack", fishTag );
        }
        
        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord ); 
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
    	NBTTagCompound nbttagcompound1 = new NBTTagCompound();

        nbttagcompound1.setBoolean( "scIsBaited", isBaited );
        nbttagcompound1.setBoolean( "scHasFish", hasFish );
        
        if ( fishStack != null)
        {
            NBTTagCompound fishTag = new NBTTagCompound();
            
            fishStack.writeToNBT( fishTag );
            
            nbttagcompound1.setCompoundTag( "fishStack", fishTag );
        }
        
        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, nbttagcompound1 );
    }
	
    
    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {
    	isBaited = tag.getBoolean( "scIsBaited" );
    	hasFish = tag.getBoolean( "scHasFish" );
    	
        NBTTagCompound fishTag = tag.getCompoundTag( "fishStack" );

        if ( fishTag != null )
        {
        	fishStack = ItemStack.loadItemStackFromNBT( fishTag );
        }

        // force a visual update for the block since the above variables affect it
        
        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );        
    } 
    
	private boolean isTouchingWater()
	{
		if (worldObj.getBlockId(xCoord - 1, yCoord, zCoord) == Block.waterStill.blockID ||
			worldObj.getBlockId(xCoord + 1, yCoord, zCoord) == Block.waterStill.blockID ||
			worldObj.getBlockId(xCoord, yCoord, zCoord - 1) == Block.waterStill.blockID ||
			worldObj.getBlockId(xCoord, yCoord, zCoord + 1) == Block.waterStill.blockID ||
			worldObj.getBlockId(xCoord, yCoord + 1, zCoord) == Block.waterStill.blockID )
		{
			return true;
		}
		else return false;
	}
	
	//SCADDDON: Copied from EntityFishHook
	public boolean isBodyOfWaterLargeEnoughForFishing()
	{
        int i = MathHelper.floor_double( xCoord );
        int j = MathHelper.floor_double( yCoord );
        int k = MathHelper.floor_double( zCoord );

        waterSpots = 0;
        
        for ( int iTempI = i - 2; iTempI <= i + 2; iTempI++ )
        {
            for ( int iTempJ = j - 2; iTempJ <= j + 2; iTempJ++ )
            {
                for ( int iTempK = k - 2; iTempK <= k + 2; iTempK++ )
                {
                	if ( FCUtilsWorld.IsWaterSourceBlock( worldObj, iTempI, iTempJ, iTempK ) )
            		{
                		waterSpots++;

            		}                	
                }
            }
        }
        
        //System.out.println("Biome: " + worldObj.getBiomeGenForCoords(j, k) );
        
        if (waterSpots < 62) 
        {
            System.out.println("I DONT have enough water:");
            System.out.println( waterSpots );
        	return false;
        }
        else 
        {
            //System.out.println("I have enough water:");
            //System.out.println( waterSpots );
            
          	return true;
        }
        
        

        
		
	}
	
	private boolean checkForBite()
    {
		
		
    	if ( isBaited )
    	{
//	        int iBiteOdds = 100; // previously 1500 
	        int iBiteOdds = 4000 - waterSpots * 15; //max waterspots is 124, min is 50 atm
	        
	        
	        
	        int iTimeOfDay = (int)( worldObj.worldInfo.getWorldTime() % 24000L );
	        
	        if ( iTimeOfDay > 14000 && iTimeOfDay < 22000 )
	        {
	        	// at night
	        	
	        	int iMoonPhase = worldObj.getMoonPhase();
	        	
	        	if ( iMoonPhase == 0 )
	        	{
	        		iBiteOdds /= 8;
	        	}
	        	else
	        	{
	        		iBiteOdds *= 4;
	        		
	                if ( worldObj.IsPrecipitatingAtPos( MathHelper.floor_double( xCoord ), 
	                	MathHelper.floor_double( zCoord ) ) )
	                {
		            	// in the rain
		            	
	                	iBiteOdds /= 2;
	                }
	        	}
	        }
	        else
	        {
	        	if ( iTimeOfDay < 2000 || iTimeOfDay > 22000 || 
	        		( iTimeOfDay > 10000 && iTimeOfDay < 14000 ) ) 
	            {
	        		// dawn or dusk
	        		
	        		iBiteOdds /= 2;
	            }
	
                if ( worldObj.IsPrecipitatingAtPos( MathHelper.floor_double( xCoord ), 
                	MathHelper.floor_double( zCoord ) ) )
	            {
	            	// in the rain
	            	
	            	iBiteOdds /= 2;
	            }
	        }
	        
	        System.out.println(iBiteOdds);
	
	        if ( worldObj.rand.nextInt( iBiteOdds ) == 0 )
	        {
//	        	if ( worldObj.canBlockSeeTheSky( MathHelper.floor_double( xCoord ), 
//	        		MathHelper.floor_double( yCoord) + 1, MathHelper.floor_double( zCoord) ) )
//	        	{	        		
//		        	if ( isBodyOfWaterLargeEnoughForFishing() )
//		        	{
//		        		return true;
//		        	}
//	        	}
	        	if ( isBodyOfWaterLargeEnoughForFishing() )
	        	{
	        		return true;
	        	}
	        }
    	}
    	
    	return false;
    }

	public void markBlockForUpdate() {
	 	
		this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );   
	}
	
    public static boolean IsFishingBait( ItemStack stack )
    {
    	int itemID = stack.itemID;
    	
    	if (validBait.contains(itemID))
    	{
    		return true;
    	}    	
    	else return false;
    }  

}
