package btw.community.sockthing.sockscrops.block.tileentities;

import btw.block.tileentity.TileEntityDataPacketHandler;
import btw.inventory.util.InventoryUtils;
import net.minecraft.src.*;

public class RopeHangingItemsTileEntity extends TileEntity implements TileEntityDataPacketHandler, IInventory {

    private ItemStack[] storageStack = new ItemStack[3];

    private int[] itemRotation = new int[3];

    private final int timeToCook = ( 10 * 60 ); //( 10 * 60 * 20 );
    private int cookCounter = 0;
    private final int m_iRainCookDecay = 10;

    private boolean isDrying;

    public RopeHangingItemsTileEntity() {
        isDrying = false;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        System.out.println("isDrying: " + isDrying);

//        if (RopeDryingCraftingManager.instance.getRecipe( storageStack ) != null)
//        {
//            UpdateCooking();
//        }
//
//        if ( worldObj.isRemote )
//        {
//            if ( isDrying && worldObj.rand.nextInt( 20 ) == 0)
//            {
//                if (RopeDryingCraftingManager.instance.getRecipe( storageStack ) != null && storageStack.isItemEqual(SCCraftingManagerRopeDrying.instance.getRecipe( storageStack ).getInput()))
//                {
//                    double xPos = xCoord + 0.25F + worldObj.rand.nextFloat() * 0.5F;
//                    double yPos = yCoord + 0.5F + worldObj.rand.nextFloat() * 0.25F;
//                    double zPos = zCoord + 0.25F + worldObj.rand.nextFloat() * 0.5F;
//
//                    worldObj.spawnParticle( "fcwhitesmoke", xPos, yPos, zPos, 0.0D, 0.0D, 0.0D );
//                }
//            }
//        }
    }

    public void UpdateCooking()
    {
        boolean newDrying;
        int blockMaxNaturalLight = worldObj.getBlockNaturalLightValueMaximum( xCoord, yCoord, zCoord );
        int blockCurrentNaturalLight = blockMaxNaturalLight - worldObj.skylightSubtracted;

        newDrying = blockCurrentNaturalLight >= 15;

        int blockAboveID = worldObj.getBlockId( xCoord, yCoord + 1, zCoord );
        Block blockAbove = Block.blocksList[blockAboveID];

        if ( blockAbove != null && blockAbove.isGroundCover( ) )
        {
            newDrying = false;
        }

        if ( newDrying != isDrying )
        {
            isDrying = newDrying;

            worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
        }

//        if ( isDrying )
//        {
//            cookCounter++;
//
//            if ( cookCounter >= timeToCook )
//            {
//                //ropeBlock.OnFinishedCooking( worldObj, xCoord, yCoord, zCoord );
//
//                this.setStorageStack( RopeDryingCraftingManager.instance.getRecipe( storageStack ).getOutput() );
//                isDrying = false;
//
//                return;
//            }
//        }
//        else
//        {
//            if ( IsRainingOnBrick( worldObj, xCoord, yCoord, zCoord ) )
//            {
//                cookCounter -= m_iRainCookDecay;
//
//                if ( cookCounter < 0 )
//                {
//                    cookCounter = 0;
//                }
//            }
//        }
    }

    public boolean IsRainingOnBrick(World world, int i, int j, int k )
    {
        return world.isRaining() && world.isRainingAtPos( i, j, k );
    }

    private int ComputeCookLevel()
    {
        if ( cookCounter > 0 )
        {
            int iCookLevel= (int)( ( (float)cookCounter / (float)timeToCook ) * 7F ) + 1;

            return MathHelper.clamp_int( iCookLevel, 0, 7 );
        }

        return 0;
    }

    public ItemStack[] getStorageStack() {
        return storageStack;
    }

    public void setStorageStack(ItemStack stackToStore, int slot) {
        this.storageStack[slot] = stackToStore;
        markBlockForUpdate();
    }

