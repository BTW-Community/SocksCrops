package btw.community.sockthing.sockscrops.block.blocks;

import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.tileentities.HayDryingTileEntity;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.world.util.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        // don't allow drying bricks on leaves as it makes for really lame drying racks in trees

        return WorldUtils.doesBlockHaveLargeCenterHardpointToFacing(world, x, y - 1, z, 1, true) &&
                world.getBlockId(x, y - 1, z) != Block.leaves.blockID;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int iBlockID) {
        if (!WorldUtils.doesBlockHaveLargeCenterHardpointToFacing(world, x, y - 1, z, 1, true)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockWithNotify(x, y, z, 0);
        }
    }

    @Override
    public boolean canGroundCoverRestOnBlock(World world, int x, int y, int z) {
        return world.doesBlockHaveSolidTopSurface(x, y - 1, z);
    }

    @Override
    public float groundCoverRestingOnVisualOffset(IBlockAccess blockAccess, int x, int y, int z) {
        return -14 / 16F;
    }

    //------------- Class Specific Methods ------------//

    public void onFinishedDrying(World world, int x, int y, int z) {
        world.setBlockAndMetadataWithNotify(x, y, z, SCBlocks.driedHay.blockID, 0);
    }

    public void setDryLevel(World world, int x, int y, int z, int dryLevel) {
        int iMetadata = setDryLevel(world.getBlockMetadata(x, y, z), dryLevel);

        world.setBlockMetadataWithNotify(x, y, z, iMetadata);
    }

    public int setDryLevel(int iMetadata, int dryLevel) {
        iMetadata &= 1; // filter out old state

        iMetadata |= dryLevel << 1;

        return iMetadata;
    }

    public int getDryLevel(IBlockAccess blockAccess, int x, int y, int z) {
        return getDryLevel(blockAccess.getBlockMetadata(x, y, z));
    }

    public int getDryLevel(int iMetadata) {
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
    @Environment(EnvType.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        int dryLevel = getDryLevel(blockAccess, x, y, z);

        if (dryLevel < 8) {
            switch (dryLevel) {
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

    @Environment(EnvType.CLIENT)
    private Icon sideIcon;
    @Environment(EnvType.CLIENT)
    private Icon topIcon;

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        topIcon = register.registerIcon("straw_bale_top");
        sideIcon = register.registerIcon("straw_bale_side");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Icon getIcon(int side, int meta) {
        if (side < 2) {
            return topIcon;
        } else return sideIcon;

    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int neighborX, int neighborY, int neighborZ, int side) {
        if (side == 0) {
            return RenderUtils.shouldRenderNeighborFullFaceSide(blockAccess,
                    neighborX, neighborY, neighborZ, side);
        }

        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int x, int y, int z) {
        IBlockAccess blockAccess = renderer.blockAccess;

        if (blockAccess.getBlockId(x, y - 1, z) != 0) {
            float fHeight = 2 / 16F;

            renderer.setRenderBounds(0F, 0F, 0F,
                    1F, fHeight, 1F);

            RenderUtils.renderStandardBlockWithTexture(renderer, this, x, y, z, blockIcon);
        }

        return true;
    }

}
