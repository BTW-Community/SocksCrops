package btw.community.sockthing.sockscrops.block.blocks;

import java.util.List;

import btw.block.blocks.LilyPadBlock;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import net.minecraft.src.*;

public class FlowerLilyBlock extends LilyPadBlock {

    public static String[] types = {"pink","white"};

    public static final int PINK = 0;
    public static final int WHITE = 1;

    public FlowerLilyBlock(int iBlockID, String name) {
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

    private boolean secondRenderpass = false;

    private Icon[] rose = new Icon[16];
    private Icon[] flower = new Icon[16];
    private Icon lily;
    private Icon roots;
    private Icon[] icon = new Icon[16];

    @Override
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < types.length; i++) {
            rose[i] = register.registerIcon("lily_rose_" + types[i]);
            flower[i] = register.registerIcon("lily_flower_" + types[i]);
            icon[i] = register.registerIcon("lily_item_" + types[i]);
        }

        lily = register.registerIcon("waterlily");
        roots = register.registerIcon("lily_roots");
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
        if (secondRenderpass)
        {
            return 0xffffff;
        }
        else return super.colorMultiplier(par1IBlockAccess, par2, par3, par4);
    }

    @Override
    public int getRenderColor(int par1)
    {
        return 0xffffff;
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k )
    {
        secondRenderpass = true;
        renderer.setOverrideBlockTexture(roots);
        renderer.setRenderBounds(0, 0, 0, 1, 15/16D, 1);
        renderer.renderCrossedSquares(this, i, j - 1, k);
        renderer.clearOverrideBlockTexture();
        secondRenderpass = false;

        renderer.setOverrideBlockTexture(lily);
        renderer.setRenderBounds( getBlockBoundsFromPoolBasedOnState( renderer.blockAccess, i, j, k ) );
        renderer.renderBlockLilyPad( this, i, j, k );
        renderer.clearOverrideBlockTexture();

        return true;
    }

    @Override
    public void renderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean bFirstPassResult)
    {
        int meta = renderer.blockAccess.getBlockMetadata(x, y, z);

        secondRenderpass = true;

        SCRenderUtils.renderVerticalPaneWithRotation(renderer, this, x,y,z,
                0, 1/16D, 0,
                60, 0, 0,
                0, -8/16D, 0,
                rose[meta], rose[meta]);

        SCRenderUtils.renderVerticalPaneWithRotation(renderer, this, x,y,z,
                0, 1/16D, 0,
                60, -90, 0,
                0, -8/16D, 0,
                rose[meta], rose[meta]);

        SCRenderUtils.renderVerticalPaneWithRotation(renderer, this, x,y,z,
                0, 1/16D, 0,
                60, 90, 0,
                0, -8/16D, 0,
                rose[meta], rose[meta]);

        SCRenderUtils.renderVerticalPaneWithRotation(renderer, this, x,y,z,
                0, 1/16D, 0,
                60, 180, 0,
                0, -8/16D, 0,
                rose[meta], rose[meta]);

        renderer.setOverrideBlockTexture(flower[meta]);
        renderer.renderCrossedSquares(this, x, y, z);
        renderer.clearOverrideBlockTexture();

        secondRenderpass = false;
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderBlocks, int damage, float fBrightness)
    {
        renderBlocks.setOverrideBlockTexture(flower[damage]);
        renderBlocks.renderBlockAsItemVanilla(this, damage, fBrightness);
        renderBlocks.clearOverrideBlockTexture();

    }

}