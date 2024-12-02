package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.TallGrassBlock;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import btw.item.items.ShearsItem;
import com.prupe.mcpatcher.cc.ColorizeBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;

public class ShortGrassBlock extends TallGrassBlock {

    private static final String[] grassTypes = new String[] {"deadbush", "short_grass", "fern"};
    private static final double HALF_WIDTH = 0.4F;

    public ShortGrassBlock(int iBlockID, String name) {
        super(iBlockID);

        initBlockBounds(0.5D - HALF_WIDTH, 0D, 0.5D - HALF_WIDTH,
                0.5D + HALF_WIDTH, 0.5D, 0.5D + HALF_WIDTH);

        setUnlocalizedName(name);
    }

    public void getSubBlocks(int blockID, CreativeTabs tabs, List list)
    {
        list.add(new ItemStack(blockID, 1, 1));

        /*
        for (int var4 = 1; var4 < 3; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
        */
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        return world.getBlockId(i,j + 1,k) == SCBlocks.mossCarpet.blockID || super.canBlockStay(world, i, j, k);
    }

    @Override
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        if (!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().getItem() instanceof ShearsItem)
        {
            par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
            this.dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(SCBlocks.shortGrass, 1, par6));
        }
        else
        {
            super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getRenderColor(int par1)
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerGrass.getGrassColor(var1, var3);
    }

    @Environment(EnvType.CLIENT)
    private Icon[] iconArray;

    @Override
    @Environment(EnvType.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        if (par2 >= this.iconArray.length)
        {
            par2 = 0;
        }

        return this.iconArray[par2];
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[grassTypes.length];

        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
        {
            this.iconArray[var2] = par1IconRegister.registerIcon(grassTypes[var2]);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        renderer.setRenderBounds(getBlockBoundsFromPoolBasedOnState(
                renderer.blockAccess, i, j, k) );

        SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i,j,k,
                this.iconArray[renderer.blockAccess.getBlockMetadata(i,j,k)],
                true,
                true);
        //renderer.renderCrossedSquares( this, i, j, k );

        BTWBlocks.weeds.renderWeeds(this, renderer, i, j, k);

        return true;
    }
}
