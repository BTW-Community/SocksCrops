package btw.community.sockthing.sockscrops.block.blocks;

import java.util.List;

import btw.block.blocks.LilyPadBlock;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import net.minecraft.src.*;

public class WaterPlantBlock extends LilyPadBlock {

    public static String[] types = {"tall_grass", "short_grass"};

    public static final int TALL_GRASS = 0;
    public static final int SHORT_GRASS = 1;

    public WaterPlantBlock(int iBlockID, String name) {
        super(iBlockID);
        setHardness(0.0F);
        setStepSound(soundGrassFootstep);
        setUnlocalizedName(name);
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

    private boolean secondRenderpass = false;

    private Icon[] icon = new Icon[8];
    private Icon[] roots = new Icon[8];

    @Override
    public void registerIcons(IconRegister register) {
        icon[0] = register.registerIcon("tallgrass");
        icon[1] = register.registerIcon("short_grass");
        roots[0] = register.registerIcon("tall_plant_grass_bottom");
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return blockIcon = icon[meta];
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor();
    }

    @Override
    public int getRenderColor(int par1)
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerGrass.getGrassColor(var1, var3);
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k )
    {
        SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j - 1, k, roots[0], true, true, 4/16D);
        renderer.clearOverrideBlockTexture();

        return true;
    }

    @Override
    public void renderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult)
    {
        int meta = renderer.blockAccess.getBlockMetadata(i, j, k);

        secondRenderpass = true;

        SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, icon[meta], true, true, 4/16D);

        secondRenderpass = false;
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderBlocks, int damage, float fBrightness)
    {
        renderBlocks.setOverrideBlockTexture(icon[damage]);
        renderBlocks.renderBlockAsItemVanilla(this, damage, fBrightness);
        renderBlocks.clearOverrideBlockTexture();

    }

}