package btw.community.sockthing.sockscrops.block.tileentities;

import btw.block.tileentity.TileEntityDataPacketHandler;
import btw.community.sockthing.sockscrops.block.blocks.FishTrapBlock;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.world.BiomeIDs;
import btw.item.BTWItems;
import btw.item.util.RandomItemStack;
import btw.world.util.WorldUtils;
import net.minecraft.src.*;

import java.util.ArrayList;

public class FishTrapTileEntity extends TileEntity implements TileEntityDataPacketHandler {

    private boolean isBaited;
    private boolean hasFish;
    private int waterSpots;

    private ItemStack fishStack;
    public int ticks;
    public double distanceToPlayer;

    private static ArrayList<Integer> validBait = new ArrayList<Integer>();

    private static ArrayList<RandomItemStack> normalLoot = new ArrayList();
    private static ArrayList<RandomItemStack> exoticLoot = new ArrayList();
    private static ArrayList<RandomItemStack> riverLoot = new ArrayList();
    private static ArrayList<RandomItemStack> oceanLoot = new ArrayList();

    public FishTrapTileEntity(){
        super();
        this.isBaited = false;
        this.hasFish = false;
    }

    @Override
    public void updateEntity()
    {
        EntityPlayer playerInRange = worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 12);

        if (hasFish && playerInRange != null)
        {
            EntityPlayer player = worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 5);

            if (player != null)
            {
                double distance = player.getDistanceSq(xCoord, yCoord, zCoord);

                this.distanceToPlayer = Math.sqrt(distance); // distance;
            }
            else this.distanceToPlayer = 1;

            //System.out.println("Distance: " + distanceToPlayer);


            if (ticks > 360)
            {
                ticks = 0;
            }

