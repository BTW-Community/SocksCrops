package btw.community.sockthing.sockscrops.block.tileentities;

import btw.block.BTWBlocks;
import btw.block.tileentity.TileEntityDataPacketHandler;
import btw.inventory.util.InventoryUtils;
import net.minecraft.src.*;

import java.util.Arrays;
import java.util.List;

public class CookingPotTileEntity extends TileEntity implements TileEntityDataPacketHandler, IInventory
{

    public float lidProgress = 0.0F;   // 0 = closed, 1 = fully open
    public boolean lidOpen = false;


    /** The pan's rotation. */
    private int potRotation;

    private ItemStack[] cookStack = new ItemStack[6];
    private ItemStack inputStack;

    private boolean hasLid;
    private boolean onCampfire;

    public int cookCounter = 0;

    private final int panBurnTimeMultiplier = 4; //campfireBurnTimeMultiplier = 8

    private final int timeToCook = ( TileEntityFurnace.DEFAULT_COOK_TIME *
            panBurnTimeMultiplier *
            3 / 2 ); // this line represents efficiency relative to furnace cooking

    private final int timeToBurnFood = ( timeToCook / 2 );

    private int cookBurningCounter = 0;

    public float tickCount;

    public static final int EMPTY = 0;
    public static final int SPOILED = 1;
    public static final int BURNED = 2;
    public static final int WATER = 3;
    public static final int MILK = 4;
    public static final int CHOCOLATE_MILK = 5;
    public static final int MUSHROOM_SOUP = 6;
    public static final int HEARTY_STEW = 7;
    public static final int CHICKEN_SOUP = 8;
    public static final int CHOWDER = 9;

    //------------- Cooking ------------//

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (lidOpen && lidProgress < 1.0F) {
            lidProgress += 0.05F; // open speed
            if (lidProgress > 1.0F) lidProgress = 1.0F;
        }
        else if (!lidOpen && lidProgress > 0.0F) {
            lidProgress -= 0.05F; // close speed
            if (lidProgress < 0.0F) lidProgress = 0.0F;
        }

        if (isFoodCooked() && getFireLevel() >= 2)
        {
            ++this.tickCount;
        }
        else this.tickCount = 0;


