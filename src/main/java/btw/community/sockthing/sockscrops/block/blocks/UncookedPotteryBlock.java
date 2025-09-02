package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.item.BTWItems;
import net.minecraft.src.*;

public class UncookedPotteryBlock extends Block {
    public static final int SUBTYPE_POT = 0;

    public UncookedPotteryBlock(int blockID, String name) {
        super(blockID, Material.clay);
        setUnlocalizedName(name);

        setHardness(0.5F);
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
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
    public int damageDropped(int par1) {
        return 0;
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

    @Override
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon("pottery_clay");
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
        RawClayBlock.renderPot(this, renderer, i, j, k);
        return true;
    }

}
