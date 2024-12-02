package btw.community.sockthing.sockscrops.block.blocks;

import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;

public class CloverBlock extends MultiFlowerBlock {

    private int color;
    private String name;


    public CloverBlock(int blockID, int color, String name) {
        super(blockID, name);

        this.color = color;
        this.name = name;
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        return (world.getBlockId(i,j+1,k) == SCBlocks.mossCarpet.blockID && Block.blocksList[world.getBlockId(i,j-1,k)].hasLargeCenterHardPointToFacing(world, i, j - 1,k,1) )
                || super.canBlockStay( world, i, j, k );
    }

    @Environment(EnvType.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }

    private Icon[] item = new Icon[4];
    private Icon[] top_flower = new Icon[4];
    private Icon[] top = new Icon[4];
    private static Icon[] stalk = new Icon[4];
    private static Icon[] stalkFlower_purple = new Icon[2];
    private static Icon[] stalkFlower_white = new Icon[2];
    private static Icon[] stalkFlower_red = new Icon[2];

    @Override
    public Icon getIcon(int side, int meta) {
        int dir = getDirection(meta);
        return this.blockIcon = item[color];
    }

    @Override
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < top_flower.length; i++) {
            if (this.color == 1)
            {
                top_flower[i] = register.registerIcon("clover_" + "purple" + "_flower_top_" + i);
            }
            else if (this.color == 2)
            {
                top_flower[i] = register.registerIcon("clover_" + "white" + "_flower_top_" + i);
            }
            else if (this.color == 3)
            {
                top_flower[i] = register.registerIcon("clover_" + "red" + "_flower_top_" + i);
            }
            top[i] = register.registerIcon("clover_top_" + i);
        }

        for (int i = 0; i < top_flower.length; i++) {
            stalk[i] = register.registerIcon("clover_stalk_" + i);
        }

        if (this.color == 1)
        {
            stalkFlower_purple[0] = register.registerIcon("clover_stalk_" + "purple" + "_flower_1");
            stalkFlower_purple[1] = register.registerIcon("clover_stalk_" + "purple" + "_flower_3");
        }
        else if (this.color == 2)
        {
            stalkFlower_white[0] = register.registerIcon("clover_stalk_" + "white" + "_flower_1");
            stalkFlower_white[1] = register.registerIcon("clover_stalk_" + "white" + "_flower_3");
        }
        else if (this.color == 3)
        {
            stalkFlower_red[0] = register.registerIcon("clover_stalk_" + "red" + "_flower_1");
            stalkFlower_red[1] = register.registerIcon("clover_stalk_" + "red" + "_flower_3");
        }

        for (int i = 0; i < item.length; i++) {
            item[i] = register.registerIcon(getItemIconName());
        }
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int x, int y, int z) {
        int meta = renderer.blockAccess.getBlockMetadata(x,y,z);
        int stage = MultiFlowerBlock.getStage(meta);
        int dir = MultiFlowerBlock.getDirection(meta);

        grassPass = true;

        SCRenderUtils.renderMultiFlowerBlock(renderer, this, x, y, z, top);

        int[] numberOfStalks = {
                3, 2, 3, 2
        };
        double[][][] shifts = {
                {
                        {4/32D, 4/32D}, {12/32D, 6/32D}, {3/32D, 11/32D}, // (xShift, zShift) for stage >= 0
                },
                {
                        {4/32D, -10/32D}, {12/32D, -4/32D},    // (xShift, zShift) for stage >= 1
                },
                {
                        {-4/32D, -8/32D}, {-12/32D, -10/32D}, {-12/32D, -2/32D}, // (xShift, zShift) for stage >= 2
                },
                {
                        {-4/32D, 4/32D}, {-12/32D, 10/32D}, // (xShift, zShift) for stage >= 3
                }
        };
        SCRenderUtils.renderStalks(renderer, this, x, y, z,
                stage, dir,
                numberOfStalks, shifts,
                stalk);
        grassPass = false;

        if (this.color > 0) {

            Icon[] stalkFlower = new Icon[2];

            if (this.color == 1) stalkFlower = stalkFlower_purple;
            if (this.color == 2) stalkFlower = stalkFlower_white;
            if (this.color == 3) stalkFlower = stalkFlower_red;

            SCRenderUtils.renderStalkFlowers(renderer, this, x, y, z,
                    stage, dir,
                    numberOfStalks, shifts,
                    stalkFlower);
        }

        return true;
    }

    @Override
    public void renderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean bFirstPassResult) {

        if (this.color > 0) SCRenderUtils.renderMultiFlowerBlock(renderer, this, x, y, z, top_flower);

    }

    @Environment(EnvType.CLIENT)
    public String getItemIconName()
    {
        if (color == 1) return "clover_purple";
        if (color == 2) return "clover_white";
        if (color == 3) return "clover_red";
        else return "clover";
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
        renderBlocks.setOverrideBlockTexture(item[this.color]);
        renderBlocks.renderBlockAsItemVanilla( this, iItemDamage, fBrightness );
        renderBlocks.clearOverrideBlockTexture();
    }
}
