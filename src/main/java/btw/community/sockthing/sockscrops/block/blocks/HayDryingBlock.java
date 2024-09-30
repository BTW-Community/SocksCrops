package btw.community.sockthing.sockscrops.block.blocks;

import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.tileentities.HayDryingTileEntity;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.world.util.WorldUtils;
import net.minecraft.src.*;

import java.util.Random;

public class HayDryingBlock extends BlockContainer {

    public HayDryingBlock(int par1, String name) {
        super(par1, Material.leaves);

        initBlockBounds(0F, 0F, 0F, 1F, 0.125F, 1F);

        setHardness(0.05F);
        setShovelsEffectiveOn();
        setBuoyant();
        setLightOpacity(0);

        setCreativeTab(null);
        setStepSound(soundGrassFootstep);
        setUnlocalizedName(name);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new HayDryingTileEntity();
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return SCItems.cuttings.itemID;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        // don't allow drying bricks on leaves as it makes for really lame drying racks in trees

        return WorldUtils.doesBlockHaveLargeCenterHardpointToFacing(world, i, j - 1, k, 1, true) &&
                world.getBlockId(i, j - 1, k) != Block.leaves.blockID;
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int iBlockID) {
        if (!WorldUtils.doesBlockHaveLargeCenterHardpointToFacing(world, i, j - 1, k, 1, true)) {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    @Override
    public boolean canGroundCoverRestOnBlock(World world, int i, int j, int k) {
        return world.doesBlockHaveSolidTopSurface(i, j - 1, k);
    }

    @Override
    public float groundCoverRestingOnVisualOffset(IBlockAccess blockAccess, int i, int j, int k) {
        return -14 / 16F;
    }

    //------------- Class Specific Methods ------------//

    public void onFinishedCooking(World world, int i, int j, int k) {
        world.setBlockAndMetadataWithNotify(i, j, k, SCBlocks.driedHay.blockID, 0);
    }

    public void setCookLevel(World world, int i, int j, int k, int iCookLevel) {
        int iMetadata = setCookLevel(world.getBlockMetadata(i, j, k), iCookLevel);

        world.setBlockMetadataWithNotify(i, j, k, iMetadata);
    }

    public int setCookLevel(int iMetadata, int iCookLevel) {
        iMetadata &= 1; // filter out old state

        iMetadata |= iCookLevel << 1;

        return iMetadata;
    }

    public int getCookLevel(IBlockAccess blockAccess, int i, int j, int k) {
        return getCookLevel(blockAccess.getBlockMetadata(i, j, k));
    }

    public int getCookLevel(int iMetadata) {
        return iMetadata >> 1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    //------------- Client ------------//

    @Override
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        int cookLevel = getCookLevel(blockAccess, x, y, z);

        if (cookLevel < 8) {
            switch (cookLevel) {
                case 0:
                    return 0xaeff99;
                case 1:
                    return 0xbbffa8;
                case 2:
                    return 0xc6ffb7;
                case 3:
                    return 0xd2ffc5;
                case 4:
                    return 0xddffd4;
                case 5:
                    return 0xe7ffe1;
                case 6:
                    return 0xf1ffed;
                case 7:
                    return 0xffffff;

                default:
                    return 0xffffff;
            }
        } else return 0xffffff;
    }

    private Icon sideIcon;
    private Icon topIcon;

    @Override
    public void registerIcons(IconRegister iconRegister) {
        topIcon = iconRegister.registerIcon("straw_bale_top");
        sideIcon = iconRegister.registerIcon("straw_bale_side");
    }

    @Override
    public Icon getIcon(int side, int meta) {
        if (side < 2) {
            return topIcon;
        } else return sideIcon;

    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        if (iSide == 0) {
            return RenderUtils.shouldRenderNeighborFullFaceSide(blockAccess,
                    iNeighborI, iNeighborJ, iNeighborK, iSide);
        }

        return true;
    }

    @Override
    public boolean renderBlock(RenderBlocks renderBlocks, int i, int j, int k) {
        IBlockAccess blockAccess = renderBlocks.blockAccess;

        if (blockAccess.getBlockId(i, j - 1, k) != 0) {
            float fHeight = 2 / 16F;

            renderBlocks.setRenderBounds(0F, 0F, 0F,
                    1F, fHeight, 1F);

            RenderUtils.renderStandardBlockWithTexture(renderBlocks, this, i, j, k, blockIcon);
        }

        return true;
    }

}
