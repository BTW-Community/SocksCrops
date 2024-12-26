package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.tileentities.RopeHangingItemsTileEntity;
import btw.item.util.ItemUtils;
import net.minecraft.src.*;

public class RopeHangingItemsBlock extends FenceRopeBlock implements ITileEntityProvider {

    public RopeHangingItemsBlock(int iBlockID) {
        super(iBlockID);
        this.isBlockContainer = true;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick) {

        ItemStack heldStack = player.getCurrentEquippedItem();

        if (heldStack == null)
        {
            TileEntity te = world.getBlockTileEntity(i, j, k);

            System.out.println("items");

            if (te != null && te instanceof RopeHangingItemsTileEntity)
            {
                RopeHangingItemsTileEntity rope = (RopeHangingItemsTileEntity) te;

                ItemStack storageStack = rope.getStackInSlot(0);

                if (!world.isRemote && storageStack != null) {

                    world.playSoundAtEntity(player, this.stepSound.getStepSound(),
                            this.stepSound.getVolume() * 0.2F, this.stepSound.getPitch() * 3F);

                    //ItemUtils.ejectStackAroundBlock(world, i, j, k, storageStack.copy());
                    ItemUtils.ejectStackFromBlockTowardsFacing(world, i, j, k, storageStack.copy(), iFacing);
                }

                rope.setInventorySlotContents(0, null);

                world.setBlockAndMetadata(i, j, k, SCBlocks.rope.blockID, world.getBlockMetadata(i, j, k));
                return true;
            }
        }

        return false;
    }

    // --- TileEntity --- //

    @Override
    public TileEntity createNewTileEntity(World var1) {

        return new RopeHangingItemsTileEntity();
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
    }

    /**
     * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
     * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
     */
    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        TileEntity var7 = par1World.getBlockTileEntity(par2, par3, par4);
        return var7 != null ? var7.receiveClientEvent(par5, par6) : false;
    }

}
