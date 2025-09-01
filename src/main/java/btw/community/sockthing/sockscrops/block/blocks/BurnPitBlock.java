package btw.community.sockthing.sockscrops.block.blocks;

import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.tileentities.BurnPitTileEntity;
import btw.item.BTWItems;
import net.minecraft.src.*;

import java.util.Random;

public class BurnPitBlock extends BlockContainer {
    public BurnPitBlock(int blockID, String name) {
        super(blockID, Material.circuits);
        setUnlocalizedName(name);
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new BurnPitTileEntity();
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return SCBlocks.unfiredPottery.blockID;
    }

    @Override
    public int damageDropped(int par1) {
        return UncookedPotteryBlock.SUBTYPE_POT;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        if (par5EntityPlayer.getHeldItem() != null){
            int oldMeta = par1World.getBlockMetadata(par2, par3, par4);
            if (oldMeta < 7) {
                if (par5EntityPlayer.getHeldItem().itemID == BTWItems.straw.itemID)
                {
                    par1World.setBlockAndMetadataWithNotify(par2, par3, par4, SCBlocks.burnPit.blockID, oldMeta + 1);
                    par5EntityPlayer.getHeldItem().stackSize--;
                    return true;
                }
            }
            else  if (oldMeta < 15) {
                if (par5EntityPlayer.getHeldItem().itemID == Item.stick.itemID)
                {
                    par1World.setBlockAndMetadataWithNotify(par2, par3, par4, SCBlocks.burnPit.blockID, oldMeta + 1);
                    par5EntityPlayer.getHeldItem().stackSize--;
                    return true;
                }
            }

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

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        return true;
    }

    private Icon straw;
    private Icon sticks;


    @Override
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon("clay");

        sticks = register.registerIcon("tree_side");
        straw = register.registerIcon("straw_bale_top");
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        int meta = renderer.blockAccess.getBlockMetadata(i,j,k);

        renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
        RawClayBlock.renderPot(this, renderer, i, j, k);

        double strawHeight = Math.min(1/16D * (meta + 1), 8/16D);

        renderer.setRenderBounds(0, 0, 0, 1, strawHeight, 1);
        RenderUtils.renderStandardBlockWithTexture(renderer, this, i, j, k, straw);



        if (meta >= 8) {
            double logWidth = Math.min(4/16D * ((meta) & 7), 1D);
            renderer.setRenderBounds(
                    0, 8/16D, 0,
                    1D, 12/16D, Math.min((logWidth + 4/16D), 1D));
            RenderUtils.renderStandardBlockWithTexture(renderer, this, i, j, k, sticks);
        }

        if (meta >= 12) {
            double logWidth = Math.min(4/16D * ((meta) & 3), 1D);
            renderer.setRenderBounds(
                    0, 12/16D, 0,
                    Math.min((logWidth + 4/16D), 1D), 16/16D, 1D);
            RenderUtils.renderStandardBlockWithTexture(renderer, this, i, j, k, sticks);
        }
        return true;
    }
}
