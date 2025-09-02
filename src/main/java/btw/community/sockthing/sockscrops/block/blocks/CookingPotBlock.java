package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.tileentity.CampfireTileEntity;
import btw.client.fx.BTWEffectManager;
import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import btw.community.sockthing.sockscrops.item.items.CookingPotItemBlock;
import btw.community.sockthing.sockscrops.utils.CookingPotUtils;
import btw.inventory.util.InventoryUtils;
import btw.item.util.ItemUtils;
import net.minecraft.src.*;

import java.util.Random;

public class CookingPotBlock extends BlockContainer {
    public CookingPotBlock(int blockID, String name) {
        super(blockID, Material.clay);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new CookingPotTileEntity();
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return 0;
    }

    @Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
        CookingPotTileEntity pot = (CookingPotTileEntity)world.getBlockTileEntity( i, j, k );

        ItemStack[] cookStack = pot.getCookStacks().toArray(new ItemStack[0]);

        ItemStack heldStack = player.getCurrentEquippedItem();

        if (pot != null) {
            if (!pot.isLidOpen()) {
                pot.setLidOpen(true);
                world.markBlockForUpdate(i, j, k);
                return true;
            } else {
                //lid is open
                if (heldStack == null) {
                    //hand is empty
                    if (player.isSneaking()){
                        pot.setLidOpen(false);
                        world.markBlockForUpdate(i, j, k);
                        return true;
                    }

                    int slot = InventoryUtils.getFirstOccupiedStack(pot);
                    if (slot != -1) {
                        ItemUtils.givePlayerStackOrEject(player, cookStack[slot], i, j, k);
                        pot.setCookStack(slot,null);

                        if (!world.isRemote) {
                            world.playAuxSFX(BTWEffectManager.ITEM_COLLECTION_POP_EFFECT_ID, i, j, k, 0);
                        }
                        return true;
                    }
                } else {
                    //hand is full
                    if (isValidCookItem(heldStack)) {
                        int slot = InventoryUtils.getFirstEmptyStackInSlotRange(pot, 0, 5);

                        if (slot != -1){
                            pot.setCookStack(slot, new ItemStack(heldStack.itemID, 1, heldStack.getItemDamage()));
                            heldStack.stackSize--;

                            if (!world.isRemote) {
                                world.playAuxSFX(BTWEffectManager.ITEM_COLLECTION_POP_EFFECT_ID, i, j, k, 0);
                            }

                            world.markBlockForUpdate(i, j, k);
                            return true;
                        }

                    }
                }
            }
        }

        return false;
    }

    public boolean isValidCookItem( ItemStack stack )
    {
//        if ( SCCraftingManagerPanCooking.instance.getRecipe( stack ) != null )
//        {
//            return true;
//        }

        return true;
    }

    @Override
    public void onBlockHarvested(World world, int i, int j, int k, int par5, EntityPlayer player) {

        CookingPotTileEntity pot = (CookingPotTileEntity) world.getBlockTileEntity(i, j, k);
        ItemStack newStack = new ItemStack(SCBlocks.cookingPot.blockID, 1, this.getDamageValue(world, i, j, k));

        if (pot != null && !pot.getCookStacks().isEmpty()) {
            NBTTagList tagList = new NBTTagList();

            // write each slot
            for (int slot = 0; slot < pot.getCookStacks().size(); slot++) {
                ItemStack stack = pot.getCookStacks().get(slot);
                if (stack != null) {
                    NBTTagCompound itemTag = new NBTTagCompound();
                    itemTag.setByte("Slot", (byte) slot); // keep track of slot index
                    stack.writeToNBT(itemTag);            // serialize stack properly
                    tagList.appendTag(itemTag);
                }
            }

            NBTTagCompound root = new NBTTagCompound();
            root.setTag("Items", tagList);
            newStack.setTagCompound(root);
        }

        // Now drop the newStack like normal
        dropBlockAsItem_do(world, i, j, k, newStack);
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        int var6 = par1World.getBlockId(par2, par3 + 1, par4);
        TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);

        if (var6 == 0 || blocksList[var6].blockMaterial.isReplaceable())
        {
            return true;
        }
        if (te != null && te instanceof CampfireTileEntity)
        {
            return true;
        }
        else return false;
    }

    @Override
    public boolean rotateAroundJAxis( World world, int i, int j, int k, boolean bReverse )
    {
        TileEntity tileEnt = world.getBlockTileEntity( i, j, k );

        if ( tileEnt != null && tileEnt instanceof CookingPotTileEntity)
        {
            CookingPotTileEntity skullEnt = (CookingPotTileEntity)tileEnt;

            int iSkullFacing = skullEnt.GetSkullRotationServerSafe();

            if ( bReverse )
            {
                iSkullFacing += 2;

                if ( iSkullFacing > 7 )
                {
                    iSkullFacing -= 8;
                }
            }
            else
            {
                iSkullFacing -= 2;

                if ( iSkullFacing < 0 )
                {
                    iSkullFacing += 8;
                }
            }

            skullEnt.setSkullRotation( iSkullFacing );

            world.markBlockForUpdate( i, j, k );

            return true;
        }

        return false;
    }

    /**
     * The type of render function that is called for this block
     */
//    public int getRenderType()
//    {
//        return -1;
//    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
    {
        // override to deprecate parent
    }

    @Override
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
    {
        int meta = blockAccess.getBlockMetadata(i, j, k);

        switch ( meta )
        {
            case 2:

                return AxisAlignedBB.getAABBPool().getAABB(
                        3/16D, 0D - 8/16D, 3/16D, 1D - 3/16D, 0D - 8/16D + 8/16D, 1D - 3/16D );

            default:

                return AxisAlignedBB.getAABBPool().getAABB(
                        3/16D, 0D, 3/16D, 1D - 3/16D, 8/16D, 1D - 3/16D );
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int i, int j, int k )
    {
        return getBlockBoundsFromPoolBasedOnState( world, i, j, k ).offset( i, j, k );
    }

    @Override
    public boolean isBlockRestingOnThatBelow( IBlockAccess blockAccess, int i, int j, int k )
    {
        int iMetadata = blockAccess.getBlockMetadata( i, j, k );

        return iMetadata == 1 || iMetadata == 2;
    }

    @Override
    public boolean canRotateOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
    {
        return true;
    }


    //----------- Client Side Functionality -----------//


    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon("cooked_clay");
    }

    @Override
    public boolean renderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
        return false;
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {
        //Base
        renderer.setRenderBounds(
                4/16D,0,4/16D,
                1D - 4/16D, 2/16D, 1D - 4/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, blockIcon);

        //walls
        renderer.setRenderBounds(
                3/16D,1/16D,4/16D,
                5/16D, 7/16D, 12/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, blockIcon);

        renderer.setRenderBounds(
                11/16D,1/16D,4/16D,
                13/16D, 7/16D, 12/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, blockIcon);

        renderer.setRenderBounds(
                4/16D,1/16D,3/16D,
                12/16D, 7/16D, 5/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, blockIcon);

        renderer.setRenderBounds(
                4/16D,1/16D,11/16D,
                12/16D, 7/16D, 13/16D
        );
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, blockIcon);
    }
}
