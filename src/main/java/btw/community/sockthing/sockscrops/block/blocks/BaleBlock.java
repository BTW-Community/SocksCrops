package btw.community.sockthing.sockscrops.block.blocks;

import btw.item.BTWItems;
import btw.util.MiscUtils;
import net.minecraft.src.*;

public abstract class BaleBlock extends Block {
    public BaleBlock(int par1, String name) {
        super(par1, Material.leaves);

        setHardness(0.5F);
        setResistance(2.0F);
        setAxesEffectiveOn(true);

        setCreativeTab(CreativeTabs.tabBlock);
        setStepSound(soundGrassFootstep);
        setUnlocalizedName(name);
    }

    @Override
    public boolean canBePistonShoveled(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return true;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return true;
    }

    @Override
    public boolean canDropFromExplosion(Explosion var1) {
        return false;
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int X, int Y, int Z, Explosion exp) {
        float dropChance = 1.0F;

        if (exp != null) {
            dropChance = 1.0F / exp.explosionSize;
        }

        for (int i = 0; i < 8; ++i)
            if (world.rand.nextFloat() <= dropChance)
                dropBlockAsItem_do(world, X, Y, Z, new ItemStack(BTWItems.straw));
    }

    @Override
    public int getFacing(IBlockAccess access, int x, int y, int z) {
        return access.getBlockMetadata(x, y, z);
    }

    @Override
    public void setFacing(World world, int X, int Y, int Z, int facing) {
        world.setBlockMetadataWithNotify(X, Y, Z, facing);
    }

    @Override
    public int getFacing(int metadata) {
        return metadata;
    }

    @Override
    public int setFacing(int var1, int var2) {
        return var2;
    }

    @Override
    public boolean canRotateOnTurntable(IBlockAccess access, int x, int y, int z) {
        return access.getBlockMetadata(x, y, z) != 0;
    }

    @Override
    public boolean canTransmitRotationHorizontallyOnTurntable(IBlockAccess access, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canTransmitRotationVerticallyOnTurntable(IBlockAccess access, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean rotateAroundJAxis(World world, int x, int y, int z, boolean reverse) {
        return MiscUtils.standardRotateAroundY(this, world, x, y, z, reverse);
    }

    @Override
    public int rotateMetadataAroundJAxis(int metadata, boolean reverse) {
        return MiscUtils.standardRotateMetadataAroundY(this, metadata, reverse);
    }

    @Override
    public boolean toggleFacing(World world, int x, int y, int z, boolean reverse) {
        this.rotateAroundJAxis(world, x, y, z, reverse);
        return true;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if (side < 2) return 0;
        else if (side < 4) return 1;
        else return 2;
    }

    @Override
    public boolean canToolsStickInBlock(IBlockAccess blockAccess, int x, int y, int u) {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player, ItemStack stack) {/*
		int var7 = FCUtilsMisc.ConvertPlacingEntityOrientationToBlockFacing(var5);
		if(var7<2)var7=0;
		else if(var7<4)var7=1;
		else var7=2;
		this.SetFacing(var1, var2, var3, var4, var7);//*/
    }

    protected Icon topIcon;
    protected Icon sideIcon;

    @Override
    public Icon getIcon(int side, int metadata) {
        switch (metadata) {
            case 0:
                return (side < 2) ? topIcon : sideIcon;
            case 1:
                return (side < 4 && side > 1) ? topIcon : sideIcon;
            default:
                return (side > 3) ? topIcon : sideIcon;
        }
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int x, int y, int z) {
        int facing = getFacing(renderer.blockAccess, x, y, z);
        switch (facing) {
            case 1:
                renderer.setUVRotateNorth(1);
                renderer.setUVRotateSouth(1);
                break;
            case 2:
                renderer.setUVRotateTop(1);
                renderer.setUVRotateBottom(1);
                renderer.setUVRotateWest(1);
                renderer.setUVRotateEast(1);
            default:

        }
        renderer.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
        renderer.renderStandardBlock(this, x, y, z);
        renderer.clearUVRotation();
        return true;
    }
}