    public void setItemRotation(int slot, int rotation) {
        this.itemRotation[slot] = rotation;
    }

    public int[] getItemRotation()
    {
        return this.itemRotation;
    }

    public void markBlockForUpdate()
    {
        this.worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
    }

    //------------- NBT ------------//

    @Override
    public void readFromNBT( NBTTagCompound tag )
    {
        super.readFromNBT( tag );


        NBTTagList tagList = tag.getTagList( "Items" );

        for ( int iTempIndex = 0; iTempIndex < tagList.tagCount(); iTempIndex++ )
        {
            NBTTagCompound tempTag = (NBTTagCompound)tagList.tagAt( iTempIndex );

            int tempSlot = tempTag.getByte( "Slot" ) & 0xff;

            if ( tempSlot >= 0 && tempSlot < storageStack.length )
            {
                storageStack[tempSlot] = ItemStack.loadItemStackFromNBT(tempTag);
            }
        }

        if( tag.hasKey( "Rot" ) )
        {
            this.itemRotation = tag.getIntArray("Rot");
        }

        if( tag.hasKey( "cookCounter" ) )
        {
            cookCounter = tag.getInteger( "cookCounter" );
        }

    }

    @Override
    public void writeToNBT( NBTTagCompound tag )
    {
        super.writeToNBT( tag );

        NBTTagList tagList = new NBTTagList();

        for (int iTempIndex = 0; iTempIndex < storageStack.length; iTempIndex++ )
        {
            if (storageStack[iTempIndex] != null )
            {
                NBTTagCompound tempTag = new NBTTagCompound();

                tempTag.setByte( "Slot", (byte)iTempIndex );

                storageStack[iTempIndex].writeToNBT(tempTag);

                tagList.appendTag( tempTag );
            }
        }

        tag.setTag( "Items", tagList );

        tag.setIntArray("Rot", this.itemRotation);
        tag.setInteger( "cookCounter", cookCounter );

        // force a visual update for the block since the above variables affect it

        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );

    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();

        NBTTagList tagList = new NBTTagList();

        for (int iTempIndex = 0; iTempIndex < storageStack.length; iTempIndex++ )
        {
            if (storageStack[iTempIndex] != null )
            {
                NBTTagCompound tempTag = new NBTTagCompound();

                tempTag.setByte( "Slot", (byte)iTempIndex );

                storageStack[iTempIndex].writeToNBT(tempTag);

                tagList.appendTag( tempTag );
            }
        }

        tag.setTag( "Items", tagList );


        tag.setIntArray("Rot", this.itemRotation);
        tag.setInteger( "cookCounter", cookCounter );

        return new Packet132TileEntityData( xCoord, yCoord, zCoord, 1, tag );
    }


    //------------- FCITileEntityDataPacketHandler ------------//

    @Override
    public void readNBTFromPacket( NBTTagCompound tag )
    {
        NBTTagList tagList = tag.getTagList( "Items" );

        for ( int iTempIndex = 0; iTempIndex < tagList.tagCount(); iTempIndex++ )
        {
            NBTTagCompound tempTag = (NBTTagCompound)tagList.tagAt( iTempIndex );

            int tempSlot = tempTag.getByte( "Slot" ) & 0xff;

            if ( tempSlot >= 0 && tempSlot < storageStack.length )
            {
                storageStack[tempSlot] = ItemStack.loadItemStackFromNBT(tempTag);
            }
        }

        if( tag.hasKey( "Rot" ) )
        {
            this.itemRotation = tag.getIntArray("Rot");
        }

        if( tag.hasKey( "cookCounter" ) )
        {
            cookCounter = tag.getInteger( "cookCounter" );
        }

        // force a visual update for the block since the above variables affect it

        worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );
    }

    //------------- Inventory ------------//

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return storageStack[slot];
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
        setStorageStack(stack, slot);
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
    public boolean isStackValidForSlot(int slot, ItemStack stack)
    {
        return true;
    }

}
