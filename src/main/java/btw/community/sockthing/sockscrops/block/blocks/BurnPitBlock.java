package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.CampfireBlock;
import btw.block.blocks.FireBlock;
import btw.block.util.Flammability;
import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.tileentities.BurnPitTileEntity;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import net.minecraft.src.*;

import java.util.Random;

public class BurnPitBlock extends BlockContainer {
    public BurnPitBlock(int blockID, String name) {
        super(blockID, Material.circuits);
        setUnlocalizedName(name);
//        setFireProperties(Flammability.EXTREME);
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new BurnPitTileEntity();
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return 0;
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
    public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer) {
        TileEntity te = par1World.getBlockTileEntity(par2,par3,par4);
        if (te instanceof BurnPitTileEntity){
            BurnPitTileEntity burnPit = (BurnPitTileEntity) te;
            if (burnPit.isCooked()){
                if (!par1World.isRemote) ItemUtils.ejectStackAroundBlock(par1World, par2,par3,par4, new ItemStack(SCBlocks.cookingPot, 1, 0));
            }
            else {
                if (!par1World.isRemote) ItemUtils.ejectStackAroundBlock(par1World, par2,par3,par4, new ItemStack(SCBlocks.unfiredPottery, 1, UncookedPotteryBlock.SUBTYPE_POT));
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
        int meta = par1World.getBlockMetadata(par2, par3, par4);

        int blockAbove = par1World.getBlockId(par2,par3 + 1, par4);

        if (meta == 15 && Block.blocksList[blockAbove] instanceof CampfireBlock){
            TileEntity te = par1World.getBlockTileEntity(par2,par3,par4);
            if (te instanceof BurnPitTileEntity){
                BurnPitTileEntity burnPit = (BurnPitTileEntity) te;
                burnPit.setSmoldering(true);
            }
        }
    }

    @Override
    public void onDestroyedByFire(World world, int i, int j, int k, int iFireAge, boolean bForcedFireSpread) {
        super.onDestroyedByFire(world, i, j, k, iFireAge, bForcedFireSpread);

        world.setBlockAndMetadataWithNotify(i,j,k, this.blockID, 0);
    }

    @Override
    public boolean isNormalCube(IBlockAccess blockAccess, int i, int j, int k) {
        if (blockAccess.getBlockMetadata(i,j,k) == 15) return true;
        return super.isNormalCube(blockAccess, i, j, k);
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

    private Icon cookedClay;
    private Icon straw;
    private Icon sticks;
    private Icon smolderingIcon;
    private Icon ash;


    @Override
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon("pottery_clay");
        cookedClay = register.registerIcon("pottery_clay_dry");

        sticks = register.registerIcon("tree_side");
        straw = register.registerIcon("straw_bale_top");

        smolderingIcon = register.registerIcon("fcOverlayLogEmbers");
        ash = register.registerIcon("fcBlockAshGroundCover");
    }

    @Override
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        TileEntity te = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
        if (te instanceof BurnPitTileEntity){
            BurnPitTileEntity burnPit = (BurnPitTileEntity) te;
            if (burnPit.isCooked()){
                return blockIcon = cookedClay;
            }
        }
        return blockIcon;
    }

    @Override
    public void renderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult) {
        TileEntity te = renderBlocks.blockAccess.getBlockTileEntity(i,j,k);
        if (te instanceof BurnPitTileEntity){
            BurnPitTileEntity burnPit = (BurnPitTileEntity) te;
            if (burnPit.isSmoldering()) {
                renderBlocks.setOverrideBlockTexture(smolderingIcon);
                renderBlocks.setRenderBounds(0, 0, 0, 1, 1, 1);
                renderBlocks.renderStandardBlock(this, i, j, k);
                renderBlocks.clearOverrideBlockTexture();
            }
        }
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        int meta = renderer.blockAccess.getBlockMetadata(i,j,k);
        Icon strawIcon = straw;
        Icon clayIcon = blockIcon;
        TileEntity te = renderer.blockAccess.getBlockTileEntity(i,j,k);
        if (te instanceof BurnPitTileEntity) {
            BurnPitTileEntity burnPit = (BurnPitTileEntity) te;
            if (burnPit.isCooked()) {
                strawIcon = ash;
                clayIcon = cookedClay;
            }
        }

        renderer.setOverrideBlockTexture(clayIcon);
        renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
        RawClayBlock.renderPot(this, renderer, i, j, k);
        renderer.clearOverrideBlockTexture();

        double strawHeight = Math.min(1/16D * (meta + 1), 8/16D);

        renderer.setRenderBounds(0, 0, 0, 1, strawHeight, 1);
        RenderUtils.renderStandardBlockWithTexture(renderer, this, i, j, k, strawIcon);



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