            ticks++;
        }


        if (!worldObj.isRemote) {



            //System.out.println(worldObj.getBiomeGenForCoords(xCoord, zCoord));
            //System.out.println(worldObj.getBiomeGenForCoords(xCoord, zCoord).biomeName);

            if (!hasFish && isBaited && isTouchingWater() && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1) {
                // System.out.println("I have water");

                if (isBodyOfWaterLargeEnoughForFishing()) { // && worldObj.rand.nextInt(64) == 0)
                    if (checkForBite()) {
                        //System.out.println("fish");

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

        if (biome == BiomeGenBase.ocean || biome == BiomeGenBase.frozenOcean || biome instanceof BiomeGenBeach || biome == BiomeGenBase.mushroomIslandShore)
        {
            RandomItemStack[] arr = new RandomItemStack[oceanLoot.size()];
            setFishStack( RandomItemStack.getRandomStack( worldObj.rand, oceanLoot.toArray(arr)) );

            System.out.println("Ocean Loot");
        }
        else if (biome.biomeID == BiomeIDs.RIVER_JUNGLE_ID || biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleHills)
        {
            RandomItemStack[] arr = new RandomItemStack[exoticLoot.size()];
            setFishStack( RandomItemStack.getRandomStack( worldObj.rand, exoticLoot.toArray(arr)) );

            System.out.println("Jungle Loot");
        }
        else if (biome instanceof BiomeGenRiver && biome.biomeID != BiomeIDs.RIVER_JUNGLE_ID  )
        {
            RandomItemStack[] arr = new RandomItemStack[riverLoot.size()];
            setFishStack( RandomItemStack.getRandomStack( worldObj.rand, riverLoot.toArray(arr)) );

            System.out.println("River Loot");
        }
        else {
            RandomItemStack[] arr = new RandomItemStack[normalLoot.size()];
            setFishStack( RandomItemStack.getRandomStack( worldObj.rand, normalLoot.toArray(arr)) );

            System.out.println("Normal Loot");
        }

        worldObj.setBlockMetadata(xCoord, yCoord, zCoord, FishTrapBlock.NO_BAIT);

        this.markBlockForUpdate();
    }

    public static void addValidBait(int itemID) {
        validBait.add(itemID);
    }

    public static void addNormalLoot(RandomItemStack randomItemStack) {
        normalLoot.add(randomItemStack);
    }

    public static void addExoticLoot(RandomItemStack randomItemStack) {
        exoticLoot.add(randomItemStack);
    }

    public static void addRiverLoot(RandomItemStack randomItemStack) {
        riverLoot.add(randomItemStack);
    }

    public static void addOceanLoot(RandomItemStack randomItemStack) {
        oceanLoot.add(randomItemStack);
    }

    static {
        addValidBait( BTWItems.creeperOysters.itemID );
        addValidBait( BTWItems.batWing.itemID );
        addValidBait( BTWItems.witchWart.itemID );
        addValidBait( Item.spiderEye.itemID );
        addValidBait( Item.rottenFlesh.itemID );

        addNormalLoot( new RandomItemStack(Item.fishRaw.itemID, 0, 1, 1, 100)); //itemID, damage, minNum, maxNum, weight
        addNormalLoot( new RandomItemStack(Item.bootsLeather.itemID, 0, 1, 1, 1));

        addExoticLoot( new RandomItemStack(Item.fishRaw.itemID, 0, 1, 1, 75)); //itemID, damage, minNum, maxNum, weight
        addExoticLoot( new RandomItemStack(SCItems.tropicalFish.itemID, 0, 1, 1, 25));
        addExoticLoot( new RandomItemStack(Item.bootsLeather.itemID, 0, 1, 1, 1));

        addRiverLoot( new RandomItemStack(Item.fishRaw.itemID, 0, 1, 1, 75)); //itemID, damage, minNum, maxNum, weight
        addRiverLoot( new RandomItemStack(SCItems.salmon.itemID, 0, 1, 1, 25));
        addRiverLoot( new RandomItemStack(Item.bootsLeather.itemID, 0, 1, 1, 1));

        addOceanLoot( new RandomItemStack(Item.fishRaw.itemID, 0, 1, 1, 75)); //itemID, damage, minNum, maxNum, weight
        addOceanLoot( new RandomItemStack(SCItems.cod.itemID, 0, 1, 1, 25));
        addOceanLoot( new RandomItemStack(Item.bootsLeather.itemID, 0, 1, 1, 1));
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
            ticks = tag.getInteger("ticks");
        }

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

        tag.setInteger("ticks", ticks);

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
        NBTTagCompound tag = new NBTTagCompound();

        tag.setInteger("ticks", ticks);
        tag.setBoolean( "scIsBaited", isBaited );
        tag.setBoolean( "scHasFish", hasFish );

        if ( fishStack != null)
        {
            NBTTagCompound fishTag = new NBTTagCompound();

            fishStack.writeToNBT( fishTag );

            tag.setCompoundTag( "fishStack", fishTag );
        }

        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
    }


    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {
        ticks = tag.getInteger("ticks");
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
                    if ( WorldUtils.isWaterSourceBlock( worldObj, iTempI, iTempJ, iTempK ) )
                    {
                        waterSpots++;

                    }
                }
            }
        }

        //System.out.println("Biome: " + worldObj.getBiomeGenForCoords(j, k) );

        if (waterSpots < 62)
        {
            //System.out.println("I DONT have enough water:");
            //System.out.println( waterSpots );
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

                    if ( worldObj.isPrecipitatingAtPos( MathHelper.floor_double( xCoord ),
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

                if ( worldObj.isPrecipitatingAtPos( MathHelper.floor_double( xCoord ),
                        MathHelper.floor_double( zCoord ) ) )
                {
                    // in the rain

                    iBiteOdds /= 2;
                }
            }

//	         System.out.println(iBiteOdds);

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
                    System.out.println("bite odds: " + iBiteOdds);
                    return true;
                }
            }
        }

        return false;
    }

    public void markBlockForUpdate() {

        this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    }

    public static boolean isFishingBait( ItemStack stack )
    {
        int itemID = stack.itemID;

        if (validBait.contains(itemID))
        {
            return true;
        }
        else return false;
    }
}
