package btw.community.sockthing.sockscrops.block.blocks;

import btw.client.render.util.RenderUtils;
import btw.item.BTWItems;
import btw.world.util.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.Random;

public class HayDriedBlock extends Block {

    public HayDriedBlock(int blockID, String name) {
        super(blockID, Material.leaves);

        initBlockBounds(0F, 0F, 0F, 1F, 0.125F, 1F);

        setHardness(0.05F);
        setShovelsEffectiveOn();
        setBuoyant();
        setLightOpacity(0);

        setStepSound(soundGrassFootstep);
        setCreativeTab(CreativeTabs.tabDecorations);
        setUnlocalizedName(name);
    }

    @Override
    public int idDropped(int metadata, Random random, int fortuneModifier) {
        return BTWItems.straw.itemID;
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
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockID) {
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

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    //------------- Client ------------//

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
