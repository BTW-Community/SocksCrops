package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import net.minecraft.src.*;

import java.util.List;

public class BerryBushBlock extends BerryBushBaseBlock {

    private int berryID;
    private int saplingID;
    private String textureName;

    public BerryBushBlock(int blockID, int berryID, int saplingID, String name)
    {
        super(blockID);
        this.textureName = name;
        this.berryID = berryID;
        this.saplingID = saplingID;

        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, 4));
        par3List.add(new ItemStack(par1, 1, 5));
    }

    @Override
    protected int getBerryID()
    {
        return berryID;
    }

    @Override
    protected int getSaplingID()
    {
        return saplingID;
    }

    private Icon[] bushIcon = new Icon[6];
    private Icon[] snowOverlay = new Icon[6];

    @Override
    public void registerIcons( IconRegister register )
    {
        for (int i = 0; i < bushIcon.length; i++) {
            bushIcon[i] = register.registerIcon( "bush_" + textureName + "_" + i );
        }

        for (int i = 0; i < snowOverlay.length; i++)
        {
            snowOverlay[i] = register.registerIcon("bush_snow_" + i);
        }
    }

    public Icon getIcon(int side, int meta)
    {
        return bushIcon[meta];
    }

    //----------- Client Side Functionality -----------//


    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k )
    {
        int meta = renderer.blockAccess.getBlockMetadata(i, j, k);

        renderer.setRenderBounds( getBlockBoundsFromPoolBasedOnState(
                renderer.blockAccess, i, j, k ) );

        SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, bushIcon[meta], false);

        BTWBlocks.weeds.renderWeeds( this, renderer, i, j, k );

        return true;
    }

    @Override
    public void renderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {

        int meta = renderer.blockAccess.getBlockMetadata(i, j, k);

        if (renderer.blockAccess.getBlockId(i, j + 1, k) == Block.snow.blockID)
        {
            renderer.setRenderBounds( getBlockBoundsFromPoolBasedOnState(
                    renderer.blockAccess, i, j, k ) );

            SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, snowOverlay[meta], false);
        }
    }

    @Override
    public boolean doesItemRenderAsBlock(int iItemDamage) {
        return false;
    }

}
