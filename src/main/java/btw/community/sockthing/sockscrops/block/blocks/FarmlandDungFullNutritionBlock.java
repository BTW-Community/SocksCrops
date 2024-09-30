package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.RenderBlocks;

public class FarmlandDungFullNutritionBlock extends FarmlandFullNutritionBlock {
    public FarmlandDungFullNutritionBlock(int blockID, String name) {
        super(blockID, name);
    }

    @Override
    protected boolean isDunged(IBlockAccess blockAccess, int x, int y, int z) {
        return true;
    }

    @Override
    protected int getFarmlandOnFullPlantGrowthWhenDunged() {
        return SCBlocks.farmlandFullNutrition.blockID;
    }

    //----------- Client Side Functionality -----------//

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (secondPass) {
            return side == 1;
        } else return super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }

    @Environment(EnvType.CLIENT)
    protected Icon dungOverlayDry;
    @Environment(EnvType.CLIENT)
    protected Icon dungOverlayWet;
    @Environment(EnvType.CLIENT)
    private boolean secondPass;

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);

        dungOverlayDry = register.registerIcon("farmland_dung_overlay_dry");
        dungOverlayWet = register.registerIcon("farmland_dung_overlay_wet");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (secondPass) {
            return getBlockTextureSecondPass(blockAccess, x, y, z, side);
        } else return super.getBlockTexture(blockAccess, x, y, z, side);
    }

    @Environment(EnvType.CLIENT)
    private Icon getBlockTextureSecondPass(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (side == 1) {
            if (isHydrated(blockAccess.getBlockMetadata(x, y, z))) {
                return dungOverlayWet;
            } else return dungOverlayDry;
        } else return null;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void renderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean firstPassResult) {
        secondPass = true;

        renderer.renderStandardBlock(this, x, y, z);

        secondPass = false;
    }
}
