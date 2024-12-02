package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.item.items.KnifeItem;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import btw.item.items.ShearsItem;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class DoubleTallWaterPlantBlock extends FlowerLilyBlock {

    public static String[] types = {"cattail", "grass"};
    public static final int CATTAIL = 0;
    public static final int GRASS = 1;
    private static final double HALF_WIDTH = 0.25F;

    public DoubleTallWaterPlantBlock(int blockID, String name) {
        super(blockID, name);
        setCreativeTab(CreativeTabs.tabDecorations);

        initBlockBounds(0.5D - HALF_WIDTH, 0D, 0.5D - HALF_WIDTH,
                0.5D + HALF_WIDTH, 0.8D, 0.7D + HALF_WIDTH);

    }

    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < types.length; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        int targetBlock = world.getBlockId(i, j, k);
        int targetBlockBelow = world.getBlockId(i, j - 1, k);
        int targetBlockBelowThat = world.getBlockId(i, j - 2, k);
        return targetBlockBelow == Block.waterStill.blockID && (targetBlockBelowThat == Block.dirt.blockID || targetBlockBelowThat == Block.sand.blockID) && (targetBlock == 0);
    }

    @Override
    public int getRenderColor(int par1)
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerGrass.getGrassColor(var1, var3);
    }

    public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int meta) {
        ItemStack heldStack = player.getCurrentEquippedItem();

        /*
        if (!world.isRemote && (meta&7) == SUNFLOWER)
        {
            this.dropBlockAsItem_do(world, i, j, k, new ItemStack(SCDefs.sunflower));
            return;
        }
        */

        if (!world.isRemote && heldStack != null) {
            if (heldStack.getItem() instanceof ShearsItem) {
                player.getHeldItem().damageItem(1, player);

                if (isTopBlock(meta)) {
                    this.dropBlockAsItem_do(world, i, j, k, new ItemStack(this.blockID, 1, meta - 8));
                } else this.dropBlockAsItem_do(world, i, j, k, new ItemStack(this.blockID, 1, meta));
            }
            /*
            else if( heldStack.getItem() instanceof KnifeItem && getType(meta) == GRASS)
            {
                player.getHeldItem().damageItem(1, player);

                if (world.rand.nextFloat() <= 1/8F)
                {
                    this.dropBlockAsItem_do(world, i, j, k, new ItemStack(SCDefs.grassSeeds));
                }
            }
            */
            else if (heldStack.getItem() instanceof KnifeItem) {
                player.getHeldItem().damageItem(1, player);

                if (world.rand.nextFloat() <= 1 / 8F) {
                    this.dropBlockAsItem_do(world, i, j, k, new ItemStack(SCItems.cuttings));
                }
            }
        } else {
            super.harvestBlock(world, player, i, j, k, meta);
        }
    }

    @Override
    public int idDropped(int iMetadata, Random rand, int iFortuneModifier) {
        /*
        if ((iMetadata&7) == SUNFLOWER)
        {
            return SCDefs.sunflower.itemID;
        }
        */
        return 0;
    }

    @Override
    public int damageDropped(int par1) {
        return 0;
    }

    @Override
    public boolean canSpitWebReplaceBlock(World world, int i, int j, int k) {
        return true;
    }

    @Override
    public boolean isReplaceableVegetation(World world, int i, int j, int k) {
        return true;
    }


    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player,
                                ItemStack itemStack) {

        if (world.getBlockId(i, j + 1, k) == 0) {
            world.setBlockAndMetadataWithNotify(i, j + 1, k, this.blockID, setTopBlock(world.getBlockMetadata(i, j, k)));
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int neighbourID) {
        int meta = world.getBlockMetadata(i, j, k);

        if (isTopBlock(meta)) {
            if (world.getBlockId(i, j - 1, k) != this.blockID) {
                world.setBlockToAir(i, j, k);
                //FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, this.blockID, meta - 8);
            }
        } else {
            if (world.getBlockId(i, j + 1, k) != this.blockID) {
                world.setBlockToAir(i, j, k);
                //FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, this.blockID, meta);
            }
        }

        super.onNeighborBlockChange(world, i, j, k, neighbourID);
    }

    @Override
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        int metadata = blockAccess.getBlockMetadata(x, y, z);
        float minHeight;
        float maxHeight;

        if (this.isTopBlock(metadata)) {
            minHeight = -1F;
            maxHeight = 0.8F;
        }
        else {
            minHeight = 0F;
            maxHeight = 1 + 0.8F;
        }

        return AxisAlignedBB.getBoundingBox(0.5F - 0.4F, minHeight, 0.5F - 0.4F,
                0.5F + 0.4F, maxHeight, 0.5F + 0.4F);
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {

        int meta = world.getBlockMetadata(i, j, k);
        if (isTopBlock(meta)) {
            return world.getBlockId(i, j - 1, k) == this.blockID;
        } else return super.canBlockStay(world, i, j, k);
    }

    public boolean isTopBlock(int meta) {
        return meta > 7;
    }

    public static int setTopBlock(int meta) {
        return meta + 8;
    }

    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        if (grassPass)
        {
            return par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor();
        }
        else return  0xffffff;
    }

    private Icon[] roots = new Icon[4];
    private Icon[] bottom = new Icon[4];
    private Icon[] top = new Icon[4];
    private Icon[] overlay = new Icon[4];

    private boolean grassPass;

    @Override
    public void registerIcons(IconRegister register) {

        top[0] = register.registerIcon("cattail_top");
        bottom[0] = register.registerIcon("cattail_middle");
        roots[0] = register.registerIcon("cattail_bottom");
        overlay[0] = register.registerIcon("cattail");

        top[1] = register.registerIcon("tall_plant_grass_top");
        bottom[1] = register.registerIcon("tall_plant_grass_bottom");
        roots[1] = register.registerIcon("tall_plant_grass_bottom");
    }

    @Override
    public Icon getIcon(int side, int meta) {
        if (isTopBlock(meta)) {
            return this.blockIcon = top[meta - 8];
        } else return this.blockIcon = bottom[meta];
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k )
    {
        grassPass = true;
        int meta = renderer.blockAccess.getBlockMetadata(i,j,k);
        if (!isTopBlock(meta))
        {
            SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j-1, k, roots[0], true, true, 4/16D);

        }
        SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, getBlockTexture(renderer.blockAccess, i, j, k, meta), true, true, 4/16D);
        grassPass = false;

        return true;
    }

    private int getType(int meta) {
        return meta & 7;
    }


    @Override
    public void renderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean firstPass)
    {
        grassPass = false;
        int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
        if (isTopBlock(meta) && getType(meta) == CATTAIL) SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, x, y, z, overlay[0], true, true, 4/16D);
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderBlocks, int damage, float fBrightness)
    {
        renderBlocks.setOverrideBlockTexture(overlay[0]);
        renderBlocks.renderBlockAsItemVanilla(this, damage, fBrightness);
        renderBlocks.clearOverrideBlockTexture();

    }
}