        if ( !worldObj.isRemote )
        {
            int fireLevel = getFireLevel();


            if ( fireLevel > 0 )
            {
                updateCookState();
            }
        }

    }


    private void updateCookState()
    {
//        if ( cookStack != null )
//        {
//
//            int fireLevel = getFireLevel();
//
//            if ( fireLevel >= 2 )
//            {
//                SCCraftingManagerPanCookingRecipe recipe = SCCraftingManagerPanCooking.instance.getRecipe( cookStack );
//
//                if ( recipe != null )
//                {
//                    cookCounter++;
//
//                    if ( cookCounter >= timeToCook )
//                    {
//                        setCookStack( recipe.getOutput() );
//
//                        cookCounter = 0;
//
//                        // don't reset burn counter here, as the food can still burn after cooking
//                    }
//                }
//
//                if ( fireLevel >= 3 && cookStack.itemID != FCBetterThanWolves.fcItemMeatBurned.itemID )
//                {
//                    cookBurningCounter++;
//
//                    if ( cookBurningCounter >= timeToBurnFood )
//                    {
//                        setCookStack( recipe.getBurnedOutput() );
//
//                        cookCounter = 0;
//                        cookBurningCounter = 0;
//                    }
//                }
//            }
//        }
    }


    private int getFireLevel() {

        int blockBelow = worldObj.getBlockId(xCoord, yCoord - 1 , zCoord);

        if (blockBelow == BTWBlocks.smallCampfire.blockID) return 1;
        else if (blockBelow == BTWBlocks.mediumCampfire.blockID) return 2;
        else if (blockBelow == BTWBlocks.largeCampfire.blockID) return 3;

        else if (blockBelow == BTWBlocks.burningOven.blockID) return 2;

        else return 0;
    }

    public boolean isFoodCooking()
    {
//        if ( cookStack != null && getFireLevel() >= 2 )
//        {
//            if ( SCCraftingManagerPanCooking.instance.getRecipe( cookStack ) != null )
//            {
//                System.out.println("cooking");
//                return true;
//            }
//        }

        return false;
    }

    public boolean isFoodBurning()
    {

//        if ( cookStack != null && getFireLevel() >= 3)
//        {
//            SCCraftingManagerPanCookingRecipe recipe = SCCraftingManagerPanCooking.instance.getRecipe( cookStack ) ;
//
//            if (recipe != null && cookStack.itemID != recipe.getBurnedOutput().itemID )
//            {
//                System.out.println("burning");
//                return true;
//            }
//
//        }

        return false;
    }

    public boolean isFoodCooked()
    {

//        if ( cookStack != null && inputStack != null ) {
//            System.out.println(cookStack.getDisplayName() + inputStack.getDisplayName());
//
//            SCCraftingManagerPanCookingRecipe recipe = SCCraftingManagerPanCooking.instance.getRecipe( inputStack ) ;
//
//            if (recipe != null && cookStack.itemID == recipe.getOutput().itemID  )
//            {
//
//                return true;
//            }
//        }
        return false;
    }


    public int getCookType() {
        return WATER;
    }

    public void markBlockForUpdate() {

        this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    }

    public ItemStack getCookStack(int slot) {
        return cookStack[slot];
    }

    public void setCookStack(int slot, ItemStack stack) {
        this.cookStack[slot] = stack;
    }

    public List<ItemStack> getCookStacks() {
        return Arrays.asList(cookStack);
    }

    public ItemStack getInputStack() {
        return inputStack;
    }

    public void setInputStack(ItemStack stack) {
        this.inputStack = stack;
    }

    //------------- IInventory ------------//

    @Override
    public int getSizeInventory() {
        return cookStack.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return cookStack[slot];
    }

    @Override
    public ItemStack decrStackSize( int iSlot, int iAmount )
    {
        return InventoryUtils.decreaseStackSize( this, iSlot, iAmount );
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        setCookStack(slot, stack);
    }

    @Override
    public String getInvName() {
        return null;
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        if ( worldObj.getBlockTileEntity( xCoord, yCoord, zCoord ) == this )
        {
            return ( entityplayer.getDistanceSq( (double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D )
                    <= 64D );
        }

        return false;
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isStackValidForSlot(int slot, ItemStack stack) {

        if (isValidCookItem(stack))
        {
            return true;
        }
        return false;
    }

    public boolean isValidCookItem( ItemStack stack )
    {
//        if ( SCCraftingManagerPanCooking.instance.getRecipe( stack ) != null )
//        {
//            return true;
//        }

        return false;
    }

    //------------- NBT ------------//

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setByte("potRotation", (byte)(this.potRotation & 255));

//        if ( cookStack != null)
//        {
//            NBTTagCompound cookTag = new NBTTagCompound();
//
//            cookStack.writeToNBT( cookTag );
//
//            tag.setCompoundTag( "cookStack", cookTag );
//        }

//        if ( inputStack != null)
//        {
//            NBTTagCompound inputTag = new NBTTagCompound();
//
//            cookStack.writeToNBT( inputTag );
//
//            tag.setCompoundTag( "inputStack", inputTag );
//        }

        NBTTagList tagList = new NBTTagList();

        for (int iTempIndex = 0; iTempIndex < cookStack.length; iTempIndex++ )
        {
            if (cookStack[iTempIndex] != null )
            {
                NBTTagCompound tempTag = new NBTTagCompound();

                tempTag.setByte( "Slot", (byte)iTempIndex );

                cookStack[iTempIndex].writeToNBT(tempTag);

                tagList.appendTag( tempTag );
            }
        }

        tag.setTag( "Items", tagList );

        tag.setInteger( "cookCounter", cookCounter );
        tag.setInteger( "cookBurning", cookBurningCounter );
        tag.setBoolean( "hasLid", hasLid );
        tag.setBoolean( "onCampfire", onCampfire );


        tag.setBoolean("lidOpen", lidOpen);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        this.potRotation = tag.getByte("potRotation");

//        NBTTagCompound cookTag = tag.getCompoundTag( "cookStack" );
//
//        if ( cookTag != null )
//        {
//            cookStack = ItemStack.loadItemStackFromNBT( cookTag );
//        }

        NBTTagList tagList = tag.getTagList( "Items" );

        cookStack = new ItemStack[getSizeInventory()];

        for ( int iTempIndex = 0; iTempIndex < tagList.tagCount(); iTempIndex++ )
        {
            NBTTagCompound tempTag = (NBTTagCompound)tagList.tagAt( iTempIndex );

            int tempSlot = tempTag.getByte( "Slot" ) & 0xff;

            if ( tempSlot >= 0 && tempSlot < cookStack.length )
            {
                cookStack[tempSlot] = ItemStack.loadItemStackFromNBT(tempTag);
            }
        }

        NBTTagCompound inputTag = tag.getCompoundTag( "inputStack" );

        if ( inputTag != null )
        {
            inputStack = ItemStack.loadItemStackFromNBT( inputTag );
        }

        if ( tag.hasKey( "cookCounter" ) )
        {
            cookCounter = tag.getInteger( "cookCounter" );
        }

        if ( tag.hasKey( "cookBurning" ) )
        {
            cookBurningCounter = tag.getInteger( "cookBurning" );
        }

        if ( tag.hasKey( "hasLid" ) )
        {
            hasLid = tag.getBoolean( "hasLid" );
        }

        if ( tag.hasKey( "onCampfire" ) )
        {
            onCampfire = tag.getBoolean( "onCampfire" );
        }

        if ( tag.hasKey( "lidOpen" ) )
        {
            lidOpen = tag.getBoolean( "lidOpen" );
        }
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }


    @Override
    public void readNBTFromPacket(NBTTagCompound nbttagcompound) {
        this.readFromNBT(nbttagcompound);
    }


    public int getSkullRotation()
    {
        return this.potRotation;
    }

    public void setSkullRotation(int par1)
    {
        this.potRotation = par1;
    }


    public int GetSkullRotationServerSafe()
    {
        return this.potRotation;
    }

    public boolean isLidOpen() {
        return this.lidOpen;
    }


    public void setLidOpen(boolean boo) {
        this.lidOpen = boo;
    }

    public boolean isOnCampfire() {
        return this.onCampfire;
    }


    public void setOnCampfire(boolean boo) {
        this.onCampfire = boo;
    }

}