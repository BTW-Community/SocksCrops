package btw.community.sockthing.sockscrops.block.tileentities;

import btw.block.BTWBlocks;
import btw.block.tileentity.TileEntityDataPacketHandler;
import btw.inventory.util.InventoryUtils;
import net.minecraft.src.*;

public class CookingPotTileEntity extends TileEntity implements TileEntityDataPacketHandler, IInventory
{

    /** The pan's rotation. */
    private int potRotation;

    private ItemStack cookStack;
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


    //------------- Cooking ------------//

    @Override
    public void updateEntity()
    {
        super.updateEntity();

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
        if ( cookStack != null ) {

            if (cookStack.itemID == Item.beefCooked.itemID )
            {
                return 7;
            }
            else return 3;

        }
        return 0;
    }

    public void markBlockForUpdate() {

        this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    }

    public ItemStack getCookStack() {
        return cookStack;
    }

    public void setCookStack(ItemStack stack) {
        this.cookStack = stack;
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
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return cookStack;
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
        if (slot == 0 ) setCookStack(stack);
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

        if ( cookStack != null)
        {
            NBTTagCompound cookTag = new NBTTagCompound();

            cookStack.writeToNBT( cookTag );

            tag.setCompoundTag( "cookStack", cookTag );
        }

        if ( inputStack != null)
        {
            NBTTagCompound inputTag = new NBTTagCompound();

            cookStack.writeToNBT( inputTag );

            tag.setCompoundTag( "inputStack", inputTag );
        }

        tag.setInteger( "cookCounter", cookCounter );
        tag.setInteger( "cookBurning", cookBurningCounter );
        tag.setBoolean( "hasLid", hasLid );
        tag.setBoolean( "onCampfire", onCampfire );
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        this.potRotation = tag.getByte("potRotation");

        NBTTagCompound cookTag = tag.getCompoundTag( "cookStack" );

        if ( cookTag != null )
        {
            cookStack = ItemStack.loadItemStackFromNBT( cookTag );
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

    public boolean hasLid() {
        return this.hasLid;
    }


    public void setHasLid(boolean boo) {
        this.hasLid = boo;
    }

    public boolean isOnCampfire() {
        return this.onCampfire;
    }


    public void setOnCampfire(boolean boo) {
        this.onCampfire = boo;
    }



}