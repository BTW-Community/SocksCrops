package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import net.minecraft.src.*;

public class SunflowerCropBlock extends SunflowerBaseBlock {
    public SunflowerCropBlock(int iBlockID, String name) {
        super(iBlockID, name);
    }

    @Override
    protected int getMaxGrowthStage() {
        return 3;
    }

    @Override
    protected int getCropItemID() {
        return 0;
    }

    @Override
    protected int getSeedItemID() {
        return 0;
    }

    @Override
    protected boolean isTopBlock() {

        return false;
    }

    private Icon stem;

    @Override
    public void registerIcons(IconRegister register)
    {
        super.registerIcons(register);

        stem = register.registerIcon(name + "_4");
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k)
    {
        int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
        renderStem(renderer, i, j, k, meta);

        return true;
    }

    protected void renderStem(RenderBlocks renderer, int i, int j, int k, int meta) {

        if (renderer.blockAccess.getBlockId(i, j + 1, k) == SCBlocks.sunflowerTopCrop.blockID)
        {
            SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, stem, false);
        }
        else SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, crop[meta], false);
    }
}
