package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.block.model.BlockModel;
import btw.client.fx.BTWEffectManager;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import net.minecraft.src.*;

public class RawClayBlock extends Block {


    private final int droppedBlockID;
    private final int droppedMeta;

    public RawClayBlock(int blockID, String name, int droppedBlockID, int droppedMeta) {
        super(blockID, Material.rock);
        setUnlocalizedName(name);

        setHardness(0.5F);
        Block.useNeighborBrightness[blockID] = true;
        initBlockBounds(0D, 0D, 0D, 1D, 1D, 1D);
        setStepSound(Block.blockClay.stepSound);
        setCreativeTab(CreativeTabs.tabMaterials);

        this.droppedBlockID = droppedBlockID;
        this.droppedMeta = droppedMeta;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        if (par1World.getBlockMetadata(par2, par3, par4) != 3) return false;

        if (par5EntityPlayer.getHeldItem() != null){
            if (par5EntityPlayer.getHeldItem().itemID == BTWItems.straw.itemID)
            {
                par1World.setBlockAndMetadataWithNotify(par2, par3, par4, SCBlocks.burnPit.blockID, 0);
                par5EntityPlayer.getHeldItem().stackSize--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int getHarvestToolLevel(IBlockAccess blockAccess, int i, int j, int k) {
        return 1000; // always convert, never harvest
    }

    @Override
    public boolean canConvertBlock(ItemStack stack, World world, int i, int j, int k) {
        return true;
    }

    @Override
    public boolean convertBlock(ItemStack stack, World world, int i, int j, int k, int iFromSide) {
        int meta = world.getBlockMetadata(i, j, k);

        if (meta < 3) {
            meta++;

            world.setBlockAndMetadataWithNotify(i,j,k, this.blockID, meta);

            if (!world.isRemote) ItemUtils.ejectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(BTWItems.clayPile, 1), iFromSide);

            return true;
        }
        else if (!world.isRemote) {
            ItemUtils.dropStackAsIfBlockHarvested(world, i, j, k, new ItemStack(droppedBlockID, 1, droppedMeta));
        }

        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    private AxisAlignedBB getBlockBoundsForRender(IBlockAccess blockAccess, int i, int j, int k) {
        int metadata = blockAccess.getBlockMetadata(i,j,k);
        AxisAlignedBB bounds = new AxisAlignedBB(
                2/16D * metadata,0,2/16D * metadata,
                1D - (2/16D * metadata),1D - (4/16D * metadata),1D - (2/16D * metadata)
        );

        return bounds;
    }

    @Override
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon("clay");
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        if (droppedBlockID == SCBlocks.unfiredPottery.blockID) {
            if (droppedMeta == UncookedPotteryBlock.SUBTYPE_POT){
                renderer.setRenderBounds(0,0,0,1,1,1);
                renderPot(this, renderer, i,j,k);
            }
        }

        //clay
        if (renderer.blockAccess.getBlockMetadata(i,j,k) < 3) {
            renderer.setRenderBounds(getBlockBoundsForRender(
                    renderer.blockAccess, i, j, k) );
            renderer.renderStandardBlock( this, i, j, k );

        }

        return true;
    }

    public static void renderPot(Block block, RenderBlocks renderer, int i, int j, int k) {
        //Base
        renderer.setRenderBounds(
                4/16D,0,4/16D,
                1D - 4/16D, 2/16D, 1D - 4/16D
        );
        renderer.renderStandardBlock( block, i, j, k );

        //walls
        renderer.setRenderBounds(
                3/16D,1/16D,4/16D,
                5/16D, 7/16D, 12/16D
        );
        renderer.renderStandardBlock( block, i, j, k );

        renderer.setRenderBounds(
                11/16D,1/16D,4/16D,
                13/16D, 7/16D, 12/16D
        );
        renderer.renderStandardBlock( block, i, j, k );

        renderer.setRenderBounds(
                4/16D,1/16D,3/16D,
                12/16D, 7/16D, 5/16D
        );
        renderer.renderStandardBlock( block, i, j, k );

        renderer.setRenderBounds(
                4/16D,1/16D,11/16D,
                12/16D, 7/16D, 13/16D
        );
        renderer.renderStandardBlock( block, i, j, k );
    }
}
